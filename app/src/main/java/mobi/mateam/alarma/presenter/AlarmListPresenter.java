package mobi.mateam.alarma.presenter;

import java.util.ArrayList;
import mobi.mateam.alarma.alarm.AlarmProvider;
import mobi.mateam.alarma.alarm.model.Alarm;
import mobi.mateam.alarma.bus.Event;
import mobi.mateam.alarma.bus.EventBus;
import mobi.mateam.alarma.model.repository.AlarmRepository;
import mobi.mateam.alarma.view.interfaces.AlarmListView;
import rx.Subscriber;
import timber.log.Timber;

public class AlarmListPresenter extends BasePresenter<AlarmListView> {

  private final EventBus eventBus;
  private AlarmRepository alarmRepository;
  private AlarmProvider alarmProvider;
  private Subscriber<Event.SetAlarm> subscriber;

  public AlarmListPresenter(AlarmRepository alarmRepository, AlarmProvider alarmProvider, EventBus eventBus) {
    this.alarmRepository = alarmRepository;
    this.alarmProvider = alarmProvider;
    this.eventBus = eventBus;


  }

  @Override public void attachView(AlarmListView alarmListView) {
    super.attachView(alarmListView);
    subscribeToEventBas();
    updateView();

  }

  private void subscribeToEventBas() {
    subscriber = new Subscriber<Event.SetAlarm>() {
      @Override public void onCompleted() {
        updateView();
      }

      @Override public void onError(Throwable e) {

      }

      @Override public void onNext(Event.SetAlarm setAlarm) {

      }
    };
    eventBus.observeEvents(Event.SetAlarm.class).subscribe(subscriber);

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
          getView().showEmptyList();
        } else {
          getView().showAlarmList(alarms);
        }
      }
    });
  }


  public void onAlarmRemoved(Alarm alarm) {
    alarmRepository.removeAlarm(alarm);
    alarmProvider.cancelAlarm(alarm);
  }

  @Override public void detachView() {
    super.detachView();
    subscriber.unsubscribe();
  }
}
