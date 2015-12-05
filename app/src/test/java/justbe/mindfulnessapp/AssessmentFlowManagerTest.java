package justbe.mindfulnessapp;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Queue;

import justbe.mindfulnessapp.models.AssessmentQuestion;
import justbe.mindfulnessapp.models.DropdownQuestion;
import justbe.mindfulnessapp.models.MultiChoiceQuestion;
import justbe.mindfulnessapp.models.SliderQuestion;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class AssessmentFlowManagerTest {

    @Mock
    Context mMockContext;
    private AssessmentFlowManager flowManager;

    String question1 = "Test Question 1";
    String question2 = "Test Question 2";
    String question3 = "Test Question 3";

    String[] choices1 = {"Test Choice 1", "Test Choice 2", "Test Choice 3"};
    String[] choices2 = {"Test Choice 4", "Test Choice 5", "Test Choice 6"};

    int sliderMinVal1 = 1;
    int sliderMinVal2 = 2;
    String sliderMinText1 = "One";
    String sliderMinText2 = "Two";
    int sliderMaxVal1 = 3;
    int sliderMaxVal2 = 4;
    String sliderMaxText1 = "Three";
    String sliderMaxText2 = "Four";

    @Test
    public void testGetInstance() throws Exception {
        assertNull(flowManager);
        flowManager = AssessmentFlowManager.getInstance(mMockContext);
        assertNotNull(flowManager);
        assertNull(flowManager.getCurrentAssessment());
        assertTrue(flowManager.getAssessments().isEmpty());
    }

    @Test
    public void testAddAssessment() throws Exception {
        flowManager = AssessmentFlowManager.getInstance(mMockContext);

        DropdownQuestion dropdownQuestion = new DropdownQuestion(question1, choices1);
        SliderQuestion sliderQuestion = new SliderQuestion(question2, sliderMinVal1, sliderMinText1,
                sliderMaxVal1, sliderMaxText1);
        MultiChoiceQuestion multiChoiceQuestion = new MultiChoiceQuestion(question3, choices2);

        flowManager.addAssessment(dropdownQuestion);
        flowManager.addAssessment(sliderQuestion);
        flowManager.addAssessment(multiChoiceQuestion);

        Queue<AssessmentQuestion> assessments = flowManager.getAssessments();
        assertNotNull(assessments);
        assertNull(flowManager.getCurrentAssessment());

        DropdownQuestion firstAssessment = (DropdownQuestion) assessments.remove();
        SliderQuestion secondAssessment = (SliderQuestion) assessments.remove();
        MultiChoiceQuestion thirdAssessment = (MultiChoiceQuestion) assessments.remove();

        assertEquals(firstAssessment.getQuestionText(), question1);
        assertEquals(firstAssessment.getDropdownOptions(), choices1);

        assertEquals(secondAssessment.getQuestionText(), question2);
        assertEquals(secondAssessment.getMinSliderVal().intValue(), sliderMinVal1);
        assertEquals(secondAssessment.getMinSliderText(), sliderMinText1);
        assertEquals(secondAssessment.getMaxSliderVal().intValue(), sliderMaxVal1);
        assertEquals(secondAssessment.getMaxSliderText(), sliderMaxText1);

        assertEquals(thirdAssessment.getQuestionText(), question3);
        assertEquals(thirdAssessment.getChoices(), choices2);

        assertNull(flowManager.getCurrentAssessment());
        assertNull(flowManager.getCurrentAssessment());
    }
}