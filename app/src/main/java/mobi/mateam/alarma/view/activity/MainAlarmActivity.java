package mobi.mateam.alarma.view.activity;

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
import mobi.mateam.alarma.view.interfaces.SetAlarmView;

public class MainAlarmActivity extends BaseActivity implements MainAlarmView {
  private static MainAlarmPresenter presenter;
  @BindView(R.id.toolbar) Toolbar toolbar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_alarm);
    ButterKnife.bind(this);
    setPresenter();
    setSupportActionBar(toolbar);
    presenter.onCreate();
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

  @Override public void showSetAlarmView(Alarm alarm) {
    SetAlarmFragment fragment = new SetAlarmFragment();
    if (alarm != null) {
      Bundle bundle = new Bundle();
      bundle.putString(SetAlarmView.ALRAM_ID_KEY, alarm.id);
      fragment.setArguments(bundle);
    }
    getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
  }

  @Override public void showAlarmsListView() {
    getFragmentManager().beginTransaction().replace(R.id.container, new AlarmListFragment()).addToBackStack(null).commit();
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
}
