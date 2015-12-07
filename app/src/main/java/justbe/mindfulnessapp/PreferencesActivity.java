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
import justbe.mindfulnessapp.models.UserProfile;
import justbe.mindfulnessapp.rest.UserPresentableException;

public class PreferencesActivity extends AppCompatActivity implements RefreshViewListener {

    /**
     * Fields
     */
    private User user;
    private UserProfile userProfile;
    private TextView currentUsername;
    private TextView meditationTimeText;
    private TextView lessonTimeText;
    private TextView wakeUpTimeText;
    private TextView goToSleepTimeText;

    private String meditationTime;
    private String lessonTime;
    private String wakeUpTime;
    private String goToSleepTime;

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

        // Setup the User and UserProfile
        user = App.getSession().getUser();
        userProfile = new UserProfile(user);

        // Set the fields to the user's values
        currentUsername.setText(user.getUsername());
        meditationTime = Util.dateToDisplayString(user.getMeditation_time());
        lessonTime = Util.dateToDisplayString(user.getExercise_time());
        wakeUpTime = Util.dateToDisplayString(user.getWake_up_time());
        goToSleepTime = Util.dateToDisplayString(user.getGo_to_sleep_time());
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
    public void saveTimes(int buttonID, Date time) {
        // Check to see what field we are editing
        switch (buttonID) {
            case R.id.meditationRow:
                meditationTime = Util.dateToDisplayString(time);
                userProfile.setMeditation_time(Util.dateToUserProfileString(time));
                break;
            case R.id.lessonRow:
                lessonTime = Util.dateToDisplayString(time);
                userProfile.setExercise_time(Util.dateToUserProfileString(time));
                break;
            case R.id.wakeUpRow:
                wakeUpTime = Util.dateToDisplayString(time);
                userProfile.setWake_up_time(Util.dateToUserProfileString(time));
                break;
            case R.id.goToSleepRow:
                goToSleepTime = Util.dateToDisplayString(time);
                userProfile.setGo_to_sleep_time(Util.dateToUserProfileString(time));
                break;
            default:
                throw new RuntimeException("Attempted to set time for unknown field");
        }
        userProfile.updateUserWithUserProfile(user);
        user = App.getSession().getUser();
    }

    /**
     * Sets time fields on view
     */
    private void setTimeFields() {
        meditationTimeText.setText(meditationTime);
        lessonTimeText.setText(lessonTime);
        wakeUpTimeText.setText(wakeUpTime);
        goToSleepTimeText.setText(goToSleepTime);
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