package mobi.mateam.alarma.di.module;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import mobi.mateam.alarma.db.DatabaseHelper;
import mobi.mateam.alarma.db.DbHelper;

@Module public class DbModule {

  @Singleton @Provides DatabaseHelper provideDatabaseHelper() {
    return new DbHelper("default");
  }
}
