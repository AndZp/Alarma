package mobi.mateam.alarma.network;

import mobi.mateam.alarma.weather.model.WeatherData;
import retrofit2.Retrofit;
import retrofit2.http.Query;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class WeatherService implements WeatherAPI {
  private final WeatherAPI weatherAPI;

  public WeatherService(Retrofit retrofit) {
    weatherAPI = retrofit.create(WeatherAPI.class);
  }

  @Override public Observable<WeatherData> getWeatherByCity(@Query("q") String city) {
    return weatherAPI.getWeatherByCity(city).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).onErrorResumeNext(throwable -> {
      Timber.e(throwable);
      return null;
    });
  }

  @Override public Observable<WeatherData> getCurrentWeatherByLocation(@Query("lat") String lat, @Query("lon") String lon) {
    return weatherAPI.getCurrentWeatherByLocation(lat, lon)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread()).onErrorResumeNext(throwable -> {
          Timber.e(throwable);
          return null;
        });
  }
}