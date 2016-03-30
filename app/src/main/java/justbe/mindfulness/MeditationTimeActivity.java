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
 * Used to set the meditation times of the user.
 */
public class MeditationTimeActivity extends AppCompatActivity implements RefreshViewListener {

    private User user;
    private UserProfile userProfile;
    private TextView meditationTimeText;
    private String meditationTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation_time);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Meditation Times");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Set variables to their Text Views
        meditationTimeText = (TextView) findViewById(R.id.meditationTime);

        user = App.getSession().getUser();
        userProfile = new UserProfile(user);
        // Set the fields to the user's values
        meditationTime = Util.dateToDisplayString(new Date());
        setTimeFields();
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
            case R.id.meditationTime:
                meditationTime = Util.dateToDisplayString(time);
                userProfile.setMeditation_time(Util.dateToUserProfileString(time));
                break;
            default:
                throw new RuntimeException("Attempted to set time for unknown field");
        }
        ServerRequests.updateUserWithUserProfile(user, userProfile, getApplicationContext());
        user = App.getSession().getUser();
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
     * Sets time fields on view
     */
    private void setTimeFields() {
        meditationTimeText.setText(meditationTime);
    }

    /**
     * Callback for when the meditation time next button is pressed
     * @param view The View
     */
    public void meditationNextButtonPressed(View view) {

        Intent intent = new Intent(MeditationTimeActivity.this, StartProgramActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }


}
