package mobi.mateam.alarma.bus;

import mobi.mateam.alarma.weather.model.sports.SportTypes;

public class SportPickedEvent extends Event {

  public final SportTypes sportType;

  public SportPickedEvent(SportTypes sportTypes) {
    super(SPORT_PICKED);
    this.sportType = sportTypes;
  }
}
