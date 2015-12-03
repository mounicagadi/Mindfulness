package justbe.mindfulnessapp.rest;

import android.app.Application;

import justbe.mindfulnessapp.App;
import justbe.mindfulnessapp.R;

/**
 * Describes an error in the user's data entry as specified by a response from the django API
 *
 * <p>The django API returns responses to requests with data validation errors like so
 * <code>
 *     {"error": {"message": "That email is already used.", "code": "duplicate_exception"}}
 * </code>
 * </p>
 *
 * @author edhurtig
 */
public class UserDataError extends UserPresentableException {

    /**
     * Sets a default title for the base UserPresentableException
     */
    public UserDataError() {
        this.setTitle("Let's try that again");
    }

    /**
     * The django error code that is returned when a request fails validation
     */
    private String code;

    /**
     * Gets the django error code that is returned when a request fails validation
     * @return The django error code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the django error code that is returned when a request fails validation
     * @param code The django error code
     */
    public void setCode(String code) {
        this.code = code;
    }
}