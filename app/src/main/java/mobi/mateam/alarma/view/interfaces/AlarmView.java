package mobi.mateam.alarma.view.interfaces;

import mobi.mateam.alarma.weather.model.AlarmWeatherConditions;

public interface AlarmView extends MvpView {

  void showWeather(AlarmWeatherConditions weatherParameters);

  void showTime(String time);

  void showAlarmLabel(String label);

  void runAnumation();
}
