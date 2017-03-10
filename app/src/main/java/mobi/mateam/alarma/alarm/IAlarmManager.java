package mobi.mateam.alarma.alarm;

import mobi.mateam.alarma.alarm.model.Alarm;

public interface IAlarmManager {
  void setNextAlarm(Alarm alarm);

  void cancelAlarm(Alarm alarm);

  void editAlarm(Alarm alarm);

  Alarm getNextSetAlarm();

  String getNewAlarmId();
}
