package mobi.mateam.alarma.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.di.component.DaggerPresenterComponent;
import mobi.mateam.alarma.di.component.PresenterComponent;
import mobi.mateam.alarma.presenter.MainAlarmPresenter;
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
  }

  private void setPresenter() {
    if (presenter == null) {
      presenter = getComponent().getMainAlarmPresenter();
    }
    presenter.attachView(this);
  }

  public PresenterComponent getComponent() {
    return DaggerPresenterComponent.builder().appComponent(getAppComponent()).build();
  }

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
}
