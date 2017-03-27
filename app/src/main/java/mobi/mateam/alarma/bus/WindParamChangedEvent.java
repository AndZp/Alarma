package mobi.mateam.alarma.bus;

import mobi.mateam.alarma.weather.model.params.implementation.ranges.WindDirectionRange;
import mobi.mateam.alarma.weather.model.params.implementation.ranges.WindSpeedRange;

public class WindParamChangedEvent extends Event {
  public final WindSpeedRange windDirectionRange;
  public final WindSpeedRange windSpeedRange;

  public WindParamChangedEvent(WindSpeedRange windSpeedRange, WindDirectionRange windDirectionRange) {
    super(WIND_PARAM_CHANGED);
    this.windSpeedRange = windSpeedRange;
    this.windDirectionRange = windSpeedRange;
  }
}
