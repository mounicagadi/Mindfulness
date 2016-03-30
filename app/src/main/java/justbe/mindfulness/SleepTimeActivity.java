package justbe.mindfulness;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import java.util.Date;
import justbe.mindfulness.models.User;
import justbe.mindfulness.models.UserProfile;

/**
 * Created by Nikhil Reddy on 27-03-2016.
 * Used to set sleep times of the user.
 */
public class SleepTimeActivity extends AppCompatActivity implements RefreshViewListener {

    private User user;
    private UserProfile userProfile;

    private TextView wakeUpTimeText;
    private TextView goToSleepTimeText;
    private String wakeUpTime;
    private String goToSleepTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_time);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sleep Times");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Set variables to their Text Views
        wakeUpTimeText = (TextView) findViewById(R.id.wakeUpTime);
        goToSleepTimeText = (TextView) findViewById(R.id.goToSleepTime);

        user = App.getSession().getUser();
        userProfile = new UserProfile(user);

        // Set the fields to the user's values
        String currentTime = Util.dateToDisplayString(new Date());
        wakeUpTime = currentTime;
        goToSleepTime = currentTime;
        setTimeFields();

    }

    /**
     * Sets time fields on view
     */
    private void setTimeFields() {
        wakeUpTimeText.setText(wakeUpTime);
        goToSleepTimeText.setText(goToSleepTime);
    }

    /***********************************************************************************************
     * RefreshViewListener Functions
     **********************************************************************************************/

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
            case R.id.wakeUpTime:
                wakeUpTime = Util.dateToDisplayString(time);
                userProfile.setWake_up_time(Util.dateToUserProfileString(time));
                break;
            case R.id.goToSleepTime:
                goToSleepTime = Util.dateToDisplayString(time);
                userProfile.setGo_to_sleep_time(Util.dateToUserProfileString(time));
                break;
            default:
                throw new RuntimeException("Attempted to set time for unknown field");
        }
        ServerRequests.updateUserWithUserProfile(user, userProfile, getApplicationContext());
        user = App.getSession().getUser();
    }

    /**
     * Callback for when the wake up, go to sleep button is pressed
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
     * Callback for when the sleep time next button is pressed
     * @param view The View
     */
    public void sleepNextButtonPressed(View view) {

        Intent intent = new Intent(SleepTimeActivity.this, LessonTimeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

}
