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
import mobi.mateam.alarma.weather.model.params.implementation.ranges.WindSpeedRange;
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
        return new RangeIntViewHolder(v);
      case WIND_POWER:
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_range, parent, false);
        return new RangeDoubleViewHolder(v);
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
    return null;
  }

  @Override public void onBindViewHolder(BaseViewHolder baseViewHolder, int position) {
    ParameterType parameterType = ParameterType.getById(baseViewHolder.getItemViewType());
    WeatherParamRange paramRange = weatherParameters.get(position);

    baseViewHolder.tvParamName.setText(paramRange.getParameterType().getName());

    switch (parameterType) {
      case TEMPERATURE:
        onBindTemperatureParameter((RangeIntViewHolder) baseViewHolder, position, (TemperatureRange) paramRange);
        break;
      case WIND_POWER:
        onBindSpeedParameter((RangeDoubleViewHolder) baseViewHolder, position, (WindSpeedRange) paramRange);
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

    //Set paramRange name and icon - relevant for all parameters
    baseViewHolder.tvParamName.setText(paramRange.getParameterType().getName());
    baseViewHolder.tvParamName.setCompoundDrawablesWithIntrinsicBounds(paramRange.getIconId(), 0, 0, 0);
  }

  //region OnBind XXX Parameter methods
  private void onBindSpeedParameter(RangeDoubleViewHolder rangeIntViewHolder, int position, WindSpeedRange parameter) {
    rangeIntViewHolder.tvUnits.setText(userSettings.getUserSpeedUnits().name().substring(0, 3));

    double minSetValue = userSettings.getSpeedInUserUnits(parameter.getMinValue());
    double maxSetValue = userSettings.getSpeedInUserUnits(parameter.getMaxValue());

    double minSeekBarValue = userSettings.getSpeedInUserUnits(0.0);
    double maxSeekBarValue = userSettings.getSpeedInUserUnits(50.0);

    rangeIntViewHolder.rangeSeekBar.setRangeValues(minSeekBarValue, maxSeekBarValue);
    rangeIntViewHolder.rangeSeekBar.setNotifyWhileDragging(false);

    rangeIntViewHolder.rangeSeekBar.setSelectedMinValue(minSetValue);
    rangeIntViewHolder.rangeSeekBar.setSelectedMaxValue(maxSetValue);

    rangeIntViewHolder.rangeSeekBar.setOnRangeSeekBarChangeListener((bar, minValue, maxValue) -> {
      parameter.setMinValue(userSettings.getSpeedInDefaultUnits(minValue));
      parameter.setMaxValue(userSettings.getSpeedInDefaultUnits(maxValue));
      weatherParameters.set(position, parameter);
      notifyWeatherParameterChange();
    });
  }

  private void onBindTemperatureParameter(RangeIntViewHolder rangeIntViewHolder, int position, TemperatureRange parameter) {
    rangeIntViewHolder.tvUnits.setText(userSettings.getUserTempUnits().name().substring(0, 3));

    int minSetValue = userSettings.getTemperatureInUserUnits(parameter.getMinValue());
    int maxSetValue = userSettings.getTemperatureInUserUnits(parameter.getMaxValue());

    int minSeekBarValue = userSettings.getTemperatureInUserUnits(-30);
    int maxSeekBarValue = userSettings.getTemperatureInUserUnits(50);

    rangeIntViewHolder.rangeSeekBar.setRangeValues(minSeekBarValue, maxSeekBarValue);
    rangeIntViewHolder.rangeSeekBar.setNotifyWhileDragging(false);

    rangeIntViewHolder.rangeSeekBar.setSelectedMinValue(minSetValue);
    rangeIntViewHolder.rangeSeekBar.setSelectedMaxValue(maxSetValue);

    rangeIntViewHolder.rangeSeekBar.setOnRangeSeekBarChangeListener((bar, minValue, maxValue) -> {
      parameter.setMinValue(userSettings.getTemperatureInDefaultUnits(minValue));
      parameter.setMaxValue(userSettings.getTemperatureInDefaultUnits(maxValue));
      weatherParameters.set(position, parameter);
      notifyWeatherParameterChange();
    });
  }
  //endregion

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

  //region ViewHolders
  public static class BaseViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_param_name) TextView tvParamName;

    public BaseViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }

  public static class RangeIntViewHolder extends BaseViewHolder {
    @BindView(R.id.tv_units) TextView tvUnits;
    @BindView(R.id.item_range_seek_bar) RangeSeekBar<Integer> rangeSeekBar;

    public RangeIntViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }

  public static class RangeDoubleViewHolder extends BaseViewHolder {
    @BindView(R.id.tv_units) TextView tvUnits;
    @BindView(R.id.item_range_seek_bar) RangeSeekBar<Double> rangeSeekBar;

    public RangeDoubleViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }
  //endregion
}