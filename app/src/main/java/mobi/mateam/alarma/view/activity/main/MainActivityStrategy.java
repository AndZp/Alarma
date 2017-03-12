package mobi.mateam.alarma.view.activity.main;

import mobi.mateam.alarma.weather.model.sports.SportTypes;

public interface MainActivityStrategy {

  void onCreate(MainAlarmActivity activity);

  void showEmptyStateMode();

  void showEditAlarmMode(String alarmId);

  void showAlarmListMode();

  void onAlarmAdded();

  void showSportPickView();

  void onDestroy();

  void onBackPressed();

  void showSetNewAlarmMode(SportTypes sportTypes);
}
