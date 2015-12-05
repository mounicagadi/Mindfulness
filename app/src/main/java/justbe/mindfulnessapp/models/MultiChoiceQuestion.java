package justbe.mindfulnessapp.models;

import justbe.mindfulnessapp.MultiChoiceAssessmentActivity;

/**
 * Holds metadata for the MuliChoiceAssessmentActivity
 */
public class MultiChoiceQuestion extends AssessmentQuestion {
    /**
     * Fields
     */
    private String questionText;
    private String[] choices;

    /**
     * MultiChoiceQuestion Constructor
     * @param questionText The text for the question
     * @param choices The different choices on the list view
     */
    public MultiChoiceQuestion(String questionText, String[] choices) {
        this.questionText = questionText;
        this.choices = choices;
    }

    /**
     * Gets this question's activity
     * @return the MutliChoiceAssessmentActivity class
     */
    public Class getQuestionActivity() { return MultiChoiceAssessmentActivity.class; }

    /**
     *  Gets the current question text
     * @return the question text
     */
    public String getQuestionText() { return this.questionText; }

    /**
     * Gets the question choices
     * @return the question choices
     */
    public String[] getChoices() { return this.choices; }
}
