package justbe.mindfulness;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Activity that displays all Yes/No questions
 * CURRENTLY THERE ARE NONE OF THESE QUESTION SO THEY HAVE NOT BEEN EXTENDED TO THE AssessmentQuestion
 * ABSTRACT CLASS. IF YOU ARE ADDING IN YES/NO ASSESSMENT QUESTIONS YOU WILL NEED TO EXTEND THIS LIKE THE OTHERS
 * SEE SliderAssessmentActivity, DropdownAssessmentActivity, or MultiChoiceAssessmentActivity
 * ALONG WITH THEIR MATCHING QUESTION MODELS
 */
public class YesNoAssessmentActivity extends AppCompatActivity {

    /***********************************************************************************************
     * YesNoAssessmentActivity Life Cycle Functions
     **********************************************************************************************/

    /**
     * Called when the view is created
     * @param savedInstanceState Saved Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yes_no_assessment);

        // Create the toolbar
        Toolbar myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Assessment");
    }

    /***********************************************************************************************
     * YesNoAssessmentActivity Life Cycle Functions
     **********************************************************************************************/

    /**
     * Callback for when the yes button is pressed
     * Saves the result and sends the user back to the main activity
     * @param view The view
     */
    public void yesButtonPressed(View view) {
        // Add yes response to the flowManager here

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /**
     * Callback for when the no button is pressed
     * Saves the result and sends the user back to the main activity
     * @param view The view
     */
    public void noButtonPressed(View view) {
        // Add no response to the flowManager here

        Intent intent = new Intent(YesNoAssessmentActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }
}
