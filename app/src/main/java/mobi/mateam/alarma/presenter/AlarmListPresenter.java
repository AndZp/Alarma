package mobi.mateam.alarma.presenter;

import java.util.ArrayList;
import mobi.mateam.alarma.alarm.AlarmProvider;
import mobi.mateam.alarma.alarm.model.Alarm;
import mobi.mateam.alarma.bus.EventBus;
import mobi.mateam.alarma.model.repository.AlarmRepository;
import mobi.mateam.alarma.view.interfaces.AlarmListView;
import rx.Subscriber;
import timber.log.Timber;

public class AlarmListPresenter extends BasePresenter<AlarmListView> {

  private AlarmRepository alarmRepository;
  private AlarmProvider alarmProvider;

  public AlarmListPresenter(AlarmRepository alarmRepository, AlarmProvider alarmProvider, EventBus eventBus) {
    this.alarmRepository = alarmRepository;
    this.alarmProvider = alarmProvider;
  }

  @Override public void attachView(AlarmListView alarmListView) {
    super.attachView(alarmListView);
    updateView();
  }

  public void onActivatedSwitchChange(Alarm alarm, boolean isActivated) {
    alarm.activated = isActivated;
    alarmRepository.updateAlarm(alarm);

    if (isActivated) {
      alarmProvider.setNextAlarm(alarm);
    } else {
      alarmProvider.cancelAlarm(alarm);
    }
  }

  public void updateView() {
    alarmRepository.getAlarmList().subscribe(new Subscriber<ArrayList<Alarm>>() {
      @Override public void onCompleted() {

      }

      @Override public void onError(Throwable e) {
        Timber.e(e);
        getView().showError(e);
      }

      @Override public void onNext(ArrayList<Alarm> alarms) {
        if (alarms == null || alarms.isEmpty()) {
          getView().showEmptyState();
        } else {
          getView().showAlarmList(alarms);
        }
      }
    });
  }

  public void onAlarmRemoved(Alarm alarm) {
    alarmRepository.removeAlarm(alarm);
    alarmProvider.cancelAlarm(alarm);
    alarmRepository.getAlarmList().subscribe(alarms -> {
      if (alarms == null || alarms.size() == 0) {
        getView().showEmptyState();
      }
    });
  }

  @Override public void detachView() {
    super.detachView();
  }
}
