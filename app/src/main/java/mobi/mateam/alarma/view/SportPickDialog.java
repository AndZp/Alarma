package mobi.mateam.alarma.view;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.Arrays;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.view.adapter.SportTypeAdapter;
import mobi.mateam.alarma.view.interfaces.PickSportListener;
import mobi.mateam.alarma.weather.model.sports.SportTypes;

public class SportPickDialog extends DialogFragment {
  private RecyclerView mRecyclerView;
  private PickSportListener pickSportListener;

  @Override public void onAttach(Context context) {
    super.onAttach(context);
  }

  // this method create view for your Dialog
  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    //inflate layout with recycler view
    pickSportListener = (PickSportListener) getActivity();
    View v = inflater.inflate(R.layout.fragment_sport_pick, container, false);
    mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_sport_pick);
    mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    //setadapter
    SportTypeAdapter adapter = new SportTypeAdapter(getActivity(), Arrays.asList(SportTypes.values()));
    adapter.setOnItemClickListener(sportTypes -> {
      if (pickSportListener != null) {
        pickSportListener.onSportPick(sportTypes);
        SportPickDialog.this.dismiss();
      }
    });
    mRecyclerView.setAdapter(adapter);
    //get your recycler view and populate it.
    return v;
  }

  @Override public void onDetach() {
    super.onDetach();
    pickSportListener = null;
  }
}
