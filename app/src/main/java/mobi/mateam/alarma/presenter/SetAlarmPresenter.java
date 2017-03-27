package mobi.mateam.alarma.presenter;

import android.app.Activity;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import java.util.ArrayList;
import java.util.List;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.alarm.AlarmProvider;
import mobi.mateam.alarma.alarm.AlarmUtils;
import mobi.mateam.alarma.alarm.model.Alarm;
import mobi.mateam.alarma.alarm.model.PlaceData;
import mobi.mateam.alarma.bus.Event;
import mobi.mateam.alarma.bus.EventBus;
import mobi.mateam.alarma.bus.ParamListChangedEvent;
import mobi.mateam.alarma.bus.SetAlarmEvent;
import mobi.mateam.alarma.bus.SportPickedEvent;
import mobi.mateam.alarma.bus.WindParamChangedEvent;
import mobi.mateam.alarma.model.repository.AlarmRepository;
import mobi.mateam.alarma.view.fragment.TimePickerFragment;
import mobi.mateam.alarma.view.interfaces.SetAlarmView;
import mobi.mateam.alarma.weather.model.ParameterType;
import mobi.mateam.alarma.weather.model.params.WeatherParamRange;
import mobi.mateam.alarma.weekdays.WeekdaysDataItem;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

public class SetAlarmPresenter extends BasePresenter<SetAlarmView> {
  public static final int TONE_PICKER_REQUEST = 222;
  public static int PLACE_PICKER_REQUEST = 111;
  private final EventBus eventBus;
  private Alarm alarm;
  private AlarmProvider alarmProvider;
  private AlarmRepository alarmRepository;
  private boolean isNewAlarm;
  private Subscriber<Event> subscriber;

  public SetAlarmPresenter(AlarmProvider alarmProvider, AlarmRepository alarmRepository, EventBus eventBus) {
    this.alarmProvider = alarmProvider;
    this.alarmRepository = alarmRepository;
    this.eventBus = eventBus;
  }

  @Override public void attachView(SetAlarmView setAlarmView) {
    super.attachView(setAlarmView);
    subscribeToEventBas();
  }

  private void subscribeToEventBas() {
    subscriber = new Subscriber<Event>() {
      @Override public void onCompleted() {

      }

      @Override public void onError(Throwable e) {
        Timber.e(e);
      }

      @Override public void onNext(Event event) {
        switch (event.id) {
          case Event.SETTINGS_CHANGED:
            onSettingsChanged();
            break;
          case Event.WIND_PARAM_CHANGED:
            onWindParamChanged((WindParamChangedEvent) event);
            break;
          case Event.PARAM_LIST_CHANGED:
            onParamListChangedEvent((ParamListChangedEvent) event);
        }
      }
    };
    eventBus.observeEvents(Event.class).subscribe(subscriber);
  }

  private void onParamListChangedEvent(ParamListChangedEvent event) {
    alarm.conditions.correctParamList(event.checkedParameters);
    getView().showWeatherParameters(alarm.conditions.getParamsList());
  }

  private void onWindParamChanged(WindParamChangedEvent paramChanged) {
    if (alarm != null) {
      alarm.conditions.addParam(paramChanged.windDirectionRange);
      alarm.conditions.addParam(paramChanged.windSpeedRange);
      onWeatherParamChange(alarm.conditions.getParamsList());
      getView().showWeatherParameters(alarm.conditions.getParamsList());
    }
  }

  private void onSettingsChanged() {
    if (alarm != null || alarm.conditions != null) getView().showWeatherParameters(alarm.conditions.getParamsList());
  }

  public void setAlarm(Bundle arguments) {
    // in case if presenter already exist (OnOrientationChange)
    if (alarm != null) {
      updateView();
      return;
    }
    // Edit Alarm case
    if (arguments != null) {
      String alarmId = arguments.getString(SetAlarmView.ALARM_ID_KEY);
      if (!TextUtils.isEmpty(alarmId)) {
        isNewAlarm = false;
        alarmRepository.getAlarmById(alarmId).subscribeOn(AndroidSchedulers.mainThread()).subscribe(alarm1 -> {
          alarm = alarm1;
          eventBus.post(new SportPickedEvent(alarm.sportType));
          updateView();
        });
      }
      // New Alarm creation
    } else {
      alarm = new Alarm(getView().getSportType());
      isNewAlarm = true;
      updateView();
    }
  }

