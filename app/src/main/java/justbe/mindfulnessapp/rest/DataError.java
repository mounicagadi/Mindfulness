package justbe.mindfulnessapp.rest;

/**
 * Created by eddiehurtig on 11/21/15.
 */
public class DataError extends RuntimeException {
    private String message;
    private String code;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}