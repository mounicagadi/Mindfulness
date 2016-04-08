package justbe.mindfulness;

import android.app.Activity;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import justbe.mindfulness.models.User;
import justbe.mindfulness.models.UserProfile;

public class CreateAccountActivity extends AppCompatActivity {

    /*
     * Fields
     */
    private int DEFAULT_GENDER = 0;
    private User user;
    private UserProfile userProfile;
    private EditText first_name_field;
    private EditText last_name_field;
    private EditText email_field;
    private EditText username_field;
    private EditText password_field;
    private EditText confirm_password_field;
    private Integer gender = DEFAULT_GENDER;
    private RadioGroup gender_group;
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
        first_name_field = (EditText) findViewById(R.id.editFirstname);
        last_name_field = (EditText) findViewById(R.id.editLastname);
        gender_group = (RadioGroup) findViewById(R.id.genderRow);
        email_field = (EditText) findViewById(R.id.editEmail);
        username_field = (EditText) findViewById(R.id.editUsername);
        password_field = (EditText) findViewById(R.id.editPassword);
        confirm_password_field = (EditText) findViewById(R.id.editConfirmPassword);

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
     * CreateAccountActivity Button Handlers
     **********************************************************************************************/

    /*
    * Radio button handler
    */

    public void onGenderButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.male:
                if (checked) {
                    gender = 0;
                    break;
                }
            case R.id.female:
                if (checked) {
                    gender = 1;
                    break;
                }
        }
    }

    /**
     * Callback for when the create account button is pressed
     * @param view The View
     */
    public void createAccountPressed(View view) {
        if ( validateActivity() ) {
            progressDialog.show();

            final Context context = CreateAccountActivity.this;
            // Run createAccount in its own thread
            Thread createAccountThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    user.setFirst_name(first_name_field.getText().toString());
                    user.setLast_name(last_name_field.getText().toString());
                    userProfile.setGender(gender);
                    user.setEmail(email_field.getText().toString());
                    user.setUsername(username_field.getText().toString());
                    user.setRaw_password(password_field.getText().toString());
                    // Run all the server requests to create an account
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
                        Intent intent = new Intent(CreateAccountActivity.this, SleepTimeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        progressDialog.dismiss();
                        finish();
                    } else { // Create account failed
                        progressDialog.dismiss();
                    }
                }
            };
        }
    }

    /***********************************************************************************************
     * CreateAccountActivity Specific Helpers
     **********************************************************************************************/

    /**
     * Validates all fields in the add user form and sets an error on the first invalid input
     * @return true if the form is valid, false if it is not
     */
   private boolean validateActivity() {

        if ( first_name_field.getText().length() == 0 ) {
            first_name_field.setError("The first name field must not be empty");
            return false;
        }

        if ( last_name_field.getText().length() == 0 ) {
            last_name_field.setError("The last name field must not be empty");
            return false;
        }

       /* if ( gender_group.getCheckedRadioButtonId() == -1 ) {
            Toast.makeText(getApplicationContext(), "Please select Gender", Toast.LENGTH_SHORT).show();
            return false;
        }*/

        if ( email_field.getText().length() == 0 ){
            email_field.setError("The email field must not be empty");
            return false;
        }
        else if ( !(android.util.Patterns.EMAIL_ADDRESS.matcher(email_field.getText()).matches()))
        {
            email_field.setError("Please enter valid email");
            return false;
        }
        if ( username_field.getText().length() == 0 ) {
            username_field.setError("The username field must not be empty");
            return false;
        }
        else if ( username_field.getText().length() < 6 ) {
            username_field.setError("Your username must be at least 6 characters");
            return false;
        }
        else if ( username_field.getText().length() > 16 ) {
            username_field.setError("Your username must be less than 16 characters");
            return false;
        }
        else if ( password_field.getText().length() < 6 ) {
            password_field.setError("Your password must be at least 6 characters");
            return false;
        }

         else if ( ! Util.samePassword(password_field, confirm_password_field) ) {
            confirm_password_field.setError("Your passwords do not match");
            return false;
        }

       return true;
    }
}
