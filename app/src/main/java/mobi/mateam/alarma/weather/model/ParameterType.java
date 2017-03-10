package mobi.mateam.alarma.weather.model;

/**
 * Created by Des63rus on 3/9/2017.
 * <p>
 * Enum for weather params types
 */

public enum ParameterType {

    TEMPERATURE("Temperature"), WIND_POWER("Wind power"), WIND_DIRECTION("Wind Direction"), PRESSURE("Pressure"), CLOUDS("Clouds");

    private String name;


    ParameterType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

