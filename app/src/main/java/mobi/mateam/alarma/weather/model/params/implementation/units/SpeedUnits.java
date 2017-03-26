package mobi.mateam.alarma.weather.model.params.implementation.units;

import android.support.annotation.StringRes;
import mobi.mateam.alarma.R;

/**
 * Created by andreik on 19/03/2017.
 *
 * Enum for wind speed units
 */

public enum SpeedUnits {
  METERSEC(0, R.string.meter_sec_short), MILESHOUR(1, R.string.miles_hour_short);

  private final int id;

  @StringRes private final int unitStringResId;

  SpeedUnits(int id, @StringRes int shortUnitStringRes) {
    this.id = id;
    this.unitStringResId = shortUnitStringRes;
  }

  public static Integer convertToDefault(SpeedUnits units, Integer value) {
    if (SpeedUnits.MILESHOUR.equals(units)) {
      Double res = value * 0.44704074119232;
      return res.intValue();
    }
    return value;
  }

  public static Integer convertToUserUnit(SpeedUnits userUnits, Integer metersecValue) {
    if (SpeedUnits.MILESHOUR.equals(userUnits)) {
      Double res = metersecValue * 2.23694;
      return res.intValue();
    }
    return metersecValue;
  }

  public static SpeedUnits getById(int id) {
    for (SpeedUnits units : values()) {
      if (units.id == id) return units;
    }
    return null;
  }

  @StringRes public int getUnitStringResId() {
    return unitStringResId;
  }
}
