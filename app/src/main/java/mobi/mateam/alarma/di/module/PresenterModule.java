package mobi.mateam.alarma.di.module;

import dagger.Module;
import dagger.Provides;
import mobi.mateam.alarma.di.anotation.PerActivity;
import mobi.mateam.alarma.presenter.MainAlarmPresenter;

@Module public class PresenterModule {
  @Provides @PerActivity MainAlarmPresenter provideMainAlarmPresenter() {

    return new MainAlarmPresenter();
  }
}
