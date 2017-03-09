package mobi.mateam.alarma.di.component;

import android.content.Context;
import dagger.Component;
import javax.inject.Singleton;
import mobi.mateam.alarma.alarm.AlarmService;
import mobi.mateam.alarma.di.module.AlarmModule;
import mobi.mateam.alarma.di.module.AppModule;
import mobi.mateam.alarma.di.module.NetModule;
import mobi.mateam.alarma.di.module.PresenterModule;
import mobi.mateam.alarma.presenter.AlarmListPresenter;
import mobi.mateam.alarma.presenter.MainAlarmPresenter;
import mobi.mateam.alarma.presenter.SetAlarmPresenter;
import mobi.mateam.alarma.view.activity.BaseActivity;

@Singleton @Component(modules = { AppModule.class, AlarmModule.class, NetModule.class, PresenterModule.class }) public interface AppComponent {

  void inject(BaseActivity mainActivity);

  Context context();

  MainAlarmPresenter getMainAlarmPresenter();

  AlarmListPresenter getAlarmListPresenter();

  void inject(AlarmService alarmService);

  SetAlarmPresenter getSetAlarmPresenter();
}

