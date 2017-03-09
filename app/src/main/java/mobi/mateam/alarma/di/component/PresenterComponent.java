package mobi.mateam.alarma.di.component;

import mobi.mateam.alarma.presenter.SetAlarmPresenter;

/*@Component(dependencies = AppComponent.class, modules = { TestModule.class })*/ public interface PresenterComponent {

  SetAlarmPresenter getSetAlarmPresenter();
}
