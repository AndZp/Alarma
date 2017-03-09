package mobi.mateam.alarma.alarm.model;

public class Weekdays {
  public static final Weekdays ALL = new Weekdays();
  String days = "Mon, Thu, Sut";

  @Override public String toString() {
    return days;
  }
}
