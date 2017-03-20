package mobi.mateam.alarma.weather.model.params.implementation;

import mobi.mateam.alarma.weather.model.ParameterType;
import mobi.mateam.alarma.weather.model.params.WeatherParamValue;
import mobi.mateam.alarma.weather.model.params.implementation.units.NoUnits;

/**
 * Created by Des63rus on 3/9/2017.
 *
 * Param for clouds
 */

public class CloudsParam extends WeatherParamValue<Integer, NoUnits> {

    public CloudsParam(Integer value) {
        super(value);
    }

    @Override
    public ParameterType getParametrType() {
        return ParameterType.CLOUDS;
    }
}
