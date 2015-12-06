package justbe.mindfulnessapp.models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by eddiehurtig on 12/5/15.
 */
public class Assessment extends PlainOldDBO<Assessment> {
    private Date complete_time;
    private ArrayList<Integer> responses; // No idea if this is correct
    private Date start_time;
    private String user;

    public Date getComplete_time() {
        return complete_time;
    }

    public void setComplete_time(Date complete_time) {
        this.complete_time = complete_time;
    }

    public ArrayList<Integer> getResponses() {
        return responses;
    }

    public void setResponses(ArrayList<Integer> responses) {
        this.responses = responses;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
