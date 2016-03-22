package justbe.mindfulness.models;

/**
 * Created by amit on 15-03-2016.
 */
public class Exercise {

    private String exerciseName;
    private String exerciseContent;

    public Exercise(){

    }

    public Exercise(String exerciseName, String exerciseContent) {
        this.exerciseName = exerciseName;
        this.exerciseContent = exerciseContent;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public String getExerciseContent() {
        return exerciseContent;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public void setExerciseContent(String exerciseContent) {
        this.exerciseContent = exerciseContent;
    }
}
