package mobi.mateam.alarma.weather.model.params.implementation.ranges;

import mobi.mateam.alarma.R;
import mobi.mateam.alarma.weather.model.ParameterType;
import mobi.mateam.alarma.weather.model.params.WeatherParamRange;
import mobi.mateam.alarma.weather.model.params.implementation.units.SpeedUnits;

/**
 * Created by Des63rus on 3/9/2017.
 *
 * Wind speed range
 */

public class WindSpeedRange extends WeatherParamRange<Double, SpeedUnits> {

    public WindSpeedRange(SpeedUnits units, Double minValue, Double maxValue) {
        super(SpeedUnits.convertToDefault(units, minValue), SpeedUnits.convertToDefault(units, maxValue), R.drawable.label);
    }

    @Override public ParameterType getParameterType() {
        return ParameterType.WIND_POWER;
    }

}
