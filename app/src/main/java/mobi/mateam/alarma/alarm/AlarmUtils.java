package mobi.mateam.alarma.alarm;

import android.content.Context;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.OpenableColumns;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.alarm.model.Alarm;

import static java.util.Calendar.DAY_OF_WEEK;

public class AlarmUtils {

  public static String getTimeStrFromAlarm(Alarm alarm) {
    Date alarmDate = new Date(getNextAlarmTime(alarm, Calendar.getInstance()).getTimeInMillis());
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    return sdf.format(alarmDate);
  }

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

  public static boolean isRepeatAlarm(Alarm alarm) {
    return alarm.weekdays != null;
  }

  public static String getFileName(Uri uri, Context context) {
    String result = context.getString(R.string.default_alarm_text);
    if (uri == null || uri.equals(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))) {
      return result;
    }
    if (uri.getScheme().equals("content")) {
      Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
      try {
        if (cursor != null && cursor.moveToFirst()) {
          result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
        }
      } finally {
        cursor.close();
      }
    }
    if (result == null) {
      result = uri.getPath();
      int cut = result.lastIndexOf('/');
      if (cut != -1) {
        result = result.substring(cut + 1);
      }
    }
    if (result.contains(".")) {
      int endIndex = result.indexOf(".");
      result = result.substring(0, endIndex);
    }

    if (result.contains("_")) {
      result = result.replaceAll("_", " ");
    }
    return result;
  }

  public static int[] getRepeatDaysIndexArray(Alarm alarm) {
    ArrayList<Integer> result = new ArrayList<>();
    for (int i = 0; i < alarm.weekdays.length; i++) {
      if (alarm.weekdays[i] == 1) {
        result.add(i);
      }
    }

    int[] res = new int[result.size()];
    for (int i = 0; i < result.size(); i++) {
      res[i] = result.get(i);
    }
    return res;
  }
}
