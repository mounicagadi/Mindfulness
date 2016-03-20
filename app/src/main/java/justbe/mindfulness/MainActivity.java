package justbe.mindfulness;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import justbe.mindfulness.models.ExerciseSession;
import justbe.mindfulness.models.User;

/**
 * Main Activity for the app
 * Contains the following features:
 *      - Meditation Media Player
 *      - View Weekly Progress
 *      - View Weekly Lesson
 *      - Go to Preferences
 */
public class MainActivity extends AppCompatActivity{

    /**
     * Fields
     */
    private Activity activity;
    private ProgressBar progressBar;

    private PopupWindow popupWindow;
    private User user;
    private SessionManager sessionManager;

    private ProgressDialog progressDialog;
    private Handler handler;
    // audio player variables
    private MeditationMediaPlayer mediaPlayer;
    private TextView lessonButtonText;
    /***********************************************************************************************
     * MainActivity Life Cycle Functions
     **********************************************************************************************/

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

        Session session = App.getSession();
        sessionManager = new SessionManager(getApplicationContext());
        user = session.getUser();

        if (user == null) {
            user = sessionManager.getUser();
            session.setUser(user);
            session.setCsrfToken(sessionManager.getCSRFToken());
            session.setSessionId(sessionManager.getSessionID());
        }


        updateCurrentWeek();
        Integer selectedWeek = user.getCurrent_week();




        // Media player setup
        setUpMeditationContent(selectedWeek);
        //mediaPlayer = new MeditationMediaPlayer(this, R.raw.sample, selectedWeek);

