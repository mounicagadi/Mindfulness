package justbe.mindfulnessapp;

import android.view.View;
import android.widget.EditText;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static android.support.test.espresso.intent.Checks.checkNotNull;

/**
 * Allows checking if a field has an error with hasErrorText
 */
public class ErrorTextMatcher extends TypeSafeMatcher<View> {
    private final String expectedError;

    private ErrorTextMatcher(String expectedError) {
        this.expectedError = checkNotNull(expectedError);
    }

    @Override
    public boolean matchesSafely(View view) {
        if (!(view instanceof EditText)) {
            return false;
        }
        EditText editText = (EditText) view;
        return expectedError.equals(editText.getError());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("with error: " + expectedError);
    }

    public static Matcher<? super View> hasErrorText(String expectedError) {
        return new ErrorTextMatcher(expectedError);
    }
}
