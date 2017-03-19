package mobi.mateam.alarma.alarm.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AlarmTest {
  Alarm alarm1;
  Alarm alarm2equalsAlarm1;
  Alarm alarm3;

  @Before public void setUp() {
    alarm1 = new Alarm();
    alarm1.id = "111";
    alarm1.minutes = 55;
    alarm1.hour = 12;

    alarm2equalsAlarm1 = new Alarm();
    alarm2equalsAlarm1.id = "111";
    alarm2equalsAlarm1.minutes = 55;
    alarm2equalsAlarm1.hour = 12;

    alarm3 = new Alarm();
    alarm3.id = "222";
    alarm3.minutes = 15;
    alarm3.hour = 1;
  }

  @Test public void equals_true() throws Exception {
    assertTrue(alarm1.equals(alarm2equalsAlarm1));
  }

  @Test public void equals_false() throws Exception {
    assertFalse(alarm1.equals(alarm3));
  }
}