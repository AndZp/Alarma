package mobi.mateam.alarma.weather.model.params.implementation.ranges;

import mobi.mateam.alarma.R;
import mobi.mateam.alarma.weather.model.ParameterType;
import mobi.mateam.alarma.weather.model.params.WeatherParamRange;
import mobi.mateam.alarma.weather.model.params.implementation.units.WindUnits;

/**
 * Created by Des63rus on 3/9/2017.
 *
 * Wind speed range
 */

public class WindSpeedRange extends WeatherParamRange<Double, WindUnits> {

    public WindSpeedRange(WindUnits units, Double minValue, Double maxValue) {
      super(WindUnits.convertToDefault(units, minValue), WindUnits.convertToDefault(units, maxValue), R.drawable.label);
    }

    @Override public ParameterType getParameterType() {
        return ParameterType.WIND_POWER;
    }

}
