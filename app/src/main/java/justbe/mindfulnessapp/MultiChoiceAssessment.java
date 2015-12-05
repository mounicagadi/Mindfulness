package justbe.mindfulnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import justbe.mindfulnessapp.models.AssessmentFlowManager;
import justbe.mindfulnessapp.models.MultiChoiceQuestion;

public class MultiChoiceAssessment extends AppCompatActivity{

    /**
     * Fields
     */
    private ListView listView;
    private AssessmentFlowManager flowManager;
    private MultiChoiceQuestion multiChoiceQuestion;

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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, multiChoiceQuestion.getChoices());
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);
    }

    /**
     * Callback for when the submit button is pressed
     * Saves the results from the assessment
     * @param view The view
     */
    public void submitPressed(View view) {
        String selected = "";
        int cntChoice = listView.getCount();
        SparseBooleanArray sparseBooleanArray = listView.getCheckedItemPositions();

        for(int i = 0; i < cntChoice; i++){
            if(sparseBooleanArray.get(i)) {
                selected += listView.getItemAtPosition(i).toString() + "\n";
            }
        }

        Intent intent = new Intent(MultiChoiceAssessment.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }

}
