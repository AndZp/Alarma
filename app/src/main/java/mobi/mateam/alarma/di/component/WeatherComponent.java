package mobi.mateam.alarma.di.component;

import mobi.mateam.alarma.network.WeatherService;
import mobi.mateam.alarma.presenter.MainAlarmPresenter;

/*@Singleton @Component(dependencies = AppComponent.class, modules = {  }) */public interface WeatherComponent {

  WeatherService getWeatherService();

  MainAlarmPresenter getMainAlarmPresenter();
}
