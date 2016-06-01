package justbe.mindfulness;

import android.content.Context;
import android.util.Log;

/**
 * Static Context class to give the app's context to classes that are not Activity or Service
 * classes.
 *
 * @author edhurtig
 * @example App.context().getResources().getString(...)
 */
public class App extends android.app.Application {

    /**
     * A static store for the application object
     */
    private static App app = null;

    /**
     * The currently signed in Session
     */
    private static Session session = null;

    /**
     * Gets the current Session object
     *
     * @return
     */
    public static Session getSession() {
        return App.session;
    }

    /**
     * Store the context in App.app static property
     */
    @Override
    public void onCreate() {
        super.onCreate();
        App.app = this;

        // Load the Session
        App.session = new Session(this.getApplicationContext());
        Log.v("Session ", "App session created");
    }

    /**
     * Gets the staticly stored context
     *
     * @return The Context
     */
    public static Context context() {
        return App.app.getApplicationContext();
    }
}
