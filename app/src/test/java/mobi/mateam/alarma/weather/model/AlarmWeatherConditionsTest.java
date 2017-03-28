package mobi.mateam.alarma.weather.model;

import java.util.Map;
import mobi.mateam.alarma.weather.model.params.ProblemParam;
import mobi.mateam.alarma.weather.model.params.WindDirectionType;
import mobi.mateam.alarma.weather.model.params.implementation.TemperatureParam;
import mobi.mateam.alarma.weather.model.params.implementation.WindDirectionParam;
import mobi.mateam.alarma.weather.model.params.implementation.WindSpeedParam;
import mobi.mateam.alarma.weather.model.params.implementation.ranges.TemperatureRange;
import mobi.mateam.alarma.weather.model.params.implementation.ranges.WindDirectionRange;
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
    private AlarmWeatherConditions conditionsFahrengeitMilesHour = new AlarmWeatherConditions();

    @Before
    public void setup(){
        conditionsCelsiumMeterSec.addParam(new TemperatureRange(TemperatureUnits.CELSIUS, 10, 20));
        conditionsCelsiumMeterSec.addParam(new WindSpeedRange(SpeedUnits.METERSEC, 10, 20));
        conditionsFahrengeitMilesHour.addParam(new WindSpeedRange(SpeedUnits.MILESHOUR, 22, 44));
        conditionsFahrengeitMilesHour.addParam(new TemperatureRange(TemperatureUnits.FAHRENHEITS, 50, 68));
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
        assertTrue(conditionsFahrengeitMilesHour.checkWeather(sutableState) == null);
        Map<ParameterType, ProblemParam> parameterTypeProblemParamMap = conditionsCelsiumMeterSec.checkWeather(notSutable);
        assertTrue(parameterTypeProblemParamMap != null && parameterTypeProblemParamMap.size() == 1);
        Map<ParameterType, ProblemParam> parameterTypeProblemParamMapKelv = conditionsFahrengeitMilesHour.checkWeather(notSutable);
        assertTrue(parameterTypeProblemParamMapKelv != null && parameterTypeProblemParamMapKelv.size() == 1);
    }

  @Test public void addParamTest() {
    assertEquals(conditionsCelsiumMeterSec.getParamsList().size(), 2);
    TemperatureRange newRangeValue = new TemperatureRange(TemperatureUnits.CELSIUS, -20, 10);
    conditionsCelsiumMeterSec.addParam(newRangeValue);
    assertEquals(conditionsCelsiumMeterSec.getParamsList().size(), 2);
  }

    @Test public void windDirectionTest() {
        AlarmWeatherConditions windConditions = new AlarmWeatherConditions();
        WindDirectionRange windDirectionRange = new WindDirectionRange(WindDirectionType.W, WindDirectionType.E);
        windConditions.addParam(windDirectionRange);
        CurrentWeatherState suitable = new CurrentWeatherState();
        suitable.addParam(new WindDirectionParam(WindDirectionType.N));
        CurrentWeatherState notSuitable = new CurrentWeatherState();
        notSuitable.addParam(new WindDirectionParam(WindDirectionType.SSE));
        Map<ParameterType, ProblemParam> suitableProblems = windConditions.checkWeather(suitable);
        Map<ParameterType, ProblemParam> notSuitableProblems = windConditions.checkWeather(notSuitable);
        assertEquals(suitableProblems, null);
        assertEquals(notSuitableProblems.values().size(), 1);
    }

}