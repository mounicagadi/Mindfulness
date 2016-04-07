package justbe.mindfulness;

import android.content.Context;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.Calendar;

import justbe.mindfulness.models.ExerciseSession;
import justbe.mindfulness.models.User;
import justbe.mindfulness.models.UserProfile;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNull.notNullValue;


@RunWith(AndroidJUnit4.class)
public class StartProgramActivityTest extends ActivityInstrumentationTestCase2<StartProgramActivity> {


    @Mock
    Context mMockContext;
    User user;
    UserProfile userProfile;

    String first_name = "ababab";
    String last_name = "ababab";
    String username = "ababab";
    Integer gender = 0;

    @Rule
    public IntentsTestRule startProgramIntentRule = new IntentsTestRule(StartProgramActivity.class);

    public StartProgramActivityTest(){

        super(StartProgramActivity.class);

        userProfile = new UserProfile();
        userProfile.setCurrent_week(1);
        userProfile.setGo_to_sleep_time(Calendar.getInstance().getTime().toString());
        userProfile.setWake_up_time(Calendar.getInstance().getTime().toString());
        userProfile.setGo_to_sleep_time(Calendar.getInstance().getTime().toString());
        userProfile.setExercise_day_of_week(1);
        userProfile.setExercise_time(Calendar.getInstance().getTime().toString());
        userProfile.setGender(gender);
        userProfile.setMeditation_time(Calendar.getInstance().getTime().toString());

        user =  new User();
        user.addUserProfileData(userProfile);
        user.setFirst_name(first_name);
        user.setLast_name(last_name);
        user.setGender(gender);
        user.setUsername(username);
        user.setCreated_at(Calendar.getInstance().getTime().toString());
        ExerciseSession exercise = new ExerciseSession();
        exercise.setExercise_id(11);
        user.setEmail("ababab@gmail.com");
        App.getSession().setUser(user);

    }

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

    @Test
    public void testWelcomeText() {
        onView(withId(R.id.textView2)).check(matches(notNullValue()));
        onView(withId(R.id.textView2)).check(matches(withText(R.string.welcome_msg)));
    }

    @Test
      public void testStartButtonClick() {
        onView(withId(R.id.startButton)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }
}


