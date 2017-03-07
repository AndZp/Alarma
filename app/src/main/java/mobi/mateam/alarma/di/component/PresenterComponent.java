package mobi.mateam.alarma.di.component;

import dagger.Component;
import mobi.mateam.alarma.di.anotation.PerActivity;
import mobi.mateam.alarma.di.module.PresenterModule;
import mobi.mateam.alarma.presenter.MainAlarmPresenter;

@PerActivity @Component(dependencies = AppComponent.class, modules = { PresenterModule.class }) public interface PresenterComponent {

  MainAlarmPresenter getMainAlarmPresenter();
}
