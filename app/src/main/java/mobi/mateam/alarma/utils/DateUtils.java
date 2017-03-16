package mobi.mateam.alarma.utils;

import android.content.Context;
import java.text.NumberFormat;
import java.util.Calendar;
import mobi.mateam.alarma.R;

public class DateUtils {
  /**
   * format "Alarm set for 2 days, 7 hours, and 53 minutes from now."
   */
  public static String formatElapsedTimeUntilAlarm(Context context, long delta) {
    delta = delta - Calendar.getInstance().getTimeInMillis();
    // If the alarm will ring within 60 seconds, just report "less than a minute."
    final String[] formats = context.getResources().getStringArray(R.array.alarm_set);
    if (delta < android.text.format.DateUtils.MINUTE_IN_MILLIS) {
      return formats[0];
    }

    // Otherwise, format the remaining time until the alarm rings.

    // Round delta upwards to the nearest whole minute. (e.g. 7m 58s -> 8m)
    final long remainder = delta % android.text.format.DateUtils.MINUTE_IN_MILLIS;
    delta += remainder == 0 ? 0 : (android.text.format.DateUtils.MINUTE_IN_MILLIS - remainder);

    int hours = (int) delta / (1000 * 60 * 60);
    final int minutes = (int) delta / (1000 * 60) % 60;
    final int days = hours / 24;
    hours = hours % 24;

    String daySeq = getNumberFormattedQuantityString(context, R.plurals.days, days);
    String minSeq = getNumberFormattedQuantityString(context, R.plurals.minutes, minutes);
    String hourSeq = getNumberFormattedQuantityString(context, R.plurals.hours, hours);

    final boolean showDays = days > 0;
    final boolean showHours = hours > 0;
    final boolean showMinutes = minutes > 0;

    // Compute the index of the most appropriate time format based on the time delta.
    final int index = (showDays ? 1 : 0) | (showHours ? 2 : 0) | (showMinutes ? 4 : 0);

    return String.format(formats[index], daySeq, hourSeq, minSeq);
  }

  public static String getNumberFormattedQuantityString(Context context, int id, int quantity) {
    final String localizedQuantity = NumberFormat.getInstance().format(quantity);
    return context.getResources().getQuantityString(id, quantity, localizedQuantity);
  }
}
