package mobi.mateam.alarma.view.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;
import java.util.ArrayList;
import java.util.List;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.presenter.SetAlarmPresenter;
import mobi.mateam.alarma.view.SportPickDialog;
import mobi.mateam.alarma.view.adapter.ParamListAdapter;
import mobi.mateam.alarma.view.interfaces.SetAlarmView;
import mobi.mateam.alarma.view.settings.UserSettings;
import mobi.mateam.alarma.weather.model.params.WeatherParamRange;
import mobi.mateam.alarma.weekdays.WeekdaysDataItem;
import mobi.mateam.alarma.weekdays.WeekdaysDataSource;

public class SetAlarmFragment extends BaseFragment implements SetAlarmView, WeekdaysDataSource.Callback {
  public static final int LAYOUT = R.layout.fragment_set_alarm;
  public static SetAlarmPresenter presenter;

  @BindView(R.id.tv_set_time) TextView tvTime;
  @BindView(R.id.tv_set_location) TextView tvLocation;
  @BindView(R.id.tv_set_ringtone) TextView tvRingtone;
  @BindView(R.id.et_set_lable) EditText etLabel;
  @BindView(R.id.cb_weekday) AppCompatCheckBox cbWeekDays;
  @BindView(R.id.cb_vibrate) AppCompatCheckBox cbVibrate;
  @BindView(R.id.rv_weather_params) RecyclerView rvParams;

  private Unbinder unbinder;
  private WeekdaysDataSource viewWeekday;

  public SetAlarmFragment() {
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(LAYOUT, container, false);
    unbinder = ButterKnife.bind(this, view);
    initWeekDaysView(view);
    setPresenter();
    presenter.setAlarm(getArguments());
    return view;
  }

  private void setPresenter() {
    if (presenter == null) {
      presenter = getAppComponent().getSetAlarmPresenter();
    }
    presenter.attachView(this);
  }

  private void initWeekDaysView(View view) {
    viewWeekday = new WeekdaysDataSource((AppCompatActivity) getActivity(), R.id.weekdays_stub, view);
    viewWeekday.setSelectedColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
    viewWeekday.setUnselectedColor(ContextCompat.getColor(getActivity(), R.color.grey));
  }

  @Override public void showTime(String time) {
    tvTime.setText(time);
  }

  @Override public void showLocation(String location) {
    tvLocation.setText(location);
  }

  @Override public void showRingtone(String ringtone) {
    tvRingtone.setText(ringtone);
  }

  @Override public void showLabel(String label) {
    etLabel.setText(label);
  }

  @Override public void showWeatherParameters(List<WeatherParamRange> conditions) {
    UserSettings userSettings = new UserSettings(getAppContext());
    ParamListAdapter paramListAdapter = new ParamListAdapter(conditions, userSettings);
    paramListAdapter.setOnWeatherParamChangeListener(weatherParameters -> presenter.onWeatherParamChange(weatherParameters));
    rvParams.setLayoutManager(new LinearLayoutManager(getActivity()));
    rvParams.setAdapter(paramListAdapter);
  }

  @Override public void showWeekDays(int[] weekdays) {
    if (weekdays != null) {
      cbWeekDays.setChecked(true);
      viewWeekday.setSelectedDays(weekdays);
    } else {
      cbWeekDays.setChecked(false);
    }
  }

  @Override public void uncheckRepeat() {
    cbWeekDays.setChecked(false);
  }

  @Override public void showSportPickDialog() {
    new SportPickDialog().show(getFragmentManager(), "SportPickDialog");
  }

  @Override public void setVibrateCheck(boolean vibrate) {
    cbVibrate.setChecked(vibrate);
  }

  @OnCheckedChanged(R.id.cb_weekday) public void onWeekDay(boolean isChecked) {
    if (isChecked) {
      viewWeekday.start(this);
    } else {
      viewWeekday.setVisible(false);
      presenter.onRepeatUncheck();
    }
  }

  @OnCheckedChanged(R.id.cb_vibrate) public void onVibrate(boolean isChecked) {
    presenter.onVibrateChange(isChecked);
  }

  @OnClick(R.id.tv_set_time) public void onTimeClick() {
    presenter.setTime();
  }

  @OnTextChanged(R.id.et_set_lable) public void onLabelClick(CharSequence text) {
    presenter.setLabel(text.toString());
  }

  @OnClick(R.id.tv_set_location) public void onLocationClick() {
    presenter.startLocationDialog();
  }

  @OnClick(R.id.tv_set_ringtone) public void onRingtoneClick() {
    presenter.startRingtoneDialog();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
    presenter.detachView();
    if (isRemoving()) presenter = null;
  }

  @Override public void onWeekdaysItemClicked(int i, WeekdaysDataItem weekdaysDataItem) {
    presenter.onWeekDayClick(weekdaysDataItem);
  }

  @Override public void onWeekdaysSelected(int i, ArrayList<WeekdaysDataItem> arrayList) {

  }
}
