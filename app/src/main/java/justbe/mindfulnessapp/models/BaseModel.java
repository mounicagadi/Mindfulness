package justbe.mindfulnessapp.models;

/**
 * Created by eddiehurtig on 11/20/15.
 */
public abstract class BaseModel {

    private String resource_uri;

    public Boolean isValid() {
        return null;
    }

    public String getResource_uri() {
        return resource_uri;
    }

    public void setResource_uri(String resource_uri) {
        this.resource_uri = resource_uri;
    }
}
