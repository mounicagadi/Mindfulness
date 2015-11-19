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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class CreateAccountActivity extends AppCompatActivity {

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
        if(samePassword()) {
            // Get params from fields
            Map<String,String> params = new HashMap<String, String>();
            params.put("username", username_field.getText().toString());
            params.put("password", password_field.getText().toString());
            params.put("email", email_field.getText().toString());
            params.put("first_name", first_name_field.getText().toString());
            params.put("last_name", last_name_field.getText().toString());
            params.put("birthday", birthday_field.getText().toString());
            params.put("gender", gender_field.getText().toString());

            // Create connection and post the new account
            PostTask task = new PostTask(this);

            task.execute("https://api.hurtigtechnologies.com/submit?content=foobaz", params);

            try {
                task.get(5000, TimeUnit.MILLISECONDS);

                // Go to the getting stated activity
                Intent intent = new Intent(this, GettingStartedActivity.class);
                startActivity(intent);

            } catch (Exception e) {
                new AlertDialog.Builder(this)
                        .setTitle("Account Creation")
                        .setMessage("Sorry! we couldn't create your account at this time")
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
}
