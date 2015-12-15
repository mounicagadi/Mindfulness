package justbe.mindfulness.models;

import justbe.mindfulness.SliderAssessmentActivity;

/**
 * Holds metadata for SliderAssessmentActivity
 */
public class SliderQuestion extends AssessmentQuestion {
    /**
     * Fields
     */
    private String questionText;
    private Integer minSliderVal;
    private String minSliderText;
    private Integer maxSliderVal;
    private String maxSliderText;

    /**]
     * SliderQuestion constructor
     * @param questionText The text for the question
     * @param minSliderVal The min value for the slider
     * @param minSliderText The text to put at the min slider position
     * @param maxSliderVal The max value for the slider
     * @param maxSliderText The text to put at the max slider position
     */
    public SliderQuestion(String questionText, Integer minSliderVal, String minSliderText,
                          Integer maxSliderVal, String maxSliderText) {
        this.questionText = questionText;
        this.minSliderVal = minSliderVal;
        this.minSliderText = minSliderText;
        this.maxSliderVal = maxSliderVal;
        this.maxSliderText = maxSliderText;
    }

    /**
     * Gets this question's activity
     * @return the SliderAssessmentActivity class
     */
    public Class getQuestionActivity() { return SliderAssessmentActivity.class; }

    /**
     * Gets the current question text
     * @return the question text
     */
    public String getQuestionText() { return this.questionText; }

    /**
     * Gets the current minimum slider value
     * @return the minimum slider value
     */
    public Integer getMinSliderVal() { return this.minSliderVal; }

    /**
     *  Gets the current minimum slider text
     * @return the minimum slider text
     */
    public String getMinSliderText() { return this.minSliderText; }

    /**
     * Gets the current maximum slider value
     * @return the maximum slider value
     */
    public Integer getMaxSliderVal() { return maxSliderVal; }

    /**
     * Gets the current maximum slider text
     * @return the maximum slider text
     */
    public String getMaxSliderText() { return maxSliderText; }
}