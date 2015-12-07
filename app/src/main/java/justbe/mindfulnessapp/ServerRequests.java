package justbe.mindfulnessapp;

import android.content.Context;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import justbe.mindfulnessapp.models.Assessment;
import justbe.mindfulnessapp.models.MeditationSession;
import justbe.mindfulnessapp.models.User;
import justbe.mindfulnessapp.models.UserProfile;
import justbe.mindfulnessapp.rest.GenericHttpRequestTask;
import justbe.mindfulnessapp.rest.RestUtil;
import justbe.mindfulnessapp.rest.UserPresentableException;

/**
 * Contains all the calls made to the database
 */
public class ServerRequests {

    /***********************************************************************************************
     * User API Calls
     **********************************************************************************************/

    /**
     * Sends the API request to create a new User from a given user object
     * @param user The user object to send to the database
     * @param context The view that calls this, used to present specific errors
     * @return If the call worked then true, else false
     */
    public static Boolean createUser(User user, Context context) {
        // Create an HTTPRequestTask that sends a User Object and Returns a User Object
        GenericHttpRequestTask<User, User> task = new GenericHttpRequestTask(User.class, User.class);

        task.execute("/api/v1/create_user/", HttpMethod.POST, user);

        Boolean success = false;
        try {
            ResponseEntity<User> result = task.waitForResponse();
            RestUtil.checkResponseHazardously(result);

            // Authenticate with the server, store session (i.e. login)
            if( ! App.getSession().authenticate(user.getUsername(), user.getRaw_password()) ) {
                success = false;
                throw new UserPresentableException(
                        context.getString(R.string.auth_failed),
                        context.getString(R.string.cant_login_to_new_account));
            }
            success = true;
        } catch (Exception e) {
            new UserPresentableException(e).alert(context);
            success = false;
        }
        return success;
    }

    /**
     * Saves the user profile data to the database and sets its data to the current user
     * @param userProfile The UserProfile with the new user data
     * @param user The User to add the data to
     * @param context The view that calls this, used to present specific errors
     * @return True if success, else false
     */
    public static Boolean updateUserWithUserProfile(User user, UserProfile userProfile, Context context) {
        // Create an HTTPRequestTask that sends a UserProfile Object and Returns a UserProfile Object
        GenericHttpRequestTask<UserProfile, UserProfile> task = new GenericHttpRequestTask(UserProfile.class, UserProfile.class);

        task.execute("/api/v1/user_profile/", HttpMethod.PATCH, userProfile);

        Boolean success = false;
        try {
            ResponseEntity<UserProfile> result = task.waitForResponse();

            // Add the data to the User now that it is saved on the server
            RestUtil.checkResponseHazardously(result);
            user.addUserProfileData(userProfile);

            success = true;
        } catch (Exception e) {
            new UserPresentableException(e).alert(context);
            success = false;
        }
        return success;
    }

    /***********************************************************************************************
     * Assessment API Calls
     **********************************************************************************************/

    /**
     *  Creates a assessment in the database
     *  @param context The view that calls this, used to present specific errors
     */
    public static Assessment createAssessment(Context context) {
        Assessment assessment = new Assessment();

        // Create an HTTPRequestTask that sends a MeditationSession Object and Returns a MeditationSession Object
        GenericHttpRequestTask<Assessment, Assessment> task
                = new GenericHttpRequestTask(MeditationSession.class, MeditationSession.class);

        task.execute("/api/v1/assessment/", HttpMethod.POST, null);

        try {
            ResponseEntity<Assessment> result = task.waitForResponse();
            RestUtil.checkResponseHazardously(result);
            assessment = result.getBody();
        } catch (Exception e) {
            new UserPresentableException(e).alert(context);
        }
        return assessment;
    }

    /***********************************************************************************************
     * Meditation API Calls
     **********************************************************************************************/

    /**
     *  Gets all the meditation sessions for the current week
     * @param context The view that calls this, used to present specific errors
     *  @return The meditation sessions from the database
     */
    public static MeditationSession[] getMeditationSessions(Context context) {
        MeditationSession[] meditationSessions = null;

        // Create an HTTPRequestTask that sends a MeditationSession Object and Returns a MeditationSession Object
        GenericHttpRequestTask<MeditationSession, MeditationSession> task
                = new GenericHttpRequestTask(MeditationSession.class, MeditationSession.class);

        task.execute("/api/v1/meditation_session/", HttpMethod.GET, null);

        try {
            ResponseEntity<MeditationSession> result = task.waitForResponse();
            RestUtil.checkResponseHazardously(result);

            meditationSessions = result.getBody().getObjects();


        } catch (Exception e) {
            new UserPresentableException(e).alert(context);
        }

        return meditationSessions;
    }

    /**
     *  Updates a meditation session in the database
     *  @param meditationSession The meditationSession to update on the database
     *  @param context The view that calls this, used to present specific errors
     */
    public static Boolean update(MeditationSession meditationSession, Context context) {
        // Create an HTTPRequestTask that sends a MeditationSession Object and Returns a MeditationSession Object
        GenericHttpRequestTask<MeditationSession, MeditationSession> task
                = new GenericHttpRequestTask(MeditationSession.class, MeditationSession.class);

        int meditationSessionID = meditationSession.getMeditation_id();
        task.execute("/api/v1/meditation_session/" + meditationSessionID + "/", HttpMethod.PATCH, meditationSession);

        Boolean success = false;
        try {
            ResponseEntity<MeditationSession> result = task.waitForResponse();
            RestUtil.checkResponseHazardously(result);
            success = true;
        } catch (Exception e) {
            new UserPresentableException(e).alert(context);
            success = false;
        }
        return success;
    }

    /**
     *  Generates the meditations for the given week in the database for the user
     *  @param week 1 -> 8 representing the week to create meditations for
     *  @param context The view that calls this, used to present specific errors
     */
    public static Boolean populateDatabaseForWeek(Integer week, Context context) {
        // Create an HTTPRequestTask that sends a MeditationSession Object and Returns a MeditationSession Object
        GenericHttpRequestTask<MeditationSession, MeditationSession> task;

        // Unstarted meditation session for the user without meditation_id
        MeditationSession meditationSession = new MeditationSession();
        meditationSession.setPercent_completed(0.0);

        Boolean success = false;
        // Create one meditation for each day of the week 0 Monday -> 6 Saturday
        for (int d = 0; d < 7; d = d + 1) {
            task = new GenericHttpRequestTask(MeditationSession.class, MeditationSession.class);

            meditationSession.setMeditation_id(week, d);

            task.execute("/api/v1/meditation_session/", HttpMethod.POST, meditationSession);

            try {
                ResponseEntity<MeditationSession> result = task.waitForResponse();
                RestUtil.checkResponseHazardously(result);
                success = true;
            } catch (Exception e) {
                new UserPresentableException(e).alert(context);
                success = false;
                break;
            }
        }
        return success;
    }
}
