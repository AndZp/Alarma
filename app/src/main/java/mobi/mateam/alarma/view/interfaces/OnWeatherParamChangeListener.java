package mobi.mateam.alarma.view.interfaces;

import java.util.List;
import mobi.mateam.alarma.weather.model.params.WeatherParamRange;

public interface OnWeatherParamChangeListener {
  void onParamChange(List<WeatherParamRange> weatherParameters);
}
