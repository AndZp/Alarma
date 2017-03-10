package mobi.mateam.alarma.view.activity;

import android.os.Bundle;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.view.interfaces.AlarmView;
import mobi.mateam.alarma.weather.model.AlarmWeatherConditions;

public class AlarmActivity extends BaseActivity implements AlarmView {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_alarm);
  }

  @Override public void showWeather(AlarmWeatherConditions weatherConditions) {

  }

  @Override public void showTime(String time) {

  }

  @Override public void showAlarmLabel(String label) {

  }

  @Override public void runAnumation() {

  }
}
