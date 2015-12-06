package justbe.mindfulnessapp;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import justbe.mindfulnessapp.models.User;
import justbe.mindfulnessapp.rest.UserPresentableException;

public class PreferencesActivity extends AppCompatActivity implements RefreshViewListener {

    /**
     * Fields
     */
    private User user;
    private TextView currentUsername;
    private TextView meditationTimeText;
    private TextView lessonTimeText;
    private TextView wakeUpTimeText;
    private TextView goToSleepTimeText;

    /**
     * Called when the view is created
     * @param savedInstanceState Saved Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        // Create the toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(getString(R.string.title_activity_preferences));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Set variables to their Text Views
        currentUsername = (TextView) findViewById(R.id.currentUsername);
        meditationTimeText = (TextView) findViewById(R.id.meditationTime);
        lessonTimeText = (TextView) findViewById(R.id.lessonTime);
        wakeUpTimeText = (TextView) findViewById(R.id.wakeUpTime);
        goToSleepTimeText = (TextView) findViewById(R.id.goToSleepTime);

        // Set the fields to the user's values
        user = App.getSession().getUser();
        currentUsername.setText(user.getUsername());
        setTimeFields();
    }

    /**
     * Sets time fields on view, callable from anywhere
     */
    public void refreshView() {
        setTimeFields();
    }

    /**
     * Saves the time from the Time Picker
     */
    public void saveTimes(int buttonID, String time) {
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
     * Sets time fields on view
     */
    private void setTimeFields() {
        // Get times from user
        user = App.getSession().getUser();
        Date meditationTime = user.getMeditation_time();
        Date lessonTime = user.getExercise_time();
        Date wakeUpTime = user.getWake_up_time();
        Date goToSleepTime = user.getGo_to_sleep_time();
        
        DateFormat sdf = new SimpleDateFormat("hh:mm a");
        if(meditationTime != null)
            meditationTimeText.setText(sdf.format(meditationTime));
        if(lessonTime != null)
            lessonTimeText.setText(sdf.format(lessonTime));
        if(wakeUpTime != null)
            wakeUpTimeText.setText(sdf.format(wakeUpTime));
        if(goToSleepTime != null)
            goToSleepTimeText.setText(sdf.format(goToSleepTime));
    }

    /**
     * Callback for when the wake up, go to sleep, lesson, or meditation button is pressed
     * Creates and displays the Time Picker
     * @param view The view
     */
    public void showTimePickerDialog(View view) {
        // Get the button ID so we know what field we are editing
        int buttonID = view.getId();
        Bundle bundle = new Bundle();
        bundle.putInt("buttonID", buttonID);

        // Create the Time Picker
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.setArguments(bundle);
        newFragment.show(getFragmentManager(), "timePicker");
    }

    /**
     * Callback for when the logout button is pressed
     * Logs the current user out of the app and sends them to the login activity
     * @param view The view
     */
    public void logout(View view) {
        if (App.getSession().invalidate()) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            new UserPresentableException(
                    getString(R.string.cannot_logout),
                    getString(R.string.cannot_logout_message)).alert(this);
        }
    }
}