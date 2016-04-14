package justbe.mindfulness;

/**
 * Created by mounica on 4/6/2016.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import justbe.mindfulness.models.User;
import justbe.mindfulness.models.UserProfile;


public class ThankYouActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyou_program);

        Toolbar myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Epilogue");
    }

    public void closeButtonPressed(View view){

        User user = App.getSession().getUser();
        UserProfile profile = new UserProfile(user);

        ServerRequests.updateUserWithUserProfile(user, profile, getApplicationContext());

        Intent intent = new Intent(ThankYouActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
