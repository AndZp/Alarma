package mobi.mateam.alarma.view.interfaces;

import java.util.List;
import mobi.mateam.alarma.model.pojo.alarm.Alarm;
import mobi.mateam.alarma.model.pojo.weather.WeatherParameter;

public interface SetAlarmView extends MvpView {
  void showTime(String time);

  void showLocation(String location);

  void showRingtone(String ringtone);

  void showLabel(String label);

  void showWeatherParameters(List<WeatherParameter> parameters);

  Alarm getAlarm();
}
