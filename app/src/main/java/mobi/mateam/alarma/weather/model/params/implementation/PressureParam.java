package mobi.mateam.alarma.weather.model.params.implementation;

import mobi.mateam.alarma.weather.model.ParameterType;
import mobi.mateam.alarma.weather.model.params.WeatherParamRange;

/**
 * Created by Des63rus on 3/9/2017.
 *
 * Implementation for pressure param
 */

public class PressureParam extends WeatherParamRange<Integer> {

    @Override
    public ParameterType getParametrType() {
        return ParameterType.PRESSURE;
    }
}
