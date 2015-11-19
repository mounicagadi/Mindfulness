package justbe.mindfulnessapp;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {
    private int buttonID;

    @Override
    public Dialog onCreateDialog(Bundle savedInstaceState) {
        // get the button ID to determine what we are changing
        Bundle bundle = getArguments();
        buttonID = bundle.getInt("buttonID");

        // Use the current time as the default values for the picker
        // TODO: Change this to get the currently selected time
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute,
                android.text.format.DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        switch (buttonID) {
            case R.id.meditationRow:
                // Save meditation time
            case R.id.lessonRow:
                // Save start lesson time
            case R.id.wakeUpRow:
                // Save wake up time
            case R.id.goToSleepRow:
                // Save go to sleep time
            default:
                // should never reach this
        }
    }
}