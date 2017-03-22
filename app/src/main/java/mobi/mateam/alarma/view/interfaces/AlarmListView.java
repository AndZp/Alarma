package mobi.mateam.alarma.view.interfaces;

import java.util.List;
import mobi.mateam.alarma.alarm.model.Alarm;

public interface AlarmListView extends MvpView {

  void showAlarmList(List<Alarm> alarms);

  void showEmptyList();
}
