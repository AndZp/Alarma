package mobi.mateam.alarma.bus;

public class Event {

  public static final int SET_ALARM = 1;
  public static final int SPORT_PICKED = 2;
  public static final int WIND_PARAM_CHANGED = 3;
  public static final int PARAM_LIST_CHANGED = 4;
  public static final int SETTINGS_CHANGED = 5;
  public int id = -1;

  Event(int id) {
    this.id = id;
  }








}