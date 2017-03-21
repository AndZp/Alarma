package mobi.mateam.alarma.di.component;

import android.content.Context;
import dagger.Component;
import javax.inject.Singleton;
import mobi.mateam.alarma.alarm.AlarmService;
import mobi.mateam.alarma.alarm.BootCompleteReceiver;
import mobi.mateam.alarma.bus.EventBus;
import mobi.mateam.alarma.di.module.AlarmModule;
import mobi.mateam.alarma.di.module.AppModule;
import mobi.mateam.alarma.di.module.EventBusModule;
import mobi.mateam.alarma.di.module.NetModule;
import mobi.mateam.alarma.di.module.PresenterModule;
import mobi.mateam.alarma.presenter.AlarmListPresenter;
import mobi.mateam.alarma.presenter.MainAlarmPresenter;
import mobi.mateam.alarma.presenter.SetAlarmPresenter;
import mobi.mateam.alarma.view.activity.main.MainAlarmActivity;

@Singleton @Component(modules = { AppModule.class, AlarmModule.class, NetModule.class, PresenterModule.class, EventBusModule.class })
public interface AppComponent {

  Context context();

  AlarmListPresenter getAlarmListPresenter();

  SetAlarmPresenter getSetAlarmPresenter();

  MainAlarmPresenter getSuperAlarmPresenter();

  EventBus getEventBus();

  void inject(AlarmService alarmService);

  void inject(BootCompleteReceiver bootCompleteReceiver);

  void inject(MainAlarmActivity mainAlarmActivity);
}


