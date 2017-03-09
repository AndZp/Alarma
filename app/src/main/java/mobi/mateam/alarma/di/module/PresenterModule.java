package mobi.mateam.alarma.di.module;

import dagger.Module;
import dagger.Provides;
import mobi.mateam.alarma.alarm.AlarmProvider;
import mobi.mateam.alarma.model.repository.AlarmRepository;
import mobi.mateam.alarma.presenter.AlarmListPresenter;
import mobi.mateam.alarma.presenter.MainAlarmPresenter;
import mobi.mateam.alarma.presenter.SetAlarmPresenter;

@Module public class PresenterModule {

  @Provides MainAlarmPresenter provideMainAlarmPresenter() {
    return new MainAlarmPresenter();
  }

  @Provides AlarmListPresenter provideAlarmListPresenter(AlarmRepository alarmRepository) {
    return new AlarmListPresenter(alarmRepository);
  }

  @Provides SetAlarmPresenter provideSetAlarmPresenter(AlarmProvider alarmProvider, AlarmRepository alarmRepository) {
    return new SetAlarmPresenter(alarmProvider, alarmRepository);
  }
}
