package mobi.mateam.alarma.alarm;

import java.util.Calendar;
import mobi.mateam.alarma.alarm.model.Alarm;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AlarmUtilsTest {
  private Alarm emptyAlarm;
  private Alarm fullWeakAlarm;
  private Alarm notRepeatAlarm;

  @Before public void setUp() {
    emptyAlarm = new Alarm();
    emptyAlarm.weekdays = new int[] { 0, 0, 0, 0, 0, 0, 0 };

    fullWeakAlarm = new Alarm();
    fullWeakAlarm.weekdays = new int[] { 1, 1, 1, 1, 1, 1, 1 };
    fullWeakAlarm.hour = 01;
    fullWeakAlarm.minutes = 59;

    notRepeatAlarm = new Alarm();
  }

  @Test public void getNextAlarmTime() throws Exception {

  }

  @Test public void isRepeatAlarm_fullWeak() throws Exception {
    assertTrue(AlarmUtils.isRepeatAlarm(fullWeakAlarm));
  }

  @Test public void isRepeatAlarm_notRepeatAlarm() throws Exception {
    assertFalse(AlarmUtils.isRepeatAlarm(notRepeatAlarm));
  }

  @Test public void getNextAlarmFire() throws Exception {
    Calendar calendar = AlarmUtils.getNextAlarmTime(fullWeakAlarm, Calendar.getInstance());
    long dif = calendar.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
  }

  @Test public void getDistanceToNextDay() throws Exception {
    int distance = AlarmUtils.getDistanceToNextDay(fullWeakAlarm, Calendar.getInstance());
  }
}