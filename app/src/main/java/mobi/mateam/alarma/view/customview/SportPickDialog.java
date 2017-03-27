package mobi.mateam.alarma.view.customview;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import java.util.Arrays;
import mobi.mateam.alarma.App;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.bus.EventBus;
import mobi.mateam.alarma.bus.SportPickedEvent;
import mobi.mateam.alarma.di.component.AppComponent;
import mobi.mateam.alarma.view.adapter.SportTypeAdapter;
import mobi.mateam.alarma.weather.model.sports.SportTypes;

public class SportPickDialog extends DialogFragment {
  public static final int LAYOUT = R.layout.fragment_sport_pick;
  private EventBus eventBus;

  public void onResume() {
    super.onResume();
    Dialog dialog = getDialog();
    if (dialog != null && dialog.getWindow() != null) {
      int width = ViewGroup.LayoutParams.MATCH_PARENT;
      int height = ViewGroup.LayoutParams.MATCH_PARENT;
      dialog.getWindow().setLayout(width, height);
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    eventBus = getAppComponent().getEventBus();

    View v = inflater.inflate(LAYOUT, container, false);
    RecyclerView mRecyclerView = ButterKnife.findById(v, R.id.rv_sport_pick);

    setLayoutManager(mRecyclerView);

    setAdapter(mRecyclerView);

    return v;
  }

  private void setAdapter(RecyclerView mRecyclerView) {
    SportTypeAdapter adapter = new SportTypeAdapter(getActivity(), Arrays.asList(SportTypes.values()));
    adapter.setOnItemClickListener(sportTypes -> {
      eventBus.post(new SportPickedEvent(sportTypes));
      SportPickDialog.this.dismiss();
    });
    mRecyclerView.setAdapter(adapter);
  }

  private void setLayoutManager(RecyclerView mRecyclerView) {
    if (getResources().getBoolean(R.bool.isLand)) {
      mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    } else {
      mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
  }

  @Override public void onDetach() {
    super.onDetach();
  }

  public AppComponent getAppComponent() {
    return ((App) getActivity().getApplication()).getAppComponent();
  }
}

