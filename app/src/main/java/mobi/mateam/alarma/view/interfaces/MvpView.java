package mobi.mateam.alarma.view.interfaces;

import android.content.Context;

public interface MvpView {

  public Context getAppContext();

  public Context getActivityContext();

  public void showError(Throwable error);
}