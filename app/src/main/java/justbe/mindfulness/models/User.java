package justbe.mindfulness.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import justbe.mindfulness.rest.UserPresentableException;

/**
 * Created by eddiehurtig on 11/20/15.
 */
public class User extends PlainOldDBO<User> {

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

    public enum DayOfWeek {
        MO(0), TU(1), WE(2), TH(3), FR(4), SA(5), SU(6);

        private final int value;
        private DayOfWeek(int value) { this.value = value; }

        public int getValue() { return value; }
    }

    private String email;
    private String first_name;
    private String last_name;
    private String username;
    private String raw_password;
    private String birthday;
    private String start_date;
    private Integer exercise_day_of_week;
    private Date exercise_time;
    private Date meditation_time;
    private Date wake_up_time;
    private Date go_to_sleep_time;
    private Integer gender;
    private Integer current_week;
	private boolean research_study;

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

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public Integer getExercise_day_of_week() { return exercise_day_of_week; }

    public void setExercise_day_of_week(Integer exercise_day_of_week) { this.exercise_day_of_week = exercise_day_of_week; }

    public Date getExercise_time() { return exercise_time; }

    public void setExercise_time(String exercise_time) {
        DateFormat sdf = new SimpleDateFormat("hh:mm", java.util.Locale.getDefault());
        try {
            this.exercise_time = sdf.parse(exercise_time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Date getMeditation_time() { return meditation_time; }

    public void setMeditation_time(String meditation_time) {
        DateFormat sdf = new SimpleDateFormat("hh:mm", java.util.Locale.getDefault());
        try {
            this.meditation_time = sdf.parse(meditation_time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Date getWake_up_time() { return wake_up_time; }

    public void setWake_up_time(String wake_up_time) {
        DateFormat sdf = new SimpleDateFormat("hh:mm", java.util.Locale.getDefault());
        try {
            this.wake_up_time = sdf.parse(wake_up_time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Date getGo_to_sleep_time() { return this.go_to_sleep_time; }

    public void setGo_to_sleep_time(String go_to_sleep_time) {
        DateFormat sdf = new SimpleDateFormat("hh:mm", java.util.Locale.getDefault());
        try {
            this.go_to_sleep_time = sdf.parse(go_to_sleep_time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getCurrent_week() { return current_week; }

    public void setCurrent_week(Integer current_week) { this.current_week = current_week; }

	public boolean getResearch_study() {
        return research_study;
    }

    public void setResearch_study(boolean research_study) {
        this.research_study = research_study;
    }
    /**
     * Sets the data from a UserProfile to this user's data
     * @param userProfile
     */
    public void addUserProfileData(UserProfile userProfile) {
        setBirthday(userProfile.getBirthday());
        setExercise_day_of_week(userProfile.getExercise_day_of_week());
        setExercise_time(userProfile.getExercise_time());
        setMeditation_time(userProfile.getMeditation_time());
        setWake_up_time(userProfile.getWake_up_time());
        setGo_to_sleep_time(userProfile.getGo_to_sleep_time());
        setGender(userProfile.getGender());
        setCurrent_week(userProfile.getCurrent_week());
		setResearch_study(userProfile.getResearch_study());
    }
}
