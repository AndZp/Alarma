package mobi.mateam.alarma.weather.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Des63rus on 3/9/2017.
 * <p>
 * Enum for weather params types
 */

public enum ParameterType {

  //TODO: Globalize or move to strings names

  TEMPERATURE(0, "Temperature"), WIND_SPEED(1, "Wind speed"), WIND_DIRECTION(2, "Wind direction"), PRESSURE(3, "Pressure"), CLOUDS(4, "Clouds"), RAIN(
      5, "Rain"), SNOW(6, "Snow");

  public final int id;

  private String name;

  ParameterType(int id, String name) {
    this.name = name;
    this.id = id;
  }

  public static ParameterType getById(int id) {
    for (ParameterType parameterType : values()) {
      if (parameterType.id == id) return parameterType;
    }
    return null;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  /**
   * @param usedParams Params that are already in use
   * @return Params that are not used yet
   */
  public List<ParameterType> getNotUsedParams(List<ParameterType> usedParams) {
    List<ParameterType> result = new ArrayList<>();
    for (ParameterType param : values()) {
      if (!usedParams.contains(param)) {
        result.add(param);
      }
    }
    return result;
  }
}

