package mobi.mateam.alarma.weather.model.params.implementation.units;

/**
 * Created by andreik on 19/03/2017.
 *
 * Enum for wind speed units
 */

public enum SpeedUnits {
  METERSEC(0), MILESHOUR(1);

  private final int id;

  SpeedUnits(int id) {
    this.id = id;
  }

  public static Double convertToDefault(SpeedUnits units, Double value) {
    if (SpeedUnits.MILESHOUR.equals(units)) {
      return value * 0.44704074119232;
    }
    return value;
  }

  public static Double convertToUserUnit(SpeedUnits userUnits, Double metersecValue) {
    if (SpeedUnits.MILESHOUR.equals(userUnits)) {
      return metersecValue * 2.23694;
    }
    return metersecValue;
  }

  public static SpeedUnits getById(int id) {
    for (SpeedUnits units : values()) {
      if (units.id == id) return units;
    }
    return null;
  }
}
