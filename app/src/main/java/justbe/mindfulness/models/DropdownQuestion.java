package justbe.mindfulness.models;

import justbe.mindfulness.DropdownAssessmentActivity;

/**
 * Holds metadata for the DropdownAssessmentActivity
 */
public class DropdownQuestion extends AssessmentQuestion  {
    /**
     * Fields
     */
    private Class questionActivity;
    private String questionText;
    private String[] dropdownOptions;

    /**
     * DropdownQuestion Constructor
     * @param questionText text to display on the assessment
     * @param dropdownOptions options to display on the dropdown
     */
    public DropdownQuestion(String questionText, String[] dropdownOptions) {
        this.questionText = questionText;
        this.dropdownOptions = dropdownOptions;
    }

    /**
     * Gets this question's activity
     * @return The DropdownAssessmentActivity class
     */
    public Class getQuestionActivity() { return DropdownAssessmentActivity.class; }

    /**
     *  Get the question text
     * @return The question text
     */
    public String getQuestionText() { return this.questionText; }

    /**
     * Get the dropdown options
     * @return the dropdown options
     */
    public String[] getDropdownOptions() { return this.dropdownOptions; }
}
