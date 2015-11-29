package justbe.mindfulnessapp.models;

/**
 * Created by eddiehurtig on 11/20/15.
 */
public class User extends BaseModel<User> {

    public enum Gender {
        MALE(0), FEMALE(1), OTHER(2);

        private final int value;
        private Gender(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private String email;
    private String first_name;
    private Integer id;
    private String last_name;
    private String username;
    private String raw_password;
    private String birthday;
    private String created_at;
    private String updated_at;
    private String start_date;
    private String lesson_start;
    private String meditation_time;
    private String wake_up_time;
    private String go_to_sleep_time;
    private Integer gender;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUsername() {
        return username;
    }

    public String getRaw_password() {
        return raw_password;
    }

    public void setRaw_password(String raw_password) {
        this.raw_password = raw_password;
    }

    public String getPassword() {
        return raw_password;
    }

    public void setPassword(String raw_password) {
        this.raw_password = raw_password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getLesson_start() { return lesson_start; }

    public void setLesson_start(String lesson_start) { this.lesson_start = lesson_start; }

    public String getMeditation_time() { return meditation_time; }

    public void setMeditation_time(String meditation_time) { this.meditation_time = meditation_time; }

    public String getWake_up_time() { return wake_up_time; }

    public void setWake_up_time(String wake_up_time) { this.wake_up_time = wake_up_time; }

    public String getGo_to_sleep_time() { return this.go_to_sleep_time; }

    public void setGo_to_sleep_time(String go_to_sleep_time) { this.go_to_sleep_time = go_to_sleep_time; }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
}
