package justbe.mindfulnessapp;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SleepAssessmentActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private static final String[]paths = {"Did Not Sleep", "1 Hour", "2 Hours", "3 Hours", "4 Hours",
                                          "5 Hours", "6 Hours", "7 Hours", "8 Hours", "9 Or More Hours"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_assessment);

        Toolbar myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Assessment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Create dropdown menu
        spinner = (Spinner)findViewById(R.id.sleep_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_init_item,paths);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

    }

    public void onNothingSelected(AdapterView parent) {

    }
}