package mobi.mateam.alarma.weather.model.params.implementation.ranges;

import mobi.mateam.alarma.weather.model.ParameterType;
import mobi.mateam.alarma.weather.model.params.SnowType;
import mobi.mateam.alarma.weather.model.params.WeatherParamRange;
import mobi.mateam.alarma.weather.model.params.implementation.units.NoUnits;

/**
 * Created by Des63rus on 3/9/2017.
 */

public class SnowRange extends WeatherParamRange<SnowType, NoUnits> {

    public SnowRange(SnowType minValue, SnowType maxValue) {
        super(SnowType.NO_SNOW, maxValue);
    }

    @Override
    public ParameterType getParametrType() {
        return ParameterType.SNOW;
    }

    @Override
    public boolean checkIfInRange(SnowType currentValue) {
        return currentValue.getRank() <= getMaxValue().getRank();
    }
}
