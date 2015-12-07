package justbe.mindfulnessapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

import justbe.mindfulnessapp.rest.UserPresentableException;

public class LoginActivity extends AppCompatActivity {

    /**
     * Fields
     */
    private EditText username_field;
    private EditText password_field;

    private ProgressDialog progressDialog;

    /**
     * Called when the view is created
     * @param savedInstanceState Saved Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Create the toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(getString(R.string.title_activity_login));

        // Create logging in progress spinner
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

    /**
     * Callback for when the login button is pressed
     * @param view The View
     */
    public void loginPressed(View view) {
        // Display the logging in spinner
        progressDialog.show();

        // Run login within a thread
        final AtomicBoolean success = new AtomicBoolean();
        Thread logInThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Get the username and password from their respective fields
                String username = username_field.getText().toString();
                String password = password_field.getText().toString();
                success.set(login(username, password));
                progressDialog.dismiss();
            }
        });

        // Start the thread and wait till its done
        logInThread.start();
        try {
            logInThread.join();
        } catch (InterruptedException e) {
            // Thread was interrupted
        }

        // Check the result from login and take the appropriate actions
        if (success.get()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            password_field = (EditText) findViewById(R.id.editPassword);
            password_field.setError("Password and username didn't match an account");
        }
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

    /**
     * Checks the username and password and moves on
     * @param username The Username
     * @param password The Password
     * @return If the login was successful or not
     */
    private Boolean login(String username, String password) {
        try {
            return App.getSession().authenticate(username, password);
        } catch (Exception e) {
            new UserPresentableException(e).alert(this);
        } finally {
            return false;
        }
    }
}

