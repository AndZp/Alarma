package mobi.mateam.alarma.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import java.util.List;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.alarm.model.Alarm;
import mobi.mateam.alarma.presenter.AlarmListPresenter;
import mobi.mateam.alarma.view.SportPickDialog;
import mobi.mateam.alarma.view.adapter.AlarmListAdapter;
import mobi.mateam.alarma.view.interfaces.AlarmListView;
import mobi.mateam.alarma.view.interfaces.MainAlarmView;

public class AlarmListFragment extends BaseFragment implements AlarmListView {

  @BindView(R.id.rv_alarm_list) RecyclerView rvAlarmsList;

  AlarmListPresenter presenter;

  private Unbinder unbinder;

  public AlarmListFragment() {
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_alarm_list, container, false);
    unbinder = ButterKnife.bind(this, view);
    if (presenter == null) {
      presenter = getAppComponent().getAlarmListPresenter();
    }
    presenter.attachView(this);
    return view;
  }

  public void showAlarmList(List<Alarm> alarms) {
    AlarmListAdapter alarmListAdapter = new AlarmListAdapter(alarms);
    alarmListAdapter.setOnItemClickListener(new AlarmListAdapter.OnItemClickListener() {
      @Override public void onItemClick(Alarm alarm) {
        showSetAlarmView(alarm, false, alarm.sportType.getId());
      }

      @Override public void onSwitchChange(Alarm alarm, boolean isActivated) {
        presenter.onActivatedSwitchChange(alarm, isActivated);
      }
    });
    rvAlarmsList.setLayoutManager(new LinearLayoutManager(getActivity()));
    rvAlarmsList.setAdapter(alarmListAdapter);
  }

  public void showEmptyList() {

  }

  public void showSetAlarmView(Alarm alarm, boolean isNew, int sportTypeId) {
    ((MainAlarmView) getActivity()).showSetAlarmView(alarm, isNew, sportTypeId);
  }

  @Override public void showSportPicker() {
    new SportPickDialog().show(getFragmentManager(), "Sport");
  }

  @OnClick(R.id.fab) public void addNewAlarm() {
    presenter.addNewAlarm();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
    presenter.detachView();
    if (isRemoving()) presenter = null;
  }

  /*public PresenterComponent getComponent() {
    BaseActivity activity = (BaseActivity) getActivity();
    return DaggerPresenterComponent.builder().appComponent(activity.getAppComponent()).build();
  }*/
}
