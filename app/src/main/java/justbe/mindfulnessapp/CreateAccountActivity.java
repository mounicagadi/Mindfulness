package justbe.mindfulnessapp;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import justbe.mindfulnessapp.models.MeditationSession;
import justbe.mindfulnessapp.models.User;
import justbe.mindfulnessapp.rest.GenericHttpRequestTask;
import justbe.mindfulnessapp.rest.RestUtil;
import justbe.mindfulnessapp.rest.UserPresentableException;

public class CreateAccountActivity extends AppCompatActivity implements RefreshViewListener {

    /*
     * Fields
     */
    private User user;
    private EditText username_field;
    private EditText password_field;
    private EditText confirm_password_field;
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
        setContentView(R.layout.activity_create_account);

        // Create Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(getString(R.string.title_activity_create_account));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Create new user object
        user = createDefaultNewUser();

        // Set variables to their Text Views
        username_field = (EditText) findViewById(R.id.editUsername);
        password_field = (EditText) findViewById(R.id.editPassword);
        confirm_password_field = (EditText) findViewById(R.id.editConfirmPassword);
        meditationTimeText = (TextView) findViewById(R.id.meditationTime);
        lessonTimeText = (TextView) findViewById(R.id.lessonTime);
        wakeUpTimeText = (TextView) findViewById(R.id.wakeUpTime);
        goToSleepTimeText = (TextView) findViewById(R.id.goToSleepTime);

        // Set the fields to the user's values
        setTimeFields();

        // TextChangedListener to handle error dismissal
        password_field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                confirm_password_field.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * Creates a default user with the current time set as the default times
     * @return the default user
     */
    private User createDefaultNewUser() {
        User u = new User();
        u.setProgram_week(0);

        // Set up default times
        Date currentTime = new Date();
        u.setMeditation_time(formatTime(currentTime));
        u.setExercise_time(formatTime(currentTime));
        u.setWake_up_time(formatTime(currentTime));
        u.setGo_to_sleep_time(formatTime(currentTime));

        return u;
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
    }

    /**
     * Sets time fields on view
     */
    private void setTimeFields() {
        // Get times from user
        Date meditationTime = user.getMeditation_time();
        Date lessonTime = user.getExercise_time();
        Date wakeUpTime = user.getWake_up_time();
        Date goToSleepTime = user.getGo_to_sleep_time();

        meditationTimeText.setText(formatTime(meditationTime));
        lessonTimeText.setText(formatTime(lessonTime));
        wakeUpTimeText.setText(formatTime(wakeUpTime));
        goToSleepTimeText.setText(formatTime(goToSleepTime));
    }

    /**
     * Formats the given Date into a string or the current Date if the given Date is null
     * @param time The Date to format
     * @return String representation of the given Date or the current Date
     */
    private String formatTime(Date time) {
        DateFormat sdf = new SimpleDateFormat("hh:mm a");
        if(time == null)
            time = new Date();
        return sdf.format(time);
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
     * Callback for when the create account button is pressed
     * @param view The View
     */
    public void createAccountPressed(View view) {
        if ( validateActivity() ) {
            user.setUsername(username_field.getText().toString());
            user.setRaw_password(password_field.getText().toString());

            // Create an HTTPRequestTask that sends a User Object and Returns a User Object
            GenericHttpRequestTask<User, User> task = new GenericHttpRequestTask(User.class, User.class);

            task.execute("/api/v1/create_user/", HttpMethod.POST, user);

            try {
                ResponseEntity<User> result = task.waitForResponse();

                RestUtil.checkResponseHazardously(result);

                // Authenticate with the server, store session
                if ( ! App.getSession().authenticate(user.getUsername(), user.getRaw_password()) ) {
                    throw new UserPresentableException(
                            getString(R.string.auth_failed),
                            getString(R.string.cant_login_to_new_account));
                }

                // Create meditation sessions for first week
                MeditationSession.populateDatabaseForWeek(1);

                // Go to the getting stated activity
                Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            } catch (Exception e) {
                new UserPresentableException(e).alert(this);
            }
        }
    }

    /**
     * Checks to make sure that the two password fields are the same
     * @return whether the password fields are the same
     */
    private boolean samePassword() {
        return password_field.getText().toString().equals(confirm_password_field.getText().toString());
    }


    /**
     * Validates all fields in the add user form and sets an error on the first invalid input
     * @return true if the form is valid, false if it is not
     */
    private boolean validateActivity() {
        if ( username_field.getText().length() == 0 ) {
            username_field.setError("The username field must not be empty");
            return false;
        } else if ( username_field.getText().length() > 16 ) {
            username_field.setError("Your username must be less than 16 characters");
            return false;
        }

        if ( ! samePassword() ) {
            confirm_password_field.setError("Your passwords do not match");
            return false;
        } else if ( password_field.getText().length() < 6 ) {
            password_field.setError("Your password must be at least 6 characters");
            return false;
        }

        return true;
    }
}
