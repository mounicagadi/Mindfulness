package justbe.mindfulness;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class YesNoAssessmentActivityTest extends ActivityInstrumentationTestCase2<YesNoAssessmentActivity> {
    @Rule
    public IntentsTestRule mActivityRule = new IntentsTestRule(YesNoAssessmentActivity.class);

    public YesNoAssessmentActivityTest() {
        super(YesNoAssessmentActivity.class);
    }

    @Test
    public void testViewDisplay() {
        //test the view
        onView(allOf(withId(R.id.yesButton), withText("Yes"))).check(ViewAssertions.matches(isDisplayed()));
        onView(allOf(withId(R.id.noButton), withText("No"))).check(ViewAssertions.matches(isDisplayed()));
    }
}