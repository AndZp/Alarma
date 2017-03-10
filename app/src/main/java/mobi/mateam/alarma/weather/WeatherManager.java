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
        Map<ParameterType, ProblemParam> problems = alarmWeatherConditions.checkWeather(currentWeatherState);
        return new WeatherCheckResponse(problems);
    }
}
