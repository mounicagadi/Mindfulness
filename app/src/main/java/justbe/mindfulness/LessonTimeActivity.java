package justbe.mindfulness;

import android.app.DialogFragment;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

import java.util.Date;

import justbe.mindfulness.models.User;
import justbe.mindfulness.models.UserProfile;

/**
 * Created by Nikhil Reddy on 27-03-2016.
 * Used to set Lesson timings of user.
 */
public class LessonTimeActivity extends AppCompatActivity implements RefreshViewListener {

    private User user;
    private UserProfile userProfile;
    private Spinner spinner;
    private TextView lessonTimeText;
    private String lessonTime, lessonTimeForNotification;
    private int lessonDayOfWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_time);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Lesson Times");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Days Spinner for lessons
        spinner = (Spinner) findViewById(R.id.days_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.days_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                lessonDayOfWeek = spinner.getSelectedItemPosition();
                userProfile.setExercise_day_of_week(lessonDayOfWeek);
                ServerRequests.updateUserWithUserProfile(user, userProfile, getApplicationContext());
                user = App.getSession().getUser();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Set variables to their Text Views
        lessonTimeText = (TextView) findViewById(R.id.lessonTime);

        user = App.getSession().getUser();
        userProfile = new UserProfile(user);

        // Set the fields to the user's values
        lessonTime = Util.dateToDisplayString(new Date());
        lessonTimeForNotification = (new Date()).toString();
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

    /**
     * Saves the time from the Time Picker
     */
    public void saveTimes(int buttonID, Date time) {
        // Check to see what field we are editing
        switch (buttonID) {
            case R.id.lessonTime:
                lessonTime = Util.dateToDisplayString(time);
                lessonTimeForNotification = time.toString();
                userProfile.setExercise_time(Util.dateToUserProfileString(time));
                break;
            default:
                throw new RuntimeException("Attempted to set time for unknown field");
        }
        ServerRequests.updateUserWithUserProfile(user, userProfile, getApplicationContext());
        user = App.getSession().getUser();
    }

    /**
     * Callback for when the lesson button is pressed
     * Creates and displays the Time Picker
     *
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
     * Sets time fields on view
     */
    private void setTimeFields() {
        lessonTimeText.setText(lessonTime);
    }

    /**
     * Callback for when the lesson time next button is pressed
     *
     * @param view The View
     */
    public void lessonNextButtonPressed(View view) {

        scheduleExerciseAlarms();
        Intent intent = new Intent(LessonTimeActivity.this, MeditationTimeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void scheduleExerciseAlarms() {
        int calendarDayID = Util.getCalendarDayId(lessonDayOfWeek);
        String timeString = lessonTimeForNotification;

        System.out.println("Sleep time: " + timeString);
        int hour = 0, min = 0, sec = 0;
        if (timeString.contains(" ")) {
            String time = timeString.split(" ")[3];
            hour = Integer.parseInt(time.split(":")[0]);
            min = Integer.parseInt(time.split(":")[1]);
        }
        try {


            AlarmManager alarmManager = (AlarmManager) App.context().getSystemService(Context.ALARM_SERVICE);
            PendingIntent cancelIntent = PendingIntent.getBroadcast(App.context(), 0,
                    new Intent(App.context(), LessonAlarmReceiver.class), 0);
            alarmManager.cancel(cancelIntent);

            //schedule the alarm
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, min);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.DAY_OF_WEEK, calendarDayID);  // notification day
            Calendar now = Calendar.getInstance();
            Log.v("Time before adding day", "" + calendar.getTime());

            if (now.after(calendar)) {
                System.out.println("Exercise time crossed. Skipping for the day");
                calendar.add(Calendar.DATE, 7);
            }


            Log.v("Time after adding day", "" + calendar.getTime());
            Intent intent = new Intent(LessonTimeActivity.this, LessonAlarmReceiver.class);
            intent.putExtra("week", "" + 1);


            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    LessonTimeActivity.this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY * 7, pendingIntent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
