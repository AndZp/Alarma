package mobi.mateam.alarma.db;

import mobi.mateam.alarma.weather.model.WeatherData;

public class DbHelper implements DatabaseHelper {
  String tag;

  public DbHelper(String s) {
    tag = s;
  }

  @Override public void saveWeatherData(WeatherData weatherData) {

  }
}
