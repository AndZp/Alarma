package mobi.mateam.alarma.view.activity;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.alarm.AlarmService;
import mobi.mateam.alarma.view.interfaces.AlarmView;
import mobi.mateam.alarma.weather.model.AlarmWeatherConditions;

public class AlarmActivity extends BaseActivity implements AlarmView {
  @BindView(R.id.iv_alarm_anim) ImageView alarmAnim;
  @BindView(R.id.tv_alarm_name) TextView alarmLabel;
  @BindView(R.id.btn_alarm_controler) ImageButton btnControler;
  @BindView(R.id.btn_alarm_dismiss) ImageButton btnDismiss;
  @BindView(R.id.btn_alarm_snooze) ImageButton btnSnooze;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_alarm);
    ButterKnife.bind(this);
    btnControler.setOnTouchListener(new DragListener());
    btnDismiss.setOnDragListener(new MyDragListener());
    btnSnooze.setOnDragListener(new MyDragListener());
  }

  @Override public void showWeather(AlarmWeatherConditions weatherConditions) {

  }

  @Override public void showTime(String time) {

  }

  @Override public void showAlarmLabel(String label) {

  }

  @Override public void runAnimation() {

  }

  @Override public void onBackPressed() {
    // Don't allow back to dismiss.
  }

  private void snoozeAlarm() {
    Intent localBroadcastIntent = new Intent(AlarmService.MOBI_MATEAM_ALARMA_ALARM_ACTION);
    //you can add additional info to the Intent
    localBroadcastIntent.putExtra(AlarmService.ACTION_KEY, AlarmService.ALARM_SNOOZE_ACTION);
    LocalBroadcastManager myLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
    myLocalBroadcastManager.sendBroadcast(localBroadcastIntent);
  }

  private void dismissAlarm() {
    Intent localBroadcastIntent = new Intent(AlarmService.MOBI_MATEAM_ALARMA_ALARM_ACTION);
    //you can add additional info to the Intent
    localBroadcastIntent.putExtra(AlarmService.ACTION_KEY, AlarmService.ALARM_DISMISS_ACTION);
    LocalBroadcastManager myLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
    myLocalBroadcastManager.sendBroadcast(localBroadcastIntent);
  }
  private final class DragListener implements View.OnTouchListener {
    public boolean onTouch(View view, MotionEvent motionEvent) {
      if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        ClipData data = ClipData.newPlainText("", "");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
        view.startDrag(data, shadowBuilder, view, 0);
        view.setVisibility(View.INVISIBLE);
        return true;
      } else {
        return false;
      }
    }
  }

  class MyDragListener implements View.OnDragListener {
    Drawable enterShape = getResources().getDrawable(R.drawable.common_full_open_on_phone);
    Drawable normalShape = getResources().getDrawable(R.drawable.common_google_signin_btn_icon_dark);

    @Override public boolean onDrag(View v, DragEvent event) {
      int action = event.getAction();
      switch (event.getAction()) {
        case DragEvent.ACTION_DRAG_STARTED:
          // do nothing
          break;
        case DragEvent.ACTION_DRAG_ENTERED:
          // v.setBackgroundDrawable(enterShape);
          break;
        case DragEvent.ACTION_DRAG_EXITED:
          // v.setBackgroundDrawable(normalShape);
          break;
        case DragEvent.ACTION_DROP:
          View view = (View) event.getLocalState();

          if (v.getId() == R.id.btn_alarm_dismiss) {
            dismissAlarm();
          } else if (view.getId() == R.id.btn_alarm_snooze) {
            snoozeAlarm();
          }
          break;
        case DragEvent.ACTION_DRAG_ENDED:
          v.setBackgroundDrawable(normalShape);
          break;
        default:
          break;
      }
      return true;
    }
  }
}
