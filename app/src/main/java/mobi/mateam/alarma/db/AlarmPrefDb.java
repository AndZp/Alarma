package mobi.mateam.alarma.db;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import mobi.mateam.alarma.alarm.model.Alarm;
import mobi.mateam.alarma.utils.PrefUtils;
import rx.Observable;

public class AlarmPrefDb implements AlarmDbHelper {

  private Gson gson;
  private Context context;
  private ArrayList<Alarm> alarmsCash;

  public AlarmPrefDb(Context context, Gson gson) {
    this.gson = gson;
    this.context = context;
  }

  @Override public Observable<ArrayList<Alarm>> getAllAlarms() {
    if (alarmsCash == null) {
      String jsonApps = PrefUtils.getStringPreference(context, Keys.ALL_ALARMS);
      alarmsCash = gson.fromJson(jsonApps, new TypeToken<ArrayList<Alarm>>() {
      }.getType());
    }
    alarmsCash = alarmsCash != null ? alarmsCash : new ArrayList<>();
    return Observable.just(alarmsCash);
  }

  @Override public Observable<Alarm> getAlarmById(int id) {
    return getAllAlarms().map(alarms1 -> {
      Alarm alarmById = null;
      for (Alarm alarm : alarmsCash) {
        if (alarm.longID == id) {
          alarmById = alarm;
        }
      }
      return alarmById;
    });
  }

  @Override public boolean persistNewAlarm(Alarm alarm) {
    getAllAlarms().subscribe(alarms -> {
      alarms.add(alarm);
      persistAlarmsList(alarms);
    });
    return false;
  }

  @Override public boolean persistAlarmsList(ArrayList<Alarm> newAlarms) {
    getAllAlarms().subscribe(alarms -> {
      alarms.addAll(newAlarms);
      alarmsCash = alarms;
      PrefUtils.setStringPreference(context, Keys.ALL_ALARMS, gson.toJson(alarms));
    });
    return false;
  }

  @Override public boolean removeAlarm(Alarm alarm) {
    getAllAlarms().subscribe(alarms1 -> {
      alarms1.remove(alarm);
      persistAlarmsList(alarms1);
    });
    return false;
  }

  @Override public boolean removeAlarmById(long id) {
    getAllAlarms().subscribe(alarms -> {
      for (Alarm alarm : new ArrayList<>(alarms)) {
        if (alarm.longID == id) {
          alarms.remove(alarm);
        }
      }
      persistAlarmsList(alarms);
    });
    return false;
  }

  @Override public boolean removeAllAlarms() {
    alarmsCash.clear();
    persistAlarmsList(alarmsCash);
    return false;
  }

  private class Keys {
    public static final String ALL_ALARMS = "PREF_KEY_ALL_ALARMS";
  }
}
