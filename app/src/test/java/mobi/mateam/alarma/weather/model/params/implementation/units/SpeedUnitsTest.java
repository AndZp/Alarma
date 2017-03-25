package mobi.mateam.alarma.weather.model.params.implementation.units;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SpeedUnitsTest {
  //1 Meters Per Second to Miles Per Hour = 2.23694
  public static final double ONE_METER_PER_SEC_IN_MHP = 2.23694;
  public static final double ONE_MILES_PER_HOUR_IN_METER_PER_SEC = 0.44704074119232;

  @Test public void convertToDefault_MilesHour() throws Exception {
    double result = SpeedUnits.convertToDefault(SpeedUnits.MILESHOUR, 1);
    assertTrue(ONE_MILES_PER_HOUR_IN_METER_PER_SEC == result);
  }

  @Test public void convertToDefault_MeterSec() throws Exception {
    double result = SpeedUnits.convertToDefault(SpeedUnits.METERSEC, 1);
    assertTrue(result == 1);
  }

  @Test public void convertToUserUnit_MilesHour() throws Exception {
    double result = SpeedUnits.convertToUserUnit(SpeedUnits.MILESHOUR, 1);
    assertTrue(ONE_METER_PER_SEC_IN_MHP == result);
  }

  @Test public void convertToUserUnit_MeterSec() throws Exception {
    double result = SpeedUnits.convertToUserUnit(SpeedUnits.METERSEC, 1);
    assertTrue(result == 1);
  }
}