package mobi.mateam.alarma.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;
import java.util.List;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.alarm.model.Alarm;
import mobi.mateam.alarma.presenter.SetAlarmPresenter;
import mobi.mateam.alarma.view.adapter.ParamListAdapter;
import mobi.mateam.alarma.view.interfaces.SetAlarmView;
import mobi.mateam.alarma.weather.model.WeatherParameter;

public class SetAlarmFragment extends BaseFragment implements SetAlarmView {
  @BindView(R.id.tv_set_time) TextView tvTime;
  @BindView(R.id.tv_set_location) TextView tvLocation;
  @BindView(R.id.tv_set_ringtone) TextView tvRingtone;
  @BindView(R.id.et_set_lable) EditText etLable;
  @BindView(R.id.view_weekdays) View weekdays;
  @BindView(R.id.rv_weather_params) RecyclerView rvParams;

  public static SetAlarmPresenter presenter;

  private Unbinder unbinder;

  public SetAlarmFragment() {
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_set_alarm, container, false);
    unbinder = ButterKnife.bind(this, view);
    if (presenter == null) {
      presenter = new SetAlarmPresenter();
    }
    presenter.attachView(this);
    return view;
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
    etLable.setText(label);
  }

  @Override public void showWeatherParameters(List<WeatherParameter> parameters) {
    ParamListAdapter paramListAdapter = new ParamListAdapter(parameters);
    rvParams.setLayoutManager(new LinearLayoutManager(getActivity()));
    rvParams.setAdapter(paramListAdapter);
  }

  @Override public Alarm getAlarm() {
    return null;
  }

  @OnClick(R.id.tv_set_time) public void onTimeClick() {
    presenter.setTime();
  }

  @OnTextChanged(R.id.et_set_lable) public void onLabelClick(CharSequence text) {
    presenter.setLabel(text.toString());
  }

  @OnClick(R.id.tv_set_location) public void onLocationClick() {
    presenter.setLocation();
  }

  @OnClick(R.id.tv_set_ringtone) public void onRingtoneClick() {
    presenter.setRingtone();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
    presenter.detachView();
    if (isRemoving()) presenter = null;
  }
}
