package mobi.mateam.alarma.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.di.component.DaggerWeatherComponent;
import mobi.mateam.alarma.di.component.WeatherComponent;
import mobi.mateam.alarma.presenter.MainAlarmPresenter;
import mobi.mateam.alarma.view.interfaces.MainAlarmView;

public class MainAlarmActivity extends BaseActivity implements MainAlarmView {
  private static MainAlarmPresenter presenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_alarm);
    if (presenter == null) {
      presenter = getComponent().getMainAlarmPresenter();
    }
    presenter.attachView(this);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show());
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main_alarm, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    return id == R.id.action_settings || super.onOptionsItemSelected(item);
  }

  public WeatherComponent getComponent() {
    return DaggerWeatherComponent.builder().appComponent(getAppComponent()).build();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    presenter.detachView();
    if (isFinishing()) presenter = null;
  }
}
