package justbe.mindfulnessapp;

/**
 * Created by eddiehurtig on 11/19/15.
 */
public class ResponseWrapper <T> {
    T response;

    public T getResponse () { return response; }
    public void setResponse(T response) {this.response = response;}
}