package justbe.mindfulnessapp;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static justbe.mindfulnessapp.ErrorTextMatcher.hasErrorText;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class CreateAccountActivityTest {


    @Rule
    public IntentsTestRule caIntentRule = new IntentsTestRule(CreateAccountActivity.class);

    @Test
    public void testViewDisplay() {
        onView(allOf(withId(R.id.editUsername), withHint("Username")))
                .check(ViewAssertions.matches(isDisplayed()));
        onView(allOf(withId(R.id.editPassword), withHint("Password")))
                .check(ViewAssertions.matches(isDisplayed()));
        onView(allOf(withId(R.id.editConfirmPassword), withHint("Confirm Password")))
                .check(ViewAssertions.matches(isDisplayed()));
        //those are the text will show on the page

        onView(withText("Meditations")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("Lessons")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("I wake up at")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("I go to sleep at")).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testBadInputs() {
        // empty username
        onView(withId(R.id.createAccountButton)).perform(click());
        onView(withId(R.id.editUsername))
                .check(matches(hasErrorText("The username field must not be empty")));

        // username is too long
        onView(withId(R.id.editUsername)).perform(typeText("thisnameismuchtoolong"));
        onView(withId(R.id.createAccountButton)).perform(click());
        onView(withId(R.id.editUsername))
                .check(matches(hasErrorText("Your username must be less than 16 characters")));


        //enter username did not enter password
        onView(withId(R.id.editUsername)).perform(clearText());
        onView(withId(R.id.editUsername)).perform(typeText("nopassword"));
        onView(withId(R.id.createAccountButton)).perform(click());
        onView(withId(R.id.editPassword))
                .check(matches(hasErrorText("Your password must be at least 6 characters")));


        // password is too short
        onView(withId(R.id.editUsername)).perform(clearText());
        onView(withId(R.id.editUsername)).perform(typeText("newuser420"));
        onView(withId(R.id.editPassword)).perform(typeText("short"));
        onView(withId(R.id.editConfirmPassword)).perform(typeText("short"), closeSoftKeyboard());
        onView(withId(R.id.createAccountButton)).perform(click());
        onView(withId(R.id.editPassword))
                .check(matches(hasErrorText("Your password must be at least 6 characters")));

       // passwords do not match
        onView(withId(R.id.editPassword)).perform(clearText());
        onView(withId(R.id.editConfirmPassword)).perform(clearText());
        onView(withId(R.id.editPassword)).perform(typeText("longer"));
        onView(withId(R.id.editConfirmPassword)).perform(typeText("otherword"), closeSoftKeyboard());
        onView(withId(R.id.createAccountButton)).perform(click());
        onView(withId(R.id.editConfirmPassword))
                .check(matches(hasErrorText("Your passwords do not match")));
    }


    ///////////////////////test the time picker  not yet///////////////////////
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
    public void testCreateAccountButton(){
        onView(withId(R.id.createAccountButton)).check(matches(notNullValue()));
        onView(withId(R.id.createAccountButton)).check(matches(withText("Create Account")));
        onView(withId(R.id.createAccountButton)).perform(click());
    }
    ///////////////////////////////////////////////////////////////
    // We didn't want to create a new account in the database every time the tests are run
    // This can test account creation, but credentials for a unique account must be entered
    // for username and password
    //////this username has already been used
   /*
    @Test
    public void testSuccessAccountCreate() {
        //clear the text
        onView(withId(R.id.editUsername)).perform(clearText());
        //type username
        onView(withId(R.id.editUsername)).perform(typeText("yoyo"));
        //type password
        onView(withId(R.id.editPassword)).perform(typeText("333333"));
        //confirmPassword
        onView(withId(R.id.editConfirmPassword)).perform(typeText("333333"), closeSoftKeyboard());
        //create count
        onView(withId(R.id.createAccountButton)).perform(click());
        //go to MainActivity page
        intended(hasComponent(StartProgramActivity.class.getName()));
        //when user reach main page the text Meditation will show
        onView(withText("Meditation")).check(ViewAssertions.matches(isDisplayed()));
    }
    */
}

