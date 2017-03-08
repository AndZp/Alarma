package mobi.mateam.alarma.alarm.model;

import android.net.Uri;
import java.util.Calendar;

public class Alarm {
  public boolean enabled;
  public boolean vibrate;
  public int longID;
  public String id;
  public String lable;
  public Weekdays daysOfWeek;
  public int hour;
  public int minutes;
  public String stringLocation;
  public Uri mRingtone = Uri.EMPTY;
  public boolean mVibrate = true;

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
