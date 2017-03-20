package mobi.mateam.alarma.weather.model.params.implementation;

import mobi.mateam.alarma.weather.model.ParameterType;
import mobi.mateam.alarma.weather.model.params.WeatherParamValue;
import mobi.mateam.alarma.weather.model.params.implementation.units.WindUnits;

/**
 * Created by Des63rus on 3/9/2017.
 *
 * Param for wind speed
 */

public class WindPowerParam extends WeatherParamValue<Double, WindUnits> {

    public WindPowerParam(WindUnits units, Double value) {
        super(WindUnits.convertToDefault(units, value));
    }

    @Override
    public ParameterType getParametrType() {
        return ParameterType.WIND_POWER;
    }

}
