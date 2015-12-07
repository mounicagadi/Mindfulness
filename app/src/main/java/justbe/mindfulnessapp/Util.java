package justbe.mindfulnessapp;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Holds various utilities used by the app
 */
public class Util {

    /**
     * Formats the given Date into a HH:mm string or the current Date if the given Date is null
     * @param time The Date to format
     * @return HH:mm of the given Date or the current Date
     */
    public static String dateToUserProfileString(Date time) {
        DateFormat sdf = new SimpleDateFormat("HH:mm");
        if(time == null)
            time = new Date();
        return sdf.format(time);
    }

    /**
     * * Formats the given Date into a 'hh:mm a' string or the current Date if the given Date is null
     * @param time The Date to format
     * @return 'hh:mm a' of the given Date or the current Date
     */
    public static String dateToDisplayString(Date time) {
        DateFormat sdf = new SimpleDateFormat("hh:mm a");
        if(time == null)
            time = new Date();
        return sdf.format(time);
    }

    /**
     * Update the text of a text view to a given time
     * @param tv text view to update information of
     * @param time new time to display
     */
    public static void setTextViewToTime(TextView tv, double time) {
        Date date = new Date((int)time);
        DateFormat formatter = new SimpleDateFormat("mm:ss");
        tv.setText(formatter.format(date));
    }

    /**
     * Checks to make sure that the two password fields are the same
     * @param passwordField The first password field
     * @param confirmPasswordField The second password field
     * @return whether the password fields are the same
     */
    public static boolean samePassword(TextView passwordField, TextView confirmPasswordField) {
        return passwordField.getText().toString().equals(confirmPasswordField.getText().toString());
    }

    /**
     * Returns the day of the week in the string format used by the day selector
     * @return The day of the week in the following format: su, m, t, w, th, f, s
     */
    public static Integer getCurrentDayOfTheWeek() {
        Integer day = 0;
        switch(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                day = 6;
                break;
            case Calendar.MONDAY:
                day = 0;
                break;
            case Calendar.TUESDAY:
                day = 1;
                break;
            case Calendar.WEDNESDAY:
                day = 2;
                break;
            case Calendar.THURSDAY:
                day = 3;
                break;
            case Calendar.FRIDAY:
                day = 4;
                break;
            case Calendar.SATURDAY:
                day = 5;
                break;
            default:
                // impossible to get here
        }
        return day;
    }

    /**
     * Helper function that finds the text view for a given week in the given view
     * @param pw_view The popup view that the text field is on
     * @param week The week
     * @return The text view for the given week
     */
    public static TextView getTextViewForWeek(View pw_view, int week) {
        TextView weekTextView;
        switch (week){
            case 1:
                weekTextView = (TextView) pw_view.findViewById(R.id.week1Text);
                break;
            case 2:
                weekTextView = (TextView) pw_view.findViewById(R.id.week2Text);
                break;
            case 3:
                weekTextView = (TextView) pw_view.findViewById(R.id.week3Text);
                break;
            case 4:
                weekTextView = (TextView) pw_view.findViewById(R.id.week4Text);
                break;
            case 5:
                weekTextView = (TextView) pw_view.findViewById(R.id.week5Text);
                break;
            case 6:
                weekTextView = (TextView) pw_view.findViewById(R.id.week6Text);
                break;
            case 7:
                weekTextView = (TextView) pw_view.findViewById(R.id.week7Text);
                break;
            case 8:
                weekTextView = (TextView) pw_view.findViewById(R.id.week8Text);
                break;
            default:
                weekTextView = null;
                break;
        }
        return weekTextView;
    }

    /**
     * Helper function that finds the image view for a given week in the given view
     * @param pw_view The popup view that the image field is on
     * @param week The week
     * @return The image view for the given week
     */
    public static ImageView getImageViewForWeek(View pw_view, int week) {
        ImageView weekImageView;
        switch (week){
            case 1:
                weekImageView = (ImageView) pw_view.findViewById(R.id.week1Check);
                break;
            case 2:
                weekImageView = (ImageView) pw_view.findViewById(R.id.week2Check);
                break;
            case 3:
                weekImageView = (ImageView) pw_view.findViewById(R.id.week3Check);
                break;
            case 4:
                weekImageView = (ImageView) pw_view.findViewById(R.id.week4Check);
                break;
            case 5:
                weekImageView = (ImageView) pw_view.findViewById(R.id.week5Check);
                break;
            case 6:
                weekImageView = (ImageView) pw_view.findViewById(R.id.week6Check);
                break;
            case 7:
                weekImageView = (ImageView) pw_view.findViewById(R.id.week7Check);
                break;
            case 8:
                weekImageView = (ImageView) pw_view.findViewById(R.id.week8Check);
                break;
            default:
                weekImageView = null;
                break;
        }
        return weekImageView;
    }
}
