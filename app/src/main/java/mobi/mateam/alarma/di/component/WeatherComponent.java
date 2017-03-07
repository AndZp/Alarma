package mobi.mateam.alarma.di.component;

import dagger.Component;
import mobi.mateam.alarma.di.anotation.PerActivity;
import mobi.mateam.alarma.di.module.DbModule;
import mobi.mateam.alarma.di.module.NetModule;
import mobi.mateam.alarma.di.module.PresenterModule;
import mobi.mateam.alarma.network.WeatherService;
import mobi.mateam.alarma.presenter.MainAlarmPresenter;

@PerActivity @Component(dependencies = AppComponent.class, modules = { NetModule.class, DbModule.class, PresenterModule.class })
public interface WeatherComponent {

  WeatherService getWeatherService();

  MainAlarmPresenter getMainAlarmPresenter();
}
