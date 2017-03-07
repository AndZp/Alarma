package mobi.mateam.alarma.presenter;

import mobi.mateam.alarma.view.interfaces.MainAlarmView;

public class MainAlarmPresenter extends BasePresenter<MainAlarmView> {

  public MainAlarmPresenter() {
  }

  @Override public void attachView(MainAlarmView mainAlarmView) {
    super.attachView(mainAlarmView);
  }

  public void onCreate() {
    getView().showAlarmsListView();
  }
}
