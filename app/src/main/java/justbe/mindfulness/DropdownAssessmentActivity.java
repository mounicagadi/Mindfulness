package justbe.mindfulness;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import justbe.mindfulness.models.DropdownQuestion;
import justbe.mindfulness.models.Response;

/**
 * Activity that displays all dropdown questions
 */
public class DropdownAssessmentActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    /**
     * Fields
     */
    private Spinner spinner;
    private AssessmentFlowManager flowManager;
    private DropdownQuestion dropdownQuestion;

    /***********************************************************************************************
     * DropdownAssessmentActivity Life Cycle Functions
     **********************************************************************************************/

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
        spinner = (Spinner)findViewById(R.id.dropdown_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.spinner_init_item, dropdownQuestion.getDropdownOptions());
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(DropdownAssessmentActivity.this);

    }

    /***********************************************************************************************
     * AdapterView.OnItemSelectedListener Functions
     **********************************************************************************************/

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
     * Callback for when the next button is pressed
     * Saves the results from the assessment
     * @param view The view
     */
    public void nextPressed(View view) {
        // When assessments/reponses are better defined by the user
        // the response will be created here and added to the list of
        // responses in AssessmentFlowManager.
        // See SliderAssessmentActivity for a example of adding a
        // response to the flowManager
        String value =  (String) spinner.getSelectedItem();
        value = value.split(" ")[0];
        Integer questionId = flowManager.getQuestionID();
        Integer assessmentId = flowManager.getAssessmentID();
        String question =  dropdownQuestion.getQuestionText();

        Response dropDownResponse = new Response(questionId,assessmentId,value,1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());

        String created_at = sdf.format(Calendar.getInstance().getTime());
        dropDownResponse.setCreated_at(created_at);

        System.out.println("Drop Down Assessment: Question ID: "+questionId+" "+question+" --> "+value);
        flowManager.addResponse(dropDownResponse);
        flowManager.startNextAssessmentQuestion();
    }
}