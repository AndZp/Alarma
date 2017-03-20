package mobi.mateam.alarma.weather.model;

import java.util.HashMap;
import java.util.Map;

import mobi.mateam.alarma.weather.model.params.WeatherParamValue;
import mobi.mateam.alarma.weather.model.params.implementation.RainParam;
import mobi.mateam.alarma.weather.model.params.implementation.TemperatureParam;
import mobi.mateam.alarma.weather.model.params.implementation.WindPowerParam;
import mobi.mateam.alarma.weather.model.params.implementation.units.TemperatureUnits;
import mobi.mateam.alarma.weather.model.params.implementation.units.WindUnits;

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
        weatherParameterMap.put(ParameterType.TEMPERATURE, new TemperatureParam(TemperatureUnits.KELVIN, data.getMain().getTemp().intValue()));
        weatherParameterMap.put(ParameterType.WIND_POWER, new WindPowerParam(WindUnits.MILESHOUR, data.getWind().getSpeed()));
        weatherParameterMap.put(ParameterType.RAIN, new RainParam(data.getRain() == null ? null : data.getRain().get3h()));
        weatherParameterMap.put(ParameterType.SNOW, new RainParam(data.getSnow() == null ? null : data.getSnow().get3h()));
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
