package justbe.mindfulnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    public final static String EXTRA_USERNAME = "justbe.mindfulnessapp.USERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void login_pushed(View view) {
        // Get the username and password from their respective fields
        EditText username_field = (EditText) findViewById(R.id.editUsername);
        EditText password_field = (EditText) findViewById(R.id.editPassword);
        String username = username_field.getText().toString();
        String password = password_field.getText().toString();

        login(username, password);
    }

    // TODO: Authenticate login
    private void login(String username, String password) {
        if(username.equals("admin") && password.equals("admin")) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(EXTRA_USERNAME, username);
            startActivity(intent);
        } else {
            // REJECTED!
        }
    }
}
