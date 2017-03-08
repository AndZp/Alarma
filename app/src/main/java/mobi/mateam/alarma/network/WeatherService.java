package mobi.mateam.alarma.network;

import mobi.mateam.alarma.db.DatabaseHelper;
import mobi.mateam.alarma.weather.model.WeatherData;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WeatherService {
  private final WeatherAPI weatherAPI;
  private DatabaseHelper databaseHelper;

  public WeatherService(Retrofit retrofit, DatabaseHelper databaseHelper) {
    this.databaseHelper = databaseHelper;
    weatherAPI = retrofit.create(WeatherAPI.class);
  }


  public void getWeatherByCity(String city, final WeatherCallback callback) {
    Observable<WeatherData> weatherByCity = weatherAPI.getWeatherByCity(city);

    weatherByCity.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .onErrorResumeNext(throwable -> null)
        .subscribe(new Observer<WeatherData>() {
          @Override public void onCompleted() {

          }

          @Override public void onError(Throwable e) {
            callback.onError(new NetworkError(e));
          }

          @Override public void onNext(WeatherData weatherData) {
            databaseHelper.saveWeatherData(weatherData);
            callback.onSuccess(weatherData);
          }
        });
  }

  public interface WeatherCallback {
    void onSuccess(WeatherData weatherData);

    void onError(NetworkError networkError);
  }
}