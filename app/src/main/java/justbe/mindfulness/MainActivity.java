package justbe.mindfulness;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;

import java.text.DateFormat;
import java.util.ArrayList;

import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
 * - Meditation Media Player
 * - View Weekly Progress
 * - View Weekly Lesson
 * - Go to Preferences
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Fields
     */
    private PopupWindow popupWindow;
    private User user;
    private SessionManager sessionManager;

    private ProgressDialog progressDialog;
    private Handler handler;
    // audio player variables
    private MeditationMediaPlayer mediaPlayer;
    private TextView lessonButtonText;
    private Integer weekToDisplay;
    ArrayList<String> ar = new ArrayList<String>();

    /***********************************************************************************************
     * MainActivity Life Cycle Functions
     **********************************************************************************************/

    /**
     * Called when the view is created
     *
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
        Intent intent = getIntent();
        if (intent != null) {
            String source = intent.getStringExtra("source");
            if (null != source) {
                Log.v("TAG", "Reached here after assessment completion");
                Log.v("From Assessment", "selectedWeek = " + selectedWeek);
                if (null == selectedWeek)
                    selectedWeek = Integer.parseInt(intent.getStringExtra("userWeek"));
                Log.v("From Assessment", "selectedWeek fetched from intent= " + selectedWeek);

            } else {


                Log.v("Main Activity", "From Lesson Notification");
                Boolean completed = Boolean.parseBoolean(intent.getStringExtra("lessonCompleted"));
                if (completed) {
                    String week = intent.getStringExtra("week");
                    Log.v("Main Activity", "Lesson Notification completed for " + week);
                    int weekIdCompleted = Integer.parseInt(week);
                    ServerRequests.completeExerciseSession(weekIdCompleted, this);
                }
            }
        }


        if (savedInstanceState == null)
            weekToDisplay = selectedWeek;
        else
            weekToDisplay = savedInstanceState.getInt("displayContentForWeek");

        /*
        * List days on Main Activity page, starting from the day the user first signed up
        * */
        listDaysOnMainActivity();

        // Media player setup
        setUpMeditationContent(selectedWeek);
        // Set the lesson button's text to the current week
        setUpLessonContent(selectedWeek);

    }

    public void setUpMeditationContent(int weekId) {
        int meditationFileId = Util.getMeditationFile(weekId);
        mediaPlayer = new MeditationMediaPlayer(this, meditationFileId, weekId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("ON DESTROY", "called");

        if (App.getSession().getUser() != null) {
            sessionManager.setUser(App.getSession().getUser());
        } else if (sessionManager != null && user != null) {
            Log.v("ON DESTROY", "session manager not null");
            sessionManager.setUser(user);
        } else if (sessionManager == null) {
            Log.v("ON DESTROY", "session manager null");
            Session session = App.getSession();
            sessionManager = new SessionManager(App.context());
            sessionManager.createLoginSession(session.getSessionId(), session.getCsrfToken(), user);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("ON STOP", "called");
        if (App.getSession().getUser() != null) {
            sessionManager.setUser(App.getSession().getUser());
        } else if (sessionManager != null && user != null) {
            Log.v("ON DESTROY", "session manager not null");
            sessionManager.setUser(user);
        } else if (sessionManager == null) {
            Log.v("ON DESTROY", "session manager null");
            Session session = App.getSession();
            sessionManager = new SessionManager(App.context());
            sessionManager.createLoginSession(session.getSessionId(), session.getCsrfToken(), user);
        }
    }

    public void setUpLessonContent(int weekId) {

        lessonButtonText = (TextView) findViewById(R.id.weeklyLessonButtonText);
        lessonButtonText.setText(String.format("Week %d Exercise", weekId));

        // Get completed exercises from db, if this week has been completed give it a green check
        ExerciseSession[] completedExercises = ServerRequests.getExerciseSessions(this);
        if (null != completedExercises) {
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

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("On pause ", "User null? " + (null == user));
        App.getSession().setUser(user);

    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        Log.v("On Resume ", "User null? " + (null == user));
        user = App.getSession().getUser();
        setUpLessonContent(weekToDisplay);
        setUpMeditationContent(weekToDisplay);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // save the week id of the current week being displayed
        // While accessing previous week content, on screen rotation,
        // current week's content will be displayed instead of accessed
        // previous week content
        outState.putInt("displayContentForWeek", weekToDisplay);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // get the week id of the current week being displayed
        weekToDisplay = savedInstanceState.getInt("displayContentForWeek");
    }
    /***********************************************************************************************
     * MainActivity Button Handlers
     **********************************************************************************************/

    /**
     * Callback for when the audio button is pressed
     *
     * @param view The View
     */
    public void audioButtonPressed(View view) {
        if (mediaPlayer.getAudioPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.setAudioButtonImageResource(R.drawable.play);
            mediaPlayer.setAudioPlaying(false);
        } else {
            mediaPlayer.start();
            mediaPlayer.setAudioButtonImageResource(R.drawable.pause);
            mediaPlayer.setAudioPlaying(true);
        }
    }

    /*
    List the days on Main Activity page starting from the day the user first created his/her account
     */
    public void listDaysOnMainActivity() {
        TextView weekday0;
        TextView weekday1;
        TextView weekday2;
        TextView weekday3;
        TextView weekday4;
        TextView weekday5;
        TextView weekday6;

        Resources res = getResources();
        Calendar c = Calendar.getInstance();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", java.util.Locale.getDefault());
        Date date = null;

        weekday0 = (TextView) findViewById(R.id.MeditationText0);
        weekday1 = (TextView) findViewById(R.id.MeditationText1);
        weekday2 = (TextView) findViewById(R.id.MeditationText2);
        weekday3 = (TextView) findViewById(R.id.MeditationText3);
        weekday4 = (TextView) findViewById(R.id.MeditationText4);
        weekday5 = (TextView) findViewById(R.id.MeditationText5);
        weekday6 = (TextView) findViewById(R.id.MeditationText6);

        try {
            date = format.parse(user.getCreated_at());
            c.setTime(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int day_created = c.get(Calendar.DAY_OF_WEEK);
        String[] days = res.getStringArray(R.array.main_activity_days);

        for (int i = day_created - 1; i < 7; i++)
            ar.add(days[i]);
        for (int i = 0; i < day_created - 1; i++)
            ar.add(days[i]);
        weekday0.setText(ar.get(0));
        weekday1.setText(ar.get(1));
        weekday2.setText(ar.get(2));
        weekday3.setText(ar.get(3));
        weekday4.setText(ar.get(4));
        weekday5.setText(ar.get(5));
        weekday6.setText(ar.get(6));

    }

    /**
     * Callback for when the preferences button is pressed
     *
     * @param view The View
     */
    public void preferencesButtonPressed(View view) {

        int checkMarkViewId = getResources().getIdentifier(
                "weeklyLessonButtonImage", "id", getPackageName());
        ImageView checkMarkView = (ImageView) findViewById(checkMarkViewId);
        // The tag of the ImageView tells us if the lesson is completed or not
        Boolean checked = Boolean.valueOf(checkMarkView.getTag().toString());

        Intent intent = new Intent(MainActivity.this, PreferencesActivity.class);
        intent.putExtra("checked", "" + checked);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    /**
     * Callback for when the calender button is pressed
     *
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
            popupWindow = new PopupWindow(pw_view, width, height, true);
            popupWindow.setBackgroundDrawable(new ColorDrawable());
            popupWindow.setOutsideTouchable(true);
            popupWindow.setTouchable(true);

            setupPopupTextFields(pw_view, popupWindow);


            popupWindow.showAtLocation(pw_view, Gravity.CENTER, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Callback for when the lesson button is pressed
     *
     * @param view The view
     */
    public void lessonButtonPressed(View view) {
        int checkMarkViewId = getResources().getIdentifier(
                "weeklyLessonButtonImage", "id", getPackageName());
        ImageView checkMarkView = (ImageView) findViewById(checkMarkViewId);
        // The tag of the ImageView tells us if the lesson is completed or not
        Boolean checked = Boolean.valueOf(checkMarkView.getTag().toString());
        String weekText = lessonButtonText.getText().toString();
        String week = weekText.split(" ")[1];
        Log.v("Main Activity", "lessonButtonPressed: week = " + week);

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

    public void loadLessonContent(int weekId) {
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
    }


    /**
     * Callback for when any weekday is pressed
     *
     * @param view The view
     */
    public void changeWeekdayButtonPressed(View view) {
        // Get the number id for weekday from end of view id
        String stringId = view.getResources().getResourceName(view.getId());
        stringId = stringId.substring(stringId.length() - 1);

        // Get the selected week day id
        int selectedDay = Integer.valueOf(stringId);
        // get the current week day id
        int currentDay = getCurrentWeekDayId();

        /* Selected Day id has to be less than or equal to current week day id.
        *  Exception: Sunday id is 6.
        *  If current day is Wednesday, the user can only access meditation and
        *  reading lessons of  Sunday, Monday, Tuesday and Wednesday
        *  */
        if (selectedDay <= currentDay)
            mediaPlayer.updateSelectedDay(Integer.valueOf(stringId));

    }

    public int getCurrentWeekDayId() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case 1:
                return ar.indexOf("SU");  // Sunday
            case 2:
                return ar.indexOf("M");   // Monday
            case 3:
                return ar.indexOf("T");   // Tuesday
            case 4:
                return ar.indexOf("W");   // Wednesday
            case 5:
                return ar.indexOf("TH");  // Thursday
            case 6:
                return ar.indexOf("F");   // Friday
            case 7:
                return ar.indexOf("S");   // Saturday
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


    public void updateCurrentWeek() {

        String createdDateString = user.getCreated_at();
        int currentWeek = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date createdDate = sdf.parse(createdDateString);
            Date currentDate = new Date();
            sdf.format(currentDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(createdDate);
            int weekCreated = cal.get(Calendar.WEEK_OF_YEAR);
            cal.setTime(currentDate);
            currentWeek = cal.get(Calendar.WEEK_OF_YEAR);
            user.setCurrent_week(currentWeek - weekCreated + 1);
            App.getSession().setUser(user);
            System.out.println("main activity user current week : " + user.getCurrent_week());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***********************************************************************************************
     * MainActivity Specific Helpers
     **********************************************************************************************/

    /**
     * Sets the Text and Image fields on the weekly progress popup
     *
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
                                    weekText + " content requested. Please wait while the content is fetched..!!", Toast.LENGTH_LONG).show();
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

    public int getWeekId(String week, int currentWeek) {

        int weekID = 0;
        switch (week) {
            case "This Week":
                weekID = currentWeek;
                break;
            case "Week 1":
                weekID = 1;
                break;
            case "Week 2":
                weekID = 2;
                break;
            case "Week 3":
                weekID = 3;
                break;
            case "Week 4":
                weekID = 4;
                break;
            case "Week 5":
                weekID = 5;
                break;
            case "Week 6":
                weekID = 6;
                break;
            case "Week 7":
                weekID = 7;
                break;
            case "Week 8":
                weekID = 8;
                break;
        }

        return weekID;
    }

    public void weekContentRequest(String weekSelected, final int currentWeek) {

        final int weekId = getWeekId(weekSelected, currentWeek);
        if (weekId <= currentWeek) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    weekToDisplay = weekId;
                    populateWeekContent(weekToDisplay);
                    loadLessonContent(weekToDisplay);
                    Toast.makeText(getApplicationContext(),
                            "Week " + weekId + " content loaded..! ", Toast.LENGTH_LONG).show();

                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Week " + currentWeek + " in progress.\nCannot Access content for Week " + weekId, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void populateWeekContent(int weekId) {
        setUpMeditationContent(weekId);
        setUpLessonContent(weekId);
    }
}