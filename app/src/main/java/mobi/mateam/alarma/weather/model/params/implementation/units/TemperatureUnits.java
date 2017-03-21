package mobi.mateam.alarma.weather.model.params.implementation.units;

public enum TemperatureUnits {
  CELSIUM, KELVIN, FAHRENGEIT;

  public static Integer convertToDefault(TemperatureUnits units, Integer value) {
    switch (units) {
      case KELVIN:
        return value - 273;
      case FAHRENGEIT:
        return (int) (((Double.valueOf(value)) - 32) / 1.8);
    }
    return value;
  }
}
