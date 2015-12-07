package justbe.mindfulnessapp.rest;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;

import justbe.mindfulnessapp.CreateAccountActivity;
import justbe.mindfulnessapp.R;
import justbe.mindfulnessapp.models.Assessment;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static justbe.mindfulnessapp.ErrorTextMatcher.hasErrorText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by eddiehurtig on 12/7/15.
 */
@RunWith(AndroidJUnit4.class)
public class GenericHttpRequestTaskTest extends TestCase {
        protected int value1, value2;

        // assigning the values
        protected void setUp(){
            value1=3;
            value2=3;
        }

        // test method to add two values
        public void testAdd(){
            double result= value1 + value2;
            assertTrue(result == 6);
        }

        @Test
        public void testGetAssessment() {
            GenericHttpRequestTask<String, Assessment> request = new GenericHttpRequestTask<String, Assessment>(String.class, Assesment.class);

            ResponseEntity<Assessment> response = request.doInBackground("/api/v1/assessment/5964/", HttpMethod.GET);

            assertNotNull(response);
            assertNotNull(response.getBody());

            assertEquals(response.getStatusCode(), 200);

            assertEquals(response.getBody().getId(), new Integer(5964));
            assertEquals(response.getBody().getUser(), "/api/v1/user/1/");

        }
}

