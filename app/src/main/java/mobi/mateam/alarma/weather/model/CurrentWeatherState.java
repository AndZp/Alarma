package mobi.mateam.alarma.weather.model;

import java.util.HashMap;
import java.util.Map;

import mobi.mateam.alarma.weather.model.params.WeatherParamValue;
import mobi.mateam.alarma.weather.model.params.implementation.TemperatureParam;
import mobi.mateam.alarma.weather.model.params.implementation.WindPowerParam;

/**
 * Created by Des63rus on 3/9/2017.
 *
 * curent weather state in terms of parameters
 *
 */

public class CurrentWeatherState {

    private Map<ParameterType, WeatherParamValue> weatherParameterMap;

    public CurrentWeatherState(){
        this.weatherParameterMap = new HashMap<>();
    }
    public CurrentWeatherState(WeatherData data){
        weatherParameterMap = new HashMap<>();
        weatherParameterMap.put(ParameterType.TEMPERATURE, new TemperatureParam(data.getMain().getTemp().intValue()));
        weatherParameterMap.put(ParameterType.WIND_POWER, new WindPowerParam(data.getWind().getSpeed()));
    }

    public Map<ParameterType, WeatherParamValue> getWeatherParameterMap() {
        return weatherParameterMap;
    }

    public WeatherParamValue getWeatherParam(ParameterType type){
       return weatherParameterMap.get(type);
    }

    public void addParam(WeatherParamValue value){
        if(weatherParameterMap == null){
            weatherParameterMap = new HashMap<>();
        }
        weatherParameterMap.put(value.getParametrType(), value);
    }
}