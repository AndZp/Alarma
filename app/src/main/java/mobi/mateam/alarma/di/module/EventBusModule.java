package mobi.mateam.alarma.di.module;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import mobi.mateam.alarma.bus.Event;
import mobi.mateam.alarma.bus.EventBus;

@Module public class EventBusModule {
  @Singleton @Provides public EventBus<Event> provideEventBus() {
    return new EventBus<>();
  }
}
