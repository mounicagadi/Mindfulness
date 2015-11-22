package justbe.mindfulnessapp;

import android.content.Context;

/**
 * Static Context class to give the app's context to classes that are not Activity or Service
 * classes.
 *
 * @example App.context().getResources().getString(...)
 *
 * @author edhurtig
 */
public class App extends android.app.Application {

    /**
     * A static store for the application object
     */
    private static App app = null;

    /**
     * Store the context in App.app static property
     */
    @Override
    public void onCreate()
    {
        super.onCreate();
        App.app = this;
    }

    /**
     * Gets the staticly stored context
     * @return The Context
     */
    public static Context context() {
        return App.app.getApplicationContext();
    }
}
