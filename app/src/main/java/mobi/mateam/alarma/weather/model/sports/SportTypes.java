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
    RUNNING("Jogging", 0, ParameterType.TEMPERATURE, ParameterType.RAIN, ParameterType.WIND_POWER),
    CYCLING("Cycling", 1, ParameterType.TEMPERATURE, ParameterType.RAIN, ParameterType.WIND_POWER),
    SNOWBOARDING("Snowboarding", 2, ParameterType.TEMPERATURE, ParameterType.RAIN, ParameterType.WIND_POWER),
    OTHERS("Others", 3, ParameterType.TEMPERATURE, ParameterType.RAIN, ParameterType.WIND_POWER);

    private String text;

    private List<ParameterType> defaultParams;

    private int imageId;

    SportTypes(String text,int imageId,  ParameterType... params) {
        this.text = text;
        if (params == null && params.length == 0) {
            this.defaultParams = Arrays.asList();
        }
        this.defaultParams = Arrays.asList(params);
    }

    public String getText() {
        return text;
    }

    public int getImageId() {
        return imageId;
    }
}
