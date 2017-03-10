package mobi.mateam.alarma.view.interfaces;

import mobi.mateam.alarma.alarm.model.Alarm;

public interface MainAlarmView extends MvpView {

  void showSetAlarmView(Alarm alarm, boolean b, int sportID);

  void showAlarmsListView();
}
