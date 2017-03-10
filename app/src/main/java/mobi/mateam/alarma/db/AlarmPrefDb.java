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

  public AlarmPrefDb(Context context, Gson gson) {
    this.gson = gson;
    this.context = context;
  }

  @Override public Observable<ArrayList<Alarm>> getAllAlarms() {

    String jsonApps = PrefUtils.getStringPreference(context, Keys.ALL_ALARMS);
    ArrayList<Alarm> alarmList = gson.fromJson(jsonApps, new TypeToken<ArrayList<Alarm>>() {
    }.getType());

    alarmList = alarmList != null ? alarmList : new ArrayList<>();
    return Observable.just(alarmList);
  }

  @Override public Observable<Alarm> getAlarmById(String id) {
    return getAllAlarms().map(alarms1 -> {
      Alarm alarmById = null;
      for (Alarm alarm : alarms1) {
        if (alarm.id.equals(id)) {
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

  @Override public Observable<Boolean> persistAlarmsList(ArrayList<Alarm> newAlarms) {
    String value = gson.toJson(newAlarms);
    PrefUtils.setStringPreference(context, Keys.ALL_ALARMS, value);
    return Observable.just(true);
  }

  @Override public void updateAlarm(Alarm alarm) {
    getAllAlarms().subscribe(alarms -> {
      int position = -1;
      for (int i = 0; i < alarms.size(); i++) {
        if (alarms.get(i).id.equals(alarm.id)) {
          position = i;
        }

        if (position >= 0) {
          alarms.set(position, alarm);
        }
        persistAlarmsList(alarms);
      }
    });
  }

  @Override public boolean removeAlarm(Alarm alarm) {
    getAllAlarms().subscribe(alarms1 -> {
      alarms1.remove(alarm);
      persistAlarmsList(alarms1);
    });
    return false;
  }

  @Override public boolean removeAlarmById(String id) {
    getAllAlarms().subscribe(alarms -> {
      for (Alarm alarm : new ArrayList<>(alarms)) {
        if (alarm.id.equals(id)) {
          alarms.remove(alarm);
        }
      }
      persistAlarmsList(alarms);
    });
    return false;
  }

  @Override public boolean removeAllAlarms() {
    persistAlarmsList(new ArrayList<>());
    return false;
  }

  private class Keys {
    public static final String ALL_ALARMS = "PREF_KEY_ALL_ALARMS";
  }


}

