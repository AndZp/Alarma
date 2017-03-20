package mobi.mateam.alarma.weather.model.params.implementation.ranges;

import mobi.mateam.alarma.weather.model.ParameterType;
import mobi.mateam.alarma.weather.model.params.WeatherParamRange;
import mobi.mateam.alarma.weather.model.params.implementation.units.NoUnits;

/**
 * Created by Des63rus on 3/9/2017.
 *
 * Clouds range
 */

public class CloudsRange extends WeatherParamRange<Integer, NoUnits> {

    public CloudsRange(Integer minValue, Integer maxValue) {
        super(minValue, maxValue);
    }

    @Override
    public ParameterType getParametrType() {
        return ParameterType.CLOUDS;
    }

}
