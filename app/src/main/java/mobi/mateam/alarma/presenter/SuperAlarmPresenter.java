package mobi.mateam.alarma.presenter;

import java.util.ArrayList;
import java.util.Calendar;
import mobi.mateam.alarma.alarm.AlarmProvider;
import mobi.mateam.alarma.alarm.AlarmUtils;
import mobi.mateam.alarma.alarm.model.Alarm;
import mobi.mateam.alarma.model.repository.AlarmRepository;
import mobi.mateam.alarma.utils.DateUtils;
import mobi.mateam.alarma.view.interfaces.SuperAlarmView;
import mobi.mateam.alarma.weather.model.sports.SportTypes;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class SuperAlarmPresenter extends BasePresenter<SuperAlarmView> {

  private AlarmRepository alarmRepository;
  private AlarmProvider alarmProvider;

  public SuperAlarmPresenter(AlarmRepository alarmRepository, AlarmProvider alarmProvider) {
    this.alarmRepository = alarmRepository;
    this.alarmProvider = alarmProvider;
  }

  @Override public void attachView(SuperAlarmView mainAlarmView) {
    super.attachView(mainAlarmView);
    updateMode();
  }

  public void updateMode() {
    alarmRepository.getAlarmList()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Subscriber<ArrayList<Alarm>>() {
          @Override public void onCompleted() {

          }

          @Override public void onError(Throwable e) {
            Timber.e(e);
          }

          @Override public void onNext(ArrayList<Alarm> alarms) {
            if (alarms == null || alarms.isEmpty()) {
              getView().showEmptyStateMode();
            } else {
              getView().showAlarmsListMode();
            }
          }
        });
  }

  public void onAddNewAlarmClick() {

    getView().showSportPickView();
  }

  public void onEditAlarmClick(String alarmId) {
    getView().showEditAlarmMode(alarmId);
  }

  public void onRemoveAlarmClick(Alarm alarm) {

  }

  public void onSportPicked(SportTypes sportTypes) {

    getView().showSetNewAlarmMode(sportTypes);
  }

  public void onAlarmSet(String alarmId) {
    alarmRepository.getAlarmById(alarmId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(alarm -> {
      Calendar nextAlarmTime = AlarmUtils.getNextAlarmTime(alarm, Calendar.getInstance());
      String message = DateUtils.formatElapsedTimeUntilAlarm(getView().getActivityContext(), nextAlarmTime.getTimeInMillis());
      getView().showNotification(message);
    });
  }
}
