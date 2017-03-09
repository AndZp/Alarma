package mobi.mateam.alarma.alarm.model;

import android.net.Uri;
import com.google.android.gms.location.places.Place;
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
  public Place place;

  public Calendar getNextAlarmTime(Calendar now) {
    return Calendar.getInstance();
  }

  public String getStringLocation() {
    return place.getName().toString();
  }

  public String getStringDays() {
    return daysOfWeek.toString();
  }
}
