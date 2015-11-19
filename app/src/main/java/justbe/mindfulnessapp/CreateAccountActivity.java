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

import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText username_field;
    private EditText password_field;
    private EditText confirm_password_field;

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

    public void createAccountPressed(View view) {
        if(samePassword()) {
            // Get params from fields
            Map<String,String> params = new HashMap<String, String>();
            params.put("username", username_field.getText().toString());
            params.put("password", password_field.getText().toString());

            // Create connection and post the new account
            new PostTask(this).execute("https://www.secure-headland-8362.herokuapp.com/api/v1/create_user/", params);

            // Go to the getting stated activity
            Intent intent = new Intent(this, GettingStartedActivity.class);
            startActivity(intent);
        } else {
            confirm_password_field.setError("Passwords didn't match");
        }
    }

    private boolean samePassword() {
        return password_field.getText().toString().equals(confirm_password_field.getText().toString());
    }
}
