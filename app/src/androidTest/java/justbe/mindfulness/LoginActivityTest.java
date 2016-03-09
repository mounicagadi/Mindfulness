package justbe.mindfulness;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static justbe.mindfulness.ErrorTextMatcher.hasErrorText;
import static org.hamcrest.core.IsNull.notNullValue;


@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {


    @Rule
    public IntentsTestRule loginIntentRule = new IntentsTestRule(LoginActivity.class);

    @Test
    public void testCreateAccountButton() {
        //createAccountButton are here not null
        onView(withId(R.id.createAccountButton)).check(matches(notNullValue()));
        //check create Account Button has text create account
        onView(withId(R.id.createAccountButton)).check(matches(withText("Sign up")));
        //when user click createAccountButton
        onView(withId(R.id.createAccountButton)).perform(click());
        //load page createAccountActivity page
        intended(hasComponent(CreateAccountActivity.class.getName()));
        onView(withId(R.id.loginButton)).check(doesNotExist());
        //on create account page it will show the createAccountButton
        onView(withId(R.id.createAccountButton)).check(matches(withText("Sign up")));
        pressBack();
        //when we back to login in page button will show
        onView(withId(R.id.loginButton)).check(matches(withText("Login")));
        onView(withId(R.id.createAccountButton)).check(matches(withText("Sign up")));
    }

    //when password and user name are not match
    @Test
    public void testFailedLogin() {
        onView(withId(R.id.editUsername))
                .perform(typeText("HELLOIAMNOTREAL"));
        onView(withId(R.id.editPassword))
                .perform(typeText("short"), closeSoftKeyboard());
        // check that the button is there
        onView(withId(R.id.loginButton)).check(matches(notNullValue()));
        onView(withId(R.id.loginButton)).check(matches(withText("Login")));
        // Error shouldn't appear until after button is pressed
        onView(withText("Username does not exist"))
                .check(doesNotExist());
        onView(withId(R.id.loginButton)).perform(click());
        // error is displayed after wrong credentials entered
        onView(withId(R.id.editPassword))
                .check(matches(hasErrorText("Invalid Username/password")));
    }

    @Test
    public void nullUsername(){

        onView(withId(R.id.editPassword))
                .perform(typeText("testing"), closeSoftKeyboard());
        // check that the button is there
        onView(withId(R.id.loginButton)).check(matches(notNullValue()));
        onView(withId(R.id.loginButton)).check(matches(withText("Login")));
        // Error shouldn't appear until after button is pressed
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.editUsername))
                .check(matches(hasErrorText("Username cannot be null")));
    }

    @Test
    public void nullPassword(){

        onView(withId(R.id.editUsername))
                .perform(typeText("testing"));
        // check that the button is there
        onView(withId(R.id.loginButton)).check(matches(notNullValue()));
        onView(withId(R.id.loginButton)).check(matches(withText("Login")));
        // Error shouldn't appear until after button is pressed
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.editPassword))
                .check(matches(hasErrorText("Password cannot be null")));
    }

    @Test
    public void nullUsernamePassword(){

        // check that the button is there
        onView(withId(R.id.loginButton)).check(matches(notNullValue()));
        onView(withId(R.id.loginButton)).check(matches(withText("Login")));
        // Error shouldn't appear until after button is pressed
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.editPassword))
                .check(matches(hasErrorText("Please enter the username and password")));
    }



    @Test
    public void testSuccessfulLogin() {
        //type username
        onView(withId(R.id.editUsername))
                .perform(typeText("testaccount"));
        //tyoe password
        onView(withId(R.id.editPassword))
                .perform(typeText("testtest"), closeSoftKeyboard());
        //cilck the login button
        onView(withId(R.id.loginButton)).perform(click());
        //go to MainActivity page
        intended(hasComponent(MainActivity.class.getName()));
        //login in button disappear
        onView(withText("Login")).check(doesNotExist());

    }
}

///done