package mobi.mateam.alarma.weather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Snow {

  @SerializedName("3h") @Expose private Integer _3h;

  public Integer get3h() {
    return _3h;
  }

  public void set3h(Integer _3h) {
    this._3h = _3h;
  }
}
