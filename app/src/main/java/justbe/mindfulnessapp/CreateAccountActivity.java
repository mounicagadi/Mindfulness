package justbe.mindfulnessapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import justbe.mindfulnessapp.models.User;
import justbe.mindfulnessapp.rest.HttpRequestTask;
import justbe.mindfulnessapp.rest.QueryStatus;
import justbe.mindfulnessapp.rest.RESTResultReceiver;
import justbe.mindfulnessapp.rest.ResponseWrapper;

public class CreateAccountActivity extends AppCompatActivity implements RESTResultReceiver.Receiver {

    private EditText username_field;
    private EditText password_field;
    private EditText confirm_password_field;
    private EditText email_field;
    private EditText first_name_field;
    private EditText last_name_field;
    private EditText birthday_field;
    private EditText gender_field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(getString(R.string.title_activity_create_account));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        username_field = (EditText) findViewById(R.id.editUsername);
        password_field = (EditText) findViewById(R.id.editPassword);
        confirm_password_field = (EditText) findViewById(R.id.editConfirmPassword);
        email_field = (EditText) findViewById(R.id.editEmail);
        first_name_field = (EditText) findViewById(R.id.editFirstName);
        last_name_field = (EditText) findViewById(R.id.editLastName);
        birthday_field = (EditText) findViewById(R.id.editBirthday);
        gender_field = (EditText) findViewById(R.id.editGender);

        confirm_password_field.addTextChangedListener(new TextWatcher() {
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

    public void createAccountPressed(View view) {
        if (samePassword()) {
            // Get params from fields
            User u = new User();
            u.setUsername(username_field.getText().toString());
            u.setRaw_password(password_field.getText().toString());
            u.setEmail(email_field.getText().toString());

            java.util.Date dt = new java.util.Date();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            u.setBirthday(sdf.format(dt)); ///birthday_field.getText()));

            switch (gender_field.getText().toString().toLowerCase()) {
                case "male":
                case "boy":
                    u.setGender(User.Gender.MALE.getValue());
                    break;
                case "female":
                case "girl":
                    u.setGender(User.Gender.FEMALE.getValue());
                    break;
                default:
                    u.setGender(User.Gender.OTHER.getValue());
                    break;
            }

            // Create an HTTPRequestTask that sends a User Object and Returns a User Object
            HttpRequestTask<User, User> task = new HttpRequestTask();

            task.execute("/api/v1/create_user/", HttpMethod.POST, u);
            ResponseEntity<ResponseWrapper<User>> result;
            try {
                result = task.get(5000, TimeUnit.SECONDS);

                if (result == null) {
                    throw new Exception("We couldn't create your account at this time. Please try again later.");
                } else if (result.getStatusCode() != HttpStatus.CREATED) {
                    if (result.getBody().getError() != null) {
                        throw result.getBody().getError();
                    }
                }

                // Go to the getting stated activity
                Intent intent = new Intent(this, GettingStartedActivity.class);
                startActivity(intent);

            } catch (Exception e) {
                new AlertDialog.Builder(this)
                        .setTitle("Account Creation Failed")
                        .setMessage(e.getMessage())
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Return to dialog
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }


        } else {
            confirm_password_field.setError("Passwords didn't match");
        }
    }

    private boolean samePassword() {
        return password_field.getText().toString().equals(confirm_password_field.getText().toString());
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case QueryStatus.STATUS_RUNNING:
                //show progress
                break;
            case QueryStatus.STATUS_FINISHED:
                List results = resultData.getParcelableArrayList("results");
                // do something interesting
                // hide progress
                break;
            case QueryStatus.STATUS_ERROR:
                // handle the error;
                new AlertDialog.Builder(this)
                        .setTitle("Account Creation 1234")
                        .setMessage("Sorry! we couldn't create your account at this time")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Return to dialog
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                break;
        }
    }
}
