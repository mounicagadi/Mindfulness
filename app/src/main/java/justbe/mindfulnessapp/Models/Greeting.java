package justbe.mindfulnessapp.models;

/**
 * Created by eddiehurtig on 11/21/15.
 */

public class Greeting extends BaseModel {

    private String id;
    private String content;

    public String getId() {
        return this.id;
    }

    public String getContent() {
        return this.content;
    }

}
