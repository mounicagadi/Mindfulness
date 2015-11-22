package justbe.mindfulnessapp.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * High level class to model responses from django
 *
 * <p>
 *     This class is required for a lot of reasons... so don't muck with it!
 *
 *     1. This class is first and foremost a wrapper class for an object model... T.  T can be a
 *        User model, Meditation Model, ect.  This wrapper class is required because of a very
 *        complicated but very powerful reflection and generic type process used in
 *        {@link GenericHttpRequestTask}.
 *
 *     2. This class provides fields and functions for some of the common fields found in API
 *        responses.  This allows the ObjectMapper to map these fields.
 *
 *     3. Because we are _forced_ to use a wrapper, this tool will not work when the API returns an
 *        Object in the root of the JSON, which is a rare and less useful case unless looking at a
 *        single resource
 * </p>
 * @param <T>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseWrapper <T> {

    /**
     * The list of objects returned by the api
     */
    private T[] objects;

    /**
     * Metadata returned in the response
     */
    private ResponseMeta meta;

    /**
     * A Python Error Message (Normally the prequel to a stacktrace)
     */
    private String error_message;

    /**
     * A python stacktrace
     */
    private String traceback;

    /**
     * A predicatable error that is normally resolvable by the user
     */
    private UserDataError error;


    /**
     * Looks at the entire request and determines the best error message to present
     * @return The best error message to present
     */
    public String getErrorMessage() {
        if (this.error != null) {
            return this.error.getMessage();
        } else if (this.error_message != null) {
            return this.error_message;
        } else {
            return null;
        }
    }

    /*******************
     * GETTERS/SETTERS *
     ******************/

    /**
     * Gets the objects
     * @return The Objects
     */
    public T[] getObjects() {
        return objects;
    }

    /**
     * Sets the objects
     * @param objects The new objects
     */
    public void setObjects(T[] objects) {
        this.objects = objects;
    }

    /**
     * Gets the metadata object
     * @return The metadata object
     */
    public ResponseMeta getMeta() { return meta; }

    /**
     * Sets the metadata object
     * @param meta The metadata object
     */
    public void setMeta(ResponseMeta meta) {
        this.meta = meta;
    }

    /**
     * Gets the python error message precluding a stacktrace
     * @return the python error message
     */
    public String getError_message() {
        return error_message;
    }

    /**
     * Sets a python error message
     * @param error_message The error message
     */
    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    /**
     * Gets the traceback
     * @return The traceback
     */
    public String getTraceback() {
        return traceback;
    }

    /**
     * Sets the traceback
     * @param traceback The traceback
     */
    public void setTraceback(String traceback) {
        this.traceback = traceback;
    }


    /**
     * Gets the UserDataError
     * @return The UserDataError
     */
    public UserDataError getError() {
        return this.error;
    }

    /**
     * Sets the UserDataError message
     * @param error the UserDataErrorMessage
     */
    public void setError(UserDataError error) {
        this.error = error;
    }
}