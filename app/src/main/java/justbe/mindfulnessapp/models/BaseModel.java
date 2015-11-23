package justbe.mindfulnessapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import justbe.mindfulnessapp.rest.ResponseMeta;
import justbe.mindfulnessapp.rest.UserDataError;

/**
 * Created by eddiehurtig on 11/20/15.
 */
public abstract class BaseModel<T> {

    private String resource_uri;

    public Boolean isValid() {
        return null;
    }

    public String getResource_uri() {
        return resource_uri;
    }

    public void setResource_uri(String resource_uri) {
        this.resource_uri = resource_uri;
    }

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
