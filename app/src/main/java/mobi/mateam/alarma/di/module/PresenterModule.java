package mobi.mateam.alarma.di.module;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import mobi.mateam.alarma.di.anotation.PerActivity;
import mobi.mateam.alarma.network.WeatherService;
import mobi.mateam.alarma.presenter.MainAlarmPresenter;

@Module public class PresenterModule {
  @Provides @PerActivity MainAlarmPresenter provideMainAlarmPresenter(Context context, WeatherService weatherService) {

    return new MainAlarmPresenter(context, weatherService);
  }
}
