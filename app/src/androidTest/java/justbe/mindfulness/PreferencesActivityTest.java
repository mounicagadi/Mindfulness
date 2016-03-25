package justbe.mindfulness;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import justbe.mindfulness.models.ExerciseSession;
import justbe.mindfulness.models.User;
import justbe.mindfulness.models.UserProfile;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
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
    private UserProfile userProfile;

    @Before
    public void setUp() {


        userProfile = new UserProfile();
        userProfile.setCurrent_week(1);
        userProfile.setGo_to_sleep_time(Calendar.getInstance().getTime().toString());
        userProfile.setWake_up_time(Calendar.getInstance().getTime().toString());
        userProfile.setGo_to_sleep_time(Calendar.getInstance().getTime().toString());
        userProfile.setExercise_day_of_week(1);
        userProfile.setExercise_time(Calendar.getInstance().getTime().toString());
        userProfile.setGender(1);
        userProfile.setMeditation_time(Calendar.getInstance().getTime().toString());
        userProfile.setBirthday(Calendar.getInstance().getTime().toString());

        User user =  App.getSession().getUser();
        user.addUserProfileData(userProfile);
        user.setUsername("ababab");
        //user.setBirthday(Calendar.getInstance().getTime().toString());
        user.setCreated_at(Calendar.getInstance().getTime().toString());
        ExerciseSession exercise = new ExerciseSession();
        exercise.setExercise_id(11);
        user.setEmail("ababab@gmail.com");
        App.getSession().setUser(user);
    }

    @Test
    public void testViewDisplay() {
        //test the view on the Preferences page
        onView(withText("Preferences")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("Username")).check(ViewAssertions.matches(isDisplayed()));
        onView(allOf(withId(R.id.currentUsername), withText("ababab"))).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("First Name")).check(ViewAssertions.matches(isDisplayed()));
        onView(allOf(withId(R.id.currentFirstname), withText("abab"))).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("Last Name")).check(ViewAssertions.matches(isDisplayed()));
        onView(allOf(withId(R.id.currentLastname), withText("abab"))).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("Gender")).check(ViewAssertions.matches(isDisplayed()));
        onView(allOf(withId(R.id.currentGender), withText("Male"))).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("Meditations")).check(ViewAssertions.matches(isDisplayed()));
        onView(allOf(withId(R.id.meditationTime), withText("12:34 AM"))).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("Lessons")).check(ViewAssertions.matches(isDisplayed()));
        onView(allOf(withId(R.id.lessonTime), withText("12:34 AM"))).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("I wake up at")).check(ViewAssertions.matches(isDisplayed()));
        onView(allOf(withId(R.id.wakeUpTime), withText("12:34 AM"))).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("I go to sleep at")).check(ViewAssertions.matches(isDisplayed()));
        onView(allOf(withId(R.id.goToSleepTime), withText("12:34 AM"))).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("Log Out")).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testUsernameNotClickable() {
        onView(withId(R.id.currentUsernameRow)).check(matches(notNullValue()));
        onView(withId(R.id.currentUsernameText)).check(matches(withText("Username")));
        onView(withId(R.id.currentUsernameRow)).check(matches(not(isClickable())));
    }

    @Test
    public void firstNameNotClickable() {
        onView(withId(R.id.currentFirstnameRow)).check(matches(notNullValue()));
        onView(withId(R.id.currentFirstnameText)).check(matches(withText("Username")));
        onView(withId(R.id.currentFirstname)).check(matches(not(isClickable())));
    }

    @Test
    public void lastNameNotClickable() {
        onView(withId(R.id.currentLastnameRow)).check(matches(notNullValue()));
        onView(withId(R.id.currentLastnameText)).check(matches(withText("Username")));
        onView(withId(R.id.currentLastname)).check(matches(not(isClickable())));
    }

    @Test
    public void genderNotClickable() {
        onView(withId(R.id.currentGenderRow)).check(matches(notNullValue()));
        onView(withId(R.id.currentGenderText)).check(matches(withText("Username")));
        onView(withId(R.id.currentGender)).check(matches(not(isClickable())));
    }

    @Test
    public void testMeditationsButton() {
        onView(withId(R.id.meditationRow)).check(matches(notNullValue()));
        onView(withId(R.id.meditationTimeText)).check(matches(withText("Lessons")));
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
    public void testLogout() {
        onView(withId(R.id.logOutRow)).check(matches(notNullValue()));
        onView(withId(R.id.logOut)).check(matches(withText("Log Out")));
        onView(withId(R.id.logOutRow)).perform(click());
        intended(hasComponent(LoginActivity.class.getName()));
        onView(withId(R.id.loginButton)).check(matches(notNullValue()));
    }
}
