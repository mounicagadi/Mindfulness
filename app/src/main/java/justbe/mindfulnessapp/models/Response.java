package justbe.mindfulnessapp.models;

import java.util.ArrayList;

/**
 * Created by eddiehurtig on 12/6/15.
 */
public class Response extends PlainOldDBO<Response> {

    public Integer getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }

    public Boolean get_boolean() {
        return _boolean;
    }

    public void set_boolean(Boolean _boolean) {
        this._boolean = _boolean;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAssessment_id() {
        return assessment_id;
    }

    public void setAssessment_id(Integer assessment_id) {
        this.assessment_id = assessment_id;
    }

    public ArrayList<Selection> getMulti_selects() {
        return multi_selects;
    }

    public void setMulti_selects(ArrayList<Selection> multi_selects) {
        this.multi_selects = multi_selects;
    }

    private Integer question_id;
    private Boolean _boolean;
    private Integer type;
    private Integer assessment_id;
    private ArrayList<Selection> multi_selects;

    public class Selection {
        private Integer selection_id;

        public Integer getSelection_id() {
            return selection_id;
        }

        public void setSelection_id(Integer selection_id) {
            this.selection_id = selection_id;
        }
    }
}
