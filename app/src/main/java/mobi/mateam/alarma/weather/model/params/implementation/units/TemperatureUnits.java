package mobi.mateam.alarma.weather.model.params.implementation.units;

import android.support.annotation.StringRes;
import mobi.mateam.alarma.R;

public enum TemperatureUnits {
  CELSIUS(0, R.string.celsius_symbol), FAHRENHEITS(1, R.string.fahrenheit_symbol);

  private final int id;

  @StringRes private final int symbolStringRes;

  TemperatureUnits(int id, @StringRes int symbolStringRes) {
    this.id = id;
    this.symbolStringRes = symbolStringRes;
  }

  public static Integer convertToDefault(TemperatureUnits units, Integer value) {
    if (units == TemperatureUnits.FAHRENHEITS) {
      return (int) (((Double.valueOf(value)) - 32) / 1.8);
    }
    return value;
  }

  public static Integer convertToUserUnit(TemperatureUnits userUnits, Integer celsiusValue) {
    if (userUnits == TemperatureUnits.FAHRENHEITS) {
      return 32 + (celsiusValue * 9 / 5);
    }
    return celsiusValue;
  }

  public static TemperatureUnits getById(int id) {
    for (TemperatureUnits units : values()) {
      if (units.id == id) return units;
    }
    return null;
  }

  public int getSymbolStringRes() {
    return symbolStringRes;
  }
}
