package justbe.mindfulness.models;

public class Response extends PlainOldDBO<Response> {
    /**
     * Feilds
     */
    private Integer assessment_id;
    private Integer type;
    private Boolean _boolean;
    private String number;
    private Integer emotion;
    private Float percent;
    private Integer question_id;
    private String question;

    /**
     * Empty constructor for Response
     */
    public Response() { }

    /**
     * Constructor for number Responses
     * @param question_id The local question foreign key
     * @param assessment_id The assessment this response belongs to
     * @param number The value of the response
     */
     public Response(Integer question_id, Integer assessment_id,String number, int type) {
        this.question_id = question_id;
        this.assessment_id = assessment_id;
        this.number = number;
        this._boolean = false;
        this.emotion = 0;
        this.percent = 0f;
        // Set the type to number response
        this.type = type;
    }


    public Response(Integer question_id, Integer assessment_id,Integer emotion,int type) {
        this.question_id = question_id;
        this.assessment_id = assessment_id;
        this.emotion = emotion;
        this.number = "0";
        this._boolean = false;
        this.percent = 0f;

        // Set the type to number response
        this.type = type;
    }

    public Response(Integer question_id, Integer assessment_id,Float percent,int type) {
        this.question_id = question_id;
        this.assessment_id = assessment_id;
        this.percent = percent;
        this.emotion = 0;
        this.number = "0";
        this._boolean = false;
        // Set the type to number response
        this.type = type;
    }

    public Response(Integer question_id, Integer assessment_id,Boolean _boolean,int type) {
        this.question_id = question_id;
        this.assessment_id = assessment_id;
        this._boolean = _boolean;
        this.emotion = 0;
        this.number = "0";
        this.percent = 0f;
        // Set the type to number response
        this.type =type;
    }

    /**
     * Get/Set the assessmentID
     */
    public Integer getAssessment_id() {
        return assessment_id;
    }
    public void setAssessment_id(Integer assessment_id) {
        this.assessment_id = assessment_id;
    }


    /**
     * Get/Set the type
     */
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) { this.type = type; }

    /**
     * Get/Set the boolean
     */
    public Boolean get_boolean() { return _boolean; }
    public void set_boolean(Boolean _boolean) { this._boolean = _boolean; }

    /**
     * Get/Set the number
     */
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    /**
     * Get/Set the emotion
     */
    public Integer getEmotion() {
        return emotion;
    }
    public void setEmotion(Integer emotion) { this.emotion = emotion; }

    /**
     * get/Set the questionID
     */
    public Integer getQuestion_id() {
        return question_id;
    }
    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }

    /**
     * Get/Set the percent
     */
    public Float getPercent() {
        return percent;
    }
    public void setPercent(Float percent) { this.percent = percent; }

    /*public String getQuestion() {return question; }
    public void setQuestion(String question) {this.question = question;}*/

    @Override
    public String toString() {
        return super.toString();
    }
}
