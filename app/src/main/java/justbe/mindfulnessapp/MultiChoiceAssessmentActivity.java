package justbe.mindfulnessapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import justbe.mindfulnessapp.models.MultiChoiceQuestion;

/**
 * Activity that displays all MultiChoice questions
 */
public class MultiChoiceAssessmentActivity extends AppCompatActivity {

    /**
     * Fields
     */
    private ListView listView;
    private AssessmentFlowManager flowManager;
    private MultiChoiceQuestion multiChoiceQuestion;

    /***********************************************************************************************
     * MultiChoiceAssessmentActivity Life Cycle Functions
     **********************************************************************************************/

    /**
     * Called when the view is created
     * @param savedInstanceState Saved Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_choice_assessment);

        // Create toolbar
        Toolbar myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Assessment");

        // Get the current question through the manager
        flowManager = AssessmentFlowManager.getInstance(this);
        multiChoiceQuestion = (MultiChoiceQuestion) flowManager.getCurrentAssessment();

        // Set the question text
        TextView questionText = (TextView) findViewById(R.id.textView);
        questionText.setText(multiChoiceQuestion.getQuestionText());
        // Setup the list choice view
        listView = (ListView) findViewById(R.id.choiceList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_multiple_choice, multiChoiceQuestion.getChoices());
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);
    }

    /***********************************************************************************************
     * MultiChoiceAssessmentActivity Button Handlers
     **********************************************************************************************/

    /**
     * Callback for when the next button is pressed
     * Saves the results from the assessment
     * @param view The view
     */
    public void nextPressed(View view) {
        String selected = "";
        int cntChoice = listView.getCount();
        SparseBooleanArray sparseBooleanArray = listView.getCheckedItemPositions();

        for(int i = 0; i < cntChoice; i++){
            if(sparseBooleanArray.get(i)) {
                selected += listView.getItemAtPosition(i).toString() + "\n";
            }
        }
        flowManager.startNextAssessmentQuestion();
    }

}
