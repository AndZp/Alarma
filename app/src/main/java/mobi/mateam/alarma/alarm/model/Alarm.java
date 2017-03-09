package mobi.mateam.alarma.alarm.model;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.location.places.Place;
import java.util.Calendar;
import mobi.mateam.alarma.weekdays.Weekday;

public class Alarm {
  public boolean enabled;
  public boolean vibrate;
  public String id;
  public String lable;
  public int hour;
  public int minutes;
  public String stringLocation;
  public Uri mRingtone = Uri.EMPTY;
  public boolean mVibrate = true;
  public Place place;
  public int[] weekdays;

  public Calendar getNextAlarmTime(Calendar now) {
    return Calendar.getInstance();
  }

  public String getStringLocation() {
    if (place == null) {
      return null;
    } else {
      return place.getName().toString();
    }
  }

  public String getStringDays() {
    if (weekdays == null) {
      return "One shot alarm";
    } else {
      String res = "";
      for (int i = 0; i < weekdays.length; i++) {
        if (weekdays[i] == 1) {
          if (!TextUtils.isEmpty(res)) {
            res += " ";
          }
          res += Weekday.getById(i + 1).getName().substring(0, 3);
        }
      }
      return res;
    }
  }
}
