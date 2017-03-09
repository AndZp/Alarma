package mobi.mateam.alarma.weather.model.params.implementation;

import mobi.mateam.alarma.weather.model.ParameterType;
import mobi.mateam.alarma.weather.model.params.WeatherParamValue;

/**
 * Created by Des63rus on 3/9/2017.
 */

public class WindPowerParam extends WeatherParamValue<Double> {

    public WindPowerParam(Double value) {
        super(value);
    }

    @Override
    public ParameterType getParametrType() {
        return ParameterType.WIND_POWER;
    }

}
