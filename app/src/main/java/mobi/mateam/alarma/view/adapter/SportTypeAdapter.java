package mobi.mateam.alarma.view.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import java.util.List;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.weather.model.sports.SportTypes;

public class SportTypeAdapter extends RecyclerView.Adapter<SportTypeAdapter.ViewHolder> {
  private final int NOTIFY_DELAY = 500;
  private final RequestManager glide;
  private SportTypeAdapter.OnItemClickListener onItemClickListener;
  private List<SportTypes> sportTypes;

  public SportTypeAdapter(Context context, List<SportTypes> sportTypes) {
    glide = Glide.with(context);
    this.sportTypes = sportTypes;
  }

  public void setOnItemClickListener(SportTypeAdapter.OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  @Override public SportTypeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_sport, parent, false);

    return new SportTypeAdapter.ViewHolder(v);
  }

  @Override public void onBindViewHolder(SportTypeAdapter.ViewHolder viewHolder, int position) {
    SportTypes sport = sportTypes.get(position);

    glide.load(sport.getImageId()).into(viewHolder.ivIcon);
    viewHolder.tvSportName.setText(sport.getText());
    viewHolder.cardView.setOnClickListener(v -> {
      if (onItemClickListener != null) {
        onItemClickListener.onItemClick(sport);
      }
    });
  }

  @Override public int getItemCount() {
    return sportTypes.size();
  }

  public void addSportTypes(final SportTypes SportTypes, final int position) {
    // notify of the insertion with a delay, so there is a brief pause after returning
    // from the new SportTypes screen; this makes the animation more noticeable
    Handler handler = new Handler();
    handler.postDelayed(() -> {
      sportTypes.add(position, SportTypes);
      notifyItemInserted(position);
    }, NOTIFY_DELAY);
  }

  // region List manipulation methods

  public void removeSportTypes(final int position) {
    sportTypes.remove(position);

    // notify of the removal with a delay so there is a brief pause after returning
    // from the book details screen; this makes the animation more noticeable
    Handler handler = new Handler();
    handler.postDelayed(() -> notifyItemRemoved(position), NOTIFY_DELAY);
  }

  public interface OnItemClickListener {
    void onItemClick(SportTypes SportTypes);
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_sport_card) CardView cardView;
    @BindView(R.id.iv_item_sport) ImageView ivIcon;
    @BindView(R.id.tv_item_sport_name) TextView tvSportName;

    public ViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }
}