package mobi.mateam.alarma.weather.model;

/**
 * Created by Des63rus on 3/9/2017.
 * <p>
 * Enum for weather params types
 */

public enum ParameterType {

    //TODO: Globalize or move to strings names

    TEMPERATURE("Temperature"), WIND_POWER("Wind power"), WIND_DIRECTION("Wind Direction"), PRESSURE("Pressure"), CLOUDS("Clouds"),
    RAIN("Rain");

    private String name;


    ParameterType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

