package mobi.mateam.alarma.view.activity;

import android.app.Fragment;
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
import mobi.mateam.alarma.alarm.model.Alarm;
import mobi.mateam.alarma.presenter.MainAlarmPresenter;
import mobi.mateam.alarma.presenter.SetAlarmPresenter;
import mobi.mateam.alarma.view.fragment.AlarmListFragment;
import mobi.mateam.alarma.view.fragment.SetAlarmFragment;
import mobi.mateam.alarma.view.interfaces.MainAlarmView;
import mobi.mateam.alarma.view.interfaces.PickSportListener;
import mobi.mateam.alarma.view.interfaces.SetAlarmView;
import mobi.mateam.alarma.weather.model.sports.SportTypes;

public class MainAlarmActivity extends BaseActivity implements MainAlarmView, PickSportListener {
  private static MainAlarmPresenter presenter;
  @BindView(R.id.toolbar) Toolbar toolbar;
  private AlarmListFragment alarmListFragment;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_alarm);
    ButterKnife.bind(this);
    setPresenter();
    setSupportActionBar(toolbar);
    presenter.onCreate();
    // startActivity(new Intent(this, AlarmActivity.class));
  }

  private void setPresenter() {
    if (presenter == null) {
      presenter = getAppComponent().getMainAlarmPresenter();
    }
    presenter.attachView(this);
  }



  /*public PresenterComponent getComponent() {
    return DaggerPresenterComponent.builder().appComponent(getAppComponent()).build();
  }*/

  @Override protected void onDestroy() {
    super.onDestroy();
    presenter.detachView();
    if (isFinishing()) presenter = null;
  }

  @Override public void showSetAlarmView(Alarm alarm, boolean b, int sportId) {
    SetAlarmFragment fragment = new SetAlarmFragment();
    if (alarm != null) {

      Bundle bundle = new Bundle();
      bundle.putString(SetAlarmView.ALRAM_ID_KEY, alarm.id);
      bundle.putBoolean(SetAlarmView.ALARM_IS_NEW, b);
      bundle.putInt(SetAlarmView.ALARM_SPORT_TYPE_ID, sportId);
      fragment.setArguments(bundle);
    }
    getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
  }

  @Override public void showAlarmsListView() {
    if (alarmListFragment == null) {
      alarmListFragment = new AlarmListFragment();
      getFragmentManager().beginTransaction().add(R.id.container, alarmListFragment).commit();
    } else {
      getFragmentManager().beginTransaction().replace(R.id.container, alarmListFragment).addToBackStack(null).commit();
    }
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
    Fragment f = getFragmentManager().findFragmentById(R.id.container);
    if (f instanceof AlarmListFragment) {
      this.finish();
    } else {
      showAlarmsListView();
    }
  }

  @Override public void onSportPick(SportTypes sportTypes) {
    showSetAlarmView(new Alarm(sportTypes), true, sportTypes.getId());
  }
}
