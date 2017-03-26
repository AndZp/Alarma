package mobi.mateam.alarma.weather.model;

import java.util.Map;
import mobi.mateam.alarma.weather.model.params.ProblemParam;
import mobi.mateam.alarma.weather.model.params.implementation.TemperatureParam;
import mobi.mateam.alarma.weather.model.params.implementation.WindSpeedParam;
import mobi.mateam.alarma.weather.model.params.implementation.ranges.TemperatureRange;
import mobi.mateam.alarma.weather.model.params.implementation.ranges.WindSpeedRange;
import mobi.mateam.alarma.weather.model.params.implementation.units.SpeedUnits;
import mobi.mateam.alarma.weather.model.params.implementation.units.TemperatureUnits;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by Des63rus on 3/9/2017.
 *
 * Test for logic of creation and checking of weather params
 */
public class AlarmWeatherConditionsTest {

    private AlarmWeatherConditions conditionsCelsiumMeterSec = new AlarmWeatherConditions();
    private AlarmWeatherConditions conditionsKelvinMilesHour = new AlarmWeatherConditions();

    @Before
    public void setup(){
      conditionsCelsiumMeterSec.addParam(new TemperatureRange(TemperatureUnits.CELSIUS, 10, 20));
      conditionsCelsiumMeterSec.addParam(new WindSpeedRange(SpeedUnits.METERSEC, 10, 20));
      conditionsKelvinMilesHour.addParam(new WindSpeedRange(SpeedUnits.MILESHOUR, 22, 44));
    }

    @Test
    public void checkWeather() throws Exception {
        CurrentWeatherState sutableState = new CurrentWeatherState();
      sutableState.addParam(new TemperatureParam(TemperatureUnits.CELSIUS, 15));
      sutableState.addParam(new WindSpeedParam(SpeedUnits.METERSEC, 15));
        CurrentWeatherState notSutable = new CurrentWeatherState();
      notSutable.addParam(new TemperatureParam(TemperatureUnits.CELSIUS, 25));
      notSutable.addParam(new WindSpeedParam(SpeedUnits.METERSEC, 15));
        assertTrue(conditionsCelsiumMeterSec.checkWeather(sutableState) == null);
        assertTrue(conditionsKelvinMilesHour.checkWeather(sutableState) == null);
        Map<ParameterType, ProblemParam> parameterTypeProblemParamMap = conditionsCelsiumMeterSec.checkWeather(notSutable);
        assertTrue(parameterTypeProblemParamMap != null && parameterTypeProblemParamMap.size()==1);
        Map<ParameterType, ProblemParam> parameterTypeProblemParamMapKelv = conditionsKelvinMilesHour.checkWeather(notSutable);
        assertTrue(parameterTypeProblemParamMapKelv != null && parameterTypeProblemParamMapKelv.size()==1);
    }

  @Test public void addParamTest() {
    assertEquals(conditionsCelsiumMeterSec.getParamsList().size(), 2);
    TemperatureRange newRangeValue = new TemperatureRange(TemperatureUnits.CELSIUS, -20, 10);
    conditionsCelsiumMeterSec.addParam(newRangeValue);
    assertEquals(conditionsCelsiumMeterSec.getParamsList().size(), 2);

    }

}