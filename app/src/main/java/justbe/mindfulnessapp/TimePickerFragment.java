package justbe.mindfulnessapp;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TimePicker;

import java.util.Date;

import justbe.mindfulnessapp.models.User;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    /**
     * Fields
     */
    private int buttonID;
    private User user;

    /**
     * Called when the dialog is created
     * @param savedInstanceState Saved Instance State
     * @return The newly created Time Picker Dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // get the button ID to determine what we are changing
        Bundle bundle = getArguments();
        buttonID = bundle.getInt("buttonID");

        // Set the picker time to the current user value
        user = App.getSession().getUser();
        int[] time = new int[2];
        switch (buttonID) {
            case R.id.meditationRow:
                time = convertDateToHourMin(user.getMeditation_time());
                break;
            case R.id.lessonRow:
                time = convertDateToHourMin(user.getExercise_time());
                break;
            case R.id.wakeUpRow:
                time = convertDateToHourMin(user.getWake_up_time());
                break;
            case R.id.goToSleepRow:
                time = convertDateToHourMin(user.getGo_to_sleep_time());
                break;
            default:
                time = convertDateToHourMin(new Date());
                break;
        }
        int hour = time[0];
        int minute = time[1];
        return new TimePickerDialog(getActivity(), this, hour, minute,
                android.text.format.DateFormat.is24HourFormat(getActivity()));
    }

    /**
     * Called when TimePicker is dismissed
     * @param frag The TimePicker
     */
    @Override
    public void onDismiss(DialogInterface frag) {
        super.onDismiss(frag);
        // Reload the time fields with the new values
        ((PreferencesActivity)getActivity()).refreshTimeFields();
    }

    /**
     * Called when the Time Picker gets set to a new time
     * @param view The Time Picker
     * @param hourOfDay The hour selected on the Time picker
     * @param minute The minute selected on the Time picker
     */
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        StringBuilder sb = new StringBuilder();
        sb.append(hourOfDay);
        sb.append(":");
        sb.append(minute);
        sb.append(":00");
        String time = sb.toString();

        // Check to see what field we are editing
        switch (buttonID) {
            case R.id.meditationRow:
                user.setMeditation_time(time);
                break;
            case R.id.lessonRow:
                user.setExercise_time(time);
                break;
            case R.id.wakeUpRow:
                user.setWake_up_time(time);
                break;
            case R.id.goToSleepRow:
                user.setGo_to_sleep_time(time);
                break;
            default:
                throw new RuntimeException("Attempted to set time for unknown field");
        }

        App.getSession().setUser(user);
    }

    /**
     * Converts the Date held by the User object to two integers
     * If the Date is null, returns the current Time
     * @param time Time in date format
     * @return Integer array containing hour and minute from given time
     *         or the current time if the given time is null
     */
    private int[] convertDateToHourMin(Date time) {
        if(time == null)
            time = new Date();
        int[] res = {time.getHours(), time.getMinutes()};
        return res;
    }
}