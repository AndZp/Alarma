package mobi.mateam.alarma.di.module;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import mobi.mateam.alarma.model.repository.AlarmRepository;
import mobi.mateam.alarma.presenter.AlarmListPresenter;
import mobi.mateam.alarma.presenter.MainAlarmPresenter;

@Module public class PresenterModule {
  @Provides @Singleton MainAlarmPresenter provideMainAlarmPresenter() {
    return new MainAlarmPresenter();
  }

  @Provides @Singleton AlarmListPresenter provideAlarmListPresenter(AlarmRepository alarmRepository) {
    return new AlarmListPresenter(alarmRepository);
  }
}
