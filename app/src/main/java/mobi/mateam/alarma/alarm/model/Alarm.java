package mobi.mateam.alarma.alarm.model;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.location.places.Place;

import java.util.List;

import mobi.mateam.alarma.weather.WeatherManager;
import mobi.mateam.alarma.weather.model.AlarmWeatherConditions;
import mobi.mateam.alarma.weather.model.ParameterType;
import mobi.mateam.alarma.weather.model.sports.SportTypes;
import mobi.mateam.alarma.weekdays.Weekday;

public class Alarm {
  public boolean activated;
  public boolean vibrate;
  public String id;
  public String label;
  public int hour;
  public int minutes;
  public Uri mRingtone = Uri.EMPTY;
  public boolean mVibrate = true;
  public Place place;
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
}
