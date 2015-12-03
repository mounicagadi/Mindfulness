package justbe.mindfulnessapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;
import android.widget.SeekBar;
import android.widget.TextView;
import android.media.MediaPlayer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    // audio player variables
    private MediaPlayer mediaPlayer;
    private SeekBar volumeBar;
    private TextView currentAudioTimeText, totalAudioTimeText;
    private Button playButton, pauseButton;
    private Handler audioInfoUpdater;
    private double currentTime;
    private double totalTime;

    private PopupWindow pw;

    // su, m, t, w, th, f, s
    private String selectedDay;

    /**
     * Called when the view is created
     * @param savedInstanceState Saved Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        // Set toolbar view to custom toolbar for main view
        LayoutInflater li = LayoutInflater.from(this);
        View customToolbarView = li.inflate(R.layout.custom_main_toolbar, null);
        getSupportActionBar().setCustomView(customToolbarView);

        mediaPlayer = MediaPlayer.create(this, R.raw.sample);
        // TODO: initialize as current day of the week
        selectedDay = "th";
        updateSelectedDay(selectedDay);
    }

    /**
     * Set up mediaPlayer and related visuals
     */
    public void initializeAudioPlayer() {
        mediaPlayer.release();
        // TODO: change the meditation file based on day selected
        mediaPlayer = MediaPlayer.create(this, R.raw.sample);

        // Initialize parts from view
        volumeBar =(SeekBar)findViewById(R.id.volumeBar);
        currentAudioTimeText = (TextView)findViewById(R.id.currentTime);
        totalAudioTimeText = (TextView)findViewById(R.id.totalTime);
        playButton = (Button)findViewById(R.id.playButton);
        pauseButton = (Button)findViewById(R.id.pauseButton);

        currentTime = mediaPlayer.getCurrentPosition();
        totalTime = mediaPlayer.getDuration();

        // Set up progress bar and make it usable
        volumeBar.setMax((int) totalTime);
        volumeBar.setProgress((int)currentTime);
        volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            // Update to position in song user seeks to
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                    setTextViewToTime(currentAudioTimeText, progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

        // Initialize time indicators
        setTextViewToTime(currentAudioTimeText, currentTime);
        setTextViewToTime(totalAudioTimeText, totalTime);

        audioInfoUpdater = new Handler();
        audioInfoUpdater.postDelayed(UpdateCurrentTime,100);

        playButton.setEnabled(true);
        pauseButton.setEnabled(false);
    }

    /**
     * Update the text of a text view to a given time
     * @param tv text view to update information of
     * @param time new time to display
     */
    private void setTextViewToTime(TextView tv, double time) {
        Date date = new Date((int)time);
        DateFormat formatter = new SimpleDateFormat("mm:ss");
        tv.setText(formatter.format(date));
    }

    // Updates the song time info every second
    private Runnable UpdateCurrentTime = new Runnable() {
        public void run() {
            currentTime = mediaPlayer.getCurrentPosition();
            setTextViewToTime(currentAudioTimeText, currentTime);

            volumeBar.setProgress((int)currentTime);
            audioInfoUpdater.postDelayed(this, 100);
        }
    };

    /**
     * Colors the currently selected day, updates selectedDay and audio file
     * @param newDay the newly selected day
     */
    private void updateSelectedDay(String newDay){
        int currentTextViewId = getResources().getIdentifier(
                selectedDay + "MeditationText" , "id", getPackageName());
        int newTextViewId = getResources().getIdentifier(
                newDay + "MeditationText" , "id", getPackageName());

        // remove styling from current day
        TextView currentDayTextView = (TextView) findViewById(currentTextViewId);
        currentDayTextView.setTextColor(ContextCompat.getColor(this, R.color.transparentLightGreen));
        currentDayTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.bpTransparent)) ;

        // add styling to new day
        TextView newDayTextView = (TextView) findViewById(newTextViewId);
        newDayTextView.setTextColor(ContextCompat.getColor(this, R.color.bpWhite));
        newDayTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.transparentOrange));

        selectedDay = newDay;

        initializeAudioPlayer();
    }

    /**
     * Callback for when the audio play button is pressed
     * @param view The View
     */
    public void playButtonPressed(View view) {
        mediaPlayer.start();
        playButton.setEnabled(false);
        pauseButton.setEnabled(true);
    }

    /**
     * Callback for when the audio pause button is pressed
     * @param view The View
     */
    public void pauseButtonPressed(View view) {
        mediaPlayer.pause();
        playButton.setEnabled(true);
        pauseButton.setEnabled(false);
    }

    /**
     * Callback for when the preferences button is pressed
     * @param view The View
     */
    public void preferencesButtonPressed(View view) {
        Intent intent = new Intent(this, PreferencesActivity.class);
        startActivity(intent);
    }

    /**
     * Callback for when the calender button is pressed
     * @param view The view
     */
    public void weekButtonPressed(View view) {
        // Get the size of the screen
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        // Attempt to create  and display the weekly progress popup
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View pw_view = inflater.inflate(R.layout.activity_check_progress_popup,
                    (ViewGroup) findViewById(R.id.checkProgressPopup));
            // TODO: Make this figure out its size better
            pw = new PopupWindow(pw_view, width-250, LayoutParams.WRAP_CONTENT, true);

            pw.showAtLocation(pw_view, Gravity.CENTER, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Callback for when the lesson button is pressed
     * @param view The view
     */
    public void lessonButtonPressed(View view) {
        Intent intent = new Intent(this, LessonActivity.class);
        startActivity(intent);
    }

    // TODO: Remove this after assessment acitivies are done
    // THIS IS A TEMP BUTTON USED TO TEST ASSESSMENT ACTIVITIES
    public void assessmentButtonPressed(View view) {
        Intent intent = new Intent(this, SmokeAssessmentActivity.class);
        startActivity(intent);
    }

    /**
     * Callback for when any weekday is pressed
     * @param view The view
     */
    public void changeWeekdayButtonPressed (View view) {
        // Get the abbreviated weekday from beginning of the id set in layout
        String stringId = view.getResources().getResourceName(view.getId());
        stringId = stringId.substring(0, stringId.length() - "Meditation".length());

        updateSelectedDay(stringId);
    }
}