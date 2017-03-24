package mobi.mateam.alarma.model.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mobi.mateam.alarma.alarm.model.Alarm;
import mobi.mateam.alarma.db.AlarmDbHelper;
import rx.Observable;

public class AlarmRepository {

  private final AlarmDbHelper alarmDb;

  public AlarmRepository(AlarmDbHelper alarmDb) {
    this.alarmDb = alarmDb;
    //alarmDb.persistAlarmsList(getTestAlarms());
  }



  public Observable<String> saveAlarm(Alarm alarm) {
    return alarmDb.persistNewAlarm(alarm);
  }

  public Observable<ArrayList<Alarm>> getAlarmList() {
    return alarmDb.getAllAlarms();
  }

  public Observable<Alarm> getAlarmById(String id) {
    return alarmDb.getAlarmById(id);
  }

  public Observable<String> updateAlarm(Alarm alarm) {
    return alarmDb.updateAlarm(alarm);
  }

  public String getNewAlarmId() {
    return UUID.randomUUID().toString();
  }

  public void removeAlarm(Alarm alarm) {
    alarmDb.removeAlarm(alarm);
  }

  public Observable<Alarm> getNextFiredAlarm() {
    // TODO: 13/03/17 Implement
    return null;
  }

  public Observable<List<Alarm>> getAllActivatedAlarms() {
    // TODO: 13/03/17 Implement
    return null;
  }
}
