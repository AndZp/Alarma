package mobi.mateam.alarma.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;

public class AlarmManagerCompat {

  private final AlarmManager mAlarmManager;

  public static AlarmManagerCompat from(Context context) {
    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    return new AlarmManagerCompat(alarmManager);
  }

  private AlarmManagerCompat(AlarmManager alarmManager) {
    this.mAlarmManager = alarmManager;
  }

  /**
   * For API level prior to 19 {@link AlarmManager#set(int, long, PendingIntent)} means to be exact and allowed while idle.
   */
  public void setExactAndAllowWhileIdle(int type, long triggerAtMillis, PendingIntent operation) {
    if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      mAlarmManager.setExactAndAllowWhileIdle(type, triggerAtMillis, operation);
    } else if (VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      mAlarmManager.setExact(type, triggerAtMillis, operation);
    } else {
      mAlarmManager.set(type, triggerAtMillis, operation);
    }
  }

  public void setAndAllowWhileIdle(int type, long triggerAtMillis, PendingIntent operation) {
    if (isAtLeastMarshmallow()) {
      mAlarmManager.setAndAllowWhileIdle(type, triggerAtMillis, operation);
    } else {
      mAlarmManager.set(type, triggerAtMillis, operation);
    }
  }

  public void cancel(PendingIntent pendingIntent) {
    mAlarmManager.cancel(pendingIntent);
  }

  private boolean isAtLeastMarshmallow() {
    return VERSION.SDK_INT >= Build.VERSION_CODES.M;
  }
}

    /*public void startServiceByTimer(int timer, int serviceId) {
        Calendar calendar = Calendar.getInstance();
        PendingIntent pendingIntent = PendingIntent.getService(context, serviceId, startServiceIntent, 0);
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.MILLISECOND, timer);

        AlarmManagerCompat alarmManager = new AlarmManagerCompat(context);
        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }*/