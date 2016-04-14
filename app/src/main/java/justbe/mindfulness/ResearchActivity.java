package justbe.mindfulness;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import justbe.mindfulness.models.User;
import justbe.mindfulness.models.UserProfile;

public class ResearchActivity extends AppCompatActivity {

    private RadioGroup research_study_group;
    private Boolean research_study;
    private User user;
    private UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Research Study");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        research_study_group = (RadioGroup) findViewById(R.id.researchStudy);
        user = App.getSession().getUser();
        userProfile = new UserProfile(user);

    }

     /*
    * Radio button handler
    */

    public void onResearchStudyButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.yes:
                if (checked) {
                    research_study = true;
                    Log.v("Research Study",""+research_study);
                    break;
                }
            case R.id.no:
                if (checked) {
                    research_study = false;
                    Log.v("Research Study",""+research_study);
                    break;
                }
        }

        userProfile.setResearch_study(research_study);
        user.setResearch_study(research_study);
        ServerRequests.updateUserWithUserProfile(user, userProfile, getApplicationContext());
        user = App.getSession().getUser();

    }


    public void researchStudyNextButtonPressed(View view) {

        Intent intent = new Intent(ResearchActivity.this, SleepTimeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

}
