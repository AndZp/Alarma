package mobi.mateam.alarma.weather.model;

/**
 * Created by Des63rus on 3/9/2017.
 */

public class WeatherCheckResponse{

    /**
     * true if weather state is sutable
     */
    private boolean isSutableWeather;

    /**
     * current weather state in terms of weather params
     */
    private CurrentWeatherState state;

    public boolean isSutableWeather() {
        return isSutableWeather;
    }

    public void setSutableWeather(boolean sutableWeather) {
        isSutableWeather = sutableWeather;
    }

    public CurrentWeatherState getState() {
        return state;
    }

    public void setState(CurrentWeatherState state) {
        this.state = state;
    }
}
