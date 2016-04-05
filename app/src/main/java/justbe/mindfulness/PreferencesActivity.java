package justbe.mindfulness;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import justbe.mindfulness.models.User;
import justbe.mindfulness.models.UserProfile;
import justbe.mindfulness.rest.UserPresentableException;

/**
 * Activity that allows the user to change their settings and log out
 */
public class PreferencesActivity extends AppCompatActivity implements RefreshViewListener {

    /**
     * Fields
     */
    private User user;
    private UserProfile userProfile;
    private Spinner spinner;
    private TextView currentUsername;
    private TextView currentFirstname;
    private TextView currentLastname;
    private TextView currentGender;
    private TextView meditationTimeText;
    private TextView lessonTimeText;
    private TextView wakeUpTimeText;
    private TextView goToSleepTimeText;

    private String gender_string;
    private String meditationTime;
    private String lessonTime;
    private String wakeUpTime;
    private String goToSleepTime;

    private ProgressDialog progressDialog;
    private static Handler logoutHandler;

    /***********************************************************************************************
     * PreferencesActivity Life Cycle Functions
     **********************************************************************************************/

    /**
     * Called when the view is created
     * @param savedInstanceState Saved Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        // Days Spinner for lessons
        spinner = (Spinner) findViewById(R.id.days_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.days_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userProfile.setExercise_day_of_week(spinner.getSelectedItemPosition());
                ServerRequests.updateUserWithUserProfile(user, userProfile, getApplicationContext());
                user = App.getSession().getUser();
				// update exercise notifications with change in preferred week day
                updateExerciseNotifications(user.getExercise_time().toString(), user.getExercise_day_of_week());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Create the toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(getString(R.string.title_activity_preferences));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Create 'logging out' progress spinner
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.loggingOut));
        progressDialog.setMessage(getString(R.string.pleaseWait));
        progressDialog.setCancelable(true);

        // Set variables to their Text Views
        currentUsername = (TextView) findViewById(R.id.currentUsername);
        currentFirstname = (TextView) findViewById(R.id.currentFirstname);
        currentLastname = (TextView) findViewById(R.id.currentLastname);
        currentGender = (TextView) findViewById(R.id.currentGender);
        meditationTimeText = (TextView) findViewById(R.id.meditationTime);
        lessonTimeText = (TextView) findViewById(R.id.lessonTime);
        wakeUpTimeText = (TextView) findViewById(R.id.wakeUpTime);
        goToSleepTimeText = (TextView) findViewById(R.id.goToSleepTime);

        // Setup the User and UserProfile
        user = App.getSession().getUser();
        userProfile = new UserProfile(user);

        // Set the fields to the user's values
        currentUsername.setText(user.getUsername());
        currentFirstname.setText(user.getFirst_name());
        currentLastname.setText(user.getLast_name());
        currentGender.setText(getGenderString(user.getGender()));
        meditationTime = Util.dateToDisplayString(user.getMeditation_time());
        lessonTime = Util.dateToDisplayString(user.getExercise_time());
        wakeUpTime = Util.dateToDisplayString(user.getWake_up_time());
        goToSleepTime = Util.dateToDisplayString(user.getGo_to_sleep_time());
        spinner.setSelection(user.getExercise_day_of_week());
        setTimeFields();
    }

    /***********************************************************************************************
     * RefreshViewListener Functions
     **********************************************************************************************/

    /**
     * Sets time fields on view, callable from anywhere
     */
    public void refreshView() {
        setTimeFields();
    }

    public String getGenderString(int gender)
    {
        if(gender == 0)
            gender_string = "Male";
        else if(gender == 1)
            gender_string = "Female";
        return gender_string;
    }

