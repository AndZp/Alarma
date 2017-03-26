package mobi.mateam.alarma.bus;

import mobi.mateam.alarma.alarm.model.Alarm;
import mobi.mateam.alarma.weather.model.params.implementation.ranges.WindDirectionRange;
import mobi.mateam.alarma.weather.model.params.implementation.ranges.WindSpeedRange;
import mobi.mateam.alarma.weather.model.sports.SportTypes;

public class Event {

  private Event() {

  }

  public static class SetAlarm {

    public Alarm alarm;

    public SetAlarm(Alarm alarm) {
      this.alarm = alarm;
    }
  }

  public static class SportPicked {
    public final SportTypes sportType;

    public SportPicked(SportTypes sportTypes) {
      this.sportType = sportTypes;
    }
  }

  public static class SettingsChanged {
  }

  public static class WindParamChanged {
    public final WindSpeedRange windDirectionRange;
    public final WindSpeedRange windSpeedRange;

    public WindParamChanged(WindSpeedRange windSpeedRange, WindDirectionRange windDirectionRange) {
      this.windSpeedRange = windSpeedRange;
      this.windDirectionRange = windSpeedRange;
    }
  }
}