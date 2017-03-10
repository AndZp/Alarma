package mobi.mateam.alarma.weather;


import java.util.Map;

import mobi.mateam.alarma.weather.model.AlarmWeatherConditions;
import mobi.mateam.alarma.weather.model.CurrentWeatherState;
import mobi.mateam.alarma.weather.model.ParameterType;
import mobi.mateam.alarma.weather.model.WeatherCheckResponse;
import mobi.mateam.alarma.weather.model.WeatherData;
import mobi.mateam.alarma.weather.model.params.ProblemParam;
import mobi.mateam.alarma.weather.model.params.RainType;
import mobi.mateam.alarma.weather.model.params.SnowType;
import mobi.mateam.alarma.weather.model.params.WeatherParamRange;
import mobi.mateam.alarma.weather.model.params.implementation.ranges.RainRange;
import mobi.mateam.alarma.weather.model.params.implementation.ranges.SnowRange;
import mobi.mateam.alarma.weather.model.params.implementation.ranges.TemperatureRange;
import mobi.mateam.alarma.weather.model.params.implementation.ranges.WindPowerRange;

public class WeatherManager {

    /**
     * @param currentWeatherData
     *      current weather data in API format
     * @param alarmWeatherConditions
     *      alarm weather conditions
     * @return
     *      Weather check response - contains data isSutable and map of problems if not sutable
     */
    public static WeatherCheckResponse checkTheWeather(WeatherData currentWeatherData, AlarmWeatherConditions alarmWeatherConditions){
        CurrentWeatherState currentWeatherState = new CurrentWeatherState(currentWeatherData);
        //convert api weather data to our object with weather params
        Map<ParameterType, ProblemParam> problems = alarmWeatherConditions.checkWeather(currentWeatherState);
        //get problematic params and return response with date about isSutable and problems
        return new WeatherCheckResponse(problems);
    }

    /**
     * @param type
     *      type of parameter
     * @return
     *      default value for current type
     */
    public static WeatherParamRange getDefaultValueForParam(ParameterType type){
        switch (type){
            case RAIN:{
                return new RainRange(RainType.NO_RAIN, RainType.NO_RAIN);
            }
            case TEMPERATURE: {
                return new TemperatureRange(15, 25);
            }
            case WIND_POWER: {
                return new WindPowerRange(18.0, 30.0);
            }
            case SNOW:{
                return new SnowRange(SnowType.NO_SNOW, SnowType.LIGHT_SNOW);
            }
            default:return null;
        }
    }
}
