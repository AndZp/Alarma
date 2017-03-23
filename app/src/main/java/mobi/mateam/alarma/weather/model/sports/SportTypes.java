package mobi.mateam.alarma.weather.model.sports;

import java.util.Arrays;
import java.util.List;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.weather.model.ParameterType;

/**
 * Created by Des63rus on 3/10/2017.
 *
 * Enum for sport types with default params
 */

public enum SportTypes {
  //TODO: move to strings all text values
  SUMMER_SPORT("Summer sport", 0, R.drawable.category_dialog_air, ParameterType.TEMPERATURE, ParameterType.RAIN,
      ParameterType.WIND_POWER), WATER_SPORT("Water sport", 1, R.drawable.category_dialog_water, ParameterType.TEMPERATURE, ParameterType.RAIN,
      ParameterType.WIND_POWER), WINTER_SPORT("Winter sport", 2, R.drawable.category_dialog_mount, ParameterType.TEMPERATURE, ParameterType.SNOW,
      ParameterType.WIND_POWER);
  //OTHERS("Others", 2, 3, ParameterType.TEMPERATURE, ParameterType.RAIN,  ParameterType.WIND_POWER);

  private int id;
  private String text;

  private List<ParameterType> defaultParams;

  private int imageId;

  SportTypes(String text, int id, int imageId, ParameterType... params) {
    this.text = text;
    this.id = id;
    this.imageId = imageId;
    if (params == null && params.length == 0) {
      this.defaultParams = Arrays.asList();
    }
    this.defaultParams = Arrays.asList(params);
  }

  public static SportTypes getById(int id) {
    for (SportTypes type : values()) {
      if (type.id == id) {
        return type;
      }
    }
    return null;
  }

  public String getText() {
    return text;
  }

  public int getImageId() {
    return imageId;
  }

  public List<ParameterType> getDefaultParams() {
    return defaultParams;
  }

  public int getId() {
    return this.id;
  }
}
