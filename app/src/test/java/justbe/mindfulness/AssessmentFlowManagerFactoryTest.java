package justbe.mindfulness;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Queue;

import justbe.mindfulness.models.AssessmentQuestion;
import justbe.mindfulness.models.DropdownQuestion;
import justbe.mindfulness.models.MultiChoiceQuestion;
import justbe.mindfulness.models.SliderQuestion;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class AssessmentFlowManagerFactoryTest {

    @Mock
    Context mMockContext;
    private AssessmentFlowManager flowManager;
    private AssessmentFlowManagerFactory flowManagerFactory;

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

    @Before
    public void setupTests() {
        flowManager = AssessmentFlowManager.getInstance(mMockContext);
        flowManagerFactory = new AssessmentFlowManagerFactory(mMockContext);
    }

    @Test
    public void testAddDropdownQuestionToManager() throws Exception {
        assertNull(flowManager.getCurrentAssessment());

        flowManagerFactory.addDropdownQuestionToManager(question1, choices1);
        flowManagerFactory.addDropdownQuestionToManager(question2, choices2);
        assertNull(flowManager.getCurrentAssessment());

        Queue<AssessmentQuestion> assessments = flowManager.getAssessments();
        DropdownQuestion firstAssessment = (DropdownQuestion) assessments.remove();
        DropdownQuestion secondAssessment = (DropdownQuestion) assessments.remove();

        assertEquals(firstAssessment.getQuestionText(), question1);
        assertEquals(firstAssessment.getDropdownOptions(), choices1);
        assertEquals(secondAssessment.getQuestionText(), question2);
        assertEquals(secondAssessment.getDropdownOptions(), choices2);

        assertNull(flowManager.getCurrentAssessment());
        assertNull(flowManager.getCurrentAssessment());
    }

    @Test
    public void testAddSliderQuestionToManager() throws Exception {
        assertNull(flowManager.getCurrentAssessment());

        flowManagerFactory.addSliderQuestionToManager(question1, sliderMinVal1, sliderMinText1,
                sliderMaxVal1, sliderMaxText1);
        flowManagerFactory.addSliderQuestionToManager(question2, sliderMinVal2, sliderMinText2,
                sliderMaxVal2, sliderMaxText2);
        assertNull(flowManager.getCurrentAssessment());

        Queue<AssessmentQuestion> assessments = flowManager.getAssessments();
        SliderQuestion firstAssessment = (SliderQuestion) assessments.remove();
        SliderQuestion secondAssessment = (SliderQuestion) assessments.remove();

        assertEquals(firstAssessment.getQuestionText(), question1);
        assertEquals(firstAssessment.getMinSliderVal().intValue(), sliderMinVal1);
        assertEquals(firstAssessment.getMinSliderText(), sliderMinText1);
        assertEquals(firstAssessment.getMaxSliderVal().intValue(), sliderMaxVal1);
        assertEquals(firstAssessment.getMaxSliderText(), sliderMaxText1);

        assertEquals(secondAssessment.getQuestionText(), question2);
        assertEquals(secondAssessment.getMinSliderVal().intValue(), sliderMinVal2);
        assertEquals(secondAssessment.getMinSliderText(), sliderMinText2);
        assertEquals(secondAssessment.getMaxSliderVal().intValue(), sliderMaxVal2);
        assertEquals(secondAssessment.getMaxSliderText(), sliderMaxText2);

        assertNull(flowManager.getCurrentAssessment());
        assertNull(flowManager.getCurrentAssessment());
    }

    @Test
    public void testAddMultiChoiceQuestionToManager() throws Exception {
        assertNull(flowManager.getCurrentAssessment());

        flowManagerFactory.addMultiChoiceQuestionToManager(question1, choices1);
        flowManagerFactory.addMultiChoiceQuestionToManager(question2, choices2);
        assertNull(flowManager.getCurrentAssessment());

        Queue<AssessmentQuestion> assessments = flowManager.getAssessments();
        MultiChoiceQuestion firstAssessment = (MultiChoiceQuestion) assessments.remove();
        MultiChoiceQuestion secondAssessment = (MultiChoiceQuestion) assessments.remove();

        assertEquals(firstAssessment.getQuestionText(), question1);
        assertEquals(firstAssessment.getChoices(), choices1);
        assertEquals(secondAssessment.getQuestionText(), question2);
        assertEquals(secondAssessment.getChoices(), choices2);

        assertNull(flowManager.getCurrentAssessment());
        assertNull(flowManager.getCurrentAssessment());
    }

    @Test
    public void testAddDifferentAssessments() throws Exception {
        assertNull(flowManager.getCurrentAssessment());

        flowManagerFactory.addDropdownQuestionToManager(question1, choices1);
        flowManagerFactory.addSliderQuestionToManager(question2, sliderMinVal2, sliderMinText2,
                sliderMaxVal2, sliderMaxText2);
        flowManagerFactory.addMultiChoiceQuestionToManager(question3, choices2);

        Queue<AssessmentQuestion> assessments = flowManager.getAssessments();
        DropdownQuestion firstAssessment = (DropdownQuestion) assessments.remove();
        SliderQuestion secondAssessment = (SliderQuestion) assessments.remove();
        MultiChoiceQuestion thirdAssessment = (MultiChoiceQuestion) assessments.remove();

        assertEquals(firstAssessment.getQuestionText(), question1);
        assertEquals(firstAssessment.getDropdownOptions(), choices1);

        assertEquals(secondAssessment.getQuestionText(), question2);
        assertEquals(secondAssessment.getMinSliderVal().intValue(), sliderMinVal2);
        assertEquals(secondAssessment.getMinSliderText(), sliderMinText2);
        assertEquals(secondAssessment.getMaxSliderVal().intValue(), sliderMaxVal2);
        assertEquals(secondAssessment.getMaxSliderText(), sliderMaxText2);

        assertEquals(thirdAssessment.getQuestionText(), question3);
        assertEquals(thirdAssessment.getChoices(), choices2);

        assertNull(flowManager.getCurrentAssessment());
        assertNull(flowManager.getCurrentAssessment());
    }
}