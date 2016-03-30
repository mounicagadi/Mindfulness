package justbe.mindfulness;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import justbe.mindfulness.models.User;

public class SessionManager {
    /**
     * Fields
     */
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;

    // Preferences mode and keys
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "JustBePref";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_SessionID = "SessionID";
    public static final String KEY_CSRFToken = "CSRFToken";
    public static final String KEY_User = "User";

    // SessionManager Constructor
    public SessionManager(Context context){
        this.context = context;
        preferences = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    /**
     * Create login session and store the sessionID
     * @param sessionID The sessionID to store
     * */
    public void createLoginSession(String sessionID, String csrfToken, User user){
        Gson gson = new Gson();
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_User, gson.toJson(user));
        editor.putString(KEY_SessionID, sessionID);
        editor.putString(KEY_CSRFToken, csrfToken);
        editor.commit();
		Log.v("Session Manager"," Shared Preferences set for user "+ user.getUsername());
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        editor.clear();
        editor.commit();
    }

    /**
     * Get/set the sessionID
     */
    public String getSessionID() {
        return preferences.getString(KEY_SessionID, null);
    }
    public void setSessionID(String sessionID) { editor.putString(KEY_SessionID, sessionID); }

    /**
     * Get/set the csrf token
     */
    public String getCSRFToken() {
        return preferences.getString(KEY_CSRFToken, null);
    }
    public void setCSRFToken(String csrfToken) { editor.putString(KEY_CSRFToken, csrfToken); }

    /**
     * Get/set the user
     */
    public User getUser() {
        Gson gson = new Gson();
        String user = preferences.getString(KEY_User, null);
        return gson.fromJson(user, User.class);
    }

    public void setUser(User user) {
        Gson gson = new Gson();
        String mUser = gson.toJson(user);
        editor.putString(KEY_User, mUser);
		editor.commit();
    }

    /**
     * Check if the user is logged in
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(this.isLoggedIn()){
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);
        }
    }

    /**
     * Get the login state
     * @return True if user is logged in, else false
     * **/
    public boolean isLoggedIn(){
        return preferences.getBoolean(IS_LOGIN, false);
    }
}
