package justbe.mindfulnessapp.models;

import java.util.ArrayList;
import java.util.Date;

public class Assessment extends PlainOldDBO<Assessment> {
    /**
     * Fields
     */
    private Date complete_time;
    private ArrayList<Response> responses; // No idea if this is correct
    private Date start_time;
    private String user;

    /**
     * Gets the time the assessment was completed at
     * @return the completion time
     */
    public Date getComplete_time() {
        return complete_time;
    }

    /**
     * Set sets the time the assessment was completed at
     * @param complete_time The time the assessment was completed
     */
    public void setComplete_time(Date complete_time) {
        this.complete_time = complete_time;
    }

    /**
     * Get the list of responses
     * @return The current responses
     */
     public ArrayList<Response> getResponses() { return responses; }


    /**
     * Sets the responses
     * @param responses The new responses
     */
    public void setResponses(ArrayList<Response> responses) { this.responses = responses; }

    /**
     * The time the assessment was started at
     * @return The start time for the assessment
     */
    public Date getStart_time() {
        return start_time;
    }

    /**
     * Sets the time the assessment was started at
     * @param start_time The new start time
     */
    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    /**
     * Gets the user who is filling out this assessment
     * @return The user
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the user who is filling out this assessment
     * @param user The new user
     */
    public void setUser(String user) {
        this.user = user;
    }
}
