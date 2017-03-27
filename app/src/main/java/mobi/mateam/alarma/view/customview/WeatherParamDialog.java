package mobi.mateam.alarma.view.customview;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import java.util.ArrayList;
import mobi.mateam.alarma.App;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.bus.EventBus;
import mobi.mateam.alarma.bus.ParamListChangedEvent;
import mobi.mateam.alarma.di.component.AppComponent;
import mobi.mateam.alarma.view.adapter.EditParamAdapter;
import mobi.mateam.alarma.weather.model.ParameterType;

public class WeatherParamDialog extends DialogFragment {
  public static final int LAYOUT = R.layout.fragment_weather_param_pick;
  private static final String KEY_USER_PARAM_LIST = "userParamList";
  private EventBus eventBus;
  private ArrayList<ParameterType> userParams;
  private EditParamAdapter editParamAdapter;
  private Unbinder unbinder;

  public static WeatherParamDialog newInstance(ArrayList<ParameterType> userParams) {
    WeatherParamDialog fragment = new WeatherParamDialog();

    Bundle args = new Bundle();
    args.putParcelableArrayList(KEY_USER_PARAM_LIST, userParams);

    fragment.setArguments(args);
    return fragment;
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    eventBus = getAppComponent().getEventBus();

    if (getArguments() != null) {
      userParams = getArguments().getParcelableArrayList(KEY_USER_PARAM_LIST);
    }

    View v = inflater.inflate(LAYOUT, container, false);
    unbinder = ButterKnife.bind(this, v);

    RecyclerView mRecyclerView = ButterKnife.findById(v, R.id.rv_weather_params);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    editParamAdapter = new EditParamAdapter(userParams);
    mRecyclerView.setAdapter(editParamAdapter);

    return v;
  }

  @OnClick(R.id.btn_dialog_ok) public void onSaveClick() {
    ArrayList<ParameterType> checkedParameters = editParamAdapter.getCheckedParameters();
    eventBus.post(new ParamListChangedEvent(checkedParameters));
    dismiss();
  }

  @OnClick(R.id.btn_dialog_cancel) public void onCancelClick() {
    dismiss();
  }

  @Override public void onDetach() {
    super.onDetach();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  public AppComponent getAppComponent() {
    return ((App) getActivity().getApplication()).getAppComponent();
  }
}

