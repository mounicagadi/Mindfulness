package justbe.mindfulnessapp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Holds various utilities used by the app
 */
public final class Util {

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
}
