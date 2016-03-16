package justbe.mindfulness.models;

/**
 * Created by amit on 15-03-2016.
 */
public class Excercise {

    private String excerciseName;
    private String excerciseContent;

    public Excercise(String excerciseName, String excerciseContent) {
        this.excerciseName = excerciseName;
        this.excerciseContent = excerciseContent;
    }

    public String getExcerciseName() {
        return excerciseName;
    }

    public String getExcerciseContent() {
        return excerciseContent;
    }

    public void setExcerciseName(String excerciseName) {
        this.excerciseName = excerciseName;
    }

    public void setExcerciseContent(String excerciseContent) {
        this.excerciseContent = excerciseContent;
    }
}
