package mobi.mateam.alarma.db;

import java.util.ArrayList;
import mobi.mateam.alarma.model.pojo.alarm.Alarm;
import rx.Observable;

public interface AlarmDbHelper {
  Observable<ArrayList<Alarm>> getAllAlarms();

  Observable<Alarm> getAlarmById(int id);

  boolean persistNewAlarm(Alarm alarm);

  boolean persistAlarmsList(ArrayList<Alarm> alarms);

  boolean removeAlarm(Alarm alarm);

  boolean removeAlarmById(long id);

  boolean removeAllAlarms();
}
