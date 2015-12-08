package justbe.mindfulnessapp;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

import justbe.mindfulnessapp.models.User;
import justbe.mindfulnessapp.models.UserProfile;
import justbe.mindfulnessapp.rest.UserPresentableException;

public class CreateAccountActivity extends AppCompatActivity implements RefreshViewListener {

    /*
     * Fields
     */
    private User user;
    private UserProfile userProfile;
    private EditText username_field;
    private EditText password_field;
    private EditText confirm_password_field;
    private TextView meditationTimeText;
    private TextView lessonTimeText;
    private TextView wakeUpTimeText;
    private TextView goToSleepTimeText;

    private String meditationTime;
    private String lessonTime;
    private String wakeUpTime;
    private String goToSleepTime;

    private ProgressDialog progressDialog;
    private static Handler createAccountHandler;

    /***********************************************************************************************
     * CreateAccountActivity Life Cycle Functions
     **********************************************************************************************/

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

        // Create 'Creating Account' progress spinner
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.creatingAccount));
        progressDialog.setMessage(getString(R.string.pleaseWait));
        progressDialog.setCancelable(false);

        // Create new user object and corresponding user profile object
        user = new User();
        userProfile = new UserProfile();

        // Set variables to their Text Views
        username_field = (EditText) findViewById(R.id.editUsername);
        password_field = (EditText) findViewById(R.id.editPassword);
        confirm_password_field = (EditText) findViewById(R.id.editConfirmPassword);
        meditationTimeText = (TextView) findViewById(R.id.meditationTime);
        lessonTimeText = (TextView) findViewById(R.id.lessonTime);
        wakeUpTimeText = (TextView) findViewById(R.id.wakeUpTime);
        goToSleepTimeText = (TextView) findViewById(R.id.goToSleepTime);

        // Set the fields to the user's values
        String currentTime = Util.dateToDisplayString(new Date());
        meditationTime = currentTime;
        lessonTime = currentTime;
        wakeUpTime = currentTime;
        goToSleepTime = currentTime;
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
    }

    /***********************************************************************************************
     * CreateAccountActivity Button Handlers
     **********************************************************************************************/

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
            progressDialog.show();

            // Run createAccount in its own thread
            Thread createAccountThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    user.setUsername(username_field.getText().toString());
                    user.setRaw_password(password_field.getText().toString());

                    // Run all the server requests to create an account
                    Context context = getApplicationContext();
                    Boolean createAccountSuccess = (ServerRequests.createUser(user, context) &&
                            ServerRequests.updateUserWithUserProfile(user, userProfile, context) &&
                            // Create meditation sessions for first week
                            ServerRequests.populateDatabaseForWeek(1, context));

                    // Attempt to login and save the result
                    if(createAccountSuccess) {
                        createAccountHandler.sendEmptyMessage(0);
                    } else {
                        createAccountHandler.sendEmptyMessage(1);
                    }
                }
            });

            // Start the thread and wait till its done
            createAccountThread.start();

            // Handler to deal with the result of the create account thread
            createAccountHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // Log in succeeded
                    if(msg.what == 0) {
                        Intent intent = new Intent(getApplicationContext(), StartProgramActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        progressDialog.dismiss();
                        finish();
                    } else { // Create account failed
                        progressDialog.dismiss();
                        throw new UserPresentableException(
                                getString(R.string.auth_failed),
                                getString(R.string.cant_login_to_new_account));
                    }
                }
            };
        }
    }

    /***********************************************************************************************
     * CreateAccountActivity Specific Helpers
     **********************************************************************************************/

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

        if ( ! Util.samePassword(password_field, confirm_password_field) ) {
            confirm_password_field.setError("Your passwords do not match");
            return false;
        } else if ( password_field.getText().length() < 6 ) {
            password_field.setError("Your password must be at least 6 characters");
            return false;
        }

        return true;
    }
}
