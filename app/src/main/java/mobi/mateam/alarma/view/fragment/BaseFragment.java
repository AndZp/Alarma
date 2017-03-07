package mobi.mateam.alarma.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.widget.Toast;
import mobi.mateam.alarma.view.interfaces.MvpView;

public class BaseFragment extends Fragment implements MvpView {
  @Override public Context getAppContext() {
    return getActivity().getApplicationContext();
  }

  @Override public Context getActivityContext() {
    return getActivity();
  }

  @Override public void showError(Throwable error) {
    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
  }
}
