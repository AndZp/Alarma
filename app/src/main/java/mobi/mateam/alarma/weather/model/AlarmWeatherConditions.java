package mobi.mateam.alarma.weather.model;

import java.util.HashMap;
import java.util.Map;

import mobi.mateam.alarma.weather.model.params.ProblemParam;
import mobi.mateam.alarma.weather.model.params.WeatherParamRange;
import mobi.mateam.alarma.weather.model.params.implementation.ranges.TemperatureRange;
import mobi.mateam.alarma.weather.model.params.implementation.ranges.WindPowerRange;

/**
 * Created by Des63rus on 3/9/2017.
 * <p>
 * Class to store all weather conditions in terms of weather params
 */

public class AlarmWeatherConditions {

    private Map<ParameterType, WeatherParamRange> weatherConditionsMap;

    public void addParam(WeatherParamRange range) {
        if (weatherConditionsMap == null) {
            weatherConditionsMap = new HashMap<>();
        }
        weatherConditionsMap.put(range.getParametrType(), range);
    }

    public Map<ParameterType, ProblemParam> checkWeather(CurrentWeatherState weatherState) {
        Map<ParameterType, ProblemParam> result = new HashMap<>();
        weatherConditionsMap
                .keySet()
                .stream()
                //getting all elemnts from weather conditions and check if current value in range
                .filter(parametrType -> !weatherConditionsMap.get(parametrType).checkIfInRange(weatherState.getWeatherParam(parametrType).getValue()))
                .forEach(parametrType -> {
                    result.put(parametrType, new ProblemParam());
                });
        if (result.size() == 0) {
            //no problems found
            return null;
        }
        return result;
    }


    public static AlarmWeatherConditions getTestConditions(){
        AlarmWeatherConditions alarmWeatherConditions = new AlarmWeatherConditions();
        alarmWeatherConditions.addParam(new TemperatureRange(10, 20));
        alarmWeatherConditions.addParam(new WindPowerRange(10.0, 20.0));
        return alarmWeatherConditions;
    }
}
