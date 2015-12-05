package justbe.mindfulnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import justbe.mindfulnessapp.models.AssessmentFlowManager;
import justbe.mindfulnessapp.models.DropdownQuestion;

public class DropdownAssessmentActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    /**
     * Fields
     */
    private Spinner spinner;
    private AssessmentFlowManager flowManager;
    private DropdownQuestion dropdownQuestion;

    /**
     * Called when the view is created
     * @param savedInstanceState Saved Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropdown_assessment);

        Toolbar myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Assessment");

        // Get the current question through the manager
        flowManager = AssessmentFlowManager.getInstance(this);
        dropdownQuestion = (DropdownQuestion) flowManager.getCurrentAssessment();

        // Set the question text
        TextView questionText = (TextView) findViewById(R.id.textView);
        questionText.setText(dropdownQuestion.getQuestionText());

        // Create dropdown menu
        spinner = (Spinner)findViewById(R.id.sleep_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_init_item, dropdownQuestion.getDropdownOptions());
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    /**
     * Controls dropdown menu when a item is selected
     * @param parent The dropdown menu
     * @param v The view
     * @param position The dropdown menu's current selection
     * @param id
     */
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

    }

    /**
     * Controls dropdown menu when nothing is selected
     * @param parent The dropdown menu
     */
    public void onNothingSelected(AdapterView parent) {

    }

    /**
     * Callback for when the submit button is pressed
     * Saves the results from the assessment
     * @param view The view
     */
    public void submitPressed(View view) {
        flowManager.startNextAssessmentQuestion();
    }
}