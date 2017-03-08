package mobi.mateam.alarma.db;

import mobi.mateam.alarma.weather.model.WeatherData;

public interface DatabaseHelper {
  void saveWeatherData(WeatherData weatherData);
}
