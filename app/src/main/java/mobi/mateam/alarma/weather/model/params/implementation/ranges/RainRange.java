package mobi.mateam.alarma.weather.model.params.implementation.ranges;

import mobi.mateam.alarma.R;
import mobi.mateam.alarma.weather.model.ParameterType;
import mobi.mateam.alarma.weather.model.params.RainType;
import mobi.mateam.alarma.weather.model.params.WeatherParamRange;
import mobi.mateam.alarma.weather.model.params.implementation.units.NoUnits;

/**
 * Created by Des63rus on 3/9/2017.
 */

public class RainRange extends WeatherParamRange<RainType, NoUnits> {

    public RainRange(RainType minValue, RainType maxValue) {
      super(RainType.NO_RAIN, maxValue, R.drawable.ic_rain);
    }

    @Override public ParameterType getParameterType() {
        return ParameterType.RAIN;
    }

    @Override
    public boolean checkIfInRange(RainType currentValue) {
        return currentValue.getRank() <= getMaxValue().getRank();
    }
}
