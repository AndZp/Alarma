package mobi.mateam.alarma.weather.model.params.implementation;

import mobi.mateam.alarma.weather.model.ParameterType;
import mobi.mateam.alarma.weather.model.params.WeatherParamValue;
import mobi.mateam.alarma.weather.model.params.implementation.units.SpeedUnits;

/**
 * Created by Des63rus on 3/9/2017.
 *
 * Param for wind speed
 */

public class WindSpeedParam extends WeatherParamValue<Integer, SpeedUnits> {

  public WindSpeedParam(SpeedUnits units, Integer value) {
    super(SpeedUnits.convertToDefault(units, value));
    }

    @Override public ParameterType getParameterType() {
      return ParameterType.WIND_SPEED;
    }

}
