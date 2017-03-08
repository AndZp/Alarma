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
import mobi.mateam.alarma.di.component.AppComponent;
import mobi.mateam.alarma.presenter.AlarmListPresenter;
import mobi.mateam.alarma.view.activity.BaseActivity;
import mobi.mateam.alarma.view.adapter.AlarmListAdapter;
import mobi.mateam.alarma.view.interfaces.AlarmListView;
import mobi.mateam.alarma.view.interfaces.MainAlarmView;

/**
 * A placeholder fragment containing a simple view.
 */
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

  private AppComponent getAppComponent() {
    return ((BaseActivity) getActivity()).getAppComponent();
  }

  public void showAlarmList(List<Alarm> alarms) {
    AlarmListAdapter alarmListAdapter = new AlarmListAdapter(alarms);
    rvAlarmsList.setLayoutManager(new LinearLayoutManager(getActivity()));
    rvAlarmsList.setAdapter(alarmListAdapter);
  }

  public void showEmptyList() {

  }

  public void showSetAlarmView(Alarm alarm) {
    ((MainAlarmView) getActivity()).showSetAlarmView();
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
