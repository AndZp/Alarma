package mobi.mateam.alarma.model.repository;

import java.util.ArrayList;
import mobi.mateam.alarma.alarm.model.Alarm;
import mobi.mateam.alarma.db.AlarmDbHelper;
import rx.Observable;

public class AlarmRepository {

  private final AlarmDbHelper alarmDb;

  public AlarmRepository(AlarmDbHelper alarmDb) {
    this.alarmDb = alarmDb;
    //alarmDb.persistAlarmsList(getTestAlarms());
  }

  public static ArrayList<Alarm> getTestAlarms() {
    Alarm alarm1 = new Alarm();
    alarm1.minutes = 05;
    alarm1.hour = 12;

    alarm1.enabled = true;
    alarm1.label = "Test1";
    alarm1.id = "777";
    alarm1.vibrate = true;


    Alarm alarm2 = new Alarm();
    alarm2.minutes = 15;
    alarm2.hour = 7;
    alarm2.enabled = false;
    alarm2.label = "Test2";
    alarm2.id = "idasd";
    alarm2.vibrate = false;
    ArrayList<Alarm> alarms = new ArrayList<>();
    alarms.add(alarm1);
    alarms.add(alarm2);

    return alarms;
  }

  public void saveAlarm(Alarm alarm) {
    alarmDb.persistNewAlarm(alarm);
  }

  public Observable<ArrayList<Alarm>> getAlarmList() {
    return alarmDb.getAllAlarms();
  }

  public Observable<Alarm> getAlarmById(String id) {
    return alarmDb.getAlarmById(id);
  }

  public void updateAlarm(Alarm alarm) {
    alarmDb.updateAlarm(alarm);
  }
}
