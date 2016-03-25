package justbe.mindfulness;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.annotation.UiThreadTest;
import android.test.ApplicationTestCase;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static justbe.mindfulness.ErrorTextMatcher.hasErrorText;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class CreateAccountActivityTest{


    @Rule
    public IntentsTestRule caIntentRule = new IntentsTestRule(CreateAccountActivity.class);


    @Test
    public void nullFirstName(){
        // empty firstname
        onView(withId(R.id.createAccountButton)).perform(scrollTo(), click());
        onView(withId(R.id.editFirstname))
                .check(matches(hasErrorText("The first name field must not be empty")));
    }

    @Test
    public void nullLastName(){
        //empty lastname
        onView(withId(R.id.editFirstname)).perform(typeText("alice"), closeSoftKeyboard());
        onView(withId(R.id.createAccountButton)).perform(scrollTo(), click());
        onView(withId(R.id.editLastname))
                .check(matches(hasErrorText("The last name field must not be empty")));
    }
    @Test
    public void nullEmail() {

        onView(withId(R.id.editFirstname)).perform(typeText("alice"), closeSoftKeyboard());
        onView(withId(R.id.editLastname)).perform(typeText("wonder"), closeSoftKeyboard());
        onView(withId(R.id.createAccountButton)).perform(scrollTo(), click());
        onView(withId(R.id.editEmail))
                .check(matches(hasErrorText("The email field must not be empty")));
    }

    @Test
    public void invalidEmail(){

        onView(withId(R.id.editFirstname)).perform(typeText("alice"), closeSoftKeyboard());
        onView(withId(R.id.editLastname)).perform(typeText("wonder"), closeSoftKeyboard());
        onView(withId(R.id.male)).perform(click());
        onView(withId(R.id.editEmail)).perform(typeText("wonder"), closeSoftKeyboard());
        onView(withId(R.id.createAccountButton)).perform(scrollTo(), click());
        onView(withId(R.id.editEmail))
                .check(matches(hasErrorText("Please enter valid email")));
    }


    @Test
    public void nullUsername(){

        onView(withId(R.id.editFirstname)).perform(typeText("alice"), closeSoftKeyboard());
        onView(withId(R.id.editLastname)).perform(typeText("wonder"), closeSoftKeyboard());
        onView(withId(R.id.editEmail)).perform(typeText("alice@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.createAccountButton)).perform(scrollTo(), click());
        onView(withId(R.id.editUsername))
                .check(matches(hasErrorText("The username field must not be empty")));
    }

    @Test
    public void shortUsername(){

        onView(withId(R.id.editFirstname)).perform(typeText("alice"), closeSoftKeyboard());
        onView(withId(R.id.editLastname)).perform(typeText("wonder"), closeSoftKeyboard());
        onView(withId(R.id.editEmail)).perform(typeText("alice@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.editUsername)).perform(typeText("ab"), closeSoftKeyboard());
        onView(withId(R.id.createAccountButton)).perform(scrollTo(), click());
        onView(withId(R.id.editUsername))
                .check(matches(hasErrorText("Your username must be at least 6 characters")));
    }

    @Test
    public void longUsername(){

        onView(withId(R.id.editFirstname)).perform(typeText("alice"), closeSoftKeyboard());
        onView(withId(R.id.editLastname)).perform(typeText("wonder"), closeSoftKeyboard());
        onView(withId(R.id.editEmail)).perform(typeText("alice@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.editUsername)).perform(typeText("thisnameistoooomuchhhhlongggg"), closeSoftKeyboard());
        onView(withId(R.id.createAccountButton)).perform(scrollTo(), click());
        onView(withId(R.id.editUsername))
                .check(matches(hasErrorText("Your username must be less than 16 characters")));

    }

    @Test
    public void emptyPassword(){

        onView(withId(R.id.editFirstname)).perform(typeText("alice"), closeSoftKeyboard());
        onView(withId(R.id.editLastname)).perform(typeText("wonder"), closeSoftKeyboard());
        onView(withId(R.id.editEmail)).perform(typeText("alice@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.editUsername)).perform(typeText("alice123"), closeSoftKeyboard());
        onView(withId(R.id.createAccountButton)).perform(scrollTo(), click());
        onView(withId(R.id.editPassword))
                .check(matches(hasErrorText("Your password must be at least 6 characters")));
    }

    @Test
    public void shortPassword(){

        onView(withId(R.id.editFirstname)).perform(typeText("alice"), closeSoftKeyboard());
        onView(withId(R.id.editLastname)).perform(typeText("wonder"), closeSoftKeyboard());
        onView(withId(R.id.editEmail)).perform(typeText("alice@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.editUsername)).perform(typeText("alice123"), closeSoftKeyboard());
        onView(withId(R.id.editPassword)).perform(typeText("wo"), closeSoftKeyboard());
        onView(withId(R.id.createAccountButton)).perform(scrollTo(), click());
        onView(withId(R.id.editPassword)).perform(scrollTo())
                .check(matches(hasErrorText("Your password must be at least 6 characters")));

    }

    @Test
    public void emptyConfirmPassword(){

        onView(withId(R.id.editFirstname)).perform(typeText("alice"), closeSoftKeyboard());
        onView(withId(R.id.editLastname)).perform(typeText("wonder"), closeSoftKeyboard());
        onView(withId(R.id.editEmail)).perform(typeText("alice@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.editUsername)).perform(typeText("alice123"), closeSoftKeyboard());
        onView(withId(R.id.editPassword)).perform(typeText("wonderland"), closeSoftKeyboard());
        onView(withId(R.id.createAccountButton)).perform(scrollTo(), click());
        onView(withId(R.id.editConfirmPassword))
                .check(matches(hasErrorText("Your passwords do not match")));

    }

        //short password






    ///////////////////////test the time picker  not yet///////////////////////
    @Test
    public void testMeditationsButton() {
        onView(withId(R.id.meditationTimeText)).perform(scrollTo()).check(matches(withText("What time do you want to meditate daily?")));
        onView(withId(R.id.meditationTime)).perform(scrollTo(),click());
    }
    @Test
    public void testLessonsButton() {
        onView(withId(R.id.lessonTimeText)).perform(scrollTo()).check(matches(withText("What time on that day would you like to receive the lesson?")));
        onView(withId(R.id.lessonTime)).perform(scrollTo(), click());
    }

    @Test
    public void testWakeUpButton() {
        onView(withId(R.id.wakeUpText)).perform(scrollTo()).check(matches(withText("What time do you wake up in the morning?")));
        onView(withId(R.id.wakeUpTime)).perform(scrollTo(), click());
    }

    @Test
    public void testGoToSleepButton() {
        onView(withId(R.id.goToSleepText)).perform(scrollTo()).check(matches(withText("What time do you go to sleep daily?")));
        onView(withId(R.id.goToSleepTime)).perform(scrollTo(),click());
    }

    @Test
    public void testCreateAccountButton(){
        onView(withId(R.id.createAccountButton)).check(matches(notNullValue()));
        //onView(withId(R.id.createAccountButton)).check(matches(withText("Sign Up")));
        onView(withId(R.id.createAccountButton)).perform(scrollTo(),click());
    }
    ///////////////////////////////////////////////////////////////
    // We didn't want to create a new account in the database every time the tests are run
    // This can test account creation, but credentials for a unique account must be entered
    // for username and password
    //////this username has already been used
    @Test
    public void testSuccessAccountCreate() {

        onView(withId(R.id.editFirstname)).perform(typeText("alice"), closeSoftKeyboard());
        onView(withId(R.id.editLastname)).perform(typeText("wonderland"), closeSoftKeyboard());
        onView(withId(R.id.editEmail)).perform(typeText("alice234562356123@gmail.com"), closeSoftKeyboard());
        //clear the text
        onView(withId(R.id.editUsername)).perform(clearText());
        //type username
        onView(withId(R.id.editUsername)).perform(typeText("alice9999911"), closeSoftKeyboard());
        //type password
        onView(withId(R.id.editPassword)).perform(typeText("333333"), closeSoftKeyboard());
        //confirmPassword
        onView(withId(R.id.editConfirmPassword)).perform(typeText("333333"), closeSoftKeyboard());
        //create count
        onView(withId(R.id.createAccountButton)).perform(scrollTo(),click());
        //go to MainActivity page
        intended(hasComponent(StartProgramActivity.class.getName()));

    }


    @Test
    public void testFailedAccountCreate() {


        onView(withId(R.id.editFirstname)).perform(typeText("abab"), closeSoftKeyboard());
        onView(withId(R.id.editLastname)).perform(typeText("abab"), closeSoftKeyboard());
        onView(withId(R.id.editEmail)).perform(typeText("amit.banne2014@gmail.com"), closeSoftKeyboard());
        //clear the text
        onView(withId(R.id.editUsername)).perform(clearText());
        //type username
        onView(withId(R.id.editUsername)).perform(typeText("ababab"), closeSoftKeyboard());
        //type password
        onView(withId(R.id.editPassword)).perform(typeText("ababab"), closeSoftKeyboard());
        //confirmPassword
        onView(withId(R.id.editConfirmPassword)).perform(typeText("ababab"), closeSoftKeyboard());
        //create count
        onView(withId(R.id.createAccountButton)).perform(scrollTo(), click());
    }
}

