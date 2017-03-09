package mobi.mateam.alarma.weather.model.params;

import mobi.mateam.alarma.weather.model.ParameterType;

/**
 * Created by Des63rus on 3/9/2017.
 * General class for weather params
 */

public abstract class WeatherParamRange<T> extends WeatherParamGeneral<T>{

    /**
     *  minimum limit value for weather param
     */
    private T minValue;

    /**
     *  maximum limit value for weather param
     */
    private T maxValue;


    private String name;

    public T getMinValue() {
        return minValue;
    }

    public void setMinValue(T minValue) {
        this.minValue = minValue;
    }

    public T getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(T maxValue) {
        this.maxValue = maxValue;
    }


}
