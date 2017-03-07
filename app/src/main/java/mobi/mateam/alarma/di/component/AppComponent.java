package mobi.mateam.alarma.di.component;

import android.content.Context;
import dagger.Component;
import javax.inject.Singleton;
import mobi.mateam.alarma.di.module.AppModule;
import mobi.mateam.alarma.view.activity.BaseActivity;

@Singleton @Component(modules = { AppModule.class }) public interface AppComponent {

  void inject(BaseActivity mainActivity);

  Context context();
}

