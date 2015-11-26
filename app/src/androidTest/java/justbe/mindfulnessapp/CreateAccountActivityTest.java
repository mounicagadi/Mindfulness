package justbe.mindfulnessapp;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static justbe.mindfulnessapp.ErrorTextMatcher.hasErrorText;

@RunWith(AndroidJUnit4.class)
public class CreateAccountActivityTest {


    @Rule
    public IntentsTestRule caIntentRule = new IntentsTestRule(CreateAccountActivity.class);

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
}
