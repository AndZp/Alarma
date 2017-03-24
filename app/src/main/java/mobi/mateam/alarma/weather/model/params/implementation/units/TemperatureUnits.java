package mobi.mateam.alarma.weather.model.params.implementation.units;

public enum TemperatureUnits {
  CELSIUS(0), KELVIN(1), FAHRENHEITS(2);

  private final int id;

  TemperatureUnits(int id) {
    this.id = id;
  }

  public static Integer convertToDefault(TemperatureUnits units, Integer value) {
    switch (units) {
      case KELVIN:
        return value - 273;
      case FAHRENHEITS:
        return (int) (((Double.valueOf(value)) - 32) / 1.8);
    }
    return value;
  }

  public static Integer convertToUserUnit(TemperatureUnits userUnits, Integer celsiusValue) {
    switch (userUnits) {
      case KELVIN:
        return celsiusValue + 273;
      case FAHRENHEITS:
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
}
