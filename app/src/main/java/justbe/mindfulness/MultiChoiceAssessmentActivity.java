package justbe.mindfulness;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import justbe.mindfulness.models.MultiChoiceQuestion;
import justbe.mindfulness.models.Response;

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
                R.layout.custom_list_item_multiple_choice, multiChoiceQuestion.getChoices());
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
        // Get the checked options in the listView
        SparseBooleanArray checkedOptions = listView.getCheckedItemPositions();

        // When assessments/reponses are better defined by the user
        // the response will be created here and added to the list of
        // responses in AssessmentFlowManager.
        // See SliderAssessmentActivity for a example of adding a
        // response to the flowManager

        Integer questionID = flowManager.getQuestionID();
        Integer assessmentID = flowManager.getAssessmentID();
        String question = multiChoiceQuestion.getQuestionText();
        for(int i = 0; i< checkedOptions.size();i++){
            if(checkedOptions.get(i)){
                Response multiChoiceResponse = new Response(questionID,assessmentID,checkedOptions.get(i));
                flowManager.addResponse(multiChoiceResponse);
                System.out.println("Multi Assessment: " + question + " --> " + checkedOptions.get(i));
            }

        }






        flowManager.startNextAssessmentQuestion();
    }
}
