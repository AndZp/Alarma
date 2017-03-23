package mobi.mateam.alarma.weather.model.params.implementation;

import mobi.mateam.alarma.weather.model.ParameterType;
import mobi.mateam.alarma.weather.model.params.WeatherParamValue;
import mobi.mateam.alarma.weather.model.params.implementation.units.PressureUnits;

/**
 * Created by Des63rus on 3/9/2017.
 *
 * Implementation for pressure param
 */

public class PressureParam extends WeatherParamValue<Integer, PressureUnits> {

    public PressureParam(Integer value, PressureUnits units) {
        super(PressureUnits.convertToDefault(units, value));
    }

    @Override public ParameterType getParameterType() {
        return ParameterType.PRESSURE;
    }

}
