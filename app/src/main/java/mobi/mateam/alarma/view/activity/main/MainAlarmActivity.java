package mobi.mateam.alarma.view.activity.main;

import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.presenter.SetAlarmPresenter;
import mobi.mateam.alarma.presenter.SuperAlarmPresenter;
import mobi.mateam.alarma.view.activity.BaseActivity;
import mobi.mateam.alarma.view.fragment.SetAlarmFragment;
import mobi.mateam.alarma.view.interfaces.OnAlarmSetListener;
import mobi.mateam.alarma.view.interfaces.OnEditAlarmListener;
import mobi.mateam.alarma.view.interfaces.PickSportListener;
import mobi.mateam.alarma.view.interfaces.SuperAlarmView;
import mobi.mateam.alarma.weather.model.sports.SportTypes;

public class MainAlarmActivity extends BaseActivity implements SuperAlarmView, PickSportListener, OnAlarmSetListener, OnEditAlarmListener {
  public static final int LAYOUT = R.layout.activity_super_main_alarm;
  public static final int SET_ALARM_MODE = 2;
  public static final int ALARM_LIST_MODE = 1;
  private static SuperAlarmPresenter presenter;

  @BindView(R.id.toolbar) Toolbar toolbar;

  private MainActivityStrategy activityStrategy;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(LAYOUT);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    setActivityStrategy();
    setPresenter();
  }

  private void setActivityStrategy() {
    if (getResources().getBoolean(R.bool.isTablet)) {
      activityStrategy = new TabletMainActivityStrategy();
    } else {
      activityStrategy = new PhoneMainActivityStrategy();
    }

    activityStrategy.onCreate(MainAlarmActivity.this);
  }

  private void setPresenter() {
    if (presenter == null) {
      presenter = getAppComponent().getSuperAlarmPresenter();
    }
    presenter.attachView(this);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    presenter.detachView();
    activityStrategy.onDestroy();
    if (isFinishing()) presenter = null;
  }

  @Override public void showAlarmsListMode() {
    activityStrategy.showAlarmListMode();
  }

  @Override public void showSportPickView() {
    activityStrategy.showSportPickView();
  }

  @Override public void showEditAlarmMode(String alarmId) {
    activityStrategy.showEditAlarmMode(alarmId);
  }

  @Override public void showSetNewAlarmMode(SportTypes sportTypes) {
    activityStrategy.showSetNewAlarmMode(sportTypes);
  }

  @Override public void showEmptyStateMode() {
    activityStrategy.showEmptyStateMode();
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
    activityStrategy.onBackPressed();
  }

  @Override public void onSportPick(SportTypes sportTypes) {
    presenter.onSportPicked(sportTypes);
  }

  @Override public void onAlarmSet(String alarmId) {
    presenter.updateMode();
  }

  @Override public void onEditAlarm(String alarmId) {
    presenter.onEditAlarmClick(alarmId);
  }

  public void onFABClick(int mode) {
    switch (mode) {
      case ALARM_LIST_MODE:
        presenter.onAddNewAlarmClick();
        break;
      case SET_ALARM_MODE:
        if (SetAlarmFragment.presenter != null) {
          SetAlarmFragment.presenter.onSaveAlarm();
          break;
        }
    }
  }
}
