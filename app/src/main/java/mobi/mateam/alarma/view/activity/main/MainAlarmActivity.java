package mobi.mateam.alarma.view.activity.main;

import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.presenter.MainAlarmPresenter;
import mobi.mateam.alarma.presenter.SetAlarmPresenter;
import mobi.mateam.alarma.view.activity.BaseActivity;
import mobi.mateam.alarma.view.activity.SettingsActivity;
import mobi.mateam.alarma.view.customview.SportPickDialog;
import mobi.mateam.alarma.view.fragment.SetAlarmFragment;
import mobi.mateam.alarma.view.interfaces.OnEditAlarmListener;
import mobi.mateam.alarma.view.interfaces.SuperAlarmView;

public class MainAlarmActivity extends BaseActivity implements SuperAlarmView, OnEditAlarmListener {
  public static final int LAYOUT = R.layout.activity_super_main_alarm;
  public static final int SET_ALARM_MODE = 2;
  public static final int ALARM_LIST_MODE = 1;
  private static MainAlarmPresenter presenter;

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.cl_main_layout) CoordinatorLayout clMainLayout;
  private Navigator navigator;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(LAYOUT);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    setPresenter();
    setNavigator();

    navigator.updateMode();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main_alarm, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_settings:
        startActivity(SettingsActivity.getStartIntent(getApplicationContext()));
        break;
      case R.id.action_about:
        Toast.makeText(this, "You have selected About Menu", Toast.LENGTH_SHORT).show();
        break;
      case android.R.id.home:
        onBackPressed();
        break;
    }
    return true;
  }

  private void setPresenter() {
    if (presenter == null) {
      presenter = getAppComponent().getSuperAlarmPresenter();
    }
    presenter.attachView(this);
  }

  private void setNavigator() {
    navigator = presenter.getNavigator();
    navigator.onAttachView(MainAlarmActivity.this);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    presenter.detachView();
    navigator.onDestroy();
    if (isFinishing()) {
      presenter = null;
    }
  }

  @Override public void showAlarmsListMode() {
    navigator.showAlarmListMode();
  }

  @Override public void showEditAlarmMode(String alarmId) {
    navigator.showEditAlarmMode(alarmId);
  }

  @Override public void showNotification(String message) {
    Snackbar.make(clMainLayout, message, Snackbar.LENGTH_LONG).show();
  }

  @Override public void setActionBarImage(int imageId) {
    navigator.setActionBarImage(imageId);
  }

  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == SetAlarmPresenter.PLACE_PICKER_REQUEST) {
      if (resultCode == RESULT_OK) {
        Place place = PlacePicker.getPlace(data, this);
        if (SetAlarmFragment.presenter != null) {
          SetAlarmFragment.presenter.onPlacePickerResult(place);
        }
      }
    } else if (requestCode == SetAlarmPresenter.TONE_PICKER_REQUEST) {
      if (resultCode == RESULT_OK) {
        Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
        if (SetAlarmFragment.presenter != null) {
          SetAlarmFragment.presenter.onRingtonePickerResult(uri);
        }
      }
    }
  }

  @Override public void onBackPressed() {
    navigator.onBackPressed();
  }

  @Override public void onEditAlarm(String alarmId) {
    presenter.onEditAlarmClick(alarmId);
  }

  public void onFABClick(int mode) {
    switch (mode) {
      case ALARM_LIST_MODE:
        new SportPickDialog().show(getSupportFragmentManager(), "SportPickDialog");
        break;
      case SET_ALARM_MODE:
        if (SetAlarmFragment.presenter != null) {
          SetAlarmFragment.presenter.onSaveAlarm();
          navigator.showAlarmListMode();
          break;
        }
    }
  }
}
