package justbe.mindfulnessapp;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import justbe.mindfulnessapp.models.User;
import justbe.mindfulnessapp.rest.UserPresentableException;

public class PreferencesActivity extends AppCompatActivity {

    /**
     * Fields
     */
    private User user;
    private TextView currentUsername;
    private TextView meditationTime;
    private TextView lessonTime;
    private TextView wakeUpTime;
    private TextView goToSleepTime;

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
        meditationTime = (TextView) findViewById(R.id.meditationTime);
        lessonTime = (TextView) findViewById(R.id.lessonTime);
        wakeUpTime = (TextView) findViewById(R.id.wakeUpTime);
        goToSleepTime = (TextView) findViewById(R.id.goToSleepTime);

        user = App.getSession().getUser();
        currentUsername.setText(user.getUsername());
        setTimeFields();
    }

    public void refreshTimeFields() {
        setTimeFields();
    }

    private void setTimeFields() {
        user = App.getSession().getUser();
        DateFormat sdf = new SimpleDateFormat("hh:mm a");
        meditationTime.setText(sdf.format(user.getMeditation_time()));
        lessonTime.setText(sdf.format(user.getExercise_time()));
        //wakeUpTime.setText(sdf.format(user.getWake_up_time()));
        //goToSleepTime.setText(sdf.format(user.getGo_to_sleep_time()));
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
     * Callback for when the change password button is pressed
     * @param view The view
     */
    public void changePassword(View view) {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    /**
     * Callback for when the logout button is pressed
     * Logs the current user out of the app and sends them to the login activity
     * @param view The view
     */
    public void logout(View view) {
        if (App.getSession().invalidate()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            new UserPresentableException(
                    getString(R.string.cannot_logout),
                    getString(R.string.cannot_logout_message)).alert(this);
        }
    }
}