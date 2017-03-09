package mobi.mateam.alarma.weather.model.params;

/**
 * Created by Des63rus on 3/9/2017.
 *
 * Class for current value for weather param
 */

public abstract class WeatherParamValue<T extends Comparable<T>> extends WeatherParamGeneral<T>{

    private T value;

    public WeatherParamValue(T value){
        setValue(value);
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
