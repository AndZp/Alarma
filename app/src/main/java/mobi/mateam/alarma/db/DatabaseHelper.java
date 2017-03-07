package mobi.mateam.alarma.db;

import mobi.mateam.alarma.model.pojo.weather.WeatherData;

public interface DatabaseHelper {
  void saveWeatherData(WeatherData weatherData);
}
