package mobi.mateam.alarma.di.module;

import dagger.Module;
import dagger.Provides;
import mobi.mateam.alarma.db.DatabaseHelper;
import mobi.mateam.alarma.db.DbHelper;
import mobi.mateam.alarma.di.anotation.PerActivity;

@Module public class DbModule {

  @PerActivity @Provides DatabaseHelper provideDatabaseHelper() {
    return new DbHelper("default");
  }
}
