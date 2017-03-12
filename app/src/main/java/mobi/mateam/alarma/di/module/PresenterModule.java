package mobi.mateam.alarma.di.module;

import dagger.Module;
import dagger.Provides;
import mobi.mateam.alarma.alarm.AlarmProvider;
import mobi.mateam.alarma.model.repository.AlarmRepository;
import mobi.mateam.alarma.presenter.AlarmListPresenter;
import mobi.mateam.alarma.presenter.SetAlarmPresenter;
import mobi.mateam.alarma.presenter.SuperAlarmPresenter;

@Module public class PresenterModule {

  @Provides AlarmListPresenter provideAlarmListPresenter(AlarmRepository alarmRepository, AlarmProvider alarmProvider) {
    return new AlarmListPresenter(alarmRepository, alarmProvider);
  }

  @Provides SetAlarmPresenter provideSetAlarmPresenter(AlarmProvider alarmProvider, AlarmRepository alarmRepository) {
    return new SetAlarmPresenter(alarmProvider, alarmRepository);
  }

  @Provides SuperAlarmPresenter provideSuperAlarmPresenter(AlarmProvider alarmProvider, AlarmRepository alarmRepository) {
    return new SuperAlarmPresenter(alarmRepository, alarmProvider);
  }
}
