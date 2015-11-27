package justbe.mindfulnessapp;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TimePicker;

import com.codetroopers.betterpickers.timepicker.TimePickerDialogFragment;

import java.text.DateFormat;
import java.util.Calendar;

import justbe.mindfulnessapp.rest.UserPresentableException;

public class PreferencesActivity extends AppCompatActivity {

    /**
     * Called when the view is created
     * @param savedInstanceState Saved Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        // Create the toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(getString(R.string.title_activity_preferences));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /**
     * Callback for when the wake up, go to sleep, lesson, or meditation button is pressed
     * Creates and displays the Time Picker
     * @param view The view
     */
    public void showTimePickerDialog(View view) {
        // Get the button ID so we know what field we are editing
        int buttonID = view.getId();
        Bundle bundle = new Bundle();
        bundle.putInt("buttonID", buttonID);

        // Create the Time Picker
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.setArguments(bundle);
        newFragment.show(getFragmentManager(), "timePicker");
    }

    /**
     * Callback for when the change password button is pressed
     * @param view The view
     */
    public void changePassword(View view) {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    /**
     * Callback for when the logout button is pressed
     * Logs the current user out of the app and sends them to the login activity
     * @param view The view
     */
    public void logout(View view) {
        if (App.getSession().invalidate()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            new UserPresentableException(
                    getString(R.string.cannot_logout),
                    getString(R.string.cannot_logout_message)).alert(this);

        }
    }
}