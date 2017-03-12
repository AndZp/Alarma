package mobi.mateam.alarma.view.interfaces;

import java.util.List;
import mobi.mateam.alarma.alarm.model.Alarm;
import mobi.mateam.alarma.weather.model.params.WeatherParamRange;

public interface SetAlarmView extends MvpView {
  String ALRAM_ID_KEY = "alarm_id_key";
  String ALARM_IS_NEW = "alarm_is_new_key";
  String ALARM_SPORT_TYPE_ID = "alarm_sport_type_id";

  void showTime(String time);

  void showLocation(String location);

  void showRingtone(String ringtone);

  void showLabel(String label);

  void showWeatherParameters(List<WeatherParamRange> conditions);

  void showWeekDays(int[] weekdays);

  void notifyAlarmSet(Alarm alarm);
}
