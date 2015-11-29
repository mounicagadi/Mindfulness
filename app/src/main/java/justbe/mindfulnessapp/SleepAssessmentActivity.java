package justbe.mindfulnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SleepAssessmentActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    /**
     * Fields
     */
    private Spinner spinner;
    private static final String[]paths = {"Did Not Sleep", "1 Hour", "2 Hours", "3 Hours", "4 Hours",
                                          "5 Hours", "6 Hours", "7 Hours", "8 Hours", "9 Or More Hours"};

    /**
     * Called when the view is created
     * @param savedInstanceState Saved Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_assessment);

        Toolbar myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Assessment");

        // Create dropdown menu
        spinner = (Spinner)findViewById(R.id.sleep_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_init_item,paths);
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
        // TODO: Save assessment here

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}