package justbe.mindfulnessapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.media.MediaPlayer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Handler;
import android.widget.Toast;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Calendar;

import justbe.mindfulnessapp.models.MeditationSession;
import justbe.mindfulnessapp.models.User;
import justbe.mindfulnessapp.rest.GenericHttpRequestTask;
import justbe.mindfulnessapp.rest.RestUtil;
import justbe.mindfulnessapp.rest.UserPresentableException;


public class MainActivity extends AppCompatActivity {

    /**
     * Fields
     */
    private PopupWindow popupWindow;
    private User user;

    // 0 Monday -> 6 Sunday
    private Integer selectedDay;
    private MeditationSession meditationSession = new MeditationSession();
    private Integer selectedWeek;

    // audio player variables
    private MediaPlayer mediaPlayer;
    private boolean audioPlaying;
    private SeekBar volumeBar;
    private TextView currentAudioTimeText, totalAudioTimeText;
    private ImageButton audioButton;
    private Handler audioInfoUpdater;
    private double currentTime;
    private double totalTime;

    /**
     * Called when the view is created
     * @param savedInstanceState Saved Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = App.getSession().getUser();

        // Create toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        // Set toolbar view to custom toolbar for main view
        LayoutInflater li = LayoutInflater.from(this);
        View customToolbarView = li.inflate(R.layout.custom_main_toolbar, null);
        getSupportActionBar().setCustomView(customToolbarView);

        // Create media player
        mediaPlayer = MediaPlayer.create(this, R.raw.sample);

        // Set the lesson button's text to the current week
        TextView lessonButtonText = (TextView) findViewById(R.id.weeklyLessonButtonText);
        user.setProgram_week(1);
        // TODO: allow changing the current week
        selectedWeek = user.getProgram_week();
        lessonButtonText.setText(String.format("Week %d Exercise", selectedWeek));

        // Set up daily meditation
        meditationSession.setPercent_completed(1.0);
        selectedDay = getCurrentDayOfTheWeek();
        updateSelectedDay(selectedDay);
        setMeditationCompletion();
        if (!comms.checkPebbleConnection()) {
            Toast.makeText(App.context(), "No Pebble connection detected!", Toast.LENGTH_LONG).show();
        }

        setUpAlarms("assessment", 4, true);
        setUpAlarms("pebble", 5, false);
    }

    /**
     * Sets up alarms to go of once a day
     * @param action The action that should be performed once the alarm goes off
     * @param count The amount of alarms that should be generated
     * @param deleteOldAlarms If old alarms should be deleted first
     */
    private void setUpAlarms(String action, int count, boolean deleteOldAlarms) {
        Date userAwakeTime = user.getWake_up_time();
        Date userSleepTime = user.getGo_to_sleep_time();
        if (userAwakeTime == null || userSleepTime == null) {
            Toast toast = Toast.makeText(App.context(), "You need to set an awake time and a sleep time!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        AlarmManager alarmManager = (AlarmManager)App.context().getSystemService(Context.ALARM_SERVICE);
        if (deleteOldAlarms) {
            PendingIntent cancelIntent = PendingIntent.getBroadcast(App.context(), 0,
                    new Intent(App.context(), AlarmReceiver.class), 0);
            alarmManager.cancel(cancelIntent);
        }
        Calendar[] alarmCals = CalenderGenerator.generateAwakeCalendars(count);
        for (int i = 0; i < alarmCals.length; i++) {
            Intent intent = new Intent(App.context(), AlarmReceiver.class);
            intent.setAction(action + "|" + String.valueOf(alarmCals[i].getTimeInMillis()));
            PendingIntent alarmIntent = PendingIntent.getBroadcast(App.context(),
                    0,
                    intent,
                    0);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                    alarmCals[i].getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    alarmIntent);
        }
    }



    /**
     * Set up mediaPlayer and related visuals
     */
    public void initializeAudioPlayer() {
        mediaPlayer.release();
        // TODO: change the meditation file based on day selected
        mediaPlayer = MediaPlayer.create(this, R.raw.sample);
        mediaPlayer.setOnCompletionListener(endOfMeditationListener);

        // Initialize parts from view
        volumeBar =(SeekBar)findViewById(R.id.volumeBar);
        currentAudioTimeText = (TextView)findViewById(R.id.currentTime);
        totalAudioTimeText = (TextView)findViewById(R.id.totalTime);
        audioButton = (ImageButton)findViewById(R.id.audioButton);

        currentTime = mediaPlayer.getCurrentPosition();
        totalTime = mediaPlayer.getDuration();

        // Set up progress bar and make it usable
        volumeBar.setMax((int) totalTime);
        volumeBar.setProgress((int)currentTime);
        volumeBar.setOnSeekBarChangeListener(seekBarListener);

        // Initialize time indicators
        setTextViewToTime(currentAudioTimeText, currentTime);
        setTextViewToTime(totalAudioTimeText, totalTime);

        audioInfoUpdater = new Handler();
        audioInfoUpdater.postDelayed(UpdateCurrentTime,100);

        audioPlaying = false;
        audioButton.setImageResource(R.drawable.play);
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

    // Called when the seekbar is interacted with
    private final SeekBar.OnSeekBarChangeListener seekBarListener =
            new SeekBar.OnSeekBarChangeListener() {

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
    };

    // Called when the audioPlayer reaches the end of a meditation
    private final MediaPlayer.OnCompletionListener endOfMeditationListener =
            new MediaPlayer.OnCompletionListener(){

        public void onCompletion(MediaPlayer mp){
            completeMeditation(selectedDay);
            meditationSession.setMeditation_id(selectedWeek, selectedDay);
            meditationSession.update();
        }
    };

    /**
     * Mark meditation as complete by changing the check to green for a day
     * @param day 0 Monday -> 6 Sunday to mark as complete
     */
    private void completeMeditation(Integer day){
        int currentImageViewId = getResources().getIdentifier(
                "MeditationImage" + day, "id", getPackageName());
        ImageView currentDayImageView = (ImageView) findViewById(currentImageViewId);

        currentDayImageView.setImageResource(R.drawable.check_green_2x);
    }


    /**
     * Colors the currently selected day, updates selectedDay and audio file
     * @param newDay the newly selected day
     */
    private void updateSelectedDay(Integer newDay){
        int currentTextViewId = getResources().getIdentifier(
                 "MeditationText" + selectedDay, "id", getPackageName());
        int newTextViewId = getResources().getIdentifier(
                 "MeditationText" + newDay, "id", getPackageName());

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
     *  Set the completion check mark color for all days of the week
     */
    private void setMeditationCompletion() {
        Integer mid, day;
        // Create an HTTPRequestTask that sends a MeditationSession Object and Returns a MeditationSession Object
        GenericHttpRequestTask<MeditationSession, MeditationSession> task
                = new GenericHttpRequestTask(MeditationSession.class, MeditationSession.class);

        task.execute("/api/v1/meditation_session/", HttpMethod.GET, null);

        try {
            ResponseEntity<MeditationSession> result = task.waitForResponse();
            RestUtil.checkResponseHazardously(result);

            for(MeditationSession m : result.getBody().getObjects()) {
                if(m.getPercent_completed() == 1.0){
                    day = m.getMeditation_id() % 10;

                    completeMeditation(day);
                }
            }

        } catch (Exception e) {
            new UserPresentableException(e);
        }
    }

    /**
     * Callback for when the audio button is pressed
     * @param view The View
     */
    public void audioButtonPressed(View view) {
        if(audioPlaying) {
            mediaPlayer.stop();
            audioButton.setImageResource(R.drawable.play);
            audioPlaying = false;
        }
        else {
            mediaPlayer.start();
            audioButton.setImageResource(R.drawable.pause);
            audioPlaying = true;
        }
    }

    /**
     * Callback for when the preferences button is pressed
     * @param view The View
     */
    public void preferencesButtonPressed(View view) {
        Intent intent = new Intent(MainActivity.this, PreferencesActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    /**
     * Callback for when the calender button is pressed
     * @param view The view
     */
    public void weekButtonPressed(View view) {
        // Get screen size
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        // Attempt to create  and display the weekly progress popup
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View pw_view = inflater.inflate(R.layout.check_progress_popup,
                    (ViewGroup) findViewById(R.id.checkProgressPopup));
            popupWindow = new PopupWindow(pw_view,  width, height, true);
            popupWindow.setBackgroundDrawable(new ColorDrawable());
            popupWindow.setOutsideTouchable(true);
            popupWindow.setTouchable(true);

            setupPopupTextFields(pw_view);

            popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                @Override public boolean onTouch(View v, MotionEvent event) {
                    popupWindow.dismiss(); return true;
                }
            });

            popupWindow.showAtLocation(pw_view, Gravity.CENTER, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the Text and Image fields on the weekly progress popup
     * @param pw_view The popup view that the fields are on
     */
    private void setupPopupTextFields(View pw_view) {
        int currentWeek = user.getProgram_week();

        // Go through each week of the program and sets the correct UI
        for(int i = 1; i <= 8; i++) {
            TextView weekTextView = getTextViewForWeek(pw_view, i);
            ImageView weekImageView = getImageViewForWeek(pw_view, i);
            if(i < currentWeek) {
                weekTextView.setTextColor(ContextCompat.getColor(this, R.color.black));
                weekImageView.setImageResource(R.drawable.check_green_2x);
            } else if(i == currentWeek) {
                weekTextView.setText(R.string.this_week_text);
                weekTextView.setTypeface(null, Typeface.BOLD);
                weekTextView.setTextColor(ContextCompat.getColor(this, R.color.black));
                weekImageView.setVisibility(View.GONE);
            } else {
                weekTextView.setTextColor(ContextCompat.getColor(this, R.color.lightGray));
                weekImageView.setImageResource(R.drawable.check_gray_2x);
            }
        }
    }

    /**
     * Callback for when the lesson button is pressed
     * @param view The view
     */
    public void lessonButtonPressed(View view) {
        Intent intent = new Intent(MainActivity.this, LessonActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    // TODO: Remove this after assessment acitivies are done
    // THIS IS A TEMP BUTTON USED TO TEST ASSESSMENT ACTIVITIES
    public void morningAssessmentButtonPressed(View view) {
        // Create the morning AssessmentQuestionFlowManagers
        AssessmentFlowManagerFactory managerFactory = new AssessmentFlowManagerFactory(this);
        managerFactory.addMorningAssessmentQuestions();
        AssessmentFlowManager morningAssessmentFlowManager = AssessmentFlowManager.getInstance(this);
        morningAssessmentFlowManager.startNextAssessmentQuestion();
    }
    public void dayAssessmentButtonPressed(View view) {
        AssessmentFlowManagerFactory managerFactory = new AssessmentFlowManagerFactory(this);
        managerFactory.addDayAssessmentQuestions();
        AssessmentFlowManager dayAssessmentFlowManager = AssessmentFlowManager.getInstance(this);
        dayAssessmentFlowManager.startNextAssessmentQuestion();
    }


    /**
     * Callback for when any weekday is pressed
     * @param view The view
     */
    public void changeWeekdayButtonPressed (View view) {
        // Get the number id for weekday from end of view id
        String stringId = view.getResources().getResourceName(view.getId());
        stringId = stringId.substring(stringId.length() - 1);

        updateSelectedDay(Integer.valueOf(stringId));
    }

    /**
     * Returns the day of the week in the string format used by the day selector
     * @return The day of the week in the following format: su, m, t, w, th, f, s
     */
    private Integer getCurrentDayOfTheWeek() {
        Integer day = 0;
        switch(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                day = 6;
                break;
            case Calendar.MONDAY:
                day = 0;
                break;
            case Calendar.TUESDAY:
                day = 1;
                break;
            case Calendar.WEDNESDAY:
                day = 2;
                break;
            case Calendar.THURSDAY:
                day = 3;
                break;
            case Calendar.FRIDAY:
                day = 4;
                break;
            case Calendar.SATURDAY:
                day = 5;
                break;
            default:
                // impossible to get here
        }
        return day;
    }

    /**
     * Helper function that finds the text view for a given week in the given view
     * @param pw_view The popup view that the text field is on
     * @param week The week
     * @return The text view for the given week
     */
    private TextView getTextViewForWeek(View pw_view, int week) {
        TextView weekTextView;
        switch (week){
            case 1:
                weekTextView = (TextView) pw_view.findViewById(R.id.week1Text);
                break;
            case 2:
                weekTextView = (TextView) pw_view.findViewById(R.id.week2Text);
                break;
            case 3:
                weekTextView = (TextView) pw_view.findViewById(R.id.week3Text);
                break;
            case 4:
                weekTextView = (TextView) pw_view.findViewById(R.id.week4Text);
                break;
            case 5:
                weekTextView = (TextView) pw_view.findViewById(R.id.week5Text);
                break;
            case 6:
                weekTextView = (TextView) pw_view.findViewById(R.id.week6Text);
                break;
            case 7:
                weekTextView = (TextView) pw_view.findViewById(R.id.week7Text);
                break;
            case 8:
                weekTextView = (TextView) pw_view.findViewById(R.id.week8Text);
                break;
            default:
                weekTextView = null;
                break;
        }
        return weekTextView;
    }

    /**
     * Helper function that finds the image view for a given week in the given view
     * @param pw_view The popup view that the image field is on
     * @param week The week
     * @return The image view for the given week
     */
    private ImageView getImageViewForWeek(View pw_view, int week) {
        ImageView weekImageView;
        switch (week){
            case 1:
                weekImageView = (ImageView) pw_view.findViewById(R.id.week1Check);
                break;
            case 2:
                weekImageView = (ImageView) pw_view.findViewById(R.id.week2Check);
                break;
            case 3:
                weekImageView = (ImageView) pw_view.findViewById(R.id.week3Check);
                break;
            case 4:
                weekImageView = (ImageView) pw_view.findViewById(R.id.week4Check);
                break;
            case 5:
                weekImageView = (ImageView) pw_view.findViewById(R.id.week5Check);
                break;
            case 6:
                weekImageView = (ImageView) pw_view.findViewById(R.id.week6Check);
                break;
            case 7:
                weekImageView = (ImageView) pw_view.findViewById(R.id.week7Check);
                break;
            case 8:
                weekImageView = (ImageView) pw_view.findViewById(R.id.week8Check);
                break;
            default:
                weekImageView = null;
                break;
        }
        return weekImageView;
    }

    private AssessmentFlowManager createDayAssessmentFlowManager() {
        AssessmentFlowManager dayAssessmentFlowManager = new AssessmentFlowManager(this);

        return dayAssessmentFlowManager;
    }
}