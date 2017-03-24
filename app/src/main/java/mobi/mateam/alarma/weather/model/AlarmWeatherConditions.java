package mobi.mateam.alarma.weather.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import mobi.mateam.alarma.weather.model.params.ProblemParam;
import mobi.mateam.alarma.weather.model.params.WeatherParamRange;
import mobi.mateam.alarma.weather.model.params.implementation.ranges.TemperatureRange;
import mobi.mateam.alarma.weather.model.params.implementation.ranges.WindSpeedRange;
import mobi.mateam.alarma.weather.model.params.implementation.units.TemperatureUnits;
import mobi.mateam.alarma.weather.model.params.implementation.units.WindUnits;

/**
 * Created by Des63rus on 3/9/2017.
 * <p>
 * Class to store all weather conditions in terms of weather params
 */

public class AlarmWeatherConditions {

  private Map<ParameterType, WeatherParamRange> weatherConditionsMap;

  public static AlarmWeatherConditions getTestConditions() {
    AlarmWeatherConditions alarmWeatherConditions = new AlarmWeatherConditions();
    alarmWeatherConditions.addParam(new TemperatureRange(TemperatureUnits.CELSIUM, 10, 20));
    alarmWeatherConditions.addParam(new WindSpeedRange(WindUnits.METERSEC, 10.0, 20.0));
    return alarmWeatherConditions;
  }

  public void addParam(WeatherParamRange range) {
    if (weatherConditionsMap == null) {
      weatherConditionsMap = new LinkedHashMap<>();
    }
    weatherConditionsMap.put(range.getParameterType(), range);
  }

  /**
   * @param weatherState current weather state
   * @return map -> ParametrType(Temperature, pressure, etc.), ProblemParam
   */
  public Map<ParameterType, ProblemParam> checkWeather(CurrentWeatherState weatherState) {
    //TODO: Check and fix

    Map<ParameterType, ProblemParam> result = new LinkedHashMap<>();
    // going over all params specified by user and check if current weather params are in range
    for (Map.Entry<ParameterType, WeatherParamRange> entry : weatherConditionsMap.entrySet()) {
      ParameterType key = entry.getKey();
      // type of param(Temperature, rain, etc)
      Comparable currentValue = weatherState.getWeatherParam(key).getValue();
      // current value for specified param
      WeatherParamRange weatherParamRange = weatherConditionsMap.get(key);
      // range specified by User
      if (!weatherParamRange.checkIfInRange(currentValue)) {
        result.put(key, new ProblemParam(weatherState.getWeatherParam(key), weatherParamRange));
      }
    }
    if (result.size() == 0) {
      //no problems found
      return null;
    }
    return result;
  }

  /**
   * @return List of param range objects
   */
  public List<WeatherParamRange> getParamsList() {
    if (weatherConditionsMap != null) {
      return new ArrayList<>(weatherConditionsMap.values());
    }
    return null;
  }

  public void setParamList(List<WeatherParamRange> weatherParameters) {
    weatherConditionsMap = null;
    for (WeatherParamRange weatherParameter : weatherParameters) {
      addParam(weatherParameter);
    }
  }
}
