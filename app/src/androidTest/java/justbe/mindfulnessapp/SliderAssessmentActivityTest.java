package justbe.mindfulnessapp;

import android.content.Context;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import org.mockito.Mock;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import justbe.mindfulnessapp.models.SliderQuestion;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class SliderAssessmentActivityTest extends ActivityInstrumentationTestCase2<SliderAssessmentActivity> {
    @Mock
    Context mMockContext;

    String question1 = "question1";
    int sliderMinVal1 = 1;
    String sliderMinText1 = "One";
    int sliderMaxVal1 = 3;
    String sliderMaxText1 = "Three";

    @Rule
    public IntentsTestRule mActivityRule = new IntentsTestRule(SliderAssessmentActivity.class);

    public SliderAssessmentActivityTest() {
        super(SliderAssessmentActivity.class);

        // Give the DropdownActivity some data to test with
        SliderQuestion sliderQuestion = new SliderQuestion(question1, sliderMinVal1, sliderMinText1,
                sliderMaxVal1, sliderMaxText1);
        AssessmentFlowManager flowManager = AssessmentFlowManager.getInstance(mMockContext);
        flowManager.setCurrentAssessment(sliderQuestion);
    }

    @Test
    public void testViewDisplay() {
        //test the view
        onView(allOf(withText(question1), withId(R.id.textView))).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.seekBar)).check(ViewAssertions.matches(isDisplayed()));
        onView(allOf(withId(R.id.minText), withText(sliderMinText1))).check(ViewAssertions.matches(isDisplayed()));
        onView(allOf(withId(R.id.maxText), withText(sliderMaxText1))).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.nextButton)).check(ViewAssertions.matches(isDisplayed()));
    }
}
