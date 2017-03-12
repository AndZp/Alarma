package mobi.mateam.alarma.alarm;

import mobi.mateam.alarma.alarm.model.Alarm;

public interface IAlarmManager {
  void setNextAlarm(Alarm alarm);

  void cancelAlarm(Alarm alarm);

  void updateAlarm(Alarm alarm);

  Alarm getNextSetAlarm();

  String getNewAlarmId();
}
