package mobi.mateam.alarma.weather.model.params;

/**
 * Created by Des63rus on 3/9/2017.
 * General class for weather params
 */

public abstract class WeatherParamRange<T extends Comparable<T>, S extends Enum> extends WeatherParamGeneral<T, S> {

  /**
   * minimum limit value for weather param
   */
  private T minValue;

  /**
   * maximum limit value for weather param
   */
  private T maxValue;

  private String name;

  public WeatherParamRange(T minValue, T maxValue) {
    this.setMaxValue(maxValue);
    this.setMinValue(minValue);
  }

  public T getMinValue() {
    return minValue;
  }

  public void setMinValue(T minValue) {
    this.minValue = minValue;
  }

  public T getMaxValue() {
    return maxValue;
  }

  public void setMaxValue(T maxValue) {
    this.maxValue = maxValue;
  }

  public boolean checkIfInRange(T currentValue) {
    return currentValue.compareTo(getMinValue()) > 0 && currentValue.compareTo(getMaxValue()) < 0;
  }
}
