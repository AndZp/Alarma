package mobi.mateam.alarma.weather.model;

/**
 * Created by Des63rus on 3/9/2017.
 */

public class WeatherCheckResponse{

    private boolean isSutableWeather;

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
