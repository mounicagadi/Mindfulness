package justbe.mindfulnessapp;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static justbe.mindfulnessapp.ErrorTextMatcher.hasErrorText;
import static org.hamcrest.core.IsNull.notNullValue;


@RunWith(AndroidJUnit4.class)
public class StartProgramActivityTest {


    @Rule
    public IntentsTestRule startProgramIntentRule = new IntentsTestRule(StartProgramActivity.class);

    @Test
    public void testStartText() {
        //Information about starting program is present
        onView(withId(R.id.startText)).check(matches(notNullValue()));

        // Make sure it has the appropriate information
        onView(withId(R.id.startText)).check(matches(withText(R.string.start_info)));
    }

    // Make sure the start program button is available
    @Test
    public void testStartButton() {
        onView(withId(R.id.startButton)).check(matches(notNullValue()));
        onView(withId(R.id.startButton)).check(matches(withText("Start")));
    }
}