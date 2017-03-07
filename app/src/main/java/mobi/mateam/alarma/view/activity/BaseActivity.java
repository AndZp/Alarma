package mobi.mateam.alarma.view.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import mobi.mateam.alarma.App;
import mobi.mateam.alarma.di.component.AppComponent;
import mobi.mateam.alarma.view.interfaces.MvpView;

public class BaseActivity extends AppCompatActivity implements MvpView {

  protected AppComponent getAppComponent() {
    return ((App) getApplication()).getAppComponent();
  }

  @Override public Context getAppContext() {
    return getApplicationContext();
  }

  @Override public Context getActivityContext() {
    return this;
  }

  @Override public void showError(Throwable error) {
    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
  }
}
