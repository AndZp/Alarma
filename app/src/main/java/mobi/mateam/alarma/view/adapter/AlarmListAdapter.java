package mobi.mateam.alarma.view.adapter;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.List;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.alarm.model.Alarm;

public class AlarmListAdapter extends RecyclerView.Adapter<AlarmListAdapter.ViewHolder> {
  private final int NOTIFY_DELAY = 500;

  private List<Alarm> alarms;

  public static class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_alarm_time) TextView tvAlarmTime;
    @BindView(R.id.tv_alarm_lable) TextView tvAlarmLable;
    @BindView(R.id.tv_alarm_days) TextView tvAlarmDays;
    @BindView(R.id.tv_alarm_location) TextView tvAlarmLocation;
    @BindView(R.id.btn_info) Button btnInfo;
    @BindView(R.id.switch_alarm) Switch switchActivate;

    public ViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }

  public AlarmListAdapter(List<Alarm> alarms) {
    this.alarms = alarms;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_alarm, parent, false);

    return new ViewHolder(v);
  }

  @Override public void onBindViewHolder(ViewHolder viewHolder, int position) {
    Alarm alarm = alarms.get(position);
    viewHolder.tvAlarmTime.setText(alarm.hour + ":" + alarm.minutes);
    viewHolder.tvAlarmLable.setText(alarm.lable);
    viewHolder.tvAlarmLocation.setText(alarm.getStringLocation());
    viewHolder.tvAlarmDays.setText(alarm.getStringDays());
    viewHolder.switchActivate.setChecked(alarm.enabled);
  }

  @Override public long getItemId(int position) {
    return alarms.get(position).longID;
  }

  @Override public int getItemCount() {
    return alarms.size();
  }

  // region List manipulation methods

  public void addAlarm(final Alarm alarm, final int position) {
    // notify of the insertion with a delay, so there is a brief pause after returning
    // from the new alarm screen; this makes the animation more noticeable
    Handler handler = new Handler();
    handler.postDelayed(() -> {
      alarms.add(position, alarm);
      notifyItemInserted(position);
    }, NOTIFY_DELAY);
  }

  public void removeAlarm(final int position) {
    alarms.remove(position);

    // notify of the removal with a delay so there is a brief pause after returning
    // from the book details screen; this makes the animation more noticeable
    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
      @Override public void run() {
        notifyItemRemoved(position);
      }
    }, NOTIFY_DELAY);
  }

  // endregion
}