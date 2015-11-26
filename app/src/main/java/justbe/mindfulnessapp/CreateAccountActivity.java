package justbe.mindfulnessapp;

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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import justbe.mindfulnessapp.models.User;
import justbe.mindfulnessapp.rest.GenericHttpRequestTask;
import justbe.mindfulnessapp.rest.ResponseWrapper;
import justbe.mindfulnessapp.rest.RestUtil;
import justbe.mindfulnessapp.rest.UserPresentableException;

public class CreateAccountActivity extends AppCompatActivity {

    /*
     * Fields
     */
    private EditText username_field;
    private EditText password_field;
    private EditText confirm_password_field;

    /**
     * Called when the view is created
     * @param savedInstanceState Instance State
     */
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
     * Callback for when the create account button is pressed
     * @param view The View
     */
    public void createAccountPressed(View view) {
        if ( validateActivity() ) {
            User u = createUser();

            // Create an HTTPRequestTask that sends a User Object and Returns a User Object
            GenericHttpRequestTask<User, User> task = new GenericHttpRequestTask(User.class, User.class);

            task.execute("/api/v1/create_user/", HttpMethod.POST, u);

            try {
                ResponseEntity<User> result = task.waitForResponse();

                RestUtil.checkResponseHazardously(result);

                // Authenticate with the server, store session
                if ( ! App.getSession().authenticate(u.getUsername(), u.getRaw_password()) ) {
                    throw new UserPresentableException(
                            getString(R.string.auth_failed),
                            getString(R.string.cant_login_to_new_account));
                }

                // Go to the getting stated activity
                Intent intent = new Intent(this, GettingStartedActivity.class);
                startActivity(intent);

            } catch (Exception e) {
                new UserPresentableException(e).alert(this);
            }
        }
    }

    /**
     * Creates a user model from the current state of the fields in the activity
     * @return A User object
     */
    private User createUser() {
        User u = new User();
        u.setUsername(username_field.getText().toString());
        u.setRaw_password(password_field.getText().toString());


        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        u.setBirthday(sdf.format(dt)); ///birthday_field.getText()));

        return u;
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