        // Set the lesson button's text to the current week
        setUpLessonContent(selectedWeek);
        /* TextView lessonButtonText = (TextView) findViewById(R.id.weeklyLessonButtonText);
        lessonButtonText.setText(String.format("Week %d Exercise", selectedWeek));

        // Get completed exercises from db, if this week has been completed give it a green check
        ExerciseSession[] completedExercises = ServerRequests.getExerciseSessions(this);
        for (ExerciseSession e : completedExercises) {
            if (e.getExercise_id() == selectedWeek) {
                int weeklyLessonImageId = getResources().getIdentifier(
                        "weeklyLessonButtonImage", "id", getPackageName());
                ImageView weeklyLessonImage = (ImageView) findViewById(weeklyLessonImageId);

                // Change the lesson to be completed
                weeklyLessonImage.setImageResource(R.drawable.check_green_2x);
                weeklyLessonImage.setTag("true");

                break;
            }

        }*/
//        // Pebble setup
//        PebbleCommunicator comms = PebbleCommunicator.getInstance();
//        if (!comms.checkPebbleConnection()) {
//            Toast.makeText(App.context(), "No Pebble connection detected!", Toast.LENGTH_LONG).show();
//        }
        setUpAlarms("assessment", 4, true);
//        setUpAlarms("pebble", 5, false);
    }

    public void setUpMeditationContent(int weekId){
        mediaPlayer = new MeditationMediaPlayer(this, R.raw.sample, weekId);
    }

    public void setUpLessonContent(int weekId){

        lessonButtonText = (TextView) findViewById(R.id.weeklyLessonButtonText);
        lessonButtonText.setText(String.format("Week %d Exercise", weekId));

        // Get completed exercises from db, if this week has been completed give it a green check
        ExerciseSession[] completedExercises = ServerRequests.getExerciseSessions(this);
        for (ExerciseSession e : completedExercises) {
            if (e.getExercise_id() == weekId) {
                int weeklyLessonImageId = getResources().getIdentifier(
                        "weeklyLessonButtonImage", "id", getPackageName());
                ImageView weeklyLessonImage = (ImageView) findViewById(weeklyLessonImageId);

                // Change the lesson to be completed
                weeklyLessonImage.setImageResource(R.drawable.check_green_2x);
                weeklyLessonImage.setTag("true");

                break;
            }

        }

    }


    @Override
    public void onBackPressed() {
    }

    /***********************************************************************************************
     * MainActivity Button Handlers
     **********************************************************************************************/

    /**
     * Callback for when the audio button is pressed
     * @param view The View
     */
    public void audioButtonPressed(View view) {
        if(mediaPlayer.getAudioPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.setAudioButtonImageResource(R.drawable.play);
            mediaPlayer.setAudioPlaying(false);
        }
        else {
            mediaPlayer.start();
            mediaPlayer.setAudioButtonImageResource(R.drawable.pause);
            mediaPlayer.setAudioPlaying(true);
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

            setupPopupTextFields(pw_view, popupWindow);
            //pw_view.setOnClickListener(popupListener);
            /*popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if (v instanceof TextView){
                        Log.v("Popup","selected is a textview ");
                    }
                        //popupWindow.dismiss();
                    return true;
                }
            });*/

            popupWindow.showAtLocation(pw_view, Gravity.CENTER, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Callback for when the lesson button is pressed
     * @param view The view
     */
    public void lessonButtonPressed(View view) {
        int checkMarkViewId = getResources().getIdentifier(
                "weeklyLessonButtonImage", "id", getPackageName());
        ImageView checkMarkView = (ImageView) findViewById(checkMarkViewId);
        // The tag of the ImageView tells us if the lesson is completed or not
        Boolean checked = Boolean.valueOf(checkMarkView.getTag().toString());
        String weekText  = lessonButtonText.getText().toString();
        String week = weekText.split(" ")[1];

        // Change the lesson to be completed
        if (!checked) {
            checkMarkView.setImageResource(R.drawable.check_green_2x);
            checkMarkView.setTag("true");
        }

        Intent intent = new Intent(MainActivity.this, LessonActivity.class);
        intent.putExtra("completed", checked);
        intent.putExtra("week", week);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void loadLessonContent(int weekId){
        int checkMarkViewId = getResources().getIdentifier(
                "weeklyLessonButtonImage", "id", getPackageName());
        ImageView checkMarkView = (ImageView) findViewById(checkMarkViewId);
        // The tag of the ImageView tells us if the lesson is completed or not
        Boolean checked = Boolean.valueOf(checkMarkView.getTag().toString());

        // Change the lesson to be completed
        if (!checked) {
            checkMarkView.setImageResource(R.drawable.check_green_2x);
            checkMarkView.setTag("true");
        }
        /*System.out.println("Lesson Change Processing");
        Intent intent = new Intent(MainActivity.this, LessonActivity.class);
        intent.putExtra("completed", checked);
        intent.putExtra("week", ""+weekId);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);*/
    }


    /**
     * Callback for when any weekday is pressed
     * @param view The view
     */
    public void changeWeekdayButtonPressed (View view) {
        // Get the number id for weekday from end of view id
        String stringId = view.getResources().getResourceName(view.getId());
        stringId = stringId.substring(stringId.length() - 1);

        int selectedDay = Integer.valueOf(stringId);
        // get the current week day id
        int currentDay = getCurrentWeekDayId();

        /* Selected Day id has to be less than or equal to current week day id.
        *  Exception: Sunday id is 6.
        *  If current day is Wednesday, the user can only access meditation and
        *  reading lessons of  Sunday, Monday, Tuesday and Wednesday
        *  */
        if(currentDay!=6 && selectedDay <= currentDay || selectedDay ==6)
            mediaPlayer.updateSelectedDay(Integer.valueOf(stringId));

    }

    public int getCurrentWeekDayId(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);


        switch(day){
            case 1: return 6;  // Sunday
            case 2: return 0;  // Monday
            case 3: return 1;  // Tuesday
            case 4: return 2;  // Wednesday
            case 5: return 3;  // Thursday
            case 6: return 4;  // Friday
            case 7: return 5;  // Saturday
        }
        return -1;
    }

    // TODO: Remove this after assessment acitivies are done
    // THIS IS A TEMP BUTTON USED TO TEST ASSESSMENT ACTIVITIES
    public void startAssessmentButtonPressed(View view) {
        Intent intent = new Intent(MainActivity.this, StartAssessmentActivity.class);
        intent.putExtra("isMorningAssessment", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }


    public void updateCurrentWeek(){

        String createdDateString =  user.getCreated_at();
        int currentWeek=0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date createdDate = sdf.parse(createdDateString);
            Date currentDate = new Date();
            sdf.format(currentDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(createdDate);
            int weekCreated =  cal.get(Calendar.WEEK_OF_YEAR);
            cal.setTime(currentDate);
            currentWeek =cal.get(Calendar.WEEK_OF_YEAR);
            user.setCurrent_week(currentWeek-weekCreated+1);
            App.getSession().setUser(user);
            System.out.println("main activity user current week : " + user.getCurrent_week());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /***********************************************************************************************
     * MainActivity Specific Helpers
     **********************************************************************************************/

    /**
     * Sets the Text and Image fields on the weekly progress popup
     * @param pw_view The popup view that the fields are on
     */
    private void setupPopupTextFields(View pw_view, PopupWindow popupWindow) {

        final int currentWeek = user.getCurrent_week();
        final PopupWindow pwindow = popupWindow;
        // Go through each week of the program and sets the correct UI
        for (int i = 1; i <= 8; i++) {
            TextView weekTextView = Util.getTextViewForWeek(pw_view, i);
            ImageView weekImageView = Util.getImageViewForWeek(pw_view, i);
            if (i < currentWeek) {
                weekTextView.setTextColor(ContextCompat.getColor(this, R.color.black));
                weekImageView.setImageResource(R.drawable.check_green_2x);
            } else if (i == currentWeek) {
                weekTextView.setText(R.string.this_week_text);
                weekTextView.setTypeface(null, Typeface.BOLD);
                weekTextView.setTextColor(ContextCompat.getColor(this, R.color.black));
                weekImageView.setVisibility(View.GONE);
            } else {
                weekTextView.setTextColor(ContextCompat.getColor(this, R.color.lightGray));
                weekImageView.setImageResource(R.drawable.check_gray_2x);
            }


            weekTextView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    final TextView tv = (TextView) v;
                    String weekString = tv.getText().toString();
                    weekString = weekString.replace(":", "");
                    final String weekText = weekString.trim();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    weekText+ " content requested. Please wait while the content is fetched..!!", Toast.LENGTH_LONG).show();
                        }
                    });


                    class LoadContent extends AsyncTask {
                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            progressDialog = new ProgressDialog(MainActivity.this);
                            progressDialog.setTitle("Loading content for selected week");
                            progressDialog.setMessage(getString(R.string.pleaseWait));
                            progressDialog.setIndeterminate(true);
                            progressDialog.setCancelable(false);
                            progressDialog.show();
                        }

                        @Override
                        protected Object doInBackground(Object[] params) {
                            weekContentRequest(weekText, currentWeek);
                            return "";
                        }

                        @Override
                        protected void onPostExecute(Object o) {
                            super.onPostExecute(o);
                            progressDialog.dismiss();
                        }
                    }


                    try {

                        pwindow.dismiss();
                        LoadContent asyncTask = new LoadContent();
                        asyncTask.execute();
                        asyncTask.get(5000, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }


                    return true;
                }
            });


        }
    }

    public int getWeekId(String week, int currentWeek){

        int weekID = 0;
        switch(week){
            case "This Week": weekID = currentWeek;
                break;
            case "Week 1": weekID = 1;
                break;
            case "Week 2": weekID = 2;
                break;
            case "Week 3": weekID = 3;
                break;
            case "Week 4": weekID = 4;
                break;
            case "Week 5": weekID = 5;
                break;
            case "Week 6": weekID = 6;
                break;
            case "Week 7": weekID = 7;
                break;
            case "Week 8": weekID = 8;
                break;
        }

        return weekID;
    }


    public void  weekContentRequest(String weekSelected, final int currentWeek) {

        final int weekId = getWeekId(weekSelected, currentWeek);
        if (weekId <= currentWeek) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    populateWeekContent(weekId);
                    loadLessonContent(weekId);
                    Toast.makeText(getApplicationContext(),
                            "Week "+weekId+" content loaded..! ", Toast.LENGTH_LONG).show();

                }
            });
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Week "+currentWeek+" in progress.\nCannot Access content for Week "+weekId,Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    public void populateWeekContent(int weekId) {
        setUpMeditationContent(weekId);
        setUpLessonContent(weekId);
    }


    /***********************************************************************************************
     * Pebble Functions
     **********************************************************************************************/

    /**
     * Sets up alarms to go of once a day
     * @param action The action that should be performed once the alarm goes off
     * @param count The amount of alarms that should be generated
     * @param deleteOldAlarms If old alarms should be deleted first
     */
    private void setUpAlarms(String action, int count, boolean deleteOldAlarms) {
        Date userAwakeTime = user.getWake_up_time();
        Date userSleepTime = user.getGo_to_sleep_time();
        //Date userAwakeTime = null, userSleepTime = null;
        try{
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
                intent.setAction(action + "##" + String.valueOf(alarmCals[i].getTimeInMillis()));
                PendingIntent alarmIntent = PendingIntent.getBroadcast(App.context(),
                        0,
                        intent,
                        0);
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                        alarmCals[i].getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY,
                        alarmIntent);

            }
        }catch(Exception e){
            e.printStackTrace();

        }
    }
}