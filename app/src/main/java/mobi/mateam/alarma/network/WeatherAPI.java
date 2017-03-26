package mobi.mateam.alarma.network;

import mobi.mateam.alarma.weather.model.WeatherData;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface WeatherAPI {
  String WEATHER_BASE_URL = "http://api.openweathermap.org";
  String WEATHER_API_ID = "a1c54c8fb21f04c211d40a350a5fe704";
  String QUERY_PARAM_APPID = "APPID";
  String QUERY_PARAM_UNITS = "units";
  String METRIC_UNITS = "metric";

  @GET("/data/2.5/weather") Observable<WeatherData> getWeatherByCity(@Query("q") String city);

  @GET("/data/2.5/weather") Observable<WeatherData> getCurrentWeatherByLocation(@Query("lat") String lat, @Query("lon") String lon);


}
