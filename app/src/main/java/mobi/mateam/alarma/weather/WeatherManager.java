package mobi.mateam.alarma.weather;


import mobi.mateam.alarma.weather.model.AlarmWeatherConditions;
import mobi.mateam.alarma.weather.model.WeatherCheckResponse;
import mobi.mateam.alarma.weather.model.WeatherData;
import rx.Observable;

public interface WeatherManager {

    /**
     * @param currentWeather
     * @param alarmWeatherConditions
     * @return
     */
    public Observable<WeatherCheckResponse> checkTheWeather(WeatherData currentWeather, AlarmWeatherConditions alarmWeatherConditions);
}
