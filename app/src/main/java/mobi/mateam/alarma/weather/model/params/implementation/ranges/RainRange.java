package mobi.mateam.alarma.weather.model.params.implementation.ranges;

import mobi.mateam.alarma.weather.model.ParameterType;
import mobi.mateam.alarma.weather.model.params.RainType;
import mobi.mateam.alarma.weather.model.params.WeatherParamRange;

/**
 * Created by Des63rus on 3/9/2017.
 */

public class RainRange extends WeatherParamRange<RainType> {

    public RainRange(RainType minValue, RainType maxValue) {
        super(RainType.NO_RAIN, maxValue);
    }

    @Override
    public ParameterType getParametrType() {
        return ParameterType.RAIN;
    }

    @Override
    public boolean checkIfInRange(RainType currentValue) {
        return currentValue.getRank() <= getMaxValue().getRank();
    }
}
