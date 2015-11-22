package justbe.mindfulnessapp.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by eddiehurtig on 11/19/15.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseWrapper <T> {
    private T[] objects;
    private APIMeta meta;

    private String error_message;
    private String traceback;
    private UserDataError error;

    public T[] getObjects() {
        return objects;
    }

    public void setObjects(T[] objects) {
        this.objects = objects;
    }

    public APIMeta getMeta() { return meta; }

    public void setMeta(APIMeta meta) {
        this.meta = meta;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public String getTraceback() {
        return traceback;
    }

    public void setTraceback(String traceback) {
        this.traceback = traceback;
    }

    public Boolean isOK() {
        return (this.error == null && this.error_message == null);
    }

    public String getErrorMessage() {
        if (this.error != null) {
            return this.error.getMessage();
        } else if (this.error_message != null) {
            return this.error_message;
        } else {
            return null;
        }
    }

    public void setError(UserDataError error) {
        this.error = error;
    }

    public UserDataError getError() {
        return this.error;
    }

    }
}