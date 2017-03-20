package mobi.mateam.alarma.weather.model.params.implementation.ranges;

import mobi.mateam.alarma.weather.model.ParameterType;
import mobi.mateam.alarma.weather.model.params.WeatherParamRange;
import mobi.mateam.alarma.weather.model.params.implementation.units.NoUnits;
import mobi.mateam.alarma.weather.model.params.implementation.units.TemperatureUnits;

/**
 * Created by Des63rus on 3/9/2017.
 */

public class TemperatureRange extends WeatherParamRange<Integer, TemperatureUnits> {

    public TemperatureRange(TemperatureUnits units, Integer minValue, Integer maxValue) {
        super(TemperatureUnits.convertToDefault(units, minValue), TemperatureUnits.convertToDefault(units, maxValue));
    }

    @Override
    public ParameterType getParametrType() {
        return ParameterType.TEMPERATURE;
    }


}
