package justbe.mindfulnessapp;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNull.notNullValue;


@RunWith(AndroidJUnit4.class)
public class PreferencesActivityTest {


    @Rule
    public IntentsTestRule prefActivityRule = new IntentsTestRule(PreferencesActivity.class);

    @Test
    public void testTextViewDisplay() {
        //test the view on the Preferences page
        onView(withText("Preferences")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("Username")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("Meditations")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("Lessons")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("I wake up at")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("I go to sleep at")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("Change Password")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("Log Out")).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testChangePassword() {
        onView(withId(R.id.changePasswordText)).check(matches(notNullValue()));
        onView(withId(R.id.changePasswordText)).check(matches(withText("Change Password")));
        onView(withId(R.id.changePasswordText)).perform(click());
        intended(hasComponent(ChangePasswordActivity.class.getName()));
        onView(withHint("New Password")).check(ViewAssertions.matches(isDisplayed()));
        pressBack();
        onView(withHint("New Password")).check(doesNotExist());
    }
}
