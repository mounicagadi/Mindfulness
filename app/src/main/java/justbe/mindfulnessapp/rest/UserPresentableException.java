package justbe.mindfulnessapp.rest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * An exception used to display user friendly alerts when an error occurs
 *
 * <p>
 *     Designed to be called from an activity controller, this exception includes an
 *     {@link UserPresentableException#alert} method to display a consistent and user friendly
 *     alert when the application encounters a problem, whether that be the application's fault or
 *     the user's fault.
 *
 *     This Exception by no means requires use of the alert method, you may use this class to simply
 *     parse and/or pass exceptions in readable titles and messages.
 * </p>
 * @example new UserPresentableException("Thing Failed", "Because xyz").alert(this)
 * @example new UserPresentableException(new Exception("XYZ failed to respond")).alert(this)
 */
public class UserPresentableException extends RuntimeException {

    /**
     * The user presentable title
     */
    private String title;

    /**
     * The user presentable message
     */
    private String message;

    /**
     * Basic Constructor
     */
    public UserPresentableException() {
        super();
        this.setTitle("Something went wrong");
        this.setMessage("We are sad to say that we aren't quite sure what happened.");
    }

    /**
     * Parse the given Exception into a user presentable alert
     * @param e The Exception to use when generating the alert
     */
    public UserPresentableException(Exception e) {
        this.setMessage(e.getMessage());
        this.setTitle("Something went wrong");
    }

    /**
     * Set the title of the Alert manually and get the message from the given exception
     * @param e The Exception to use for the message
     * @param title The title of the alert box
     */
    public UserPresentableException(Exception e, String title) {
        this.setMessage(e.getMessage());
        this.setTitle(title);
    }

    /**
     * Set the Title and the Message for the alert
     * @param title The title of the alert
     * @param message The Message of the alert
     */
    public UserPresentableException(String title, String message) {
        this.setTitle(title);
        this.setMessage(message);
    }

    /**
     * Displays an alert box in the given context with content from this exception
     * @param context The Context (Activity/View) to show the alert on
     */
    public void alert(Context context) {
        new AlertDialog.Builder(context)
                .setTitle(this.getTitle())
                .setMessage(this.getMessage())
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Return to dialog
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * Gets the title of the exception
     * @return The title that will be used in the alert
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the exception
     * @param title The title that will be used in the alert
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the message of the exception
     * @return The message that will be used in the alert
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message of the exception
     * @param message The message that will be used in the alert
     */
    public void setMessage(String message) {
        if (message.length() > 150) {
            message = message.substring(0, 150) + "...";
        }
        this.message = message;
    }
}
