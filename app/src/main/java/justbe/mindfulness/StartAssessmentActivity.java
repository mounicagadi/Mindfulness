package justbe.mindfulness;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Activity the user lands on after clicking on a notification
 * The user has the option to take the assessment now or put it off for later
 */
public class StartAssessmentActivity extends AppCompatActivity {

    /***********************************************************************************************
     * StartAssessmentActivity Life Cycle Functions
     **********************************************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_assessment);

        // Create toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Assessment");
        getSupportActionBar().setDisplayShowCustomEnabled(true);
    }

    /***********************************************************************************************
     * StartAssessmentActivity Button Handlers
     **********************************************************************************************/
    /**
     * Callback for when the yes button is pressed
     * Starts the assessment
     * @param view The view
     */
    public void yesButtonPressed(View view) {
        // Create new assessment here

        AssessmentFlowManagerFactory managerFactory = new AssessmentFlowManagerFactory(this);
        // Get the correct assessment flow
        Intent intent = getIntent();
        if (intent.getExtras().getBoolean("isMorningAssessment")) {
            managerFactory.addMorningAssessmentQuestions();
        } else {
            managerFactory.addDayAssessmentQuestions();
        }

        AssessmentFlowManager flowManager = AssessmentFlowManager.getInstance(this);
        flowManager.setAssessmentID(1);
        flowManager.startNextAssessmentQuestion();
    }

    /**
     * Callback for when the no button is pressed
     * Sends the user back to the main activity
     * @param view The view
     */
    public void noButtonPressed(View view) {
        Intent intent = new Intent(StartAssessmentActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }
}
