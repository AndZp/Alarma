package mobi.mateam.alarma.weather.model.params.implementation;

import mobi.mateam.alarma.weather.model.ParameterType;
import mobi.mateam.alarma.weather.model.params.RainType;
import mobi.mateam.alarma.weather.model.params.WeatherParamValue;

/**
 * Created by Des63rus on 3/10/2017.
 *
 *  Weather param for rain types
 */

public class RainParam extends WeatherParamValue<RainType> {

    public RainParam(RainType value) {
        super(value);
    }

    @Override
    public ParameterType getParametrType() {
        return ParameterType.RAIN;
    }

    public RainParam(Integer rainMM){
        super(RainType.getRainTypeByMM(rainMM));
    }

}
