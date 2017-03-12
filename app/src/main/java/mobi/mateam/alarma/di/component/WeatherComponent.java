package mobi.mateam.alarma.di.component;

import mobi.mateam.alarma.network.WeatherService;

/*@Singleton @Component(dependencies = AppComponent.class, modules = {  }) */public interface WeatherComponent {

  WeatherService getWeatherService();
}
