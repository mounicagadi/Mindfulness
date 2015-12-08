package justbe.mindfulnessapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.os.Handler;

import justbe.mindfulnessapp.models.User;
import justbe.mindfulnessapp.rest.UserPresentableException;

/**
 * Activity that controls logging in
 * First Activity in the app
 */
public class LoginActivity extends AppCompatActivity  {

    /**
     * Fields
     */
    private EditText username_field;
    private EditText password_field;

    private ProgressDialog progressDialog;
    private static Handler loginHandler;
    private SessionManager sessionManager;

    /***********************************************************************************************
     * LoginActivity Life Cycle Functions
     **********************************************************************************************/
    /**
     * Called when the view is created
     * @param savedInstanceState Saved Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();


        // Create the toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(getString(R.string.title_activity_login));

        // Create new session manager
        sessionManager = new SessionManager(getApplicationContext());

        // Create 'logging in' progress spinner
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.loggingIn));
        progressDialog.setMessage(getString(R.string.pleaseWait));
        progressDialog.setCancelable(false);

        // Assign EditText fields to their variables
        username_field = (EditText) findViewById(R.id.editUsername);
        password_field = (EditText) findViewById(R.id.editPassword);

        // Add TextChangedListener to handle error dismissal
        username_field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EditText password_field = (EditText) findViewById(R.id.editPassword);
                password_field.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /***********************************************************************************************
     * LoginActivity Button Handlers
     **********************************************************************************************/

    /**
     * Callback for when the login button is pressed
     * @param view The View
     */
    public void loginPressed(View view) {
        // Display the logging in spinner
        progressDialog.show();

        // Run login within a thread
        Thread logInThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Get the username and password from their respective fields
                String username = username_field.getText().toString();
                String password = password_field.getText().toString();

                // Attempt to login and save the result
                if(login(username, password)) {
                    loginHandler.sendEmptyMessage(0);
                } else {
                    loginHandler.sendEmptyMessage(1);
                }
            }
        });

        // Start the thread and wait till its done
        logInThread.start();

        // Handler to deal with the result of the login thread
        loginHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // Log in succeeded
                if(msg.what == 0) {
                    Session session = App.getSession();
                    User user = session.getUser();
                    sessionManager.createLoginSession(session.getSessionId(), session.getCsrfToken(),
                            user);

                    // If user hasn't started program yet, go to StartProgramActivity
                    if (user.getCurrent_week() == 0) {
                        Intent intent = new Intent(getApplicationContext(), StartProgramActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    progressDialog.dismiss();
                    finish();
                } else { // Log in failed
                    password_field = (EditText) findViewById(R.id.editPassword);
                    password_field.setError("Password and username didn't match an account");
                    progressDialog.dismiss();
                }
            }
        };
    }

    /**
     * Callback for when the create account button is pressed
     * @param view The View
     */
    public void createAccountPressed(View view) {
        Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    /***********************************************************************************************
     * LoginActivity Specific Helpers
     **********************************************************************************************/

    /**
     * Checks the username and password and moves on
     * @param username The Username
     * @param password The Password
     * @return If the login was successful or not
     */
    private Boolean login(String username, String password) {
        Boolean success = false;
        try {
            success = App.getSession().authenticate(username, password);
        } catch (Exception e) {
            new UserPresentableException(e).alert(this);
        }
        return success;
    }
}

