package justbe.mindfulness;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import justbe.mindfulness.models.Assessment;
import justbe.mindfulness.models.AssessmentQuestion;
import justbe.mindfulness.models.Response;
import justbe.mindfulness.models.User;

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
    private Integer assessmentID;
    private Integer questionID;
    private List<Response> responses = new ArrayList<Response>();

    /**
     * AssessmentFlowManager constructor
     * @param context The context of the view the manager was created in
     */
    public AssessmentFlowManager(Context context) {
        this.context = context;
        questionID = 0;
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
     * @param newAssessment The new Assessment
     */
    public void addAssessment(AssessmentQuestion newAssessment) { assessments.add(newAssessment); }

    /**
     * Gets the current assessment ID
     * @return The assessmentID
     */
    public Integer getAssessmentID() { return assessmentID; }

    /**
     * Sets the assessmentID for this assessment
     * @param assessmentID
     */
    public void setAssessmentID(Integer assessmentID) { this.assessmentID = assessmentID; }

    /**
     * Gets the Question ID and increment it by one
     * @return The question ID
     */
    public Integer getQuestionID() {
        Integer oldQuestionID = questionID;
        questionID = oldQuestionID + 1;
        return oldQuestionID;
    }

    /**
     * Adds a response to the queue
     * @param response The new Response
     */
    public void addResponse(Response response) { responses.add(response); }

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
            // Set questionID back to zero for next assessment
            questionID = 0;

            // This is where responses should be saved to the database
            User user  = App.getSession().getUser();
            Assessment updated_assessment = ServerRequests.updateAssessmentWithCompleteTime(sInstance.getAssessmentID(), context);
            //ServerRequests.submitUserAssessment(user, responses, context);
            if(null == user){
                Log.v("Start Assessment","Session user is null");
                SessionManager sessionManager = new SessionManager(context.getApplicationContext());
                user = sessionManager.getUser();
                App.getSession().setUser(user);
                Log.v("Start Assessment", "Session user from session manager"+user.getId());
            }
            System.out.println("Assessment completed for User: " + user.getId());
            // Go back to the main activity when done
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("source","assessment");
            intent.putExtra("userWeek",user.getCurrent_week());
            context.startActivity(intent);
            ((Activity) context).finish();
        }
    }
}
