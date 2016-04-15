package justbe.mindfulness;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.util.Log;
import android.support.v7.widget.Toolbar;
import java.util.ArrayList;
import java.util.List;
import justbe.mindfulness.models.Assessment;
import justbe.mindfulness.models.Response;
import justbe.mindfulness.models.User;

/**
 * Created by Nikhil Reddy on 13-04-2016.
 * Blank Activity to wait for all the responses to be saved on the database
 */
public class ResponseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private Context context;
    private User user;
    private List<Response> responses = new ArrayList<Response>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_response);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.title_activity_response));

        responses = AssessmentFlowManager.getResponses();

        /* This is where responses should be saved to the database
         * Show progress dialog until all responses have been stored in database
         * Create 'Storing Responses' progress spinner
         */
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.storingResponses));
        progressDialog.setMessage(getString(R.string.pleaseWait));
        progressDialog.setCancelable(false);
        progressDialog.show();

        Thread pushResponsesThread = new Thread(new Runnable() {
            @Override
            public void run() {
                User user = App.getSession().getUser();
                Assessment updated_assessment = ServerRequests.updateAssessmentWithCompleteTime(AssessmentFlowManager.getInstance(context).getAssessmentID(), context);
                Log.v("Assessment", "Before submission");
                Boolean response_success = ServerRequests.submitUserAssessment(responses, context);
                if (response_success) {
                    Log.v("Assessment", "After submission");

                    if (user.getId() == null) {
                        Log.v("End Assessment", "Session user is null");
                        SessionManager sessionManager = new SessionManager(context.getApplicationContext());
                        user = sessionManager.getUser();
                        App.getSession().setUser(user);
                        Log.v("End Assessment", "Session user from session manager" + user.getId());
                    }
                    System.out.println("Assessment completed for User: " + user.getId());
                    AssessmentFlowManager.emptyResponses();
                    // Go back to the main activity when done
                    Intent intent = new Intent(ResponseActivity.this, MainActivity.class);
                    intent.putExtra("source", "assessment");
                    intent.putExtra("userWeek", user.getCurrent_week());
                    startActivity(intent);
                } else {
                    progressDialog.dismiss();
                }
            }
        });
        pushResponsesThread.start();
    }
}