  public void setTime() {
    TimePickerFragment newFragment = new TimePickerFragment();
    Activity activity = (Activity) getView().getActivityContext();
    newFragment.show(activity.getFragmentManager(), "TAG");
    newFragment.setOnTimeSetListener((view, hourOfDay, minute) -> {
      alarm.hour = hourOfDay;
      alarm.minutes = minute;
      getView().showTime(AlarmUtils.getTimeStrFromAlarm(alarm));
    });
  }

  public void setLabel(String s) {
    if (alarm != null) {
      alarm.label = s;
    }
  }

  public void startRingtoneDialog() {
    final Uri currentTone = RingtoneManager.getActualDefaultRingtoneUri(getView().getActivityContext(), RingtoneManager.TYPE_ALARM);
    Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, currentTone);
    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
    ((Activity) getView().getActivityContext()).startActivityForResult(intent, TONE_PICKER_REQUEST);
  }

  public void startLocationDialog() {

    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
    try {
      Activity activity = (Activity) getView().getActivityContext();
      activity.startActivityForResult(builder.build(activity), PLACE_PICKER_REQUEST);
    } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
      Timber.e(e);
    }
  }

  public void onRingtonePickerResult(Uri uri) {
    if (uri != null) {
      alarm.mRingtone = uri;
      getView().showRingtone(AlarmUtils.getFileName(uri, getView().getActivityContext()));
    }
  }

  public void onPlacePickerResult(Place place) {
    String toastMsg = String.format(getView().getAppContext().getString(R.string.set_fragment_place), place.getName());
    alarm.place = new PlaceData(place);
    getView().showLocation(toastMsg);
  }

  public void updateView() {
    showWeekDays();

    getView().showTime(AlarmUtils.getTimeStrFromAlarm(alarm));

    getView().setVibrateCheck(alarm.vibrate);

    showLabel();

    showLocation();

    getView().showRingtone(AlarmUtils.getFileName(alarm.mRingtone, getView().getActivityContext()));

    if (alarm.conditions != null) {
      getView().showWeatherParameters(alarm.conditions.getParamsList());
    }
  }

  private void showLocation() {
    String stringLocation = this.alarm.getStringLocation();
    if (!TextUtils.isEmpty(stringLocation)) {
      getView().showLocation(stringLocation);
    }
  }

  private void showWeekDays() {
    if (this.alarm.weekdays != null) {
      int[] res = AlarmUtils.getRepeatDaysIndexArray(this.alarm);
      getView().showWeekDays(res);
    }
  }

  private void showLabel() {
    String label;
    if (alarm.sportType != null && TextUtils.isEmpty(this.alarm.label)) {
      label = this.alarm.sportType.getText();
    } else {
      label = this.alarm.label;
    }
    getView().showLabel(label);
  }

  public void onSaveAlarm() {
    if (isNewAlarm) {
      alarm.activated = true;
      alarm.id = alarmRepository.getNewAlarmId();
      alarmRepository.saveAlarm(alarm).subscribe(s -> eventBus.post(new SetAlarmEvent(alarm)));
    } else {
      alarmRepository.updateAlarm(alarm).subscribe(s -> eventBus.post(new SetAlarmEvent(alarm)));
    }
    alarmProvider.setNextAlarm(alarm);
  }

  public void onWeekDayClick(WeekdaysDataItem weekdaysDataItem) {
    if (alarm.weekdays == null) {
      alarm.weekdays = new int[] { 0, 0, 0, 0, 0, 0, 0 };
    }
    alarm.weekdays[weekdaysDataItem.getCalendarDayId() - 1] = weekdaysDataItem.isSelected() ? 1 : 0;

    boolean isRepeatDaysExist = false;
    for (int i = 0; i < alarm.weekdays.length; i++) {
      if (alarm.weekdays[i] > 0) {
        isRepeatDaysExist = true;
      }
    }

    if (!isRepeatDaysExist) {
      onRepeatUncheck();
      getView().uncheckRepeat();
    }
  }

  public void onRepeatUncheck() {
    alarm.weekdays = null;
  }

  @Override public void detachView() {
    super.detachView();
    subscriber.unsubscribe();
  }

  public void onVibrateChange(boolean isChecked) {
    alarm.vibrate = isChecked;
  }

  public void onWeatherParamChange(List<WeatherParamRange> weatherParameters) {
    alarm.conditions.setParamList(weatherParameters);
  }

  public void onEditParamListClick() {
    ArrayList<ParameterType> parameterTypes = alarm.conditions.getParameterTypesList();
    getView().showEditParamListDialog(parameterTypes);
  }
}

