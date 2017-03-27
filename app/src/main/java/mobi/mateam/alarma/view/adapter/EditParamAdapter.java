package mobi.mateam.alarma.view.adapter;

import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.weather.model.ParameterType;

public class EditParamAdapter extends RecyclerView.Adapter<EditParamAdapter.ViewHolder> {

  private ArrayList<ParameterType> allParamTypes;
  private ArrayList<ParameterType> userParamTypes;
  private HashMap<ParameterType, Boolean> paramMap;

  public EditParamAdapter(ArrayList<ParameterType> userParamTypes) {
    this.userParamTypes = userParamTypes;
    this.allParamTypes = new ArrayList<>(Arrays.asList(ParameterType.values()));
    allParamTypes.remove(ParameterType.WIND_DIRECTION);
    initParamMap();
  }

  private void initParamMap() {
    paramMap = new HashMap<>();
    for (ParameterType paramType : allParamTypes) {

      boolean contains = userParamTypes.contains(paramType);
      //exclude wind direction from dialog
      if (paramType != ParameterType.WIND_DIRECTION) {
        paramMap.put(paramType, contains);
      }
    }
  }

  public ArrayList<ParameterType> getCheckedParameters() {
    ArrayList<ParameterType> userParamTypes = new ArrayList<>();
    for (Map.Entry<ParameterType, Boolean> entry : paramMap.entrySet()) {
      if (entry.getValue()) {
        userParamTypes.add(entry.getKey());
      }
    }

    if (userParamTypes.contains(ParameterType.WIND_SPEED)) {
      userParamTypes.add(ParameterType.WIND_DIRECTION);
    }

    return userParamTypes;
  }

  @Override public EditParamAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pararm_check, parent, false);
    return new EditParamAdapter.ViewHolder(v);
  }

  @Override public void onBindViewHolder(EditParamAdapter.ViewHolder viewHolder, int position) {
    ParameterType parameterType = allParamTypes.get(position);

    if (parameterType == ParameterType.WIND_SPEED) {
      viewHolder.cbParam.setText(R.string.wind_title);
    } else {
      viewHolder.cbParam.setText(parameterType.getName());
    }

    viewHolder.cbParam.setChecked(paramMap.get(parameterType));
    viewHolder.cbParam.setOnCheckedChangeListener((buttonView, isChecked) -> paramMap.put(parameterType, isChecked));
  }

  @Override public int getItemCount() {
    return paramMap.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.cb_weather_param) AppCompatCheckBox cbParam;

    public ViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }
}