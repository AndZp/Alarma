package mobi.mateam.alarma.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.List;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.view.interfaces.OnWeatherParamChangeListener;
import mobi.mateam.alarma.view.settings.UserSettings;
import mobi.mateam.alarma.weather.model.ParameterType;
import mobi.mateam.alarma.weather.model.params.WeatherParamRange;
import mobi.mateam.alarma.weather.model.params.implementation.ranges.TemperatureRange;
import org.florescu.android.rangeseekbar.RangeSeekBar;

public class ParamListAdapter extends RecyclerView.Adapter<ParamListAdapter.BaseViewHolder> {

  private UserSettings userSettings;

  private List<WeatherParamRange> weatherParameters;
  private OnWeatherParamChangeListener onWeatherParamChangeListener;

  public ParamListAdapter(List<WeatherParamRange> weatherParameters, UserSettings userSettings) {
    this.weatherParameters = weatherParameters;

    this.userSettings = userSettings;
  }

  public void setOnWeatherParamChangeListener(OnWeatherParamChangeListener onWeatherParamChangeListener) {
    this.onWeatherParamChangeListener = onWeatherParamChangeListener;
  }

  @Override public int getItemViewType(int position) {
    return weatherParameters.get(position).getParameterType().getId();
  }

  @Override public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    ParameterType parameterType = ParameterType.getById(viewType);

    switch (parameterType) {
      case TEMPERATURE:
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_range, parent, false);
        return new RangeViewHolder(v);
      case WIND_POWER:
        break;
      case WIND_DIRECTION:
        break;
      case PRESSURE:
        break;
      case CLOUDS:
        break;
      case RAIN:
        break;
      case SNOW:
        break;
      default:
    }
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_range, parent, false);
    return new RangeViewHolder(v);
  }

  @Override public void onBindViewHolder(BaseViewHolder baseViewHolder, int position) {
    ParameterType parameterType = ParameterType.getById(baseViewHolder.getItemViewType());
    WeatherParamRange parameter = weatherParameters.get(position);

    baseViewHolder.tvParamName.setText(parameter.getParameterType().getName());

    switch (parameterType) {
      case TEMPERATURE:
        RangeViewHolder rangeViewHolder = (RangeViewHolder) baseViewHolder;
        TemperatureRange temperatureRange = (TemperatureRange) parameter;

        rangeViewHolder.tvUnits.setText(userSettings.getUserTempUnits().name().substring(0, 3));

        int minSetValue = userSettings.getTemperatureInUserUnits(temperatureRange.getMinValue());
        int maxSetValue = userSettings.getTemperatureInUserUnits(temperatureRange.getMaxValue());

        int minSeekBarValue = userSettings.getTemperatureInUserUnits(-30);
        int maxSeekBarValue = userSettings.getTemperatureInUserUnits(50);

        rangeViewHolder.rangeSeekBar.setRangeValues(minSeekBarValue, maxSeekBarValue);
        rangeViewHolder.rangeSeekBar.setNotifyWhileDragging(false);

        rangeViewHolder.rangeSeekBar.setSelectedMinValue(minSetValue);
        rangeViewHolder.rangeSeekBar.setSelectedMaxValue(maxSetValue);

        rangeViewHolder.rangeSeekBar.setOnRangeSeekBarChangeListener((bar, minValue, maxValue) -> {
          temperatureRange.setMinValue(userSettings.getTemperatureInDefaultUnits(minValue));
          temperatureRange.setMaxValue(userSettings.getTemperatureInDefaultUnits(maxValue));
          weatherParameters.set(position, temperatureRange);
          notifyWeatherParameterChange();
        });

        break;
      case WIND_POWER:
        break;
      case WIND_DIRECTION:
        break;
      case PRESSURE:
        break;
      case CLOUDS:
        break;
      case RAIN:
        break;
      case SNOW:
        break;
      default:
    }

    baseViewHolder.tvParamName.setText(parameter.getParameterType().getName());
    baseViewHolder.tvParamName.setCompoundDrawablesWithIntrinsicBounds(parameter.getIconId(), 0, 0, 0);
  }

  private void notifyWeatherParameterChange() {
    onWeatherParamChangeListener.onParamChange(weatherParameters);
  }

  public void setUserSettings(UserSettings userSettings) {
    this.userSettings = userSettings;
    notifyDataSetChanged();
  }

  @Override public int getItemCount() {
    return weatherParameters.size();
  }

  public static class BaseViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_param_name) TextView tvParamName;

    public BaseViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }

  public static class RangeViewHolder extends BaseViewHolder {
    @BindView(R.id.tv_units) TextView tvUnits;
    @BindView(R.id.item_range_seek_bar) RangeSeekBar<Integer> rangeSeekBar;

    public RangeViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }
  // endregion
}