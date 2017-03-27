package mobi.mateam.alarma.bus;

public class SettingsChangedEvent extends Event {

  public SettingsChangedEvent() {
    super(SETTINGS_CHANGED);
  }
}
