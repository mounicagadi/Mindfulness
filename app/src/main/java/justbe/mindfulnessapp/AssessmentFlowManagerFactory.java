package justbe.mindfulnessapp;


import android.content.Context;

import java.util.Arrays;

import justbe.mindfulnessapp.models.DropdownQuestion;
import justbe.mindfulnessapp.models.MultiChoiceQuestion;
import justbe.mindfulnessapp.models.SliderQuestion;

/**
 * Factory class used to add assessments to the AssessmentFlowManager
 * Contains a number of methods to make adding assessments easier
 * Currently contains the hardcoded surveys
 * Future groups will need to extend this to examine some database input and create
 * the questions from that.
 */
public class AssessmentFlowManagerFactory {
    /**
     * Fields
     */
    private final Context context;
    private AssessmentFlowManager flowManager;

    /**
     * AssessmentFlowManagerFactory Constructor
     * @param context The view that the flow starts from
     */
    public AssessmentFlowManagerFactory(Context context) {
        this.context = context;
        this.flowManager = AssessmentFlowManager.getInstance(context);
    }

    /**
     * Adds a Dropdown Question to the manager
     * @param questionText The dropdown question text
     * @param choices The dropdown question choices
     */
    public void addDropdownQuestionToManager(String questionText, String[] choices) {
        DropdownQuestion dropdownAssessment = new DropdownQuestion(questionText, choices);
        flowManager.addAssessment(dropdownAssessment);
    }

    /**
     * Adds a Slider Question to the manager
     * @param quesitionText The slider question text
     * @param minSliderVal The slider minimum value
     * @param minSliderText The slider minimum text
     * @param maxSliderVal The slider maximum value
     * @param maxSliderText The slider maximum text
     */
    public void addSliderQuestionToManager(String quesitionText, int minSliderVal, String minSliderText,
                                           int maxSliderVal, String maxSliderText) {
        SliderQuestion sliderAssessment = new SliderQuestion(quesitionText, minSliderVal, minSliderText,
                maxSliderVal, maxSliderText);
        flowManager.addAssessment(sliderAssessment);
    }

    /**
     * Adds a Multi Choice Question to the manager
     * @param questionText The multi choice question text
     * @param listOptions The multi choice question list options
     */
    public void addMultiChoiceQuestionToManager(String questionText, String[] listOptions) {
        MultiChoiceQuestion multiChoiceQuestion = new MultiChoiceQuestion(questionText, listOptions);
        flowManager.addAssessment(multiChoiceQuestion);
    }

    /**
     * Adds all the questions to make the morning survey flow
     * Hardcoded to a unfinished spec from our client
     */
    public void addMorningAssessmentQuestions() {
        // Life Satisfaction
        addSliderQuestionToManager(context.getString(R.string.lifeQuestion),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));

        // Leisure
        addDropdownQuestionToManager(context.getString(R.string.leisureQuestion),
                context.getResources().getStringArray(R.array.HRS16));

        // Sleep
        addDropdownQuestionToManager(context.getString(R.string.sleepQuestion1),
                context.getResources().getStringArray(R.array.HRS16));

        addSliderQuestionToManager(context.getString(R.string.sleepQuestion2),
                0, context.getString(R.string.veryTired), 10, context.getString(R.string.veryRested));

