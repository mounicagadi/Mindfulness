package justbe.mindfulnessapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class StressAssessmentActivity extends AppCompatActivity
        implements SeekBar.OnSeekBarChangeListener {

    /**
     * Fields
     */
    private SeekBar seekBar;

    /**
     * Called when the view is created
     * @param savedInstanceState Saved Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stress_assessment);

        // Create the toolbar
        Toolbar myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Assessment");

        // Draw scale onto seek bar
        seekBar = (SeekBar)findViewById(R.id.stressSeekBar);
        seekBar.setProgress(4);
        seekBar.incrementProgressBy(1);
        seekBar.setMax(10);
        seekBar.setOnSeekBarChangeListener(this);
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
        // TODO: Save assessment here

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
