package justbe.mindfulness;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import justbe.mindfulness.models.Response;
import justbe.mindfulness.models.SliderQuestion;

/**
 * Activity that displays all Slider questions
 */
public class SliderAssessmentActivity extends AppCompatActivity
        implements SeekBar.OnSeekBarChangeListener {

    /**
     * Fields
     */
    private SeekBar seekBar;
    private AssessmentFlowManager flowManager;
    private SliderQuestion sliderQuestion;

    /***********************************************************************************************
     * SliderAssessmentActivity Life Cycle Functions
     **********************************************************************************************/

    /**
     * Called when the view is created
     * @param savedInstanceState Saved Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_assessment);

        // Create the toolbar
        Toolbar myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Assessment");

        // Get the current question through the manager
        flowManager = AssessmentFlowManager.getInstance(this);
        sliderQuestion = (SliderQuestion) flowManager.getCurrentAssessment();

        // Set the question text
        TextView questionText = (TextView) findViewById(R.id.textView);
        questionText.setText(sliderQuestion.getQuestionText());

        // Draw scale onto seek bar
        Integer maxSeek = sliderQuestion.getMaxSliderVal();
        Integer minSeek = sliderQuestion.getMinSliderVal();

        // start with seek bar at the middle
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBar.setProgress(minSeek);
        seekBar.setMax(maxSeek);
        seekBar.setOnSeekBarChangeListener(SliderAssessmentActivity.this);

        // Set text on min/max text fields
        TextView minText = (TextView) findViewById(R.id.minText);
        minText.setText(sliderQuestion.getMinSliderText());
        TextView maxText = (TextView) findViewById(R.id.maxText);
        maxText.setText(sliderQuestion.getMaxSliderText());
    }

    /***********************************************************************************************
     * SeekBar.OnSeekBarChangeListener Functions
     **********************************************************************************************/

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
        int interval = 1;
        progress = ((int)Math.round(progress/interval))*interval;
        seekBar.setProgress(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    /***********************************************************************************************
     * PreferencesActivity Button Handlers
     **********************************************************************************************/

    /**
     * Callback for when the next button is pressed
     * Saves the results from the assessment
     * @param view The view
     */
    public void nextPressed(View view) {
        Integer emotion = seekBar.getProgress();
        Integer questionID = flowManager.getQuestionID();
        Integer assessmentID = flowManager.getAssessmentID();
        String question = sliderQuestion.getQuestionText();
        Response sliderResponse = new Response(questionID, assessmentID, emotion,2);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
        String created_at = sdf.format(Calendar.getInstance().getTime());

        sliderResponse.setCreated_at(created_at);

        System.out.println("Slider Assessment: Question ID: "+questionID+" "+question+" --> "+emotion);
        flowManager.addResponse(sliderResponse);
        flowManager.startNextAssessmentQuestion();
    }
}
