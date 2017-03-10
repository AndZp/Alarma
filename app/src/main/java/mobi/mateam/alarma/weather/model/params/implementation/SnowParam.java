package mobi.mateam.alarma.weather.model.params.implementation;

import mobi.mateam.alarma.weather.model.ParameterType;
import mobi.mateam.alarma.weather.model.params.SnowType;
import mobi.mateam.alarma.weather.model.params.WeatherParamValue;

/**
 * Created by Des63rus on 3/10/2017.
 *
 *  Weather param for rain types
 */

public class SnowParam extends WeatherParamValue<SnowType> {

    public SnowParam(SnowType value) {
        super(value);
    }

    @Override
    public ParameterType getParametrType() {
        return ParameterType.SNOW;
    }

    public SnowParam(Integer rainMM){
        super(SnowType.getSnowTypeByMM(rainMM));
    }

}
