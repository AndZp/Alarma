package mobi.mateam.alarma.model.repository;

import java.util.ArrayList;
import mobi.mateam.alarma.model.pojo.alarm.Alarm;
import mobi.mateam.alarma.model.pojo.alarm.Weekdays;
import rx.Observable;

public class AlarmRepository {

  public Observable<ArrayList<Alarm>> getAlarmList() {
    return Observable.just(getTestAlarms());
  }

  private ArrayList<Alarm> getTestAlarms() {
    Alarm alarm1 = new Alarm();
    alarm1.minutes = 05;
    alarm1.hour = 12;
    alarm1.daysOfWeek = Weekdays.ALL;
    alarm1.enabled = true;
    alarm1.lable = "Test1";
    alarm1.longID = 01;
    alarm1.vibrate = true;
    alarm1.stringLocation = "Israel, dead sea";

    Alarm alarm2 = new Alarm();
    alarm2.minutes = 15;
    alarm2.hour = 7;
    alarm2.daysOfWeek = Weekdays.ALL;
    alarm2.enabled = false;
    alarm2.lable = "Test2";
    alarm2.longID = 02;
    alarm2.vibrate = false;
    alarm2.stringLocation = "Ukraine, Drag";
    ArrayList<Alarm> alarms = new ArrayList<>();
    alarms.add(alarm1);
    alarms.add(alarm2);

    return alarms;
  }
}
