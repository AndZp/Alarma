package mobi.mateam.alarma.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import javax.inject.Inject;
import mobi.mateam.alarma.App;
import mobi.mateam.alarma.alarm.model.Alarm;
import mobi.mateam.alarma.di.component.AppComponent;
import mobi.mateam.alarma.model.repository.AlarmRepository;
import timber.log.Timber;

public class BootCompleteReceiver extends BroadcastReceiver {
  @Inject AlarmProvider alarmProvider;
  @Inject AlarmRepository alarmRepository;

  @Override public void onReceive(Context context, Intent intent) {
    Timber.d("onReceive boot complete");
    getAppComponent(context).inject(this);
    alarmRepository.getAlarmList().subscribe(alarms -> {
      for (Alarm alarm : alarms) {
        if (alarm.activated) {
          alarmProvider.setNextAlarm(alarm);
        }
      }
    });
  }

  public AppComponent getAppComponent(Context context) {
    return ((App) context.getApplicationContext()).getAppComponent();
  }
}
