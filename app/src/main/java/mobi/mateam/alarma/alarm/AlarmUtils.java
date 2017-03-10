package mobi.mateam.alarma.alarm;

import java.util.Calendar;
import mobi.mateam.alarma.alarm.model.Alarm;

public class AlarmUtils {
  public static long getNextAlarmFire(Alarm alarm) {
    return alarm.getNextAlarmTime(Calendar.getInstance()).getTimeInMillis() + 200000;
  }
}
