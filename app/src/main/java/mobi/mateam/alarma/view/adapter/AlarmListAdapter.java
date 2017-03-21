package mobi.mateam.alarma.view.adapter;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.alarm.model.Alarm;

public class AlarmListAdapter extends RecyclerView.Adapter<AlarmListAdapter.ViewHolder> {
  private final int NOTIFY_DELAY = 500;
  private static final int PENDING_REMOVAL_TIMEOUT = 2000; // 2sec

  boolean undoOn = true; // is undo on, you can turn it on from the toolbar menu
  private OnItemClickListener onItemClickListener;
  private List<Alarm> alarms;
  private List<Alarm> itemsPendingRemoval;
  private Handler handler = new Handler();
  HashMap<Alarm, Runnable> pendingRunnables = new HashMap<>(); // map of items to pending runnables, so we can cancel a removal if need be


  public AlarmListAdapter(List<Alarm> alarms) {
    this.alarms = alarms;
    itemsPendingRemoval = new ArrayList<>();

  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_alarm, parent, false);

    return new ViewHolder(v);
  }

  @Override public void onBindViewHolder(ViewHolder viewHolder, int position) {
    Alarm alarm = alarms.get(position);

    if (itemsPendingRemoval.contains(alarm)) {
      viewHolder.itemView.setBackgroundColor(Color.RED);
      viewHolder.btnUndo.setVisibility(View.VISIBLE);

      viewHolder.tvAlarmTime.setVisibility(View.INVISIBLE);
      viewHolder.tvAlarmLable.setVisibility(View.INVISIBLE);
      viewHolder.tvAlarmDays.setVisibility(View.INVISIBLE);
      viewHolder.switchActivate.setVisibility(View.INVISIBLE);
      viewHolder.btnInfo.setVisibility(View.INVISIBLE);

      viewHolder.btnUndo.setOnClickListener(v -> {
        // user wants to undo the removal, let's cancel the pending task
        Runnable pendingRemovalRunnable = pendingRunnables.get(alarm);
        pendingRunnables.remove(alarm);
        if (pendingRemovalRunnable != null) handler.removeCallbacks(pendingRemovalRunnable);
        itemsPendingRemoval.remove(alarm);
        // this will rebind the row in "normal" state
        notifyItemChanged(alarms.indexOf(alarm));
      });
    } else {

      viewHolder.tvAlarmTime.setText(alarm.hour + ":" + alarm.minutes);
      viewHolder.tvAlarmLable.setText(alarm.label);
      viewHolder.tvAlarmDays.setText(alarm.getStringDays());
      viewHolder.switchActivate.setChecked(alarm.activated);

      viewHolder.tvAlarmTime.setOnClickListener(v -> onItemClickListener.onItemClick(alarm));
      viewHolder.tvAlarmLable.setOnClickListener(v -> onItemClickListener.onItemClick(alarm));
      viewHolder.tvAlarmDays.setOnClickListener(v -> onItemClickListener.onItemClick(alarm));
      viewHolder.switchActivate.setOnCheckedChangeListener((buttonView, isChecked) -> onItemClickListener.onSwitchChange(alarm, isChecked));
    }
  }

  @Override public int getItemCount() {
    return alarms.size();
  }


  public void setUndoOn(boolean undoOn) {
    this.undoOn = undoOn;
  }

  public boolean isUndoOn() {
    return undoOn;
  }

  public void pendingRemoval(int position) {
    final Alarm item = alarms.get(position);
    if (!itemsPendingRemoval.contains(item)) {
      itemsPendingRemoval.add(item);
      // this will redraw row in "undo" state
      notifyItemChanged(position);
      // let's create, store and post a runnable to remove the item
      Runnable pendingRemovalRunnable = () -> remove(alarms.indexOf(item));
      handler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT);
      pendingRunnables.put(item, pendingRemovalRunnable);
    }
  }

  public void remove(int position) {
    Alarm alarm = alarms.get(position);
    if (itemsPendingRemoval.contains(alarm)) {
      itemsPendingRemoval.remove(alarm);
    }
    if (alarms.contains(alarm)) {
      alarms.remove(position);
      onItemClickListener.onItemRemoved(alarm);

      notifyItemRemoved(position);
    }
  }

  public boolean isPendingRemoval(int position) {
    Alarm item = alarms.get(position);
    return itemsPendingRemoval.contains(item);
  }

  public interface OnItemClickListener {
    void onItemClick(Alarm alarm);

    void onSwitchChange(Alarm alarm, boolean isActivated);

    void onItemRemoved(Alarm alarm);
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_alarm_time) TextView tvAlarmTime;
    @BindView(R.id.tv_alarm_lable) TextView tvAlarmLable;
    @BindView(R.id.tv_alarm_days) TextView tvAlarmDays;
    @BindView(R.id.btn_info) ImageView btnInfo;
    @BindView(R.id.switch_alarm) SwitchCompat switchActivate;
    @BindView(R.id.undo_button) Button btnUndo;

    public ViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }
  // endregion
}