package mobi.mateam.alarma.weather.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Des63rus on 3/9/2017.
 * <p>
 * Enum for weather params types
 */

public enum ParameterType {

    //TODO: Globalize or move to strings names

    TEMPERATURE("Temperature"), WIND_POWER("Wind power"), WIND_DIRECTION("Wind Direction"), PRESSURE("Pressure"), CLOUDS("Clouds"),
    RAIN("Rain"), SNOW("Snow");

    private String name;


    ParameterType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * @param usedParams
     *      Params that are already in use
     * @return
     *      Params that are not used yet
     */
    public List<ParameterType> getNotUsedParams(List<ParameterType> usedParams){
        List<ParameterType> result = new ArrayList<>();
        for (ParameterType param: values()) {
            if(!usedParams.contains(param)){
                result.add(param);
            }
        }
    }
}

