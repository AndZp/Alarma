package mobi.mateam.alarma.view.customview;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import mobi.mateam.alarma.App;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.bus.Event;
import mobi.mateam.alarma.bus.EventBus;
import mobi.mateam.alarma.di.component.AppComponent;
import mobi.mateam.alarma.view.settings.UserSettings;
import mobi.mateam.alarma.weather.model.params.WindDirectionType;
import mobi.mateam.alarma.weather.model.params.implementation.ranges.WindDirectionRange;
import mobi.mateam.alarma.weather.model.params.implementation.ranges.WindSpeedRange;


public class WindPickerDialog extends DialogFragment {
  public static final int LAYOUT = R.layout.fragment_wind_direction_pick;
  private static final String KEY_WIND_SPEED = "wind_speed_range_key";
  private static final String KEY_WIND_DIRECTION = "wind_direction_range_key";
  @BindView(R.id.rsb_wind_dir_picker) CircularSliderRange sliderRangeDirection;
  @BindView(R.id.speed_range_seek_bar) RangeSeekBar<Integer> sbWindSpeed;
  @BindView(R.id.tv_param_name) TextView tvParamName;
  @BindView(R.id.tv_units) TextView tvUnits;

  private EventBus eventBus;
  private Unbinder unbinder;
  private UserSettings userSettings;
  private WindSpeedRange windSpeedRange;
  private WindDirectionRange windDirectionRange;

  public static WindPickerDialog newInstance(WindSpeedRange windSpeedRange, WindDirectionRange windDirectionRange) {
    WindPickerDialog fragment = new WindPickerDialog();
    Bundle args = new Bundle();
    args.putParcelable(KEY_WIND_SPEED, windSpeedRange);
    args.putParcelable(KEY_WIND_DIRECTION, windDirectionRange);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    eventBus = getAppComponent().getEventBus();
    getDialog().setTitle("Wind parameters");
    if (getArguments() != null) {
      windSpeedRange = getArguments().getParcelable(KEY_WIND_SPEED);
      windDirectionRange = getArguments().getParcelable(KEY_WIND_DIRECTION);
    }
    View v = inflater.inflate(LAYOUT, container, false);
    unbinder = ButterKnife.bind(this, v);
    userSettings = new UserSettings(getContext());
    updateWindSpeedView();
    updateWindDirectionView();
    return v;
  }

  private void updateWindDirectionView() {
    sliderRangeDirection.setStartAngle(windDirectionRange.getMinValue().getStartDegree());
    sliderRangeDirection.setEndAngle(windDirectionRange.getMaxValue().getEndDegree());
    sliderRangeDirection.setOnSliderRangeMovedListener(new CircularSliderRange.OnSliderRangeMovedListener() {
      @Override public void onStartSliderMoved(double pos) {
        Double position = pos;
        WindDirectionType direction = WindDirectionType.getDirection(position.intValue());
        windDirectionRange.setMinValue(direction);
      }

      @Override public void onEndSliderMoved(double pos) {
        Double position = pos;
        WindDirectionType direction = WindDirectionType.getDirection(position.intValue());
        windDirectionRange.setMaxValue(direction);
      }

      @Override public void onStartSliderEvent(ThumbEvent event) {

      }

      @Override public void onEndSliderEvent(ThumbEvent event) {

      }
    });
  }

  private void updateWindSpeedView() {
    tvUnits.setText(userSettings.getUserSpeedUnits().getUnitStringResId());

    int minSetValue = userSettings.getSpeedInUserUnits(windSpeedRange.getMinValue());
    int maxSetValue = userSettings.getSpeedInUserUnits(windSpeedRange.getMaxValue());

    int minSeekBarValue = userSettings.getSpeedInUserUnits(0);
    int maxSeekBarValue = userSettings.getSpeedInUserUnits(50);

    sbWindSpeed.setRangeValues(minSeekBarValue, maxSeekBarValue);
    sbWindSpeed.setNotifyWhileDragging(false);

    sbWindSpeed.setSelectedMinValue(minSetValue);
    sbWindSpeed.setSelectedMaxValue(maxSetValue);

    sbWindSpeed.setOnRangeSeekBarChangeListener((bar, minValue, maxValue) -> {
      windSpeedRange.setMinValue(userSettings.getSpeedInDefaultUnits(minValue));
      windSpeedRange.setMaxValue(userSettings.getSpeedInDefaultUnits(maxValue));
    });
  }

  @OnClick(R.id.btn_dialog_ok) public void onSaveClick() {
    eventBus.post(new Event.WindParamChanged(windSpeedRange, windDirectionRange));
    dismiss();
  }

  @Override public void onDetach() {
    super.onDetach();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  public AppComponent getAppComponent() {
    return ((App) getActivity().getApplication()).getAppComponent();
  }
}

