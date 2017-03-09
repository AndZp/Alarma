package mobi.mateam.alarma.presenter;

import java.util.ArrayList;
import mobi.mateam.alarma.alarm.model.Alarm;
import mobi.mateam.alarma.model.repository.AlarmRepository;
import mobi.mateam.alarma.view.interfaces.AlarmListView;
import rx.Subscriber;

public class AlarmListPresenter extends BasePresenter<AlarmListView> {

  AlarmRepository alarmRepository;

  public AlarmListPresenter(AlarmRepository alarmRepository) {
    this.alarmRepository = alarmRepository;
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
    getView().showSetAlarmView(null);
  }

  void editAlarm(Alarm alarm) {
    getView().showSetAlarmView(alarm);
  }

  void removeAlarm(Alarm alarm) {
  }
}