    /**
     * Saves the time from the Time Picker
     */
    public void saveTimes(int buttonID, Date time) {
        // Check to see what field we are editing
        switch (buttonID) {
            case R.id.meditationRow:
                meditationTime = Util.dateToDisplayString(time);
                userProfile.setMeditation_time(Util.dateToUserProfileString(time));
				// update meditation notifications with change in preferred meditation time
                updateMeditationNotifications(userProfile.getMeditation_time().toString());
                break;
            case R.id.lessonRow:
                lessonTime = Util.dateToDisplayString(time);
                userProfile.setExercise_time(Util.dateToUserProfileString(time));
				// update exercise notifications with change in lesson time
                updateExerciseNotifications(userProfile.getExercise_time().toString(), userProfile.getExercise_day_of_week());
                break;
            case R.id.wakeUpRow:
                wakeUpTime = Util.dateToDisplayString(time);
                userProfile.setWake_up_time(Util.dateToUserProfileString(time));
                break;
            case R.id.goToSleepRow:
                goToSleepTime = Util.dateToDisplayString(time);
                userProfile.setGo_to_sleep_time(Util.dateToUserProfileString(time));
				// update assessment notifications with change in sleep time time
                updateAssessmentAlarm(userProfile.getGo_to_sleep_time().toString());
                break;
            default:
                throw new RuntimeException("Attempted to set time for unknown field");
        }
        ServerRequests.updateUserWithUserProfile(user, userProfile, getApplicationContext());
        user = App.getSession().getUser();
    }

	// on change of meditation time, notification time should also be updated
    public void updateMeditationNotifications(String meditationTime){

        System.out.println("Updated meditation time " + meditationTime);


        int hour = 0, min = 0, sec = 0;
        if(meditationTime.contains(" ")){
            String time = meditationTime.split(" ")[3];
            hour = Integer.parseInt(time.split(":")[0]);
            min = Integer.parseInt(time.split(":")[1]);
            //sec = Integer.parseInt(time.split(":")[2]);
        }else{
            hour = Integer.parseInt(meditationTime.split(":")[0]);
            min = Integer.parseInt(meditationTime.split(":")[1]);
        }


        System.out.println("User medi time: "+ meditationTime);

        try{

            if (null == meditationTime) {
                Toast toast = Toast.makeText(App.context(), "You need to set a meditation time!", Toast.LENGTH_LONG);
                toast.show();
                return;
            }

            AlarmManager alarmManager = (AlarmManager)App.context().getSystemService(Context.ALARM_SERVICE);
            PendingIntent cancelIntent = PendingIntent.getBroadcast(App.context(), 0,
                    new Intent(App.context(), MeditationAlarmReceiver.class), 0);
            alarmManager.cancel(cancelIntent);

            //schedule the alarm
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,hour);
            calendar.set(Calendar.MINUTE,min);
            //calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.SECOND,0);
            Calendar now = Calendar.getInstance();
            Log.v("Time before adding day", "" + calendar.getTime());

            if(now.after(calendar)) {
                System.out.println("Meditation time crossed. Skipping for the day");
                calendar.add(Calendar.DATE, 1);
            }

            Log.v("Time after adding day",""+calendar.getTime());
            Intent intent = new Intent(PreferencesActivity.this, MeditationAlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    PreferencesActivity.this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);

        }catch(Exception e){
            e.printStackTrace();
        }

    }

