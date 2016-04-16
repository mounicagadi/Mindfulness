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

/**
 * Created by amit on 15-04-2016.
 */

@RunWith(AndroidJUnit4.class)
public class ThankyouActivityTest extends ActivityInstrumentationTestCase2<ThankYouActivity> {

    @Mock
    Context mMockContext;
    User user;
    UserProfile userProfile;

    String first_name = "ababab";
    String last_name = "ababab";
    String username = "ababab";
    Integer gender = 0;

    @Rule
    public IntentsTestRule thankyouProgramIntentRule = new IntentsTestRule(ThankYouActivity.class);

    public ThankyouActivityTest(){

        super(ThankYouActivity.class);

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
    public void testThankyouText() {
        //Information about starting program is present
        onView(withId(R.id.textView2)).check(matches(notNullValue()));
        // Make sure it has the appropriate information
        onView(withId(R.id.textView2)).check(matches(withText("Thank You")));
    }


    @Test
    public void testThankyouContent() {
        //Information about starting program is present
        onView(withId(R.id.thankYouText)).check(matches(notNullValue()));
        // Make sure it has the appropriate information
        onView(withId(R.id.thankYouText)).check(matches(withText("You have successfully completed the 8-week Mindfulness program")));
    }


    @Test
    public void testFinishButton() {
        onView(withId(R.id.endButton)).check(matches(notNullValue()));
        onView(withId(R.id.endButton)).check(matches(withText("Finish")));
    }

    @Test
    public void testFinishButtonClick() {
        onView(withId(R.id.endButton)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }


}
