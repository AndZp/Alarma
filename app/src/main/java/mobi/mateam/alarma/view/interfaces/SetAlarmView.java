package mobi.mateam.alarma.view.interfaces;

import java.util.ArrayList;
import java.util.List;
import mobi.mateam.alarma.weather.model.ParameterType;
import mobi.mateam.alarma.weather.model.params.WeatherParamRange;
import mobi.mateam.alarma.weather.model.sports.SportTypes;

public interface SetAlarmView extends MvpView {
  String ALARM_ID_KEY = "alarm_id_key";

  void showTime(String time);

  void showLocation(String location);

  void showRingtone(String ringtone);

  void showLabel(String label);

  void showWeatherParameters(List<WeatherParamRange> conditions);

  void showWeekDays(int[] weekdays);

  void uncheckRepeat();

  void setVibrateCheck(boolean vibrate);

  SportTypes getSportType();

  void showEditParamListDialog(ArrayList<ParameterType> values);
}
