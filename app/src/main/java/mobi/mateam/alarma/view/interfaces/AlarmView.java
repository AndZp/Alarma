package mobi.mateam.alarma.view.interfaces;

import java.util.List;
import mobi.mateam.alarma.weather.model.WeatherParameter;

public interface AlarmView extends MvpView {

  void showWeather(List<WeatherParameter> weatherParameters);

  void showTime(String time);

  void showAlarmLabel(String label);
}
