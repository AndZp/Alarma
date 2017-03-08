package mobi.mateam.alarma.alarm;

import mobi.mateam.alarma.model.pojo.alarm.Alarm;

public interface IAlarmManager {
  void setNewAlarm(Alarm alarm);

  void cancelAlarm(Alarm alarm);

  void editAlarm(Alarm alarm);

  Alarm getNextSetAlarm();
}
