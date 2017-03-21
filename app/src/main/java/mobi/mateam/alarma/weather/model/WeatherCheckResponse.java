package mobi.mateam.alarma.weather.model;

import java.util.Map;
import mobi.mateam.alarma.weather.model.params.ProblemParam;

public class WeatherCheckResponse {

  /**
   * true if weather state is sutable
   */
  private boolean isSutableWeather;

  private Map<ParameterType, ProblemParam> problemParamMap;

  public WeatherCheckResponse(Map<ParameterType, ProblemParam> problemParamMap) {
    if (problemParamMap != null) {
      this.isSutableWeather = false;
      this.problemParamMap = problemParamMap;
    } else {
      this.isSutableWeather = true;
    }
  }

  public boolean isSutableWeather() {
    return isSutableWeather;
  }

  public void setSutableWeather(boolean sutableWeather) {
    isSutableWeather = sutableWeather;
  }

  public Map<ParameterType, ProblemParam> getProblemParamMap() {
    return problemParamMap;
  }
}
