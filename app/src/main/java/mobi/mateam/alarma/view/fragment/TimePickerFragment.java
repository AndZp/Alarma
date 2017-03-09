package mobi.mateam.alarma.view.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

  TimePickerDialog.OnTimeSetListener onTimeSetListener;

  public TimePickerFragment() {
    // Required empty public constructor
  }

  @NonNull @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
    final Calendar c = Calendar.getInstance();
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int minute = c.get(Calendar.MINUTE);

    //Create and return a new instance of TimePickerDialog
    return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
  }

  @Override public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
    onTimeSetListener.onTimeSet(view, hourOfDay, minute);
  }

  public void setOnTimeSetListener(TimePickerDialog.OnTimeSetListener onTimeSetListener) {
    this.onTimeSetListener = onTimeSetListener;
  }
}
