package mobi.mateam.alarma.di.module;

import android.content.Context;
import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import mobi.mateam.alarma.alarm.AlarmProvider;
import mobi.mateam.alarma.db.AlarmDbHelper;
import mobi.mateam.alarma.db.AlarmPrefDb;
import mobi.mateam.alarma.model.repository.AlarmRepository;

@Module public class AlarmModule {

  @Provides @Singleton AlarmDbHelper provideAlarmDbHelper(Context context, Gson gson) {
    return new AlarmPrefDb(context, gson);
  }

  @Provides @Singleton AlarmRepository provideAlarmRepository(AlarmDbHelper alarmDbHelper) {
    return new AlarmRepository(alarmDbHelper);
  }

  @Provides @Singleton AlarmProvider provideAlarmManager(Context context) {
    return new AlarmProvider(context);
  }
}
