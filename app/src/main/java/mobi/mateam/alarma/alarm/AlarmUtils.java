package mobi.mateam.alarma.alarm;

import java.util.Calendar;
import mobi.mateam.alarma.alarm.model.Alarm;

import static java.util.Calendar.DAY_OF_WEEK;

public class AlarmUtils {
  public static long getNextAlarmFire(Alarm alarm) {
    return getNextAlarmTime(alarm, Calendar.getInstance()).getTimeInMillis();
  }

  public static int getDistanceToNextDay(Alarm alarm, Calendar time) {
    if (alarm.weekdays == null) {
      return -1;
    }

    int calendarDay = time.get(DAY_OF_WEEK);
    for (int count = 0; count < 7; count++) {
      if (isWeekDayOn(alarm, calendarDay)) {
        return count;
      }

      calendarDay++;
      if (calendarDay > Calendar.SATURDAY) {
        calendarDay = Calendar.SUNDAY;
      }
    }

    return -1;
  }

  private static boolean isWeekDayOn(Alarm alarm, int calendarDay) {

    return alarm.weekdays[calendarDay - 1] != 0;
  }

  public static Calendar getNextAlarmTime(Alarm alarm, Calendar currentTime) {
    final Calendar nextInstanceTime = Calendar.getInstance(currentTime.getTimeZone());
    nextInstanceTime.set(Calendar.YEAR, currentTime.get(Calendar.YEAR));
    nextInstanceTime.set(Calendar.MONTH, currentTime.get(Calendar.MONTH));
    nextInstanceTime.set(Calendar.DAY_OF_MONTH, currentTime.get(Calendar.DAY_OF_MONTH));
    nextInstanceTime.set(Calendar.HOUR_OF_DAY, alarm.hour);
    nextInstanceTime.set(Calendar.MINUTE, alarm.minutes);
    nextInstanceTime.set(Calendar.SECOND, 0);
    nextInstanceTime.set(Calendar.MILLISECOND, 0);

    // If we are still behind the passed in currentTime, then add a day
    if (nextInstanceTime.getTimeInMillis() <= currentTime.getTimeInMillis()) {
      nextInstanceTime.add(Calendar.DAY_OF_YEAR, 1);
    }

    // The day of the week might be invalid, so find next valid one
    final int addDays = getDistanceToNextDay(alarm, nextInstanceTime);
    if (addDays > 0) {
      nextInstanceTime.add(Calendar.DAY_OF_WEEK, addDays);
    }

    // Daylight Savings Time can alter the hours and minutes when adjusting the day above.
    // Reset the desired hour and minute now that the correct day has been chosen.
    nextInstanceTime.set(Calendar.HOUR_OF_DAY, alarm.hour);
    nextInstanceTime.set(Calendar.MINUTE, alarm.minutes);

    return nextInstanceTime;
  }
}
