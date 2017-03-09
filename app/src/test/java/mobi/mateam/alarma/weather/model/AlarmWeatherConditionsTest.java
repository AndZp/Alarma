package mobi.mateam.alarma.weather.model;

import org.junit.Before;
import org.junit.Test;

import mobi.mateam.alarma.weather.model.params.implementation.TemperatureParam;
import mobi.mateam.alarma.weather.model.params.implementation.TemperatureRange;
import mobi.mateam.alarma.weather.model.params.implementation.WindPowerParam;
import mobi.mateam.alarma.weather.model.params.implementation.WindPowerRange;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Des63rus on 3/9/2017.
 */
public class AlarmWeatherConditionsTest {

    private AlarmWeatherConditions conditions = new AlarmWeatherConditions();

    @Before
    public void setup(){
        conditions.addParam(new TemperatureRange(10,20));
        conditions.addParam(new WindPowerRange(10.0, 20.0));
    }

    @Test
    public void checkWeather() throws Exception {
        CurrentWeatherState sutableState = new CurrentWeatherState();
        sutableState.addParam(new TemperatureParam(15));
        sutableState.addParam(new WindPowerParam(15.0));
        CurrentWeatherState notSutable = new CurrentWeatherState();
        notSutable.addParam(new TemperatureParam(25));
        notSutable.addParam(new WindPowerParam(15.0));
        assertTrue(conditions.checkWeather(sutableState));
        assertFalse(conditions.checkWeather(notSutable));
    }

}