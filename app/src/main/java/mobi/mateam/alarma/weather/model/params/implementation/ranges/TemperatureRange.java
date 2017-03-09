package mobi.mateam.alarma.weather.model.params.implementation.ranges;

import mobi.mateam.alarma.weather.model.ParameterType;
import mobi.mateam.alarma.weather.model.params.WeatherParamRange;

/**
 * Created by Des63rus on 3/9/2017.
 */

public class TemperatureRange extends WeatherParamRange<Integer> {

    public TemperatureRange(Integer minValue, Integer maxValue) {
        super(minValue, maxValue);
    }

    @Override
    public ParameterType getParametrType() {
        return ParameterType.TEMPERATURE;
    }


}
