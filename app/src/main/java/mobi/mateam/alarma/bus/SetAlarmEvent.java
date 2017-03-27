package mobi.mateam.alarma.bus;

import mobi.mateam.alarma.alarm.model.Alarm;

public class SetAlarmEvent extends Event {

  public final Alarm alarm;

  public SetAlarmEvent(Alarm alarm) {
    super(SET_ALARM);
    this.alarm = alarm;
  }
}
