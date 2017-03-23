package mobi.mateam.alarma.alarm.model;

import android.media.RingtoneManager;
import android.net.Uri;
import android.text.TextUtils;
import java.util.List;
import mobi.mateam.alarma.weather.WeatherManager;
import mobi.mateam.alarma.weather.model.AlarmWeatherConditions;
import mobi.mateam.alarma.weather.model.ParameterType;
import mobi.mateam.alarma.weather.model.sports.SportTypes;
import mobi.mateam.alarma.weekdays.Weekday;

public class Alarm {
  public boolean activated;
  public boolean vibrate = true;
  public String id;
  public String label;
  public int hour = 7;
  public int minutes = 40;
  public Uri mRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
  public boolean mVibrate = true;
  public PlaceData place;
  public int[] weekdays;

  public AlarmWeatherConditions conditions;
  public SportTypes sportType;

  public Alarm() {

  }

  public Alarm(SportTypes sportTypes) {
    List<ParameterType> defaultParams = sportTypes.getDefaultParams();
    this.sportType = sportTypes;
    conditions = new AlarmWeatherConditions();
    for (ParameterType paramType : defaultParams) {
      conditions.addParam(WeatherManager.getDefaultValueForParam(paramType));
    }
  }

  public String getStringLocation() {
    return place == null ? null : place.getName().toString();
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

  @Override public boolean equals(Object o) {

    if (o == this) return true;
    if (!(o instanceof Alarm)) {
      return false;
    }

    Alarm alarm = (Alarm) o;

    return alarm.id.equals(id) && alarm.hour == hour && alarm.minutes == minutes;
  }

  //Idea from effective Java : Item 9
  @Override public int hashCode() {
    int result = 17;
    result = 31 * result + id.hashCode();
    result = 31 * result + minutes;
    result = 31 * result + hour;
    return result;
  }
}
