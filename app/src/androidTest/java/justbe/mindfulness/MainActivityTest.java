package justbe.mindfulness;

import android.content.Context;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.Calendar;
import java.util.Date;

import justbe.mindfulness.models.ExerciseSession;
import justbe.mindfulness.models.User;
import justbe.mindfulness.models.UserProfile;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {


    @Mock
    Context mMockContext;

    String first_name = "ababab";
    String last_name = "ababab";
    String username = "ababab";
    Integer gender = 0;
    Date exercise_time;

    Date meditation_time;
    Date wake_up_time;
    Date go_to_sleep_time;
    UserProfile userProfile;
    User user;

    @Rule
    public IntentsTestRule mActivityRule = new IntentsTestRule(MainActivity.class);

    public MainActivityTest() {
        super(MainActivity.class);

        userProfile = new UserProfile();
        userProfile.setCurrent_week(1);
        userProfile.setGo_to_sleep_time(Util.dateToUserProfileString(Calendar.getInstance().getTime()));
        userProfile.setWake_up_time(Util.dateToUserProfileString(Calendar.getInstance().getTime()));
        userProfile.setGo_to_sleep_time(Util.dateToUserProfileString(Calendar.getInstance().getTime()));
        userProfile.setExercise_day_of_week(1);
        userProfile.setExercise_time(Util.dateToUserProfileString(Calendar.getInstance().getTime()));
        userProfile.setGender(1);
        String medTime  = (Util.dateToUserProfileString(Calendar.getInstance().getTime()));
        userProfile.setMeditation_time(medTime);


        user =  new User();
        user.addUserProfileData(userProfile);
        user.setUsername("abababa");
        user.setCreated_at(Calendar.getInstance().getTime().toString());
        ExerciseSession exercise = new ExerciseSession();
        exercise.setExercise_id(11);
        user.setEmail("ababab@gmail.com");
        user.setMeditation_time(medTime);
        App.getSession().setUser(user);

    }

    //test preferenceButton on main page
    @Test
    public void testPreferencesButton() {
        onView(withId(R.id.preferencesButton)).check(matches(isDisplayed()));
        onView(withId(R.id.preferencesButton)).perform(click());
        intended(hasComponent(PreferencesActivity.class.getName()));
        onView(withText("Preferences")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("Username")).check(ViewAssertions.matches(isDisplayed()));
        pressBack();
        onView(withText("Preferences")).check(doesNotExist());

    }

    //testWeekButton on the main page
    @Test
    public void testWeekButton() {
        onView(withId(R.id.weekButton)).check(matches(isDisplayed()));
        onView(withId(R.id.weekButton)).perform(click());
        onView(withText("This Week")).check(ViewAssertions.matches(isDisplayed()));
        pressBack();
        onView(withText("This Week")).check(doesNotExist());
    }

    //test WeeklyLessonButton on the main page
    @Test
    public void testWeeklyLessonButton() {
        int currentWeek = user.getCurrent_week();
        onView(withId(R.id.weeklyLessonButton)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.weeklyLessonButtonText), withText("Week " + currentWeek+" Exercise")))
                .check(matches(isDisplayed()));
        onView(withId(R.id.weeklyLessonButton)).perform(click());
        intended(hasComponent(LessonActivity.class.getName()));
        onView(withText("Weekly Lesson")).check(ViewAssertions.matches(isDisplayed()));
        pressBack();
        onView(withText("Weekly Lesson")).check(doesNotExist());
    }

    @Test
    public void WeekButton() {
        onView(withId(R.id.Meditation6)).perform(click());
        onView(withId(R.id.Meditation0)).perform(click());
        onView(withId(R.id.Meditation1)).perform(click());
        onView(withId(R.id.Meditation2)).perform(click());
        onView(withId(R.id.Meditation3)).perform(click());
        onView(withId(R.id.Meditation4)).perform(click());
        onView(withId(R.id.Meditation5)).perform(click());

    }

    @Test
    public void AudioButton() {
        int audioButtonId = R.id.audioButton;

        onView(withId(audioButtonId)).perform(click());
        onView(withId(audioButtonId)).perform(click());
    }

}
