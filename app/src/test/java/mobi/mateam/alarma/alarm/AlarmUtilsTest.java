package mobi.mateam.alarma.alarm;

import java.util.Calendar;
import mobi.mateam.alarma.alarm.model.Alarm;
import org.junit.Before;
import org.junit.Test;

public class AlarmUtilsTest {
  @Test public void getNextAlarmTime() throws Exception {

  }

  Alarm emptyAlarm;
  Alarm fullWeakAlarm;

  @Before public void setUp() {
    emptyAlarm = new Alarm();
    emptyAlarm.weekdays = new int[] { 0, 0, 0, 0, 0, 0, 0 };

    fullWeakAlarm = new Alarm();
    fullWeakAlarm.weekdays = new int[] { 1, 1, 1, 1, 1, 1, 1 };
    fullWeakAlarm.hour = 01;
    fullWeakAlarm.minutes = 59;
  }

  @Test public void getNextAlarmFire() throws Exception {
    Calendar calendar = AlarmUtils.getNextAlarmTime(fullWeakAlarm, Calendar.getInstance());
    long dif = calendar.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
  }

  @Test public void getDistanceToNextDay() throws Exception {
    int distance = AlarmUtils.getDistanceToNextDay(fullWeakAlarm, Calendar.getInstance());
  }
}