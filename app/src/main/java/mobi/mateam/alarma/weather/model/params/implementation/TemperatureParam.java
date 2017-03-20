package mobi.mateam.alarma.weather.model.params.implementation;

import mobi.mateam.alarma.weather.model.ParameterType;
import mobi.mateam.alarma.weather.model.params.WeatherParamValue;
import mobi.mateam.alarma.weather.model.params.implementation.units.TemperatureUnits;

import static mobi.mateam.alarma.weather.model.params.implementation.units.TemperatureUnits.KELVIN;

/**
 * Created by Des63rus on 3/9/2017.
 *
 * Class for temperature param implementation
 */

public class TemperatureParam extends WeatherParamValue<Integer, TemperatureUnits> {


    public TemperatureParam( TemperatureUnits units, Integer value) {
        super(TemperatureUnits.convertToDefault(units, value));
    }

    @Override
    public ParameterType getParametrType() {
        return ParameterType.TEMPERATURE;
    }

}
