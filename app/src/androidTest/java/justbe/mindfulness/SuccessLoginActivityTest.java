package justbe.mindfulness;

import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by amit on 24-03-2016.
 */
public class SuccessLoginActivityTest {

    @Rule
    public IntentsTestRule loginIntentRule = new IntentsTestRule(LoginActivity.class);

    @Test
    public void testSuccessfulLogin() {
        //type username
        onView(withId(R.id.editUsername))
                .perform(typeText("ababab"), closeSoftKeyboard());
        //tyoe password
        onView(withId(R.id.editPassword))
                .perform(typeText("ababab"), closeSoftKeyboard());
        //cilck the login button
        onView(withId(R.id.loginButton)).perform(click());
        //go to MainActivity page
        intended(hasComponent(MainActivity.class.getName()));
        //login in button disappear
        onView(withText("Login")).check(doesNotExist());

    }
}
