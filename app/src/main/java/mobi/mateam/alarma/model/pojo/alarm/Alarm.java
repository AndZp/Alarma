package mobi.mateam.alarma.model.pojo.alarm;

import java.util.Calendar;

public class Alarm {
  public boolean enabled;
  public boolean vibrate;
  public long longID;
  public String id;
  public String lable;
  public Weekdays daysOfWeek;
  public int hour;
  public int minutes;
  public String stringLocation;

  public Calendar getNextAlarmTime(Calendar now) {
    return Calendar.getInstance();
  }

  public String getStringLocation() {
    return stringLocation;
  }

  public String getStringDays() {
    return daysOfWeek.toString();
  }
}
