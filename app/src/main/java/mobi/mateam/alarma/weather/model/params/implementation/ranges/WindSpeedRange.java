package mobi.mateam.alarma.weather.model.params.implementation.ranges;

import android.os.Parcel;
import android.os.Parcelable;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.weather.model.ParameterType;
import mobi.mateam.alarma.weather.model.params.WeatherParamRange;
import mobi.mateam.alarma.weather.model.params.implementation.units.SpeedUnits;

/**
 * Created by Des63rus on 3/9/2017.
 *
 * Wind speed range
 */

public class WindSpeedRange extends WeatherParamRange<Integer, SpeedUnits> implements Parcelable {

  public WindSpeedRange(SpeedUnits units, Integer minValue, Integer maxValue) {
    super(SpeedUnits.convertToDefault(units, minValue), SpeedUnits.convertToDefault(units, maxValue), R.drawable.ic_wind);
    }

  protected WindSpeedRange(Parcel in) {
    super(in.readInt(), in.readInt(), in.readInt());
  }

  public static final Creator<WindSpeedRange> CREATOR = new Creator<WindSpeedRange>() {
    @Override public WindSpeedRange createFromParcel(Parcel in) {
      return new WindSpeedRange(in);
    }

    @Override public WindSpeedRange[] newArray(int size) {
      return new WindSpeedRange[size];
    }
  };

  @Override public ParameterType getParameterType() {
    return ParameterType.WIND_SPEED;
    }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(getIconId());
    dest.writeInt(getMinValue());
    dest.writeInt(getMinValue());
  }
}
