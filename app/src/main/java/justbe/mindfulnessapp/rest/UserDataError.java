package justbe.mindfulnessapp.rest;

/**
 * Created by eddiehurtig on 11/21/15.
 */
public class UserDataError extends UserPresentableException {
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}