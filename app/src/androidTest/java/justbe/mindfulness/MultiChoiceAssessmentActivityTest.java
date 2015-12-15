package justbe.mindfulness;

import android.content.Context;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import org.mockito.Mock;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import justbe.mindfulness.models.MultiChoiceQuestion;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class MultiChoiceAssessmentActivityTest extends ActivityInstrumentationTestCase2<MultiChoiceAssessmentActivity> {
    @Mock
    Context mMockContext;

    String question1 = "question1";
    String[] choices1 = {"choice 1", "choice2", "choice3"};

    @Rule
    public IntentsTestRule mActivityRule = new IntentsTestRule(MultiChoiceAssessmentActivity.class);

    public MultiChoiceAssessmentActivityTest() {
        super(MultiChoiceAssessmentActivity.class);

        // Give the DropdownActivity some data to test with
        MultiChoiceQuestion multiChoiceQuestion1 = new MultiChoiceQuestion(question1, choices1);
        AssessmentFlowManager flowManager = AssessmentFlowManager.getInstance(mMockContext);
        flowManager.setCurrentAssessment(multiChoiceQuestion1);
    }

    @Test
    public void testViewDisplay() {
        //test the view
        onView(allOf(withText(question1), withId(R.id.textView))).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.choiceList)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.nextButton)).check(ViewAssertions.matches(isDisplayed()));
    }
}