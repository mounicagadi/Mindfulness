package justbe.mindfulnessapp.models;

/**
 * Model for representing Exercise Sessions.
 * API: exercise_session
 */
public class ExerciseSession extends PlainOldDBO<ExerciseSession> {
    private Integer exercise_id;

    public Integer getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(Integer exercise_id) {
        this.exercise_id = exercise_id;
    }
}
