package mobi.mateam.alarma.alarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import timber.log.Timber;

public class AlarmService extends Service {
  /**
   * AlarmActivity and AlarmService (when unbound) listen for this broadcast intent
   * so that other applications can snooze the alarm (after ALARM_ALERT_ACTION and before
   * ALARM_DONE_ACTION).
   */
  public static final String ALARM_SNOOZE_ACTION = "mobi.mateam.alarma.ALARM_SNOOZE";

  /**
   * AlarmActivity and AlarmService listen for this broadcast intent so that other
   * applications can dismiss the alarm (after ALARM_ALERT_ACTION and before ALARM_DONE_ACTION).
   */
  public static final String ALARM_DISMISS_ACTION = "mobi.mateam.alarma.ALARM_DISMISS";

  /** A public action sent by AlarmService when the alarm has started. */
  public static final String ALARM_ALERT_ACTION = "mobi.mateam.alarma.ALARM_ALERT";

  /** A public action sent by AlarmService when the alarm has stopped for any reason. */
  public static final String ALARM_DONE_ACTION = "mobi.mateam.alarma.ALARM_DONE";

  /** Private action used to stop an alarm with this service. */
  public static final String STOP_ALARM_ACTION = "STOP_ALARM";

  public AlarmService() {
  }

  @Override public IBinder onBind(Intent intent) {
    // TODO: Return the communication channel to the service.
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    Timber.v("AlarmService.onStartCommand() with %s", intent);
    int id = intent.getExtras().getInt(AlarmManager.KEY_ALARM_ID);
    return Service.START_NOT_STICKY;
  }
}
