package mobi.mateam.alarma.weather.model.sports;

import java.util.Arrays;
import java.util.List;

import mobi.mateam.alarma.weather.model.ParameterType;

/**
 * Created by Des63rus on 3/10/2017.
 *
 * Enum for sport types with default params
 */

public enum SportTypes {
    //TODO: move to strings all text values
    RUNNING("Jogging", ParameterType.TEMPERATURE, ParameterType.RAIN, ParameterType.WIND_POWER),
    CYCLING("Cycling", ParameterType.TEMPERATURE, ParameterType.RAIN, ParameterType.WIND_POWER),
    SNOWBOARDING("Snowboarding", ParameterType.TEMPERATURE, ParameterType.RAIN, ParameterType.WIND_POWER),
    OTHERS("Others", ParameterType.TEMPERATURE, ParameterType.RAIN, ParameterType.WIND_POWER);

    private String text;

    private List<ParameterType> defaultParams;

    SportTypes(String text, ParameterType... params) {
        this.text = text;
        if (params == null && params.length == 0) {
            this.defaultParams = Arrays.asList();
        }
        this.defaultParams = Arrays.asList(params);
    }
}
