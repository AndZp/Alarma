package mobi.mateam.alarma.weather.model;

public class WeatherParameter {

  private String name = "Default name";
  private String minValue = "Min";
  private String maxValue = "Max";
  private long id;

  public String getName() {
    return name;
  }

  public String getMinValue() {
    return minValue;
  }

  public String getMaxValue() {
    return maxValue;
  }

  public long getId() {
    return id;
  }
}
