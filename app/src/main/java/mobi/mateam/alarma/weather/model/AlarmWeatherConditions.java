package mobi.mateam.alarma.weather.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
            weatherConditionsMap = new LinkedHashMap<>();
        }
        weatherConditionsMap.put(range.getParametrType(), range);
    }

    /**
     * @param weatherState
     *      current weather state
     * @return
     *      map -> ParametrType(Temperature, pressure, etc.), ProblemParam
     */
    public Map<ParameterType, ProblemParam> checkWeather(CurrentWeatherState weatherState) {
      //TODO: Check and fix

      Map<ParameterType, ProblemParam> result = new LinkedHashMap<>();

      for (Map.Entry<ParameterType, WeatherParamRange> entry : weatherConditionsMap.entrySet()) {
        boolean inRange = weatherConditionsMap.get(entry.getKey()).checkIfInRange(weatherState.getWeatherParam(entry.getKey()).getValue());
        if (!inRange) {
          result.put(entry.getKey(), new ProblemParam());
        }
      }

        /*weatherConditionsMap
                .keySet()
                .stream()
                //getting all elements from weather conditions and filter problematic ones
                .filter(parametrType -> !weatherConditionsMap.get(parametrType).checkIfInRange(weatherState.getWeatherParam(parametrType).getValue()))
                .forEach(parametrType -> {
                    result.put(parametrType, new ProblemParam());
                });*/
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

    /**
     * @return
     *      List of param range objects
     */
    public List<WeatherParamRange> getParamsList(){
        if(weatherConditionsMap!=null){
            return new ArrayList<>(weatherConditionsMap.values());
        }
        return null;
    }
}
