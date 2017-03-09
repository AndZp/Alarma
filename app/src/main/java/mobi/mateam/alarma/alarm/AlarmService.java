package mobi.mateam.alarma.alarm;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import javax.inject.Inject;
import mobi.mateam.alarma.App;
import mobi.mateam.alarma.alarm.model.Alarm;
import mobi.mateam.alarma.di.component.AppComponent;
import mobi.mateam.alarma.model.repository.AlarmRepository;
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

  @Inject AlarmProvider alarmProvider;
  @Inject AlarmRepository alarmRepository;

  @Override public void onCreate() {
    super.onCreate();
    getAppComponent().inject(AlarmService.this);
  }

  @Override public IBinder onBind(Intent intent) {
    // TODO: Return the communication channel to the service.
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    Timber.v("AlarmService.onStartCommand() with %s", intent);
    String id = intent.getExtras().getString(AlarmProvider.KEY_ALARM_ID);
    alarmRepository.getAlarmById(id).subscribe(this::startAlarm);
    return Service.START_NOT_STICKY;
  }

  private void startAlarm(Alarm alarm) {
    Timber.d(alarm.id + ", " + alarm.lable + ", " + alarm.getStringLocation());
    AlarmKlaxon.start(getApplicationContext(), alarm);
    alarmProvider.setNextAlarm(alarm);
    final Handler handler = new Handler();
    handler.postDelayed(() -> AlarmKlaxon.stop(getApplicationContext()), 6000);
  }

  public AppComponent getAppComponent() {
    return ((App) getApplication()).getAppComponent();
  }
}
