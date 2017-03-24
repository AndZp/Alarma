package mobi.mateam.alarma.view.settings;

import android.content.Context;
import android.text.TextUtils;
import mobi.mateam.alarma.utils.PrefUtils;
import mobi.mateam.alarma.weather.model.params.implementation.units.TemperatureUnits;

public class UserSettings {
  public static final String PREF_LANGUAGE = "pref_key_language";
  public static final String PREF_TEMP_UNIT = "pref_key_temp_units";
  public static final String PREF_SPEED_UNIT = "pref_key_speed_units";
  public static final String PREF_PRESSURE_UNIT = "pref_key_pressure_units";
  public static final String PREF_IS_VIBRATE = "pref_key_is_vibrate";
  public static final String PREF_AUTO_SILENT = "pref_auto_silent";
  public static final String PREF_SNOOZE_DURATION = "pref_key_snooze_duration";

  private TemperatureUnits userTempUnits;

  public UserSettings(Context context) {
    userTempUnits = getUserTemperatureUnits(context);
  }

  public static int getTemperatureInUserUnits(int defaultUnitValue, Context context) {
    TemperatureUnits units = getUserTemperatureUnits(context);
    return TemperatureUnits.convertToUserUnit(units, defaultUnitValue);
  }

  public static int getTemperatureInUserUnits(int defaultUnitValue, TemperatureUnits units) {
    return TemperatureUnits.convertToUserUnit(units, defaultUnitValue);
  }

  public static TemperatureUnits getUserTemperatureUnits(Context context) {
    String stringPreference = PrefUtils.getStringPreference(context, PREF_TEMP_UNIT);
    int userUnit = TextUtils.isEmpty(stringPreference) ? 0 : Integer.valueOf(stringPreference);
    return TemperatureUnits.getById(userUnit);
  }

  public TemperatureUnits getUserTempUnits() {
    return userTempUnits;
  }

  public int getTemperatureInUserUnits(Integer value) {
    return getTemperatureInUserUnits(value, userTempUnits);
  }

  public int getTemperatureInDefaultUnits(Integer value) {
    return TemperatureUnits.convertToDefault(userTempUnits, value);
  }
}
