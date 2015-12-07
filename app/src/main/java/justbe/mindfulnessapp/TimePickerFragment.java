package justbe.mindfulnessapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TimePicker;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Calendar;
import java.util.Date;

import justbe.mindfulnessapp.models.User;
import justbe.mindfulnessapp.rest.GenericHttpRequestTask;
import justbe.mindfulnessapp.rest.RestUtil;

/**
 * Time Picker used to choose times for the user preferences
 * Determines what field to update using the pressed button's ID
 * To use this the class must implement RefreshViewListener to handle saving and updating
 */
public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    /**
     * Fields
     */
    private int buttonID;
    private RefreshViewListener listener;
    private User user;

    /***********************************************************************************************
     * TimePickerFragment Life Cycle Functions
     **********************************************************************************************/
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

        // check to see of the user exists yet
        user = App.getSession().getUser();
        if(user == null)
            user = new User();

        // Set the picker time to the current user value
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

    /***********************************************************************************************
     * TimePickerDialog.OnTimeSetListener Functions
     **********************************************************************************************/

    /**
     * Called when TimePicker is dismissed
     * @param frag The TimePicker
     */
    @Override
    public void onDismiss(DialogInterface frag) {
        super.onDismiss(frag);

        // Reload the time fields with the new values
        listener.refreshView();
    }

    /**
     * Attach the listener to the time picker so we can refresh the view
     * @param activity The parent activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (RefreshViewListener) activity;
        } catch (ClassCastException castException) {

        }
    }

    /**
     * Called when the Time Picker gets set to a new time
     * @param view The Time Picker
     * @param hourOfDay The hour selected on the Time picker
     * @param minute The minute selected on the Time picker
     */
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        Date time = cal.getTime();

        // Call parent activity's save time method
        listener.saveTimes(buttonID, time);
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