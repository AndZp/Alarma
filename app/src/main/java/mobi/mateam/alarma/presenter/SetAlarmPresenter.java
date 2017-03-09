package mobi.mateam.alarma.presenter;

import android.app.Activity;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import java.util.ArrayList;
import java.util.List;
import mobi.mateam.alarma.alarm.AlarmManager;
import mobi.mateam.alarma.alarm.model.Alarm;
import mobi.mateam.alarma.alarm.model.Weekdays;
import mobi.mateam.alarma.view.fragment.TimePickerFragment;
import mobi.mateam.alarma.view.interfaces.SetAlarmView;
import mobi.mateam.alarma.weather.model.WeatherParameter;

public class SetAlarmPresenter extends BasePresenter<SetAlarmView> {
  public static final int TONE_PICKER_REQUEST = 222;
  public static int PLACE_PICKER_REQUEST = 111;
  private Alarm alarm;
  private AlarmManager alarmManager;

  public SetAlarmPresenter(AlarmManager alarmManager) {
    this.alarmManager = alarmManager;
  }

  @Override public void attachView(SetAlarmView setAlarmView) {
    super.attachView(setAlarmView);
  }

  public void setTime() {
    TimePickerFragment newFragment = new TimePickerFragment();
    Activity activity = (Activity) getView().getActivityContext();
    newFragment.show(activity.getFragmentManager(), "TAG");
    newFragment.setOnTimeSetListener((view, hourOfDay, minute) -> {
      alarm.hour = hourOfDay;
      alarm.minutes = minute;
      getView().showTime(hourOfDay + " : " + minute);
    });
  }

  public void setLabel(String s) {
    alarm.lable = s;
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

  public List<WeatherParameter> getParamList() {

    ArrayList<WeatherParameter> weatherParameters = new ArrayList<>();
    weatherParameters.add(new WeatherParameter());
    weatherParameters.add(new WeatherParameter());

    return weatherParameters;
  }

  public void onRingtonePickerResult(Uri uri) {
    if (uri != null) {
      alarm.mRingtone = uri;
      String ringTonePath = uri.getAuthority();
      getView().showRingtone(ringTonePath);
    }
  }

  public void onPlacePickerResult(Place place) {
    String toastMsg = String.format("Place: %s", place.getName());
    alarm.place = place;
    getView().showLocation(toastMsg);
  }

  public void setAlarm(Bundle arguments) {
    if (arguments != null) {
      int alarmId = arguments.getInt(SetAlarmView.ALRAM_ID_KEY);
      if (alarmId > 0) {
        alarmManager.getAlarmById(alarmId).subscribe(alarm -> this.alarm = alarm);
      }
    } else {
      alarm = new Alarm();
    }

    getView().showTime(alarm.hour + ":" + alarm.minutes);
    getView().showLabel(alarm.lable);
    getView().showLocation(alarm.stringLocation);
    getView().showRingtone("Set Ringtone");
    getView().showWeatherParameters(getParamList());
  }

  public void onSaveAlarm() {
    alarmManager.setNextAlarm(alarm);
    getView().returnResultAlarm(alarm);
  }

  public void setWeekDay(int day) {
    alarm.daysOfWeek = new Weekdays();
  }
}

