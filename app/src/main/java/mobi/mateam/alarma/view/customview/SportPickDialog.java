package mobi.mateam.alarma.view.customview;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.Arrays;
import mobi.mateam.alarma.App;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.bus.Event;
import mobi.mateam.alarma.bus.EventBus;
import mobi.mateam.alarma.di.component.AppComponent;
import mobi.mateam.alarma.view.adapter.SportTypeAdapter;
import mobi.mateam.alarma.weather.model.sports.SportTypes;

public class SportPickDialog extends DialogFragment {
  public static final int LAYOUT = R.layout.fragment_sport_pick;
  private EventBus eventBus;


  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    eventBus = getAppComponent().getEventBus();
    getDialog().setTitle(R.string.sport_pick_dialog_title);

    View v = inflater.inflate(LAYOUT, container, false);

    RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_sport_pick);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    SportTypeAdapter adapter = new SportTypeAdapter(getActivity(), Arrays.asList(SportTypes.values()));
    adapter.setOnItemClickListener(sportTypes -> {
      eventBus.post(new Event.SportPicked(sportTypes));
      SportPickDialog.this.dismiss();
    });
    mRecyclerView.setAdapter(adapter);

    return v;
  }

  @Override public void onDetach() {
    super.onDetach();
  }

  public AppComponent getAppComponent() {
    return ((App) getActivity().getApplication()).getAppComponent();
  }
}

