package mobi.mateam.alarma.weather;


import java.util.Map;

import mobi.mateam.alarma.weather.model.AlarmWeatherConditions;
import mobi.mateam.alarma.weather.model.CurrentWeatherState;
import mobi.mateam.alarma.weather.model.ParameterType;
import mobi.mateam.alarma.weather.model.WeatherCheckResponse;
import mobi.mateam.alarma.weather.model.WeatherData;
import mobi.mateam.alarma.weather.model.params.ProblemParam;

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
}
