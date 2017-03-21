package mobi.mateam.alarma.di.module;

import dagger.Module;
import dagger.Provides;
import mobi.mateam.alarma.alarm.AlarmProvider;
import mobi.mateam.alarma.bus.EventBus;
import mobi.mateam.alarma.model.repository.AlarmRepository;
import mobi.mateam.alarma.presenter.AlarmListPresenter;
import mobi.mateam.alarma.presenter.MainAlarmPresenter;
import mobi.mateam.alarma.presenter.SetAlarmPresenter;

@Module public class PresenterModule {

  @Provides AlarmListPresenter provideAlarmListPresenter(AlarmRepository alarmRepository, AlarmProvider alarmProvider, EventBus eventBus) {
    return new AlarmListPresenter(alarmRepository, alarmProvider, eventBus);
  }

  @Provides SetAlarmPresenter provideSetAlarmPresenter(AlarmProvider alarmProvider, AlarmRepository alarmRepository, EventBus eventBus) {
    return new SetAlarmPresenter(alarmProvider, alarmRepository, eventBus);
  }

  @Provides MainAlarmPresenter provideSuperAlarmPresenter(EventBus eventBus) {
    return new MainAlarmPresenter(eventBus);
  }
}
