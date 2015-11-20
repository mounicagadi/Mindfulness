package justbe.mindfulnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText current_password_field;
    private EditText new_password_field;
    private EditText confirm_new_password_field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(getString(R.string.title_activity_preferences));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        current_password_field = (EditText) findViewById(R.id.editCurrentPassword);
        new_password_field = (EditText) findViewById(R.id.editNewPassword);
        confirm_new_password_field = (EditText) findViewById(R.id.editConfirmNewPassword);

        new_password_field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                confirm_new_password_field.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void changePassword(View view) {
        // TODO: Make sure the current password is valid

        if(new_password_field.getText().toString().isEmpty()) {
            new_password_field.setError("Did not enter a password");
        } else if(current_password_field.getText().toString().equals("admin")) {
            if (samePassword()) {
                // TODO: Change password here

                // Go to the getting stated activity
                Intent intent = new Intent(this, PreferencesActivity.class);
                startActivity(intent);
            } else {
                confirm_new_password_field.setError("Passwords didn't match");
            }
        } else {
            current_password_field.setError("Incorrect Password");
        }
    }

    private boolean samePassword() {
        return new_password_field.getText().toString().equals(confirm_new_password_field.getText().toString());
    }

}
