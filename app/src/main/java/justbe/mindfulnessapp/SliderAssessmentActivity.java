package justbe.mindfulnessapp;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import justbe.mindfulnessapp.models.SliderQuestion;

public class SliderAssessmentActivity extends AppCompatActivity
        implements SeekBar.OnSeekBarChangeListener {

    /**
     * Fields
     */
    private SeekBar seekBar;
    private AssessmentFlowManager flowManager;
    private SliderQuestion sliderQuestion;

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
        Integer middleSeek = maxSeek / 2;

        seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBar.setProgress(middleSeek);
        seekBar.setMax(maxSeek);
        seekBar.setOnSeekBarChangeListener(this);

        // Set text on min/max text fields
        TextView minText = (TextView) findViewById(R.id.minText);
        minText.setText(sliderQuestion.getMinSliderText());
        TextView maxText = (TextView) findViewById(R.id.maxText);
        maxText.setText(sliderQuestion.getMaxSliderText());
    }

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

    /**
     * Callback for when the submit button is pressed
     * Saves the results from the assessment
     * @param view The view
     */
    public void submitPressed(View view) {
        flowManager.startNextAssessmentQuestion();
    }
}
