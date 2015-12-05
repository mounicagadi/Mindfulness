package justbe.mindfulnessapp.models;

/**
 * Created by eddiehurtig on 11/30/15.
 */
public class MediationSession extends PlainOldDBO<MediationSession> {
    private Integer meditation_id;
    private Double percent_completed;
    private String user;

    public Integer getMeditation_id() {
        return meditation_id;
    }

    public void setMeditation_id(Integer meditation_id) {
        this.meditation_id = meditation_id;
    }

    public Double getPercent_completed() {
        return percent_completed;
    }

    public void setPercent_completed(Double percent_completed) {
        this.percent_completed = percent_completed;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
