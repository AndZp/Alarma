package mobi.mateam.alarma.weather.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import mobi.mateam.alarma.weather.WeatherManager;
import mobi.mateam.alarma.weather.model.params.ProblemParam;
import mobi.mateam.alarma.weather.model.params.WeatherParamRange;
import mobi.mateam.alarma.weather.model.params.implementation.ranges.TemperatureRange;
import mobi.mateam.alarma.weather.model.params.implementation.ranges.WindSpeedRange;
import mobi.mateam.alarma.weather.model.params.implementation.units.SpeedUnits;
import mobi.mateam.alarma.weather.model.params.implementation.units.TemperatureUnits;

/**
 * Created by Des63rus on 3/9/2017.
 * <p>
 * Class to store all weather conditions in terms of weather params
 */

public class AlarmWeatherConditions {

  private Map<ParameterType, WeatherParamRange> weatherConditionsMap;

  public static AlarmWeatherConditions getTestConditions() {
    AlarmWeatherConditions alarmWeatherConditions = new AlarmWeatherConditions();
    alarmWeatherConditions.addParam(new TemperatureRange(TemperatureUnits.CELSIUS, 10, 20));
    alarmWeatherConditions.addParam(new WindSpeedRange(SpeedUnits.METERSEC, 10, 20));
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
      ArrayList<WeatherParamRange> weatherParamRanges = new ArrayList<>(weatherConditionsMap.values());

      weatherParamRanges.sort((first, second) -> {
        int dif = first.getParameterType().getId() - second.getParameterType().getId();
        if (dif > 0) {
          return 1;
        } else if (dif < 0) {
          return -1;
        }
        return 0;
      });
      return weatherParamRanges;
    }
    return null;
  }

  public void setParamList(List<WeatherParamRange> weatherParameters) {
    weatherConditionsMap = null;
    for (WeatherParamRange weatherParameter : weatherParameters) {
      addParam(weatherParameter);
    }
  }

  public ArrayList<ParameterType> getParameterTypesList() {
    if (weatherConditionsMap != null) {
      return new ArrayList<>(weatherConditionsMap.keySet());
    }
    return null;
  }

  /**
   * The method is correct weather condition map depend on user choice
   * if parameter was set before  - it will exist in new map
   * if if is new parameter  - weather parameters will be set with default values
   */
  public void correctParamList(ArrayList<ParameterType> checkedParameters) {
    Map<ParameterType, WeatherParamRange> updatedWeatherConditionMap = new LinkedHashMap<>();
    for (ParameterType checkedParameter : checkedParameters) {
      WeatherParamRange weatherParamRange;
      if (weatherConditionsMap.containsKey(checkedParameter)) {
        weatherParamRange = weatherConditionsMap.get(checkedParameter);
      } else {
        weatherParamRange = WeatherManager.getDefaultValueForParam(checkedParameter);
      }
      updatedWeatherConditionMap.put(checkedParameter, weatherParamRange);
    }
    this.weatherConditionsMap = updatedWeatherConditionMap;
  }
}
