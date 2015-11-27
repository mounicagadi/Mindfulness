package justbe.mindfulnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class ChangePasswordActivity extends AppCompatActivity {

    /*
    * Fields
    */
    private EditText current_password_field;
    private EditText password_field;
    private EditText confirm_password_field;

    /**
     * Called when the view is created
     * @param savedInstanceState Saved Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        // Create toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(getString(R.string.title_activity_preferences));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Assign EditTetx Fields to their vars
        current_password_field = (EditText) findViewById(R.id.editCurrentPassword);
        password_field = (EditText) findViewById(R.id.editNewPassword);
        confirm_password_field = (EditText) findViewById(R.id.editConfirmNewPassword);

        // TextChangedListener to handle error dismissal
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
     * Callback for when the change password button is pressed
     * @param view The View
     */
    public void changePassword(View view) {
        if( validateNewPassword() ) {
            // TODO: Change password here

            // Go to the getting stated activity
            Intent intent = new Intent(this, PreferencesActivity.class);
            startActivity(intent);
        } else {
            current_password_field.setError("Incorrect Password");
        }
    }

    /**
     * Validates all fields in the chagne password form and sets an error on the first invalid input
     * @return true if the from is valid, else false
     */
    private boolean validateNewPassword() {
        if ( ! passwordCorrect() ) {
            current_password_field.setError("Incorrect Password");
        } else {
            if (!samePassword()) {
                confirm_password_field.setError("Your passwords do not match");
                return false;
            } else if (password_field.getText().length() < 6) {
                password_field.setError("Your password must be at least 6 characters");
                return false;
            }
        }

        return true;
    }

    /**
     * Checks to make sure that the two password fields are the same
     * @return true if passwords are the same, else false
     */
    private boolean samePassword() {
        return password_field.getText().toString().equals(confirm_password_field.getText().toString());
    }

}
