package mobi.mateam.alarma.presenter;

import java.util.Calendar;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.alarm.AlarmUtils;
import mobi.mateam.alarma.bus.Event;
import mobi.mateam.alarma.bus.EventBus;
import mobi.mateam.alarma.utils.DateUtils;
import mobi.mateam.alarma.view.activity.main.Navigator;
import mobi.mateam.alarma.view.activity.main.PhoneNavigator;
import mobi.mateam.alarma.view.activity.main.TabletNavigator;
import mobi.mateam.alarma.view.interfaces.SuperAlarmView;
import rx.Subscriber;
import timber.log.Timber;

public class MainAlarmPresenter extends BasePresenter<SuperAlarmView> {

  private final EventBus eventBus;

  private Subscriber subscriber;
  private Navigator navigator;

  public MainAlarmPresenter(EventBus eventBus) {
    this.eventBus = eventBus;
  }

  @Override public void attachView(SuperAlarmView mainAlarmView) {
    super.attachView(mainAlarmView);
    subscribeToEventBas();
  }

  public Navigator getNavigator() {
    if (navigator == null) {
      if (getView().getActivityContext().getResources().getBoolean(R.bool.isTablet)) {
        navigator = new TabletNavigator();
      } else {
        navigator = new PhoneNavigator();
      }
    }
    return navigator;
  }

  private void subscribeToEventBas() {
    subscriber = new Subscriber() {
      @Override public void onCompleted() {

      }

      @Override public void onError(Throwable e) {
        Timber.e(e);
      }

      @Override public void onNext(Object event) {
        if (event instanceof Event.SetAlarm) {
          Event.SetAlarm setAlarm = (Event.SetAlarm) event;
          Calendar nextAlarmTime = AlarmUtils.getNextAlarmTime(setAlarm.alarm, Calendar.getInstance());
          String message = DateUtils.formatElapsedTimeUntilAlarm(getView().getActivityContext(), nextAlarmTime.getTimeInMillis());
          getView().showAlarmsListMode();
          getView().showNotification(message);
        } else if (event instanceof Event.SportPicked) {
          Event.SportPicked sportPicked = (Event.SportPicked) event;
          getView().setActionBarImage(sportPicked.sportType.getImageId());
        }
      }
    };
    eventBus.observe().subscribe(subscriber);
  }

  public void onEditAlarmClick(String alarmId) {
    getView().showEditAlarmMode(alarmId);
  }

  @Override public void detachView() {
    super.detachView();
    subscriber.unsubscribe();
  }
}
