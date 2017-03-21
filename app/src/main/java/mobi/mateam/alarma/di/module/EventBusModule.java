package mobi.mateam.alarma.di.module;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import mobi.mateam.alarma.bus.EventBus;

@Module public class EventBusModule {
  @Singleton @Provides EventBus provideEventBus() {
    return new EventBus();
  }
}
