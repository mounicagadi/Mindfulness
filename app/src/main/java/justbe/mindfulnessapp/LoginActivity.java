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
    public final static String EXTRA_USERNAME = "justbe.mindfulnessapp.USERNAME";

    private EditText username_field;
    private EditText password_field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(getString(R.string.title_activity_login));

        username_field = (EditText) findViewById(R.id.editUsername);
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

    public void loginPressed(View view) {
        // Get the username and password from their respective fields
        username_field = (EditText) findViewById(R.id.editUsername);
        password_field = (EditText) findViewById(R.id.editPassword);
        String username = username_field.getText().toString();
        String password = password_field.getText().toString();

        login(username, password);
    }

    public void createAccountPressed(View view) {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    // TODO: Authenticate login
    private void login(String username, String password) {
        if(username.equals("admin") && password.equals("admin")) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(EXTRA_USERNAME, username);
            startActivity(intent);
        } else {
            password_field = (EditText) findViewById(R.id.editPassword);
            password_field.setError("Password and username didn't match");
        }
    }
}

