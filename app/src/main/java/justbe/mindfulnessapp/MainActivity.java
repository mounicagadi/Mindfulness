package justbe.mindfulnessapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;

public class MainActivity extends AppCompatActivity {

    private PopupWindow pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        // Set toolbar view to custom toolbar for main view
        LayoutInflater li = LayoutInflater.from(this);
        View customToolbarView = li.inflate(R.layout.custom_main_toolbar, null);
        getSupportActionBar().setCustomView(customToolbarView);
    }

    public void preferencesButtonPressed(View view) {
        Intent intent = new Intent(this, PreferencesActivity.class);
        startActivity(intent);
    }

    public void weekButtonPressed(View view) {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View pw_view = inflater.inflate(R.layout.activity_check_progress_popup,
                    (ViewGroup) findViewById(R.id.checkProgressPopup));
            // TODO: Make this figure out its size better
            pw = new PopupWindow(pw_view, width-250, LayoutParams.WRAP_CONTENT, true);
            pw.showAtLocation(pw_view, Gravity.CENTER, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void lessonButtonPressed(View view) {
        Intent intent = new Intent(this, LessonActivity.class);
        startActivity(intent);
    }

    public void assessmentButtonPressed(View view) {
        Intent intent = new Intent(this, SleepAssessmentActivity.class);
        startActivity(intent);
    }
}
