package mobi.mateam.alarma.view.activity.main;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.view.fragment.AlarmListFragment;
import mobi.mateam.alarma.view.fragment.SetAlarmFragment;
import mobi.mateam.alarma.view.interfaces.SetAlarmView;

public class PhoneNavigator implements Navigator {
  private AppBarLayout appBarLayout;
  private FloatingActionButton floatingActionButton;
  private MainAlarmActivity activity;
  private AlarmListFragment alarmListFragment;
  private SetAlarmFragment setAlarmFragment;
  private int state = MainAlarmActivity.ALARM_LIST_MODE;
  private AppCompatImageView toolbarImageView;
  private NestedScrollView nestedScrollView;
  private CollapsingToolbarLayout collapsingToolbarLayout;

  @Override public void onAttachView(MainAlarmActivity activity) {
    this.activity = activity;
    appBarLayout = ButterKnife.findById(activity, R.id.app_bar_lay);
    toolbarImageView = ButterKnife.findById(activity, R.id.iv_toolbar_image);
    floatingActionButton = ButterKnife.findById(activity, R.id.fab);
    nestedScrollView = ButterKnife.findById(activity, R.id.scroll);
    collapsingToolbarLayout = ButterKnife.findById(activity, R.id.collapsing_toolbar);
  }

  @Override public void showEditAlarmMode(String alarmID) {
    if (setAlarmFragment == null) {
      setAlarmFragment = new SetAlarmFragment();
      if (!TextUtils.isEmpty(alarmID)) {
        Bundle bundle = new Bundle();
        bundle.putString(SetAlarmView.ALARM_ID_KEY, alarmID);
        setAlarmFragment.setArguments(bundle);
      }
      FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
      transaction.replace(R.id.container, setAlarmFragment);
      transaction.commit();
    }

    state = MainAlarmActivity.SET_ALARM_MODE;

    setActionBarToMode(state);
    setFABtoMode(state);
  }

  @Override public void showAlarmListMode() {
    if (alarmListFragment == null) {
      alarmListFragment = new AlarmListFragment();
      activity.getSupportFragmentManager().beginTransaction().add(R.id.container, alarmListFragment, null).commit();
    }

    FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
    transaction.replace(R.id.container, alarmListFragment);
    transaction.commit();

    /*activity.getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.container, alarmListFragment).commit();*/
    state = MainAlarmActivity.ALARM_LIST_MODE;
    setFABtoMode(state);
    setActionBarToMode(state);
  }

  private void setActionBarToMode(int mode) {
    ActionBar supportActionBar = activity.getSupportActionBar();

    switch (mode) {
      case MainAlarmActivity.SET_ALARM_MODE:
        if (supportActionBar != null) {
          supportActionBar.setDisplayShowTitleEnabled(false);
          supportActionBar.setDisplayHomeAsUpEnabled(true);

          //unlock collapsing state
          if (activity.getResources().getBoolean(R.bool.isLand)) {
            appBarLayout.setExpanded(false, true);
          } else {
            appBarLayout.setExpanded(true, true);
            ViewCompat.setNestedScrollingEnabled(nestedScrollView, true);
            toolbarImageView.setVisibility(View.VISIBLE);
            AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) collapsingToolbarLayout.getLayoutParams();
            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);

            if (SetAlarmFragment.sportType != null) {
              setActionBarImage(SetAlarmFragment.sportType.getImageId());
            }
          }
        }
        break;
      case MainAlarmActivity.ALARM_LIST_MODE:
        if (supportActionBar != null) {
          supportActionBar.setDisplayShowTitleEnabled(true);
          supportActionBar.setDisplayHomeAsUpEnabled(false);
          appBarLayout.setExpanded(false, false);
          ViewCompat.setNestedScrollingEnabled(nestedScrollView, false);

          toolbarImageView.setVisibility(View.GONE);

          //Lock in collapsed state
          AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) collapsingToolbarLayout.getLayoutParams();
          params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
          params.setScrollFlags(0);
          collapsingToolbarLayout.setTitleEnabled(false);
          setActionBarImage(0);
        }
        break;
    }
  }

  private void setFABtoMode(int state) {
    CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) floatingActionButton.getLayoutParams();
    switch (state) {
      case MainAlarmActivity.ALARM_LIST_MODE:
        floatingActionButton.setImageResource(R.drawable.ic_add_white_24dp);
        floatingActionButton.setOnClickListener(v -> activity.onFABClick(MainAlarmActivity.ALARM_LIST_MODE));
        params.setAnchorId(R.id.scroll);
        params.anchorGravity = Gravity.BOTTOM | Gravity.RIGHT | GravityCompat.END;
        break;
      case MainAlarmActivity.SET_ALARM_MODE:
        floatingActionButton.setImageResource(R.drawable.ic_done);
        params.setAnchorId(R.id.scroll);
        params.anchorGravity = Gravity.TOP | Gravity.RIGHT | GravityCompat.END;
        params.gravity = 0;

        floatingActionButton.setOnClickListener(v -> {
          activity.onFABClick(MainAlarmActivity.SET_ALARM_MODE);
          setAlarmFragment = null;
        });
    }
    floatingActionButton.setLayoutParams(params);
  }

  @Override public void onDestroy() {
  }

  @Override public void onBackPressed() {
    Fragment f = activity.getSupportFragmentManager().findFragmentById(R.id.container);
    if (f instanceof AlarmListFragment) {
      activity.finish();
    } else if (f instanceof SetAlarmFragment) {
      setAlarmFragment = null;
      showAlarmListMode();
    }
  }

  @Override public void updateMode() {
    switch (state) {
      case MainAlarmActivity.ALARM_LIST_MODE:
        showAlarmListMode();
        break;
      case MainAlarmActivity.SET_ALARM_MODE:
        showEditAlarmMode(null);
    }
  }

  @Override public void setActionBarImage(int imageId) {
    Glide.with(activity).load(imageId).into(toolbarImageView);
  }
}
