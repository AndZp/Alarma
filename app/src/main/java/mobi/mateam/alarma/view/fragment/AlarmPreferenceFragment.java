package mobi.mateam.alarma.view.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import mobi.mateam.alarma.App;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.bus.Event;
import mobi.mateam.alarma.bus.EventBus;
import mobi.mateam.alarma.di.component.AppComponent;
import mobi.mateam.alarma.view.settings.UserSettings;

public class AlarmPreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

  private SharedPreferences sharedPreferences;
  private EventBus eventBus;

  @Override public void onResume() {
    super.onResume();
    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
  }

  @Override public void onPause() {
    super.onPause();

    getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.eventBus = getAppComponent().getEventBus();
  }

  @Override public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    addPreferencesFromResource(R.xml.preferences);
    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
    ListPreference tempUnitPref = (ListPreference) findPreference(UserSettings.PREF_TEMP_UNIT);
    tempUnitPref.setSummary(tempUnitPref.getEntry());
  }

  @Override public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    Preference preference = findPreference(key);
    if (preference instanceof ListPreference) {
      ListPreference listPreference = (ListPreference) preference;
      listPreference.setSummary(listPreference.getEntry());
    }

    eventBus.post(new Event.SettingsChanged());
  }

  public AppComponent getAppComponent() {
    return ((App) getActivity().getApplication()).getAppComponent();
  }
}
