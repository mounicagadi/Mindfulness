package justbe.mindfulnessapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import java.util.Calendar;

import justbe.mindfulnessapp.models.User;

public class MainActivity extends AppCompatActivity {

    /**
     * Fields
     */
    private PopupWindow pw;
    private User user;
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

        user = App.getSession().getUser();

        // Set the lesson button's text to the current week
        TextView lessonButtonText = (TextView) findViewById(R.id.weeklyLessonButtonText);
        user.setProgramWeek(1);
        lessonButtonText.setText(String.format("Week %d Exercise", user.getProgramWeek()));

        selectedDay = getCurrentDayOfTheWeek();
        updateSelectedDay(selectedDay);
    }

    /**
     * Colors the currently selected day and updates selectedDay
     * @param newDay the newly selected day
     */
    @SuppressLint("NewApi")
    private void updateSelectedDay(String newDay){
        int currentMeditationId = getResources().getIdentifier(
                selectedDay + "Meditation" , "id", getPackageName());
        int newMeditationId = getResources().getIdentifier(
                newDay + "Meditation" , "id", getPackageName());
        int currentTextViewId = getResources().getIdentifier(
                selectedDay + "MeditationText" , "id", getPackageName());
        int newTextViewId = getResources().getIdentifier(
                newDay + "MeditationText" , "id", getPackageName());

        // remove styling from current day
        TextView currentDayTextView = (TextView) findViewById(currentTextViewId);
        currentDayTextView.setTextColor(ContextCompat.getColor(this, R.color.transparentLightGreen));

        View currentDayLayout = findViewById(currentMeditationId);
        // Note: lint incorrectly marks setForeground as requiring api level 23 (https://code.google.com/p/android/issues/detail?id=186273)
        currentDayLayout.setForeground(null);

        // add styling to new day
        TextView newDayTextView = (TextView) findViewById(newTextViewId);
        newDayTextView.setTextColor(ContextCompat.getColor(this, R.color.bpWhite));

        View newDayLayout = findViewById(newMeditationId);
        newDayLayout.setForeground(ContextCompat.getDrawable(this, R.drawable.selected_day_border));

        selectedDay = newDay;
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

    /**
     * Returns the day of the week in the string format used by the day selector
     * @return The day of the week in the following format: su, m, t, w, th, f, s
     */
    private String getCurrentDayOfTheWeek() {
        String day = "";
        switch(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                day = "su";
                break;
            case Calendar.MONDAY:
                day = "m";
                break;
            case Calendar.TUESDAY:
                day = "t";
                break;
            case Calendar.WEDNESDAY:
                day = "w";
                break;
            case Calendar.THURSDAY:
                day = "th";
                break;
            case Calendar.FRIDAY:
                day = "f";
                break;
            case Calendar.SATURDAY:
                day = "s";
                break;
            default:
                // impossible to get here
        }
        return day;
    }
}