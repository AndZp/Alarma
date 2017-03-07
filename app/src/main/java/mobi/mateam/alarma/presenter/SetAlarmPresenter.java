package mobi.mateam.alarma.presenter;

import java.util.ArrayList;
import java.util.List;
import mobi.mateam.alarma.model.pojo.alarm.Alarm;
import mobi.mateam.alarma.model.pojo.alarm.Weekdays;
import mobi.mateam.alarma.model.pojo.weather.WeatherParameter;
import mobi.mateam.alarma.view.interfaces.SetAlarmView;

public class SetAlarmPresenter extends BasePresenter<SetAlarmView> {

  private Alarm alarm;

  @Override public void attachView(SetAlarmView setAlarmView) {
    super.attachView(setAlarmView);
    alarm = getView().getAlarm();
    if (alarm == null) {
      alarm = getDefaultAlarm();
    }

    getView().showTime(alarm.hour + ":" + alarm.minutes);
    getView().showLabel(alarm.lable);
    getView().showLocation(alarm.stringLocation);
    getView().showRingtone("Set Ringtone");
    getView().showWeatherParameters(getParamList());
  }

  public void setTime() {

  }

  public void setLabel(String s) {

  }

  public void setRingtone() {

  }

  public void setLocation() {

  }

  public List<WeatherParameter> getParamList() {

    ArrayList<WeatherParameter> weatherParameters = new ArrayList<>();
    weatherParameters.add(new WeatherParameter());
    weatherParameters.add(new WeatherParameter());

    return weatherParameters;
  }

  public Alarm getDefaultAlarm() {
    Alarm alarm2 = new Alarm();
    alarm2.minutes = 15;
    alarm2.hour = 12;
    alarm2.daysOfWeek = Weekdays.ALL;
    alarm2.enabled = true;
    alarm2.lable = "Test2";
    alarm2.longID = 02;
    alarm2.vibrate = false;
    alarm2.stringLocation = "Ukraine, Drag";

    return alarm2;
  }
}
