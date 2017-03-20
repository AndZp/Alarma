package mobi.mateam.alarma.weather.model.params.implementation.ranges;

import mobi.mateam.alarma.weather.model.ParameterType;
import mobi.mateam.alarma.weather.model.params.WeatherParamRange;
import mobi.mateam.alarma.weather.model.params.implementation.units.PressureUnits;

/**
 * Created by Des63rus on 3/9/2017.
 */

public class PressureRange extends WeatherParamRange<Integer, PressureUnits> {

    public PressureRange(Integer minValue, Integer maxValue, PressureUnits units) {
        super(PressureUnits.convertToDefault(units, minValue), PressureUnits.convertToDefault(units,maxValue));
    }


    @Override
    public ParameterType getParametrType() {
        return ParameterType.CLOUDS;
    }
}
