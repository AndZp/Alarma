package mobi.mateam.alarma.view.activity.main;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.Gravity;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import mobi.mateam.alarma.R;
import mobi.mateam.alarma.view.SportPickDialog;
import mobi.mateam.alarma.view.fragment.AlarmListFragment;
import mobi.mateam.alarma.view.fragment.SetAlarmFragment;
import mobi.mateam.alarma.view.interfaces.SetAlarmView;
import mobi.mateam.alarma.weather.model.sports.SportTypes;

import static mobi.mateam.alarma.R.id.fab;

public class PhoneMainActivityStrategy implements MainActivityStrategy {
  private AppBarLayout appBarLayout;
  private AppCompatImageView toolbarImageView;
  private FloatingActionButton floatingActionButton;

  private MainAlarmActivity activity;
  private AlarmListFragment alarmListFragment;

  @Override public void onCreate(MainAlarmActivity activity) {
    this.activity = activity;

    appBarLayout = ButterKnife.findById(activity, R.id.app_bar_lay);
    toolbarImageView = ButterKnife.findById(activity, R.id.iv_toolbar_image);
    floatingActionButton = ButterKnife.findById(activity, fab);
  }

  @Override public void showEmptyStateMode() {
    setFABinAlarmListMode();
    toolbarImageView.setImageResource(0);
    appBarLayout.setExpanded(true);
    if (alarmListFragment == null) {
      alarmListFragment = new AlarmListFragment();
      activity.getFragmentManager().beginTransaction().add(R.id.container, alarmListFragment).commit();
    } else {
      activity.getFragmentManager().beginTransaction().replace(R.id.container, alarmListFragment).addToBackStack(null).commit();
      alarmListFragment.updateView();
    }
  }

  @Override public void showEditAlarmMode(String alarmID) {
    SetAlarmFragment fragment = new SetAlarmFragment();
    if (!TextUtils.isEmpty(alarmID)) {
      Bundle bundle = new Bundle();
      bundle.putString(SetAlarmView.ALRAM_ID_KEY, alarmID);
      fragment.setArguments(bundle);
    }
    activity.getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();

    Glide.with(activity).load(R.drawable.mount_background).into(toolbarImageView);
    appBarLayout.setExpanded(true);
    setFABinSetAlarmMode();
  }

  @Override public void showSetNewAlarmMode(SportTypes sportTypes) {
    SetAlarmFragment fragment = new SetAlarmFragment();
    if (sportTypes != null) {
      Bundle bundle = new Bundle();
      bundle.putInt(SetAlarmView.ALARM_SPORT_TYPE_ID, sportTypes.getId());
      fragment.setArguments(bundle);
    }
    activity.getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();

    Glide.with(activity).load(R.drawable.mount_background).into(toolbarImageView);
    appBarLayout.setExpanded(true);
    setFABinSetAlarmMode();
  }

  @Override public void showAlarmListMode() {
    appBarLayout.setExpanded(false);
    toolbarImageView.setImageResource(0);
    if (alarmListFragment == null) {
      alarmListFragment = new AlarmListFragment();
    }
    activity.getFragmentManager().beginTransaction().replace(R.id.container, alarmListFragment).commit();
    setFABinAlarmListMode();
  }

  private void setFABinSetAlarmMode() {
    floatingActionButton.setImageResource(R.drawable.ic_done);
    floatingActionButton.setOnClickListener(v -> activity.onFABClick(MainAlarmActivity.SET_ALARM_MODE));

    CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) floatingActionButton.getLayoutParams();
    params.setAnchorId(R.id.scroll);
    params.anchorGravity = Gravity.TOP | Gravity.RIGHT | GravityCompat.END;
    params.gravity = 0;
    floatingActionButton.setLayoutParams(params);
  }

  private void setFABinAlarmListMode() {
    floatingActionButton.setImageResource(R.drawable.ic_add_white);
    floatingActionButton.setOnClickListener(v -> activity.onFABClick(MainAlarmActivity.ALARM_LIST_MODE));

    CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) floatingActionButton.getLayoutParams();
    params.setAnchorId(R.id.scroll);
    params.anchorGravity = Gravity.BOTTOM | Gravity.RIGHT | GravityCompat.END;
    floatingActionButton.setLayoutParams(params);
  }

  @Override public void onAlarmAdded() {

  }

  @Override public void showSportPickView() {
    new SportPickDialog().show(activity.getFragmentManager(), "Sport");
  }

  @Override public void onDestroy() {
    activity = null;
  }

  @Override public void onBackPressed() {
    Fragment f = activity.getFragmentManager().findFragmentById(R.id.container);
    if (f instanceof AlarmListFragment) {
      activity.finish();
    } else if (f instanceof SetAlarmFragment) {
      showAlarmListMode();
    }
  }
}
