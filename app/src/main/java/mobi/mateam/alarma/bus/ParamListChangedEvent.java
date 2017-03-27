package mobi.mateam.alarma.bus;

import java.util.ArrayList;
import mobi.mateam.alarma.weather.model.ParameterType;

public class ParamListChangedEvent extends Event {
  public final ArrayList<ParameterType> checkedParameters;

  public ParamListChangedEvent(ArrayList<ParameterType> checkedParameters) {
    super(PARAM_LIST_CHANGED);
    this.checkedParameters = checkedParameters;
  }
}
