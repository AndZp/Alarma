package mobi.mateam.alarma.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.weather.model.params.WeatherParamRange;

public class ParamListAdapter extends RecyclerView.Adapter<ParamListAdapter.ViewHolder> {
  public static final int NORMAL = 1;
  public static final int FOOTER = 2;
  private final int NOTIFY_DELAY = 100;
  private List<WeatherParamRange> weatherParameters;

  public ParamListAdapter(List<WeatherParamRange> weatherParameters) {
    this.weatherParameters = weatherParameters;
  }

  @Override public int getItemViewType(int position) {

    if (position == weatherParameters.size()) {
      return FOOTER;
    } else {
      return NORMAL;
    }
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_param, parent, false);

    return new ViewHolder(v);
  }

  @Override public void onBindViewHolder(ViewHolder viewHolder, int position) {
    if (position == weatherParameters.size()) {
      viewHolder.tvParamName.setVisibility(View.GONE);
      viewHolder.etMinValue.setVisibility(View.GONE);
      viewHolder.etMaxValue.setVisibility(View.GONE);
      viewHolder.btnAddRemoveParam.setText("Add param");
      viewHolder.btnAddRemoveParam.setOnClickListener(v -> {
        //addParam(new WeatherParameterRange(), position);
        //open selection for parameter type
      });
    } else {
      WeatherParamRange parameter = weatherParameters.get(position);
      viewHolder.tvParamName.setText(parameter.getParametrType().getName());
      viewHolder.etMinValue.setText(parameter.getMinValue().toString());
      viewHolder.etMaxValue.setText(parameter.getMaxValue().toString());
      viewHolder.btnAddRemoveParam.setText("--");
      viewHolder.btnAddRemoveParam.setOnClickListener(v -> {
        removeParam(position);
      });
    }
  }

  //@Override public long getItemId(int position) {
  //  return weatherParameters.get(position).getId();
  //}

  @Override public int getItemCount() {
    return weatherParameters.size() + 1;
  }

  public void addParam(final WeatherParamRange parameter, final int position) {
    weatherParameters.add(position, parameter);
    notifyItemInserted(position);
    notifyDataSetChanged();
   /* // notify of the insertion with a delay, so there is a brief pause after returning
    // from the new parameter screen; this makes the animation more noticeable
    Handler handler = new Handler();
    handler.postDelayed(() -> {
      weatherParameters.add(position, parameter);
      notifyItemInserted(position);
    }, NOTIFY_DELAY);*/
  }

  // region List manipulation methods

  public void removeParam(final int position) {
    weatherParameters.remove(position);
    notifyItemRemoved(position);
    notifyItemRangeRemoved(position, position + 1);
    // notify of the removal with a delay so there is a brief pause after returning
    // from the book details screen; this makes the animation more noticeable
   /* Handler handler = new Handler();
    handler.postDelayed(() -> notifyItemRemoved(position), NOTIFY_DELAY);*/
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_param_name) TextView tvParamName;
    @BindView(R.id.et_min_value) EditText etMinValue;
    @BindView(R.id.et_max_value) EditText etMaxValue;
    @BindView(R.id.btn_item_param) Button btnAddRemoveParam;

    public ViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }
  // endregion
}