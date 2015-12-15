package justbe.mindfulness;

import android.content.Context;
import android.util.Log;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.google.gson.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import justbe.mindfulness.models.Success;
import justbe.mindfulness.models.User;
import justbe.mindfulness.rest.GenericHttpRequestTask;
import justbe.mindfulness.rest.RestUtil;

/**
 * Describes all the information we need for communicating with the server
 */
public class Session {

    private Context context;
    private SessionManager sessionManager;

    private String user;
    private String sessionId;
    private String csrfToken;


    public Session(Context context) {
        this.setContext(context);
        sessionManager = new SessionManager(context);
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
        sessionManager.setUser(user);
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
        sessionManager.setSessionID(sessionId);
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
        sessionManager.setCSRFToken(csrfToken);
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
        this.context = context;
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
            App.getSession().setUser(null);
            return true;
        } else {
            return false;
        }
    }
}
