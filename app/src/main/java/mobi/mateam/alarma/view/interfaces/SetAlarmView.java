package mobi.mateam.alarma.view.interfaces;

import java.util.List;
import mobi.mateam.alarma.alarm.model.Alarm;
import mobi.mateam.alarma.weather.model.WeatherParameter;

public interface SetAlarmView extends MvpView {
  void showTime(String time);

  void showLocation(String location);

  void showRingtone(String ringtone);

  void showLabel(String label);

  void showWeatherParameters(List<WeatherParameter> parameters);

  Alarm getAlarm();
}
