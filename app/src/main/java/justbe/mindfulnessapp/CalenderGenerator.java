package justbe.mindfulnessapp;

import java.util.Calendar;
import java.util.Date;

import justbe.mindfulnessapp.models.User;

/**
 * Helper class for generating calendars for when the user is awake
 */
public class CalenderGenerator {
    /**
     * Generates the specified number of calendars randomly
     * spread out during the day when the user is awake.
     * The calendars will be no closer than 30 minutes apart.
     * Returns null if the user has not set an awake time and a sleep time
     * @param count The number of calendars to generate
     * @return A Calendar array of times or null if the user has not set
     * an awake time and a sleep time
     */
    public static Calendar[] generateAwakeCalendars(int count) {
        User user = App.getSession().getUser();
        Date userAwakeTime = user.getWake_up_time();
        Date userSleepTime = user.getGo_to_sleep_time();
        if (userAwakeTime == null || userSleepTime == null) {
            return null;
        }
        Calendar awakeCal = Calendar.getInstance();
        awakeCal.setTime(userAwakeTime);
        Calendar sleepCal = Calendar.getInstance();
        sleepCal.setTime(userSleepTime);
        long diff = sleepCal.getTimeInMillis() - awakeCal.getTimeInMillis();
        long period = diff / count;
        long[] times = new long[count];
        times[0] = randomWithRange(0, period);
        for (int i = 1; i < times.length; i++) {
            times[i] = 0;
            do {
                times[i] = randomWithRange(times[i - 1], times[i - 1] + period);
                if (times[i] - times[i - 1] < 1800000) {
                    times[i] = 0;
                }
            }
            while(times[i] == 0);
        }
        Calendar[] cals = new Calendar[count];
        for (int i = 0; i < cals.length; i++) {
            cals[i] = Calendar.getInstance();
            cals[i].setTimeInMillis(awakeCal.getTimeInMillis() + times[i]);
        }
        return cals;
    }

    private static int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    private static long randomWithRange(long min, long max) {
        long range = (max - min) + 1;
        return (long)(Math.random() * range) + min;
    }
}
