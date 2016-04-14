package justbe.mindfulness.models;

import java.util.Date;

import justbe.mindfulness.Util;

public class UserProfile extends PlainOldDBO<User> {
    private String birthday;
    private Integer exercise_day_of_week;
    private String exercise_time;
    private String meditation_time;
    private String wake_up_time;
    private String go_to_sleep_time;
    private Integer gender;
    private Integer current_week;
	private boolean research_study;

    /**
     * UserProfile Constructor that creates a default UserProfile object with default values
     * The times are set to the current time and the current program week to 0 (Not started)
     */
    public UserProfile() {
        // Set the program week to 0 (Not Started)
        setCurrent_week(0);

        // Set up default times
        String currentTime = Util.dateToUserProfileString(new Date());
        setMeditation_time(currentTime);
        setExercise_time(currentTime);
        setWake_up_time(currentTime);
        setGo_to_sleep_time(currentTime);
    }

    /**
     * UserProfile Constructor that populates it with values from a given User
     * @param user The user who's values are going into the new UserProfile
     */
    public UserProfile(User user) {
        setBirthday(user.getBirthday());
        setExercise_day_of_week(user.getExercise_day_of_week());
        setExercise_time(Util.dateToUserProfileString(user.getExercise_time()));
        setMeditation_time(Util.dateToUserProfileString(user.getMeditation_time()));
        setWake_up_time(Util.dateToUserProfileString(user.getWake_up_time()));
        setGo_to_sleep_time(Util.dateToUserProfileString(user.getGo_to_sleep_time()));
        setGender(user.getGender());
        setCurrent_week(user.getCurrent_week());
		setResearch_study(user.getResearch_study());
    }

    public String getBirthday() { return birthday; }

    public void setBirthday(String birthday) { this.birthday = birthday; }

    public Integer getExercise_day_of_week() { return exercise_day_of_week; }

    public void setExercise_day_of_week(Integer exercise_day_of_week) { this.exercise_day_of_week = exercise_day_of_week; }

    public String getExercise_time() { return exercise_time; }

    public void setExercise_time(String exercise_time) { this.exercise_time = exercise_time; }

    public String getMeditation_time() { return meditation_time; }

    public void setMeditation_time(String meditation_time) { this.meditation_time = meditation_time; }

    public String getWake_up_time() { return wake_up_time; }

    public void setWake_up_time(String wake_up_time) { this.wake_up_time = wake_up_time; }

    public String getGo_to_sleep_time() { return this.go_to_sleep_time; }

    public void setGo_to_sleep_time(String go_to_sleep_time) { this.go_to_sleep_time = go_to_sleep_time; }

    public Integer getGender() {  return gender; }

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
}
