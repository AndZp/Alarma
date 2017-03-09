package mobi.mateam.alarma.weather.model.params.implementation;

import mobi.mateam.alarma.weather.model.ParameterType;
import mobi.mateam.alarma.weather.model.params.WeatherParamValue;

/**
 * Created by Des63rus on 3/9/2017.
 *
 * Class for temperature param implementation
 */

public class TemperatureParam extends WeatherParamValue<Integer> {


    public TemperatureParam(Integer value) {
        super(value);
    }

    @Override
    public ParameterType getParametrType() {
        return ParameterType.TEMPERATURE;
    }
}
