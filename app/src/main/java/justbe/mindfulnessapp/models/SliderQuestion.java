package justbe.mindfulnessapp.models;

import justbe.mindfulnessapp.SliderAssessmentActivity;

public class SliderQuestion extends AssessmentQuestion {
    /**
     * Fields
     */
    private String questionText;
    private Integer minSliderVal;
    private String minSliderText;
    private Integer maxSliderVal;
    private String maxSliderText;

    public SliderQuestion(String questionText, Integer minSliderVal, String minSliderText,
                          Integer maxSliderVal, String maxSliderText) {
        this.questionText = questionText;
        this.minSliderVal = minSliderVal;
        this.minSliderText = minSliderText;
        this.maxSliderVal = maxSliderVal;
        this.maxSliderText = maxSliderText;
    }

    public Class getQuestionActivity() { return SliderAssessmentActivity.class; }
    public String getQuestionText() { return this.questionText; }
    public Integer getMinSliderVal() { return this.minSliderVal; }
    public String getMinSliderText() { return this.minSliderText; }
    public Integer getMaxSliderVal() { return maxSliderVal; }
    public String getMaxSliderText() { return maxSliderText; }
}