package mobi.mateam.alarma.view.activity.main;

public interface Navigator {

  void onAttachView(MainAlarmActivity activity);

  void showEditAlarmMode(String alarmId);

  void showAlarmListMode();

  void onDestroy();

  void onBackPressed();

  void updateMode();
}
