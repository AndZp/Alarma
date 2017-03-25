package mobi.mateam.alarma.view.interfaces;

import java.util.List;
import mobi.mateam.alarma.weather.model.params.WeatherParamRange;
import mobi.mateam.alarma.weather.model.params.implementation.ranges.WindDirectionRange;
import mobi.mateam.alarma.weather.model.params.implementation.ranges.WindSpeedRange;

public interface OnWeatherParamListener {
  void onParamChange(List<WeatherParamRange> weatherParameters);

  void onWindParamClick(WindSpeedRange windSpeedRange, WindDirectionRange windDirectionRange);
}
