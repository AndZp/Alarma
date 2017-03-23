package mobi.mateam.alarma.view.interfaces;

public interface SuperAlarmView extends MvpView {

  void showAlarmsListMode();

  void showEditAlarmMode(String alarmId);

  void showNotification(String message);

  void setActionBarImage(int imageId);
}
