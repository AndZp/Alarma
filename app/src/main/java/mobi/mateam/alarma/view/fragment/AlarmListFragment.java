package mobi.mateam.alarma.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import mobi.mateam.alarma.view.adapter.AlarmListAdapter;
import mobi.mateam.alarma.view.interfaces.AlarmListView;
import mobi.mateam.alarma.view.interfaces.OnEditAlarmListener;
import mobi.mateam.alarma.view.tools.AlarmAnimDecorHelper;
import mobi.mateam.alarma.view.tools.AlarmListCallBack;
import timber.log.Timber;

public class AlarmListFragment extends BaseFragment implements AlarmListView {

  public static final int LAYOUT = R.layout.fragment_alarm_list;
  @BindView(R.id.rv_alarm_list) RecyclerView rvAlarmsList;
  @BindView(R.id.iv_empty_stay) TextView tvEmptyState;

  AlarmListPresenter presenter;

  private Unbinder unbinder;

  public AlarmListFragment() {
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(LAYOUT, container, false);
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

      @Override public void onItemRemoved(Alarm alarm) {
        presenter.onAlarmRemoved(alarm);
      }
    });
    rvAlarmsList.setLayoutManager(new LinearLayoutManager(getActivity()));
    rvAlarmsList.setAdapter(alarmListAdapter);

    ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(new AlarmListCallBack(getActivity(), alarmListAdapter, 0, ItemTouchHelper.LEFT));
    mItemTouchHelper.attachToRecyclerView(rvAlarmsList);

    rvAlarmsList.addItemDecoration(new AlarmAnimDecorHelper());
  }

  public void showEmptyList() {
    tvEmptyState.setVisibility(View.VISIBLE);
    rvAlarmsList.setVisibility(View.GONE);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
    presenter.detachView();
    if (isRemoving()) presenter = null;
  }
}
