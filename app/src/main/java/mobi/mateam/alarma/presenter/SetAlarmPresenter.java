package mobi.mateam.alarma.presenter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import java.util.ArrayList;
import java.util.List;
import mobi.mateam.alarma.alarm.AlarmProvider;
import mobi.mateam.alarma.alarm.model.Alarm;
import mobi.mateam.alarma.alarm.model.PlaceData;
import mobi.mateam.alarma.model.repository.AlarmRepository;
import mobi.mateam.alarma.view.fragment.TimePickerFragment;
import mobi.mateam.alarma.view.interfaces.SetAlarmView;
import mobi.mateam.alarma.weather.model.AlarmWeatherConditions;
import mobi.mateam.alarma.weather.model.params.WeatherParamRange;
import mobi.mateam.alarma.weather.model.params.implementation.ranges.TemperatureRange;
import mobi.mateam.alarma.weather.model.params.implementation.ranges.WindPowerRange;
import mobi.mateam.alarma.weather.model.sports.SportTypes;
import mobi.mateam.alarma.weekdays.WeekdaysDataItem;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SetAlarmPresenter extends BasePresenter<SetAlarmView> {
  public static final int TONE_PICKER_REQUEST = 222;
  public static int PLACE_PICKER_REQUEST = 111;
  private Alarm alarm;
  private AlarmProvider alarmProvider;
  private AlarmRepository alarmRepository;
  private boolean isNewAlarm;

  public SetAlarmPresenter(AlarmProvider alarmProvider, AlarmRepository alarmRepository) {
    this.alarmProvider = alarmProvider;
    this.alarmRepository = alarmRepository;
  }

  private static int[] getRepeatDaysIndexArray(Alarm alarm) {
    ArrayList<Integer> result = new ArrayList<>();
    for (int i = 0; i < alarm.weekdays.length; i++) {
      if (alarm.weekdays[i] == 1) {
        result.add(i);
      }
    }

    int[] res = new int[result.size()];
    for (int i = 0; i < result.size(); i++) {
      res[i] = result.get(i);
    }
    return res;
  }

  @Override public void attachView(SetAlarmView setAlarmView) {
    super.attachView(setAlarmView);
  }

  public void setAlarm(Bundle arguments) {
    if (arguments != null) {
      String alarmId = arguments.getString(SetAlarmView.ALRAM_ID_KEY);
      SportTypes sportTypes = SportTypes.getById(arguments.getInt(SetAlarmView.ALARM_SPORT_TYPE_ID));
      if (!TextUtils.isEmpty(alarmId)) {
        isNewAlarm = false;

        alarmRepository.getAlarmById(alarmId).subscribeOn(AndroidSchedulers.mainThread()).subscribe(alarm1 -> {
          alarm = alarm1;
          updateView(alarm);
        });
      } else if (sportTypes != null) {
        isNewAlarm = true;
        alarm = new Alarm();

        alarm.sportType = sportTypes;

        AlarmWeatherConditions alarmWeatherConditions = new AlarmWeatherConditions();
        alarmWeatherConditions.addParam(getParamList().get(0));
        alarm.conditions = alarmWeatherConditions;

        updateView(alarm);
      }
    }
  }

  public void setTime() {
    TimePickerFragment newFragment = new TimePickerFragment();
    Activity activity = (Activity) getView().getActivityContext();
    newFragment.show(activity.getFragmentManager(), "TAG");
    newFragment.setOnTimeSetListener((view, hourOfDay, minute) -> {
      alarm.hour = hourOfDay;
      alarm.minutes = minute;

      String hour = hourOfDay < 10 ? "0" + hourOfDay : String.valueOf(hourOfDay);
      String valueOf = String.valueOf(minute);
      String minutes = valueOf.length() < 1 ? 0 + valueOf : valueOf;

      getView().showTime(hour + " : " + minutes);
    });
  }

  public void setLabel(String s) {
    alarm.label = s;
  }

  public void setRingtone() {
    final Uri currentTone = RingtoneManager.getActualDefaultRingtoneUri(getView().getActivityContext(), RingtoneManager.TYPE_ALARM);
    Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, currentTone);
    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
    ((Activity) getView().getActivityContext()).startActivityForResult(intent, TONE_PICKER_REQUEST);
  }

  public void setLocation() {

    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
    try {
      Activity activity = (Activity) getView().getActivityContext();
      activity.startActivityForResult(builder.build(activity), PLACE_PICKER_REQUEST);
    } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
      e.printStackTrace();
    }
  }

  public List<WeatherParamRange> getParamList() {

    ArrayList<WeatherParamRange> weatherParameters = new ArrayList<>();
    weatherParameters.add(new TemperatureRange(13, 20));
    weatherParameters.add(new WindPowerRange(20.0, 40.0));

    return weatherParameters;
  }

  public void onRingtonePickerResult(Uri uri) {
    if (uri != null) {
      alarm.mRingtone = uri;
      String ringTonePath = uri.getAuthority();
      getView().showRingtone(getFileName(uri));
    }
  }

  public String getFileName(Uri uri) {
    String result = null;
    if (uri.getScheme().equals("content")) {
      Cursor cursor = getView().getActivityContext().getContentResolver().query(uri, null, null, null, null);
      try {
        if (cursor != null && cursor.moveToFirst()) {
          result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
        }
      } finally {
        cursor.close();
      }
    }
    if (result == null) {
      result = uri.getPath();
      int cut = result.lastIndexOf('/');
      if (cut != -1) {
        result = result.substring(cut + 1);
      }
    }
    return result;
  }

  public void onPlacePickerResult(Place place) {
    String toastMsg = String.format("Place: %s", place.getName());
    alarm.place = new PlaceData(place);
    getView().showLocation(toastMsg);
  }

  private void updateView(Alarm alarm) {
    if (this.alarm.weekdays != null) {
      int[] res = getRepeatDaysIndexArray(this.alarm);
      getView().showWeekDays(res);
    }
    getView().showTime(this.alarm.hour + ":" + this.alarm.minutes);

    String label = "Label";
    if (TextUtils.isEmpty(this.alarm.label)) {
      label = this.alarm.sportType.getText();
    } else {
      label = this.alarm.label;
    }
    getView().showLabel(label);

    String stringLocation = this.alarm.getStringLocation();
    if (!TextUtils.isEmpty(stringLocation)) {
      getView().showLocation(stringLocation);
    }
    getView().showRingtone("Set Ringtone");
    getView().showWeatherParameters(alarm.conditions.getParamsList());
  }

  public void onSaveAlarm() {
    if (isNewAlarm) {
      alarm.activated = true;
      alarm.id = alarmRepository.getNewAlarmId();
      alarmProvider.setNextAlarm(alarm);
      alarmRepository.saveAlarm(alarm)
          .observeOn(AndroidSchedulers.mainThread())
          .subscribeOn(Schedulers.io())
          .subscribe(s -> getView().notifyAlarmSet(alarm));
    } else {
      alarmRepository.updateAlarm(alarm);
      alarmProvider.updateAlarm(alarm);
      getView().notifyAlarmSet(alarm);
    }

  }

  public void onWeekDayClick(WeekdaysDataItem weekdaysDataItem) {
    if (alarm.weekdays == null) {
      alarm.weekdays = new int[] { 0, 0, 0, 0, 0, 0, 0 };
    }
    alarm.weekdays[weekdaysDataItem.getCalendarDayId() - 1] = weekdaysDataItem.isSelected() ? 1 : 0;
  }

  public void onRepeatUncheck() {
    alarm.weekdays = null;
  }

  public void onBackPressed() {
    alarmRepository.removeAlarm(alarm);
  }
}

