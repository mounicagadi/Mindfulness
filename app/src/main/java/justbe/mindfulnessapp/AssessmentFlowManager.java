package justbe.mindfulnessapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.LinkedList;
import java.util.Queue;

import justbe.mindfulnessapp.models.AssessmentQuestion;

/**
 * Singleton that contains and runs the assessments
 * Assessments are added to the manager via the AssessmentFlowManagerFactory
 * Flow is started by calling startNextAssessmentQuestion() for the first time
 * The manager will continue to display assessments till there are no more
 */
public final class AssessmentFlowManager {

    /**
     * Makes this manager a singleton
     * @param context The context
     * @return The singleton instance of this manager
     */
    public static synchronized AssessmentFlowManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new AssessmentFlowManager(context);
        }
        return sInstance;
    }

    /**
     * Fields
     */
    private static AssessmentFlowManager sInstance;
    private AssessmentQuestion currentAssessment;
    private Queue<AssessmentQuestion> assessments = new LinkedList<AssessmentQuestion>();
    private Context context;

    /**
     * AssessmentFlowManager constructor
     * @param context The context of the view the manager was created in
     */
    public AssessmentFlowManager(Context context) {
        this.context = context;
    }

    /**
     * Sets the context
     * @param context the new context
     */
    public void setContext(Context context) {this.context = context; }

    /**
     * Gets the current assessment
     * @return the current assessment
     */
    public AssessmentQuestion getCurrentAssessment() { return this.currentAssessment; }

    /**
     * Sets the current assessment question
     * DO NOT USE OTHER THAN IN TESTS
     * @param assessment The new assessment question
     */
    public void setCurrentAssessment(AssessmentQuestion assessment) { this.currentAssessment = assessment; }

    /**
     * Gets the assessments
     * @return Assessments currently added to the flow
     */
    public Queue<AssessmentQuestion> getAssessments() { return this.assessments; }

    /**
     * Adds a assessment to the queue
     * @param newAssessment the new assessment
     */
    public void addAssessment(AssessmentQuestion newAssessment) { assessments.add(newAssessment); }

    /**
     * Starts the next assessment question provided there is one.
     * Otherwise goes back to the MainActivity
     */
    public void startNextAssessmentQuestion() {
        // If there are more assessments then the next one
        if ( !assessments.isEmpty() ) {
            currentAssessment = assessments.remove();

            Intent intent = new Intent();
            intent.setClass(context, currentAssessment.getQuestionActivity());
            context.startActivity(intent);
            ((Activity) context).finish();
        } else {
            // Go back to the main activity when done
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
            ((Activity) context).finish();
        }
    }
}
