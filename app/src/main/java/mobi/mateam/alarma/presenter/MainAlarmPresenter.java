package mobi.mateam.alarma.presenter;

import java.util.Calendar;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.alarm.AlarmUtils;
import mobi.mateam.alarma.bus.Event;
import mobi.mateam.alarma.bus.EventBus;
import mobi.mateam.alarma.bus.SetAlarmEvent;
import mobi.mateam.alarma.bus.SportPickedEvent;
import mobi.mateam.alarma.utils.DateUtils;
import mobi.mateam.alarma.view.activity.main.Navigator;
import mobi.mateam.alarma.view.activity.main.PhoneNavigator;
import mobi.mateam.alarma.view.activity.main.TabletNavigator;
import mobi.mateam.alarma.view.fragment.SetAlarmFragment;
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
    subscriber = new Subscriber<Event>() {
      @Override public void onCompleted() {

      }

      @Override public void onError(Throwable e) {
        Timber.e(e);
      }

      @Override public void onNext(Event event) {
        switch (event.id) {
          case Event.SET_ALARM:
            onSetAlarmEvent((SetAlarmEvent) event);
            break;
          case Event.SPORT_PICKED:
            onSportPickedEvent((SportPickedEvent) event);
            break;
        }
      }
    };
    eventBus.observeEvents(Event.class).subscribe(subscriber);
  }

  private void onSportPickedEvent(SportPickedEvent event) {
    SportPickedEvent sportPicked = event;
    getView().setActionBarImage(sportPicked.sportType.getImageId());
    SetAlarmFragment.sportType = sportPicked.sportType;
    getView().showEditAlarmMode(null);
  }

  private void onSetAlarmEvent(SetAlarmEvent event) {
    Calendar nextAlarmTime = AlarmUtils.getNextAlarmTime(event.alarm, Calendar.getInstance());
    String message = DateUtils.formatElapsedTimeUntilAlarm(getView().getActivityContext(), nextAlarmTime.getTimeInMillis());
    getView().showAlarmsListMode();
    getView().showNotification(message);
  }

  public void onEditAlarmClick(String alarmId) {
    getView().showEditAlarmMode(alarmId);
  }

  @Override public void detachView() {
    super.detachView();
    subscriber.unsubscribe();
  }
}
