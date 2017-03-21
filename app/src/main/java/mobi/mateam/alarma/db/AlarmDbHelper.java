package mobi.mateam.alarma.db;

import java.util.ArrayList;
import mobi.mateam.alarma.alarm.model.Alarm;
import rx.Observable;

public interface AlarmDbHelper {
  Observable<ArrayList<Alarm>> getAllAlarms();

  Observable<Alarm> getAlarmById(String id);

  Observable<String> persistNewAlarm(Alarm alarm);

  Observable<Boolean> persistAlarmsList(ArrayList<Alarm> alarms);

  Observable<String> updateAlarm(Alarm alarm);

  boolean removeAlarm(Alarm alarm);

  boolean removeAlarmById(String id);

  boolean removeAllAlarms();
}
