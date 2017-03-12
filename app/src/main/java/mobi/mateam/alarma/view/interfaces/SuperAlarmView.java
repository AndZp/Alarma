package mobi.mateam.alarma.view.interfaces;

import mobi.mateam.alarma.weather.model.sports.SportTypes;

public interface SuperAlarmView extends MvpView {

  void showEmptyStateMode();

  void showAlarmsListMode();

  void showSportPickView();

  void showEditAlarmMode(String alarmId);

  void showSetNewAlarmMode(SportTypes sportTypes);
}
