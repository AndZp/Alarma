package mobi.mateam.alarma.weekdays;

import java.util.Calendar;

public enum Weekday {

  SUNDAY("SUNDAY", Calendar.SUNDAY), MONDAY("MONDAY", Calendar.MONDAY), TUESDAY("TUESDAY", Calendar.TUESDAY), WEDNESDAY("WEDNESDAY",
      Calendar.WEDNESDAY), THURSDAY("THURSDAY", Calendar.THURSDAY), FRIDAY("FRIDAY", Calendar.FRIDAY), SATURDAY("SATURDAY", Calendar.SATURDAY);

  private final String name;
  private final int id;

  Weekday(String name, int calendarId) {
    this.name = name;
    this.id = calendarId;
  }

  public static Weekday getById(int id) {
    for (Weekday day : values()) {
      if (day.id == id) return day;
    }
    return null;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }
}