        addSliderQuestionToManager(context.getString(R.string.sleepQuestion3),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));

        // Drinking
        addDropdownQuestionToManager(context.getString(R.string.drinkingQuestion),
                context.getResources().getStringArray(R.array.N16));

        // Smoking
        int[] nums = new int[41];
        for (int i = 0; i <= 40; i++) { nums[i] = i; }
        String[] H40 = Arrays.toString(nums).split("[\\[\\]]")[1].split(", ");
        addDropdownQuestionToManager(context.getString(R.string.smokingQuestion), H40);

        // Physical Activity
        addDropdownQuestionToManager(context.getString(R.string.physicalQuestion1),
                context.getResources().getStringArray(R.array.HALF5));

        addDropdownQuestionToManager(context.getString(R.string.physicalQuestion2),
                context.getResources().getStringArray(R.array.HALF5));

        addDropdownQuestionToManager(context.getString(R.string.physicalQuestion3),
                context.getResources().getStringArray(R.array.HALF5));

        // Symptoms
        addSliderQuestionToManager(context.getString(R.string.symptomsQuestion1),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.veryMuch));

        addSliderQuestionToManager(context.getString(R.string.symptomsQuestion2),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.veryMuch));

        // Social
        addDropdownQuestionToManager(context.getString(R.string.socialConnectednessQuestion1),
                context.getResources().getStringArray(R.array.HRS12));

        addSliderQuestionToManager(context.getString(R.string.socialConnectednessQuestion2),
                0, context.getString(R.string.notAtAll), 10,context.getString(R.string.veryMuch));

        addSliderQuestionToManager(context.getString(R.string.socialConnectednessQuestion3),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.veryMuch));

        // Stress Management
        addMultiChoiceQuestionToManager(context.getString(R.string.stressManagementQuestion),
                context.getResources().getStringArray(R.array.stressManagementChoices));
    }

    /**
     * Adds all the questions to make the day survey flow
     * Hardcoded to a unfinished spec from our client
     */
    public void addDayAssessmentQuestions() {
        // Emotions
        addEmotionQuestions();

        // Stress
        addSliderQuestionToManager(context.getString(R.string.stressQuestion),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));

        // Rumination
        addSliderQuestionToManager(context.getString(R.string.ruminationQuestion1),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));

        addSliderQuestionToManager(context.getString(R.string.ruminationQuestion2),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));

        addSliderQuestionToManager(context.getString(R.string.ruminationQuestion3),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));

        // Self-Compassion
        addSliderQuestionToManager(context.getString(R.string.selfCompassionQuestion1),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));

        addSliderQuestionToManager(context.getString(R.string.selfCompassionQuestion2),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));

        // Mindfulness
        addMindfulnessQuestions();
    }

    /**
     * Adds the emotion assessment questions
     * Hardcoded to a unfinished spec from our client
     */
    public void addEmotionQuestions() {
        String iFeel = context.getString(R.string.iFeel) + " %s.";
        // Angry
        addSliderQuestionToManager(String.format(iFeel, context.getString(R.string.angry)),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));

        // Sad
        addSliderQuestionToManager(String.format(iFeel, context.getString(R.string.sad)),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));

        // Depressed
        addSliderQuestionToManager(String.format(iFeel, context.getString(R.string.depressed)),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));

        // Nervous
        addSliderQuestionToManager(String.format(iFeel, context.getString(R.string.nervous)),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));

        // Happy
        addSliderQuestionToManager(String.format(iFeel, context.getString(R.string.happy)),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));

        // Relaxed
        addSliderQuestionToManager(String.format(iFeel, context.getString(R.string.relaxed)),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));

        // Content
        addSliderQuestionToManager(String.format(iFeel, context.getString(R.string.content)),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));

        // Energetic
        addSliderQuestionToManager(String.format(iFeel, context.getString(R.string.energetic)),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));

        // Bored
        addSliderQuestionToManager(String.format(iFeel, context.getString(R.string.bored)),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));

        // Lonely
        addSliderQuestionToManager(String.format(iFeel, context.getString(R.string.lonely)),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));
    }

    /**
     * Adds the mindfulness assessment questions
     * Hardcoded to a unfinished spec from our client
     */
    public void addMindfulnessQuestions() {
        addSliderQuestionToManager(context.getString(R.string.mindfulnessQuestion1),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));

        addSliderQuestionToManager(context.getString(R.string.mindfulnessQuestion2),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));

        addSliderQuestionToManager(context.getString(R.string.mindfulnessQuestion3),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));

        addSliderQuestionToManager(context.getString(R.string.mindfulnessQuestion4),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));

        addSliderQuestionToManager(context.getString(R.string.mindfulnessQuestion5),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));

        addSliderQuestionToManager(context.getString(R.string.mindfulnessQuestion6),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));

        addSliderQuestionToManager(context.getString(R.string.mindfulnessQuestion7),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));

        addSliderQuestionToManager(context.getString(R.string.mindfulnessQuestion8),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));

        addSliderQuestionToManager(context.getString(R.string.mindfulnessQuestion9),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));

        addSliderQuestionToManager(context.getString(R.string.mindfulnessQuestion10),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));

        addSliderQuestionToManager(context.getString(R.string.mindfulnessQuestion11),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));

        addSliderQuestionToManager(context.getString(R.string.mindfulnessQuestion12),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));

        addSliderQuestionToManager(context.getString(R.string.mindfulnessQuestion13),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));

        addSliderQuestionToManager(context.getString(R.string.mindfulnessQuestion14),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));

        addSliderQuestionToManager(context.getString(R.string.mindfulnessQuestion15),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));

        addSliderQuestionToManager(context.getString(R.string.mindfulnessQuestion16),
                0, context.getString(R.string.notAtAll), 10, context.getString(R.string.extremely));
    }
}
