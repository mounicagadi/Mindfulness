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
    private ViewPager mPager;
    private FragmentPagerAdapter mPagerAdapter;

    private List<String> pageContents;
    LinearLayout mPageIndicator = null;
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


        User user = App.getSession().getUser();

        Intent intent = getIntent();
        String weekData =intent.getStringExtra("week");
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

        //lessonContent.setText(text);


        /**/
        ViewGroup textviewPage = (ViewGroup) getLayoutInflater().inflate(R.layout.fragment, (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content), false);
        lessonContent = null;
        lessonContent =  (TextView) textviewPage.findViewById(R.id.text);
        String contentString = weekExercise.getExerciseContent();

        float horizontalMargin = getResources().getDimension(R.dimen.activity_horizontal_margin) * 2;
        float verticalMargin = getResources().getDimension(R.dimen.activity_vertical_margin) * 2;

        // obtaining screen dimensions
        Display display = getWindowManager().getDefaultDisplay();
        int screenWidth = (int) (display.getWidth() - horizontalMargin);
        int screenHeight = display.getHeight();

        TextPaint paint = lessonContent.getPaint();

        //clear previous content
        pageContents = new ArrayList<String>();

        //Working Out How Many Lines Can Be Entered In The Screen
        Paint.FontMetrics fm = paint.getFontMetrics();
        float fullHeight = fm.top- fm.bottom-3;
        fullHeight = Math.abs(fullHeight);



        int numChars = 0;
        int lineCount = 0;
        int maxLineCount = (int) ((screenHeight - (/*actionBarHeight + */ verticalMargin) )/fullHeight);
        /*maxLineCount-=2;*/
        //contentTextView.setLines(maxLineCount);

        // contentString is the whole string of the book
        int totalPages = 0;
        while (contentString != null && contentString.length() != 0 )
        {
            while ((lineCount < maxLineCount) && (numChars < contentString.length())) {
                numChars = numChars + paint.breakText(contentString.substring(numChars), true, screenWidth, null);
                lineCount ++;
            }

            // retrieve the String to be displayed in the current textbox
            String toBeDisplayed = contentString.substring(0, numChars);
            int nextIndex = numChars;
            char nextChar = nextIndex < contentString.length() ? contentString.charAt(nextIndex) : ' ';
            if (!Character.isWhitespace(nextChar)) {
                toBeDisplayed = toBeDisplayed.substring(0, toBeDisplayed.lastIndexOf(" "));
            }
            numChars = toBeDisplayed.length();
            contentString = contentString.substring(numChars);

            pageContents.add(toBeDisplayed.trim());

            numChars = 0;
            lineCount = 0;

            totalPages ++;
        }
        Log.v("TOTAL PAGES",""+totalPages);
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new TestPagerAdapter(getSupportFragmentManager(), totalPages,"LessonActivity");
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                showPageIndicator(position);
            }
        });

        createPageIndicator();

        /**/

        if (!completed) {
            // TODO: get currently selected week instead of current program week
            int week = weekId;
            ServerRequests.completeExerciseSession(week, this);
        }
    }

    @SuppressWarnings("deprecation")
    private void createPageIndicator() {
        mPageIndicator = (LinearLayout) findViewById(R.id.pageIndicator);
        for (int i = 0; i < pageContents.size(); i++) {
            View view = new View(this);
            ViewGroup.LayoutParams params = new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f);
            view.setLayoutParams(params );
            view.setBackgroundDrawable(getResources().getDrawable(i == 0 ? R.drawable.current_page_indicator : R.drawable.indicator_background));
            view.setTag(i);
            mPageIndicator.addView(view);
        }
    }

    @SuppressWarnings("deprecation")
    protected void showPageIndicator(int position) {
        try {
            mPageIndicator = (LinearLayout) findViewById(R.id.pageIndicator);
            for (int i = 0; i < pageContents.size(); i++) {
                View view = mPageIndicator.findViewWithTag(i);
                view.setBackgroundDrawable(getResources().getDrawable(i == position ? R.drawable.current_page_indicator : R.drawable.indicator_background));
            }
        } catch (Exception e) {
           // Log.e(TAG, e.toString());
        }
    }

    public String getContents(int pageNumber){
        return pageContents.get(pageNumber);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lesson, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
