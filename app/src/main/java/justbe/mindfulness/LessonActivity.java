package justbe.mindfulness;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import justbe.mindfulness.models.Exercise;
import justbe.mindfulness.models.User;

/**
 * Activity that displays weekly lessons
 */
public class LessonActivity extends AppCompatActivity {

    /***********************************************************************************************
     * LessonActivty Life Cycle Functions
     **********************************************************************************************/
    /**
     * Called when the view is created
     * @param savedInstanceState Saved Instance State
     */

    TextView lessonTitle, lessonContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        // Create the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.title_activity_lesson));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        lessonTitle =  (TextView) findViewById(R.id.lesson_title_text);
        lessonContent = (TextView) findViewById(R.id.lesson_content);

        User user = App.getSession().getUser();

        Intent intent = getIntent();
        String weekData =intent.getStringExtra("week");
 Log.v("Lesson Activity", "lesson intent data: week = " + weekData);
        int weekId = 0;
        System.out.println("Lesson Activity: "+ weekData);

        if(weekData == null)
            weekId = user.getCurrent_week();
        else
            weekId = Integer.parseInt(weekData);



        // Creates exercise session in database if it does not exist yet
        Boolean completed = getIntent().getExtras().getBoolean("completed");
        Exercise weekExercise = Util.getExerciseForWeek(weekId);

        Log.v("Lesson Activity", "" + weekId);
        lessonTitle.setText(weekExercise.getExerciseName());
        lessonContent.setText(weekExercise.getExerciseContent());

     if (!completed) {
            // TODO: get currently selected week instead of current program week
            int week = weekId;
            ServerRequests.completeExerciseSession(week, this);
        }
    }




}
