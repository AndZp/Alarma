package mobi.mateam.alarma.view.interfaces;

import java.util.List;
import mobi.mateam.alarma.model.pojo.alarm.Alarm;

public interface AlarmListView extends MvpView {

  void showAlarmList(List<Alarm> alarms);

  void showEmptyList();

  void showSetAlarmView(Alarm alarm);
}
