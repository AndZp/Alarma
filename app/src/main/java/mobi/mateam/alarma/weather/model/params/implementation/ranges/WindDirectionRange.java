package mobi.mateam.alarma.weather.model.params.implementation.ranges;

import android.os.Parcel;
import android.os.Parcelable;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.weather.model.ParameterType;
import mobi.mateam.alarma.weather.model.params.WeatherParamRange;
import mobi.mateam.alarma.weather.model.params.WindDirectionType;
import mobi.mateam.alarma.weather.model.params.implementation.units.NoUnits;

public class WindDirectionRange extends WeatherParamRange<WindDirectionType, NoUnits> implements Parcelable {

  public static final Creator<WindDirectionRange> CREATOR = new Creator<WindDirectionRange>() {
    @Override public WindDirectionRange createFromParcel(Parcel in) {
      return new WindDirectionRange(in);
    }

    @Override public WindDirectionRange[] newArray(int size) {
      return new WindDirectionRange[size];
    }
  };

  public WindDirectionRange(WindDirectionType minValue, WindDirectionType maxValue) {
    super(minValue, maxValue, R.drawable.label);
  }

  protected WindDirectionRange(Parcel in) {
    super((WindDirectionType) in.readSerializable(), (WindDirectionType) in.readSerializable(), in.readInt());
  }

  @Override public ParameterType getParameterType() {
    return ParameterType.WIND_DIRECTION;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeSerializable(getMinValue());
    dest.writeSerializable(getMaxValue());
    dest.writeInt(getIconId());
  }
}
