package justbe.mindfulness;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import justbe.mindfulness.models.User;
import justbe.mindfulness.models.UserProfile;

/**
 * Delays starting the meditation program until the user chooses to do so
 */
public class StartProgramActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_program);

        Toolbar myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Start Program");
    }

    /**
     * Callback for when the start button is pressed
     * Begins the meditation program by setting the current program week to 1
     * @param view The View
     */
    public void startButtonPressed(View view) {
        User user = App.getSession().getUser();
        UserProfile profile = new UserProfile(user);

        profile.setCurrent_week(1);

        ServerRequests.updateUserWithUserProfile(user, profile, getApplicationContext());

        Intent intent = new Intent(StartProgramActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
