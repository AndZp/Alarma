package mobi.mateam.alarma.weather.model.params.implementation;

import mobi.mateam.alarma.weather.model.ParameterType;
import mobi.mateam.alarma.weather.model.params.WeatherParamValue;
import mobi.mateam.alarma.weather.model.params.WindDirectionType;
import mobi.mateam.alarma.weather.model.params.implementation.units.NoUnits;

/**
 * Created by Des63rus on 3/9/2017.
 *
 * Param for clouds
 */

public class WindDirectionParam extends WeatherParamValue<WindDirectionType, NoUnits> {

  public WindDirectionParam(WindDirectionType value) {
    super(value);
  }

  @Override public ParameterType getParameterType() {
    return ParameterType.WIND_DIRECTION;
  }
}
