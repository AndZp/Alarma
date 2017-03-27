package mobi.mateam.alarma.weather.model.params;

import android.support.annotation.DrawableRes;

/**
 * Created by Des63rus on 3/9/2017.
 * General class for weather params
 */

public abstract class WeatherParamRange<T extends Comparable<T>, S extends Enum> extends WeatherParamGeneral<T, S> {

  private final int iconId;
  /**
   * minimum limit value for weather param
   */
  private T minValue;

  /**
   * maximum limit value for weather param
   */
  private T maxValue;

  public WeatherParamRange(T minValue, T maxValue, @DrawableRes int iconId) {
    this.setMaxValue(maxValue);
    this.setMinValue(minValue);
    this.iconId = iconId;
  }

  public int getIconId() {
    return iconId;
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
