package mobi.mateam.alarma.alarm;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import mobi.mateam.alarma.model.pojo.alarm.Alarm;
import mobi.mateam.alarma.utils.AlarmManagerCompat;

public class AlarmManager implements IAlarmManager {
  public static final String KEY_ALARM_ID = "alarma.key.alarm.id";

  private final AlarmManagerCompat alarmManagerCompat;
  private Context context;

  public AlarmManager(Context context) {
    this.context = context;
    alarmManagerCompat = AlarmManagerCompat.from(context);
  }

  @Override public void setNewAlarm(Alarm alarm) {
    PendingIntent pendingIntent = getStartAlarmIntent(alarm.longID);
    alarmManagerCompat.setExactAndAllowWhileIdle(android.app.AlarmManager.RTC_WAKEUP, AlarmUtils.getNextAlarmFire(alarm), pendingIntent);
  }

  @Override public void cancelAlarm(Alarm alarm) {
    PendingIntent pendingIntent = getCancelAlarmIntent(alarm.longID);
    alarmManagerCompat.cancel(pendingIntent);
    pendingIntent.cancel();
  }

  private PendingIntent getCancelAlarmIntent(int alarmId) {
    Intent serviceIntent = new Intent(context, AlarmService.class);
    serviceIntent.putExtra(KEY_ALARM_ID, alarmId);
    return PendingIntent.getService(context, alarmId, serviceIntent, PendingIntent.FLAG_NO_CREATE);
  }

  @Override public void editAlarm(Alarm alarm) {
    cancelAlarm(alarm);
    setNewAlarm(alarm);
  }

  @Override public Alarm getNextSetAlarm() {
    return null;
  }

  private PendingIntent getStartAlarmIntent(int alarmId) {
    Intent serviceIntent = new Intent(context, AlarmService.class);
    serviceIntent.putExtra(KEY_ALARM_ID, alarmId);
    return PendingIntent.getService(context, alarmId, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
  }
}
