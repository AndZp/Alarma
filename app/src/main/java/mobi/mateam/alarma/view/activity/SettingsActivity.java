package mobi.mateam.alarma.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import mobi.mateam.alarma.view.fragment.AlarmPreferenceFragment;

public class SettingsActivity extends AppCompatActivity {

  public static Intent getStartIntent(Context applicationContext) {
    return new Intent(applicationContext, SettingsActivity.class);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new AlarmPreferenceFragment()).commit();
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      onBackPressed();
    }
    return super.onOptionsItemSelected(item);
  }
}

