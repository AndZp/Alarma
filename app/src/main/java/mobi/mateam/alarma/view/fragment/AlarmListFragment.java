package mobi.mateam.alarma.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import java.util.List;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.alarm.model.Alarm;
import mobi.mateam.alarma.presenter.AlarmListPresenter;
import mobi.mateam.alarma.view.SportPickDialog;
import mobi.mateam.alarma.view.adapter.AlarmListAdapter;
import mobi.mateam.alarma.view.interfaces.AlarmListView;
import mobi.mateam.alarma.view.interfaces.MainAlarmView;
import mobi.mateam.alarma.view.interfaces.OnEditAlarmListener;
import timber.log.Timber;

public class AlarmListFragment extends BaseFragment implements AlarmListView {

  @BindView(R.id.rv_alarm_list) RecyclerView rvAlarmsList;
  @BindView(R.id.iv_empty_stay) TextView tvEmptyState;

  AlarmListPresenter presenter;

  private Unbinder unbinder;
  private boolean isNeedUpdate;

  public AlarmListFragment() {
  }

  @Override public void onResume() {
    super.onResume();
    if (isNeedUpdate) {
      presenter.updateView();
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_alarm_list, container, false);
    unbinder = ButterKnife.bind(this, view);
    setPresenter();

    return view;
  }

  private void setPresenter() {
    if (presenter == null) {
      presenter = getAppComponent().getAlarmListPresenter();
    }
    presenter.attachView(this);
  }

  public void showAlarmList(List<Alarm> alarms) {
    tvEmptyState.setVisibility(View.GONE);
    rvAlarmsList.setVisibility(View.VISIBLE);

    AlarmListAdapter alarmListAdapter = new AlarmListAdapter(alarms);
    alarmListAdapter.setOnItemClickListener(new AlarmListAdapter.OnItemClickListener() {
      @Override public void onItemClick(Alarm alarm) {
        try {
          ((OnEditAlarmListener) getActivity()).onEditAlarm(alarm.id);
        } catch (Exception e) {
          Timber.e(e);
        }
      }

      @Override public void onSwitchChange(Alarm alarm, boolean isActivated) {
        presenter.onActivatedSwitchChange(alarm, isActivated);
      }
    });
    rvAlarmsList.setLayoutManager(new LinearLayoutManager(getActivity()));
    rvAlarmsList.setAdapter(alarmListAdapter);
  }

  public void showEmptyList() {
    tvEmptyState.setVisibility(View.VISIBLE);
    rvAlarmsList.setVisibility(View.GONE);
  }

  public void showSetAlarmView(Alarm alarm, boolean isNew, int sportTypeId) {
    tvEmptyState.setVisibility(View.GONE);
    rvAlarmsList.setVisibility(View.VISIBLE);
    ((MainAlarmView) getActivity()).showSetAlarmView(alarm, isNew, sportTypeId);
  }

  @Override public void showSportPicker() {
    new SportPickDialog().show(getFragmentManager(), "Sport");
  }

  public void addNewAlarm() {
    presenter.addNewAlarm();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
    presenter.detachView();
    if (isRemoving()) presenter = null;
  }

  public void updateView() {
    if (presenter != null) {
      presenter.updateView();
    } else {
      isNeedUpdate = true;
    }
  }

  /*public PresenterComponent getComponent() {
    BaseActivity activity = (BaseActivity) getActivity();
    return DaggerPresenterComponent.builder().appComponent(activity.getAppComponent()).build();
  }*/
}
