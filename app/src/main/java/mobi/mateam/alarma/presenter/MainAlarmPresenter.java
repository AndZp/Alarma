package mobi.mateam.alarma.presenter;

import android.content.Context;
import mobi.mateam.alarma.model.pojo.weather.WeatherData;
import mobi.mateam.alarma.network.NetworkError;
import mobi.mateam.alarma.network.WeatherService;
import mobi.mateam.alarma.view.interfaces.MainAlarmView;

public class MainAlarmPresenter extends BasePresenter<MainAlarmView> {
  private final Context context;
  private WeatherService weatherService;

  public MainAlarmPresenter(Context context, WeatherService weatherService) {
    this.weatherService = weatherService;
    this.context = context;
  }

  @Override public void attachView(MainAlarmView mainAlarmView) {
    super.attachView(mainAlarmView);
    weatherService.getWeatherByCity("Tel-Aviv", new WeatherService.WeatherCallback() {
      @Override public void onSuccess(WeatherData weatherData) {

      }

      @Override public void onError(NetworkError networkError) {

      }
    });
  }
}
