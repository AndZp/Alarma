package mobi.mateam.alarma.alarm;

import mobi.mateam.alarma.alarm.model.Alarm;
import rx.Observable;

public interface IAlarmManager {
  void setNextAlarm(Alarm alarm);

  void cancelAlarm(Alarm alarm);

  void editAlarm(Alarm alarm);

  Alarm getNextSetAlarm();

  Observable<Alarm> getAlarmById(int id);
}
