package mobi.mateam.alarma.alarm;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import mobi.mateam.alarma.alarm.model.Alarm;
import mobi.mateam.alarma.utils.AlarmManagerCompat;
import timber.log.Timber;

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
    long nextAlarmFire = AlarmUtils.getNextAlarmFire(alarm);
    Timber.d("alarmId = " + alarm.id + ", Set next alarm at " + nextAlarmFire);
    alarmManagerCompat.setExactAndAllowWhileIdle(android.app.AlarmManager.RTC_WAKEUP, nextAlarmFire, pendingIntent);
  }

  @Override public void cancelAlarm(Alarm alarm) {
    PendingIntent pendingIntent = getStartAlarmIntent(alarm.id);
    alarmManagerCompat.cancel(pendingIntent);
  }

  @Override public void updateAlarm(Alarm alarm) {
    cancelAlarm(alarm);
    setNextAlarm(alarm);
  }

  private PendingIntent getStartAlarmIntent(String alarmId) {
    Intent serviceIntent = new Intent(context, AlarmService.class);
    serviceIntent.putExtra(KEY_ALARM_ID, alarmId);
    return PendingIntent.getService(context, 0, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
  }
}
