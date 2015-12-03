package justbe.mindfulnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;

public class LoginActivity extends AppCompatActivity {

    /**
     * Fields
     */
    private EditText username_field;
    private EditText password_field;

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

        // Assign EditText fields to their variables
        username_field = (EditText) findViewById(R.id.editUsername);
        password_field = (EditText) findViewById(R.id.editPassword);

        // Add TextChangedListener to handle error dismisal
        username_field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EditText password_field = (EditText) findViewById(R.id.editPassword);
                password_field.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    /**
     * Callback for when the login button is pressed
     * @param view The View
     */
    public void loginPressed(View view) {
        // Get the username and password from their respective fields
        String username = username_field.getText().toString();
        String password = password_field.getText().toString();

        login(username, password);
    }

    /**
     * Callback for when the create account button is presed
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
     */
    private void login(String username, String password) {
        if(App.getSession().authenticate(username, password)) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            password_field = (EditText) findViewById(R.id.editPassword);
            password_field.setError("Password and username didn't match an account");
        }
    }
}