public void updateAssessmentAlarm(String sleepTime){

        try{

            if (null == sleepTime) {
                Toast toast = Toast.makeText(App.context(), "You need to set a exercise time!", Toast.LENGTH_LONG);
                toast.show();
                return;
            }
            String timeString = sleepTime;

            String time = timeString.split(" ")[3];
            int hour = Integer.parseInt(time.split(":")[0]);
            int min = Integer.parseInt(time.split(":")[1]);

            AlarmManager alarmManager = (AlarmManager)App.context().getSystemService(Context.ALARM_SERVICE);
            PendingIntent cancelIntent = PendingIntent.getBroadcast(App.context(), 0,
                    new Intent(App.context(), AssessmentNotification.class), 0);
            alarmManager.cancel(cancelIntent);

            //schedule the alarm
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,hour);
            calendar.add(Calendar.HOUR, -2);
            calendar.set(Calendar.MINUTE, min);
            calendar.set(Calendar.SECOND, 0);

            Calendar now = Calendar.getInstance();
            Log.v("Time before adding day",""+calendar.getTime());

            if(now.after(calendar)) {
                System.out.println("Assessment time crossed. Skipping for the day");
                calendar.add(Calendar.DATE, 1);
            }

            Log.v("Time after adding day", "" + calendar.getTime());
            Intent intent = new Intent(PreferencesActivity.this, AssessmentNotification.class);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    PreferencesActivity.this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);

        }catch(Exception e){
            e.printStackTrace();
        }


    }
    public void updateExerciseNotifications(String exerciseTime, int weekDayID){

        System.out.println("Updated exercise time " + exerciseTime);

        int hour = 0, min = 0, sec = 0;
        if(exerciseTime.contains(" ")){
            String time = exerciseTime.split(" ")[3];
            hour = Integer.parseInt(time.split(":")[0]);
            min = Integer.parseInt(time.split(":")[1]);
            //sec = Integer.parseInt(time.split(":")[2]);
        }else{
            hour = Integer.parseInt(exerciseTime.split(":")[0]);
            min = Integer.parseInt(exerciseTime.split(":")[1]);
        }

        int calendarDayID = Util.getCalendarDayId(weekDayID);

        try{

            if (null == exerciseTime) {
                Toast toast = Toast.makeText(App.context(), "You need to set a exercise time!", Toast.LENGTH_LONG);
                toast.show();
                return;
            }

            AlarmManager alarmManager = (AlarmManager)App.context().getSystemService(Context.ALARM_SERVICE);
            PendingIntent cancelIntent = PendingIntent.getBroadcast(App.context(), 0,
                    new Intent(App.context(), LessonAlarmReceiver.class), 0);
            alarmManager.cancel(cancelIntent);

            //schedule the alarm
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,hour);
            calendar.set(Calendar.MINUTE,min);
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.DAY_OF_WEEK, calendarDayID);  // notification day
            Calendar now = Calendar.getInstance();
            Log.v("Time before adding day", "" + calendar.getTime());

            if(now.after(calendar)) {
                System.out.println("Exercise time crossed. Skipping for the day");
                calendar.add(Calendar.DATE, 1);
            }

            Log.v("Time after adding day",""+calendar.getTime());

            String currentWeek = user.getCurrent_week().toString();
            Boolean checked = Boolean.parseBoolean(getIntent().getStringExtra("checked"));

            Intent intent = new Intent(PreferencesActivity.this, LessonAlarmReceiver.class);
            intent.putExtra("currentWeek",""+currentWeek);
            intent.putExtra("checked",""+checked);

            Log.v("Preferences", "" + currentWeek);
            Log.v("Preferences",""+checked);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    PreferencesActivity.this, 0, intent,
                    0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY*7, pendingIntent);

        }catch(Exception e){
            e.printStackTrace();
        }



    }
    /**
     * Sets time fields on view
     */
    private void setTimeFields() {
        meditationTimeText.setText(meditationTime);
        lessonTimeText.setText(lessonTime);
        wakeUpTimeText.setText(wakeUpTime);
        goToSleepTimeText.setText(goToSleepTime);
    }

    /***********************************************************************************************
     * PreferencesActivity Button Handlers
     **********************************************************************************************/

    /**
     * Callback for when the wake up, go to sleep, lesson, or meditation button is pressed
     * Creates and displays the Time Picker
     * @param view The view
     */
    public void showTimePickerDialog(View view) {
        // Get the button ID so we know what field we are editing
        int buttonID = view.getId();
        Bundle bundle = new Bundle();
        bundle.putInt("buttonID", buttonID);

        // Create the Time Picker
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.setArguments(bundle);
        newFragment.show(getFragmentManager(), "timePicker");
    }

    /**
     * Callback for when the logout button is pressed
     * Logs the current user out of the app and sends them to the login activity
     * @param view The view
     */
    public void logout(View view) {
        // Display the logging in spinner
        progressDialog.show();

        // Run logout within a thread
        final Context context = PreferencesActivity.this;
        Thread logoutThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Boolean success = false;
                try {
                    success = App.getSession().invalidate();
                } catch (Exception e) {
                    new UserPresentableException(e).alert(context);
                }

                // Attempt to logout
                if(success) {
                    logoutHandler.sendEmptyMessage(0);
                } else {
                    logoutHandler.sendEmptyMessage(1);
                }
            }
        });

        // Start the thread and wait till its done
        logoutThread.start();

        // Handler to deal with the result of the login thread
        logoutHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // Logout succeeded
                if(msg.what == 0) {
                    // clear data from shared preferences
                    SessionManager sessionManager = new SessionManager(getApplicationContext());
                    sessionManager.logoutUser();

                    // Go to Login
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    progressDialog.dismiss();
                    finish();
                } else { // Logout failed
                    new UserPresentableException(
                            getString(R.string.cannot_logout),
                            getString(R.string.cannot_logout_message)).alert(context);
                }
            }
        };
    }
}