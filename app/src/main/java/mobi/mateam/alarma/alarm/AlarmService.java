package mobi.mateam.alarma.alarm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import java.util.Map;
import javax.inject.Inject;
import mobi.mateam.alarma.App;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.alarm.model.Alarm;
import mobi.mateam.alarma.di.component.AppComponent;
import mobi.mateam.alarma.model.repository.AlarmRepository;
import mobi.mateam.alarma.network.WeatherService;
import mobi.mateam.alarma.view.activity.AlarmActivity;
import mobi.mateam.alarma.view.activity.main.MainAlarmActivity;
import mobi.mateam.alarma.weather.WeatherManager;
import mobi.mateam.alarma.weather.model.ParameterType;
import mobi.mateam.alarma.weather.model.WeatherCheckResponse;
import mobi.mateam.alarma.weather.model.params.ProblemParam;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

public class AlarmService extends Service {

  public static final String ACTION_KEY = "action_key";
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
  public static final String MOBI_MATEAM_ALARMA_ALARM_ACTION = "mobi.mateam.alarma.alarm_action";
  public static final String ALARM_ID_KEY = "alarm_id_key";

  @Inject AlarmProvider alarmProvider;
  @Inject AlarmRepository alarmRepository;
  @Inject WeatherService weatherService;
  private AlarmLocalReceiver alarmLocalReceiver;
  private Alarm alarm;

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
    alarmRepository.getAlarmById(id).subscribe(this::checkAlarm);
    registerReceiver();

    return Service.START_NOT_STICKY;
  }

  @Override public void onDestroy() {
    super.onDestroy();
    alarmLocalReceiver = null;
  }

  private void registerReceiver() {
    alarmLocalReceiver = new AlarmLocalReceiver();

    //the intent filter will be action = "com.example.demo_service.action.SERVICE_FINISHED"
    IntentFilter filter = new IntentFilter(MOBI_MATEAM_ALARMA_ALARM_ACTION);

    // register the receiver:
    registerReceiver(alarmLocalReceiver, filter);
  }

  private void checkAlarm(Alarm alarm) {
    this.alarm = alarm;
    if (alarm.place == null) {
      //TODO: check and fix this place - ze le tov
      return;
    }
    String lat = alarm.place.getLat();
    String lon = alarm.place.getLon();
    weatherService.getCurrentWeatherByLocation(lat, lon)
        .map(weatherData -> WeatherManager.checkTheWeather(weatherData, alarm.conditions))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<WeatherCheckResponse>() {
          @Override public void onCompleted() {
            Timber.d("WeatherCheckResponse onCompleted");
          }

          @Override public void onError(Throwable e) {
            Timber.e(e);
          }

          @Override public void onNext(WeatherCheckResponse weatherCheckResponse) {
            if (weatherCheckResponse.isSutableWeather()) {
              //if (true) {
              startAlarmAction(weatherCheckResponse);
            } else {
              showUnsutebleNotification(weatherCheckResponse);
            }
          }
        });

    Timber.d(alarm.id + ", " + alarm.label + ", " + alarm.getStringLocation());

    alarmProvider.setNextAlarm(alarm);
  }

  private void startAlarmAction(WeatherCheckResponse weatherCheckResponse) {
    AlarmKlaxon.start(getApplicationContext(), alarm);

    alarmProvider.setNextAlarm(alarm);
    final Handler handler = new Handler();
    handler.postDelayed(() -> {
      AlarmKlaxon.stop(getApplicationContext());
      dismissAlarm();
    }, 1000 * 60);

    Intent intent = new Intent(getApplicationContext(), AlarmActivity.class);
    intent.putExtra(ALARM_ID_KEY, alarm.id);
    getApplicationContext().startActivity(intent);
  }

  public AppComponent getAppComponent() {
    return ((App) getApplication()).getAppComponent();
  }

  private void snoozeAction() {
  }

  private void doneAction() {
  }

  private void dismissAlarm() {

    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainAlarmActivity.class), 0);

    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this).setSmallIcon(
        R.drawable.leak_canary_icon) //TODO: IF ONE DAY WE'LL DECIDE REMOVE THIS ICON - DO NOTIFY SERVER ABOUT IT !!!!!!
        .setContentTitle("You miss your alarm. But I tried")
        .setContentText("Some body text for notification")
        .setAutoCancel(true)
        .setSound(defaultSoundUri)
        .setContentIntent(pendingIntent);

    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
  }

  private void showUnsutebleNotification(WeatherCheckResponse response) {
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainAlarmActivity.class), 0);
    Map<ParameterType, ProblemParam> hashMap = response.getProblemParamMap();

    final String[] result = { "" };

    if (hashMap != null || hashMap.size() > 0) {
      Map.Entry<ParameterType, ProblemParam> next = hashMap.entrySet().iterator().next();
      result[0] = next.getKey().getName();
    }

    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.leak_canary_icon)
        .setContentTitle("Weather is not sutable for your parameters")
        .setContentText(result[0] + " was not OK")
        .setAutoCancel(true)
        .setSound(defaultSoundUri)
        .setContentIntent(pendingIntent);

    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
  }

  private class AlarmLocalReceiver extends BroadcastReceiver {

    @Override public void onReceive(Context context, Intent intent) {
      Timber.d("GOT IN THE RECIVING");
      String action = intent.getExtras().getString(ACTION_KEY);

      if (action.equals(ALARM_DISMISS_ACTION)) {
        dismissAlarm();
      } else if (action.equals(ALARM_DONE_ACTION)) {
        doneAction();
      } else if (action.equals(ALARM_SNOOZE_ACTION)) {
        snoozeAction();
      }
    }
  }
}
