package mobi.mateam.alarma.alarm;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.util.UUID;
import mobi.mateam.alarma.alarm.model.Alarm;
import mobi.mateam.alarma.utils.AlarmManagerCompat;

public class AlarmProvider implements IAlarmManager {
  public static final String KEY_ALARM_ID = "alarma.key.alarm.id";

  private final AlarmManagerCompat alarmManagerCompat;
  private Context context;

  public AlarmProvider(Context context) {
    this.context = context;
    alarmManagerCompat = AlarmManagerCompat.from(context);
  }

  @Override public void setNextAlarm(Alarm alarm) {
    PendingIntent pendingIntent = getStartAlarmIntent(alarm.id);
    alarmManagerCompat.setExactAndAllowWhileIdle(android.app.AlarmManager.RTC_WAKEUP, AlarmUtils.getNextAlarmFire(alarm), pendingIntent);
  }

  @Override public void cancelAlarm(Alarm alarm) {
    PendingIntent pendingIntent = getCancelAlarmIntent(alarm.id);
    alarmManagerCompat.cancel(pendingIntent);
    pendingIntent.cancel();
  }

  private PendingIntent getCancelAlarmIntent(String alarmId) {
    Intent serviceIntent = new Intent(context, AlarmService.class);
    serviceIntent.putExtra(KEY_ALARM_ID, alarmId);
    return PendingIntent.getService(context, 0, serviceIntent, PendingIntent.FLAG_NO_CREATE);
  }

  @Override public void editAlarm(Alarm alarm) {
    cancelAlarm(alarm);
    setNextAlarm(alarm);
  }

  @Override public Alarm getNextSetAlarm() {
    return null;
  }

  @Override public String getNewAlarmId() {
    return UUID.randomUUID().toString();
  }

  private PendingIntent getStartAlarmIntent(String alarmId) {
    Intent serviceIntent = new Intent(context, AlarmService.class);
    serviceIntent.putExtra(KEY_ALARM_ID, alarmId);
    return PendingIntent.getService(context, 0, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
  }
}
