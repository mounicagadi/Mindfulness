package justbe.mindfulnessapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.google.gson.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import justbe.mindfulnessapp.models.Success;
import justbe.mindfulnessapp.models.User;
import justbe.mindfulnessapp.rest.GenericHttpRequestTask;
import justbe.mindfulnessapp.rest.RestUtil;

/**
 * Describes all the information we need for communicating with the server
 */
public class Session {

    public Session(Context context) {
        this.setContext(context);
    }

    private Context context;
    public static final String PREFERENCES_KEY = "CURRENT_SESSION";

    private String username;
    private String user;
    private String sessionId;
    private String csrfToken;

    /**
     * Loads the preferences from the current context
     */
    private void load() {
        SharedPreferences settings = this.getContext().getSharedPreferences(PREFERENCES_KEY, 0);

        this.username = settings.getString("username", null);
        this.user = settings.getString("user", null);
        this.sessionId = settings.getString("sessionId", null);
        this.csrfToken = settings.getString("csrfToken", null);
    }

    /**
     * Saves the current Session state to the current context
     */
    private void save() {
        SharedPreferences settings = this.getContext().getSharedPreferences(PREFERENCES_KEY, 0);

        SharedPreferences.Editor editor = settings.edit();

        if (this.username != null) {
            editor.putString("username", this.username);
        }

        if (this.sessionId != null) {
            editor.putString("sessionId", this.sessionId);
        }

        if (this.csrfToken != null) {
            editor.putString("csrfToken", this.csrfToken);
        }

        // Save the preferences
        editor.commit();
    }

    /**
     * Removes the given key from the store
     *
     * @param key The Key to remove / delete
     */
    private void remove(String key) {
        SharedPreferences settings = this.getContext().getSharedPreferences(PREFERENCES_KEY, 0);

        SharedPreferences.Editor editor = settings.edit();

        editor.remove(key);

        // Save the preferences
        editor.commit();
    }

    /**
     * Removes the Username from the store
     */
    private void removeUsername() {
        this.username = null;
        this.remove("username");
    }

    /**
     * Removes the user from the store
     */
    private void removeUser() {
        this.user = null;
        this.remove("user");
    }

    /**
     * Removes the Session Id from the Store
     */
    private void removeSessionId() {
        this.sessionId = null;
        this.remove("sessionId");
    }

    /**
     * Removes the CsrfToken from the store
     */
    private void removeCsrfToken() {
        this.csrfToken = null;
        this.remove("csrfToken");
    }


    /**
     * Gets the current username
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * Updates the session username
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
        this.save();
    }

    /**
     * Gets the current user
     */
    public User getUser() {
        Gson gson = new Gson();
        return gson.fromJson(this.user, User.class);
    }

    /**
     * Updates the session user
     * @param user the new user
     */
    public void setUser(User user) {
        Gson gson = new Gson();
        this.user = gson.toJson(user);
        this.save();
    }

    /**
     * Gets the session ID
     * @return The session id
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Updates the session id
     * @param sessionId the new session id
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
        this.save();
    }

    /**
     * Gets the CSRF Token
     * @return The CSRF Token
     */
    public String getCsrfToken() {
        return csrfToken;
    }

    /**
     * Updates the CSRF Token
     * @param csrfToken The new CSRF Token
     */
    public void setCsrfToken(String csrfToken) {
        this.csrfToken = csrfToken;
        this.save();
    }

    /**
     * Gets the current context (Internal use only)
     * @return The Current context
     */
    private Context getContext() {
        return context;
    }

    /**
     * Updates the context, replacing the current context with the given new context
     * Will Discard whatever is in this session and reload it's values from the new context
     * @param context The new context
     */
    public void setContext(Context context) {
        this.setContext(context, false);
    }

    /**
     * Updates the context, replacing the current context with the given new context
     * @param context The New Context
     * @param preserveCurrent whether to save the current session state to the new context, or to load the state in the context's preference file
     */
    public void setContext(Context context, boolean preserveCurrent) {
        try {
            this.save();
        } catch (Exception e) {
            // Don't care
        } finally {
            this.context = context;
            if (preserveCurrent) {
                this.save();
            } else {
                this.load();
            }
        }

    }

    /**
     * Authenticates with the API and stores the returned tokens in this session object, writing them
     * to a persistent store for reuse later
     *
     * @param username     The Username
     * @param raw_password The Password
     * @return True if the authentication was successful, otherwise false
     */
    public boolean authenticate(String username, String raw_password) throws InterruptedException, ExecutionException, TimeoutException {
        GenericHttpRequestTask<User, User> task = new GenericHttpRequestTask(User.class, User.class);

        User u = new User();

        Log.v("username", username);
        Log.v("password", raw_password);
        u.setUsername(username);
        u.setPassword(raw_password);
        task.execute("/api/v1/user/login/", HttpMethod.POST, u);


        ResponseEntity<User> response;

        response = task.waitForResponse();


        if (RestUtil.checkResponse(response)) {
            for (String s : response.getHeaders().get("Set-Cookie")) {
                String key = s.split("=")[0];
                String value = s.split("=")[1].split(";")[0];

                switch (key) {
                    case "sessionid":
                        this.setSessionId(value);
                        break;
                    case "csrftoken":
                        this.setCsrfToken(value);
                        break;
                    default:
                        Log.e("Session", "Unknown Key: " + key);
                        break;
                }
            }

            this.setUser(response.getBody());
            this.setUsername(username);
            return true;
        } else {
            return false;
        }
    }

    public boolean invalidate() throws InterruptedException, ExecutionException, TimeoutException {
        GenericHttpRequestTask<User, Success> task = new GenericHttpRequestTask(User.class, Success.class);

        task.execute("/api/v1/user/logout/", HttpMethod.GET);


        ResponseEntity<Success> response;
        response = task.waitForResponse();

        if (RestUtil.checkResponse(response) && response.getBody() != null && response.getBody().getSuccess()) {
            this.removeSessionId();
            this.removeCsrfToken();
            App.getSession().setUser(null);
            return true;
        } else {
            return false;
        }
    }
}
