package justbe.mindfulness.models;


/**
 * Model for representing daily Meditation Sessions.
 * API: meditation_session
 */
public class MeditationSession extends PlainOldDBO<MeditationSession> {
    private Integer meditation_id;
    private Double percent_completed;

    public Integer getMeditation_id() {
        return meditation_id;
    }

    public void setMeditation_id(Integer week, Integer day) {
        this.meditation_id = convertToMeditationId(week, day);
    }

    // id is in form of week followed by day i.e. week 2 day 5, id = 25
    public Integer convertToMeditationId(Integer week, Integer day) {
        return week * 10 + day;
    }

    public Double getPercent_completed() {
        return percent_completed;
    }

    public void setPercent_completed(Double percent_completed) {
        this.percent_completed = percent_completed;
    }
}
