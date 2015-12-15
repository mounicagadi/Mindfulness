package justbe.mindfulness.models;

/**
 * Abstract class for any assessment question
 * The only element that ALL assessment questions must have is a way to return their
 * activity class.
 */
public abstract class AssessmentQuestion {
    /**
     * Returns the class of the assessment activity.
     * @return the assessment question's activity class
     */
    public abstract Class getQuestionActivity();
}
