package mobi.mateam.alarma.weather.model.params.implementation;

import mobi.mateam.alarma.weather.model.ParameterType;
import mobi.mateam.alarma.weather.model.params.WeatherParamRange;

/**
 * Created by Des63rus on 3/9/2017.
 */

public class WindPowerRange extends WeatherParamRange<Double> {

    public WindPowerRange(Double minValue, Double maxValue) {
        super(minValue, maxValue);
    }

    @Override
    public ParameterType getParametrType() {
        return ParameterType.WIND_POWER;
    }

}
