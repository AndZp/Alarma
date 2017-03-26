package mobi.mateam.alarma.weather.model.params;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WindDirectionTypeTest {
  @Test public void getAverageDegree_N() throws Exception {
    assertEquals(WindDirectionType.N.getAverageDegree(), 0.0, 0);
  }

  @Test public void getAverageDegree_S() throws Exception {
    assertEquals(WindDirectionType.S.getAverageDegree(), 180, 1);
  }

  @Test public void getDir_North() throws Exception {
    WindDirectionType windDirectionType = WindDirectionType.getDirection(0);
    assertEquals(WindDirectionType.N, windDirectionType);
  }

  @Test public void getDir_East() throws Exception {
    WindDirectionType windDirectionType = WindDirectionType.getDirection(85);
    assertEquals(WindDirectionType.E, windDirectionType);
  }

  @Test public void getDir_South() throws Exception {
    WindDirectionType windDirectionType = WindDirectionType.getDirection(181);
    assertEquals(WindDirectionType.S, windDirectionType);
  }

  @Test public void getDir_West() throws Exception {
    WindDirectionType windDirectionType = WindDirectionType.getDirection(260);
    assertEquals(WindDirectionType.W, windDirectionType);
  }
}