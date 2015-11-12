package justbe.mindfulnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText username_field;
    private EditText password_field;
    private EditText confirm_password_field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(getString(R.string.title_activity_create_account));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        confirm_password_field = (EditText) findViewById(R.id.editPassword);
        confirm_password_field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EditText confirm_password_field = (EditText) findViewById(R.id.editConfirmPassword);
                confirm_password_field.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void createAccountPressed(View view) {
        if(samePassword()) {
            Intent intent = new Intent(this, GettingStartedActivity.class);
            startActivity(intent);
        } else {
            confirm_password_field = (EditText) findViewById(R.id.editConfirmPassword);
            confirm_password_field.setError("Passwords didn't match");
        }
    }

    private boolean samePassword() {
        password_field         = (EditText) findViewById(R.id.editPassword);
        confirm_password_field = (EditText) findViewById(R.id.editConfirmPassword);
        return password_field.getText().toString().equals(confirm_password_field.getText().toString());
    }
}
