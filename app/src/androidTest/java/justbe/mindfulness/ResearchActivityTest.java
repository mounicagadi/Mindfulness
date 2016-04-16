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
public class ResearchActivityTest extends ActivityInstrumentationTestCase2<ResearchActivity> {

    @Mock
    Context mMockContext;
    User user;
    UserProfile userProfile;

    String first_name = "ababab";
    String last_name = "ababab";
    String username = "ababab";
    Integer gender = 0;

    @Rule
    public IntentsTestRule startProgramIntentRule = new IntentsTestRule(ResearchActivity.class);

    public ResearchActivityTest() {
        super(ResearchActivity.class);

        userProfile = new UserProfile();
        userProfile.setGender(gender);

        user = new User();
        user.addUserProfileData(userProfile);
        user.setFirst_name(first_name);
        user.setLast_name(last_name);
        user.setGender(gender);
        user.setUsername(username);
        user.setCreated_at(Calendar.getInstance().getTime().toString());
        user.setEmail("ababab@gmail.com");
        App.getSession().setUser(user);
    }

        @Test
        public void testResearchActivityText() {
            //Information about starting program is present
            onView(withId(R.id.researchStudyText)).check(matches(notNullValue()));

            // Make sure it has the appropriate information
            onView(withId(R.id.researchStudyText)).check(matches(withText(R.string.resarch_study)));
        }

        @Test
        public void testRadioGroup() {
            onView(withId(R.id.yes)).check(matches(notNullValue()));
            onView(withId(R.id.yes)).check(matches(withText(R.string.yes)));
            onView(withId(R.id.no)).check(matches(notNullValue()));
            onView(withId(R.id.no)).check(matches(withText(R.string.no)));
        }


    // Make sure the start program button is available
        @Test
        public void testNextButton() {
            onView(withId(R.id.researchStudyNextButton)).check(matches(notNullValue()));
            onView(withId(R.id.researchStudyNextButton)).check(matches(withText("Next")));
        }

        @Test
        public void testNextButtonClick() {
            onView(withId(R.id.researchStudyNextButton)).perform(click());
            intended(hasComponent(SleepTimeActivity.class.getName()));
        }

}



