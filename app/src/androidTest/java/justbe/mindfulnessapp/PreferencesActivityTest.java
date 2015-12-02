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
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class PreferencesActivityTest {


    @Rule
    public IntentsTestRule prefActivityRule = new IntentsTestRule(PreferencesActivity.class);

    @Test
    public void testViewDisplay() {
        //test the view on the Preferences page
        onView(withText("Preferences")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("Username")).check(ViewAssertions.matches(isDisplayed()));
        onView(allOf(withId(R.id.currentUsername), withText("test"))).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("Meditations")).check(ViewAssertions.matches(isDisplayed()));
        onView(allOf(withId(R.id.meditationTime), withText("09:00 AM"))).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("Lessons")).check(ViewAssertions.matches(isDisplayed()));
        onView(allOf(withId(R.id.lessonTime), withText("09:00 AM"))).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("I wake up at")).check(ViewAssertions.matches(isDisplayed()));
        onView(allOf(withId(R.id.wakeUpTime), withText(""))).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("I go to sleep at")).check(ViewAssertions.matches(isDisplayed()));
        onView(allOf(withId(R.id.goToSleepTime), withText(""))).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("Change Password")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("Log Out")).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testUsernameNotClickable() {
        onView(withId(R.id.currentUsernameRow)).check(matches(notNullValue()));
        onView(withId(R.id.currentUsernameText)).check(matches(withText("Username")));
        onView(withId(R.id.currentUsernameRow)).check(matches(not(isClickable())));
    }

    @Test
    public void testMeditationsButton() {
        onView(withId(R.id.meditationRow)).check(matches(notNullValue()));
        onView(withId(R.id.meditationTimeText)).check(matches(withText("Meditations")));
        onView(withId(R.id.meditationRow)).perform(click());
    }

    @Test
    public void testLessonsButton() {
        onView(withId(R.id.lessonRow)).check(matches(notNullValue()));
        onView(withId(R.id.lessonTimeText)).check(matches(withText("Lessons")));
        onView(withId(R.id.lessonRow)).perform(click());
    }

    @Test
    public void testWakeUpButton() {
        onView(withId(R.id.wakeUpRow)).check(matches(notNullValue()));
        onView(withId(R.id.wakeUpText)).check(matches(withText("I wake up at")));
        onView(withId(R.id.wakeUpRow)).perform(click());
    }

    @Test
    public void testGoToSleepButton() {
        onView(withId(R.id.goToSleepRow)).check(matches(notNullValue()));
        onView(withId(R.id.goToSleepText)).check(matches(withText("I go to sleep at")));
        onView(withId(R.id.goToSleepRow)).perform(click());
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

    @Test
    public void testLogout() {
        onView(withId(R.id.logOutRow)).check(matches(notNullValue()));
        onView(withId(R.id.logOut)).check(matches(withText("Log Out")));
        onView(withId(R.id.logOutRow)).perform(click());
        intended(hasComponent(LoginActivity.class.getName()));
        onView(withId(R.id.loginButton)).check(matches(notNullValue()));
    }
}
