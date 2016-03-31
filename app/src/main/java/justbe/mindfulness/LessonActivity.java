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
    Boolean completed;
    int weekId;

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
        Log.v("Notification user","user= "+user);

        Intent intent = getIntent();
        String weekData =getIntent().getExtras().getString("week");
        Log.v("Lesson Activity", "lesson intent data: week = " + weekData);



        if(weekData == null)
            weekId = user.getCurrent_week();
        else
            weekId = Integer.parseInt(weekData);



        // Creates exercise session in database if it does not exist yet
        completed = getIntent().getExtras().getBoolean("completed");
        Exercise weekExercise = Util.getExerciseForWeek(weekId);

        Log.v("Lesson Activity", "" + weekId);
        lessonTitle.setText(weekExercise.getExerciseName());
        lessonContent.setText(weekExercise.getExerciseContent());

     if (!completed) {
            // TODO: get currently selected week instead of current program week
            int week = weekId;
            completed = true;
            ServerRequests.completeExerciseSession(week, this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.v("Lesson Activity","...Back button pressed");
                Intent homeIntent = new Intent(this, MainActivity.class);
                homeIntent.putExtra("lessonCompleted", "" + completed);
                homeIntent.putExtra("week",""+weekId);

                startActivity(homeIntent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




}
