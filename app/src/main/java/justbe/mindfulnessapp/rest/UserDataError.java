package justbe.mindfulnessapp.rest;

/**
 * Describes an error in the user's data entry as specified by a response from the django API
 *
 * <p>The django API returns responses to requests with data validation errors like so
 * <code>
 *     {"error": {"message": "That email is already used.", "code": "duplicate_exception"}}
 * </code>
 *
 * This Class is expected to be a member of the {@link ResponseWrapper} class
 * </p>
 *
 * @author edhurtig
 */
public class UserDataError extends UserPresentableException {
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