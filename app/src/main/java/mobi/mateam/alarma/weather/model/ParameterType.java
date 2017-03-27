package mobi.mateam.alarma.weather.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Des63rus on 3/9/2017.
 * <p>
 * Enum for weather params types
 */

public enum ParameterType implements Parcelable {

  //TODO: Globalize or move to strings names

  TEMPERATURE(0, "Temperature"), WIND_SPEED(1, "Wind speed"), WIND_DIRECTION(2, "Wind direction"), PRESSURE(3, "Pressure"), CLOUDS(4, "Clouds"), RAIN(
      5, "Rain"), SNOW(6, "Snow");

  public static final Creator<ParameterType> CREATOR = new Creator<ParameterType>() {
    @Override public ParameterType createFromParcel(Parcel in) {
      return getById(in.readInt());
    }

    @Override public ParameterType[] newArray(int size) {
      return new ParameterType[size];
    }
  };
  public final int id;
  private String name;

  ParameterType(int id, String name) {
    this.name = name;
    this.id = id;
  }

  public static ParameterType getById(int id) {
    for (ParameterType parameterType : values()) {
      if (parameterType.id == id) return parameterType;
    }
    return null;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(id);
  }

  @Override public int describeContents() {
    return 0;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  /**
   * @param usedParams Params that are already in use
   * @return Params that are not used yet
   */
  public List<ParameterType> getNotUsedParams(List<ParameterType> usedParams) {
    List<ParameterType> result = new ArrayList<>();
    for (ParameterType param : values()) {
      if (!usedParams.contains(param)) {
        result.add(param);
      }
    }
    return result;
  }
}

