package mobi.mateam.alarma;

import android.app.Application;
import android.support.annotation.Nullable;
import android.util.Log;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.squareup.leakcanary.LeakCanary;
import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class App extends Application {
  @Override public void onCreate() {
    super.onCreate();
    initFabric();
    initLeakCanary();
  }

  private void initFabric() {
    CrashlyticsCore core = new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build();
    Fabric.with(this, new Crashlytics.Builder().core(core).build());

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }
    Timber.plant(new CrashlyticsTree());
  }

  private void initLeakCanary() {
    if (LeakCanary.isInAnalyzerProcess(this)) {
      // This process is dedicated to LeakCanary for heap analysis.
      // You should not init your app in this process.
      return;
    }
    LeakCanary.install(this);
  }

  public class CrashlyticsTree extends Timber.Tree {
    private static final String CRASHLYTICS_KEY_PRIORITY = "priority";
    private static final String CRASHLYTICS_KEY_TAG = "tag";
    private static final String CRASHLYTICS_KEY_MESSAGE = "message";

    @Override protected void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable t) {
      if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
        return;
      }

      Crashlytics.setInt(CRASHLYTICS_KEY_PRIORITY, priority);
      Crashlytics.setString(CRASHLYTICS_KEY_TAG, tag);
      Crashlytics.setString(CRASHLYTICS_KEY_MESSAGE, message);

      if (t == null) {
        Crashlytics.logException(new Exception(message));
      } else {
        Crashlytics.logException(t);
      }
    }
  }
}
