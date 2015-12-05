package justbe.mindfulnessapp.models;

import justbe.mindfulnessapp.MultiChoiceAssessment;

public class MultiChoiceQuestion extends AssessmentQuestion {
    /**
     * Fields
     */
    private Class questionActivity;
    private String questionText;
    private String[] choices;

    /**
     * MultiChoiceQuestion Constructor
     * @param questionText The text for the question
     * @param choices The different choices on the list view
     */
    public MultiChoiceQuestion(String questionText, String[] choices) {
        this.questionActivity = MultiChoiceAssessment.class;
        this.questionText = questionText;
        this.choices = choices;
    }

    public Class getQuestionActivity() { return this.questionActivity; }
    public String getQuestionText() { return this.questionText; }
    public String[] getChoices() { return this.choices; }
}
