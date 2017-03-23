package mobi.mateam.alarma.view.tools;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.view.adapter.AlarmListAdapter;

public class AlarmListCallBack extends ItemTouchHelper.SimpleCallback {

  private final AlarmListAdapter adapter;
  // we want to cache these and not allocate anything repeatedly in the onChildDraw method
  private Drawable background;
  private Drawable xMark;
  private int xMarkMargin;
  private boolean initiated;
  private Context mContext;

  public AlarmListCallBack(Context context, AlarmListAdapter adapter, int dragDirs, int swipeDirs) {
    super(dragDirs, swipeDirs);
    this.mContext = context;
    this.adapter = adapter;
  }

  private void init() {
    background = new ColorDrawable(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
    xMark = ContextCompat.getDrawable(mContext, R.drawable.ic_clear_24dp);
    xMark.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
    xMarkMargin = (int) mContext.getResources().getDimension(R.dimen.ic_clear_margin);
    initiated = true;
  }

  @Override public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
    return false;
  }

  @Override public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
    int swipedPosition = viewHolder.getAdapterPosition();
    boolean undoOn = adapter.isUndoOn();
    if (undoOn) {
      adapter.pendingRemoval(swipedPosition);
    } else {
      adapter.remove(swipedPosition);
    }
  }

  @Override public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
    int position = viewHolder.getAdapterPosition();
    if (adapter.isUndoOn() && adapter.isPendingRemoval(position)) {
      return 0;
    }
    return super.getSwipeDirs(recyclerView, viewHolder);
  }

  @Override public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState,
      boolean isCurrentlyActive) {
    View itemView = viewHolder.itemView;

    // not sure why, but this method get's called for viewholder that are already swiped away
    if (viewHolder.getAdapterPosition() == -1) {
      // not interested in those
      return;
    }

    if (!initiated) {
      init();
    }

    // draw red background
    background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
    background.draw(c);

    // draw x mark
    int itemHeight = itemView.getBottom() - itemView.getTop();
    int intrinsicWidth = xMark.getIntrinsicWidth();
    int intrinsicHeight = xMark.getIntrinsicWidth();

    int xMarkLeft = itemView.getRight() - xMarkMargin - intrinsicWidth;
    int xMarkRight = itemView.getRight() - xMarkMargin;
    int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
    int xMarkBottom = xMarkTop + intrinsicHeight;
    xMark.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);

    xMark.draw(c);

    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
  }
}
