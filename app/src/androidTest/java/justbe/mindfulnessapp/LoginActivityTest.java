package justbe.mindfulnessapp;

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
import static justbe.mindfulnessapp.ErrorTextMatcher.hasErrorText;
import static org.hamcrest.core.IsNull.notNullValue;


@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {


    @Rule
    public IntentsTestRule loginIntentRule = new IntentsTestRule(LoginActivity.class);

    @Test
    public void testCreateAccountButton() {
        onView(withId(R.id.createAccountButton)).check(matches(notNullValue()));
        onView(withId(R.id.createAccountButton)).check(matches(withText("Create Account")));
        onView(withId(R.id.createAccountButton)).perform(click());
        intended(hasComponent(CreateAccountActivity.class.getName()));
        onView(withId(R.id.loginButton)).check(doesNotExist());
        pressBack();
        onView(withId(R.id.loginButton)).check(matches(withText("Login")));
    }

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
        onView(withText("Password and username didn't match an account"))
                .check(doesNotExist());
        onView(withId(R.id.loginButton)).perform(click());
        // error is displayed after wrong credentials entered
        onView(withId(R.id.editPassword))
                .check(matches(hasErrorText("Password and username didn't match an account")));

    }

    @Test
    public void testSuccessfulLogin() {
        onView(withId(R.id.editUsername))
                .perform(typeText("ckohler"));
        onView(withId(R.id.editPassword))
                .perform(typeText("bearbear"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
        onView(withText("Login")).check(doesNotExist());
    }
}
