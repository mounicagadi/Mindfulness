package justbe.mindfulnessapp;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {


    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule(PreferencesActivity.class);

    @Test
    public void testTextViewDisplay() {
        //test the view on the Preferences page
        onView(withText("Username")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("Meditations")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("Lessons")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("I wake up at")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("I go to sleep at")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("Change Password")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("Log Out")).check(ViewAssertions.matches(isDisplayed()));
    }
}
