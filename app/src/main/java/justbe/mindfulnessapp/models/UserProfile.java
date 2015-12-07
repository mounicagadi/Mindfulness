package justbe.mindfulnessapp.models;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import justbe.mindfulnessapp.App;
import justbe.mindfulnessapp.Util;
import justbe.mindfulnessapp.rest.GenericHttpRequestTask;
import justbe.mindfulnessapp.rest.RestUtil;
import justbe.mindfulnessapp.rest.UserPresentableException;

public class UserProfile extends PlainOldDBO<User> {
    private String birthday;
    private Integer exercise_day_of_week;
    private String exercise_time;
    private String meditation_time;
    private String wake_up_time;
    private String go_to_sleep_time;
    private Integer gender;
    private Integer program_week;

    /**
     * Empty UserProfile Constructor
     */
    public UserProfile() { }

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
        setProgram_week(user.getProgram_week());
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

    public Integer getProgram_week() { return program_week; }

    public void setProgram_week(Integer program_week) { this.program_week = program_week; }

    /**
     * Saves the user profile data to the database and sets its data to the current user
     * @param user The User to add the data to
     * @return True if success, else false
     */
    public boolean updateUserWithUserProfile(User user) {
        // Create an HTTPRequestTask that sends a UserProfile Object and Returns a UserProfile Object
        GenericHttpRequestTask<UserProfile, UserProfile> task = new GenericHttpRequestTask(UserProfile.class, UserProfile.class);

        task.execute("/api/v1/user_profile/", HttpMethod.PATCH, this);

        try {
            ResponseEntity<UserProfile> result = task.waitForResponse();

            // Add the data to the User now that it is saved on the server
            RestUtil.checkResponseHazardously(result);
            user.addUserProfileData(result.getBody());
            App.getSession().setUser(user);

            return true;
        } catch (Exception e) {
           return false;
        }
    }
}
