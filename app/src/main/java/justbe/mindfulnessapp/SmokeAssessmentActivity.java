package justbe.mindfulnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SmokeAssessmentActivity extends AppCompatActivity {

    /**
     * Called when the view is created
     * @param savedInstanceState Saved Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smoke_assessment);

        // Create the toolbar
        Toolbar myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Assessment");
    }

    /**
     * Callback for when the yes button is pressed
     * Saves the result and sends the user back to the main activity
     * @param view The view
     */
    public void yesButtonPressed(View view) {
        // TODO: Save the result

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Callback for when the no button is pressed
     * Saves the result and sends the user back to the main activity
     * @param view The view
     */
    public void noButtonPressed(View view) {
        // TODO: Save the result

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
