package mobi.mateam.alarma.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.alarm.AlarmManager;
import mobi.mateam.alarma.model.repository.AlarmRepository;
import mobi.mateam.alarma.presenter.MainAlarmPresenter;
import mobi.mateam.alarma.presenter.SetAlarmPresenter;
import mobi.mateam.alarma.view.fragment.AlarmListFragment;
import mobi.mateam.alarma.view.fragment.SetAlarmFragment;
import mobi.mateam.alarma.view.interfaces.MainAlarmView;

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
    AlarmManager alarmManager = new AlarmManager(getApplicationContext());
    alarmManager.setNewAlarm(AlarmRepository.getTestAlarms().get(0));
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

  @Override public void showSetAlarmView() {
    getFragmentManager().beginTransaction().replace(R.id.container, new SetAlarmFragment()).addToBackStack(null).commit();
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
    }
  }
}
