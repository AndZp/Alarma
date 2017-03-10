package mobi.mateam.alarma.presenter;

import java.util.ArrayList;
import mobi.mateam.alarma.alarm.AlarmProvider;
import mobi.mateam.alarma.alarm.model.Alarm;
import mobi.mateam.alarma.model.repository.AlarmRepository;
import mobi.mateam.alarma.view.interfaces.AlarmListView;
import rx.Subscriber;

public class AlarmListPresenter extends BasePresenter<AlarmListView> {

  AlarmRepository alarmRepository;
  AlarmProvider alarmProvider;

  public AlarmListPresenter(AlarmRepository alarmRepository, AlarmProvider alarmProvider) {
    this.alarmRepository = alarmRepository;
    this.alarmProvider = alarmProvider;
  }

  @Override public void attachView(AlarmListView alarmListView) {
    super.attachView(alarmListView);
    alarmRepository.getAlarmList().subscribe(new Subscriber<ArrayList<Alarm>>() {
      @Override public void onCompleted() {

      }

      @Override public void onError(Throwable e) {
        getView().showError(e);
      }

      @Override public void onNext(ArrayList<Alarm> alarms) {
        if (alarms == null || alarms.isEmpty()) {
          getView().showEmptyList();
        } else {
          getView().showAlarmList(alarms);
        }
      }
    });
  }

  public void addNewAlarm() {
    getView().showSportPicker();
  }

  void editAlarm(Alarm alarm) {
    getView().showSetAlarmView(alarm, false);
  }

  void removeAlarm(Alarm alarm) {
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
}
