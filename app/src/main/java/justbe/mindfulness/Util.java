package justbe.mindfulness;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import justbe.mindfulness.models.Exercise;

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

    /** getExerciseForWeek() returns an Exercise bean
     * with title and lesson content based on week id
     *
     *  @param weekId : weekId for which exercise
     *                  content needs to be fetched
     *
     *  @return Exercise bean for current week
     *  (with title and content)
     * */
    public static Exercise getExerciseForWeek(int weekId){

        Exercise exercise = null;

        switch(weekId){
            case 1: exercise = new Exercise("WEEK 1: KNOW YOUR BREATH",
                    "Breath awareness is the first step in developing the skill of objective awareness. Breath is a natural physiological process, always available to you, free of charge, no matter where you are. The body knows how to breathe on its own; no involvement on your part is necessary. This week’s practice is NOT to try to control the breath. Instead, the practice is to develop objectivity in observing the breath in any given moment. If it is rapid and shallow, then observe it being rapid and shallow without trying to change it. If it is deep and relaxed, observe it in that way without assigning labels of “good” or “bad.” The purpose is to train yourself to become present with your breath, aware of it, and notice mental tendencies to evaluate and label breathing in one way or another.\r\n\r\n" +
                            "Initially, it is likely that your focus may not be very sharp. You might struggle to stay with the breath for a long time. Habitually, thoughts will come up and take you away from your focus. This is completely normal, and there is nothing wrong with that. When you notice being distracted, gently return awareness back to your breath without judgment or disappointment. Like a friend, listening with interest and kindness, you are beginning to observe your breath and anything else that goes on internally, in a kind and accepting way." +
                            "\r\n\r\nYou will practice breath awareness in two ways. First is a guided meditation done daily for about 20 minutes. This is your focused time spent deliberately on training objective awareness. To help you get started, follow these steps:\r\n\r\n" +
                            "\u2022 Find a quiet place where you can be uninterrupted for about 20 minutes.\r\n\r\n" +
                            "\u2022 During meditation, sit comfortably. You will have to experiment with finding the best posture for yourself. Usually, sitting on the edge of a chair with feet grounded, spine tall, and arms relaxed is comfortable. It also prevents you from falling asleep. Another option is to sit on a pillow or a rolled up towels under your sits bones, so that hips are above the knees.\r\n\r\n" +
                            "\u2022 During meditation, move slowly and with awareness. If you need to adjust your posture, first, notice the discomfort, and then slowly move only if absolutely necessary to not distract yourself.\r\n\r\n" +
                            "\u2022 Try not to meditate immediately after a meal; it will put you to sleep.\r\n\r\n" +
                            "\u2022 Try practicing the same time every day (e.g., 1st thing in the morning or during your lunch break before a meal). This will help you form a habit.\r\n\r\n" +
                            "\u2022 If you skip a day, do not waste time on feeling guilty, simply get right back into it.\r\n\r\n" +
                            "In addition to daily meditation, whenever you remember during your day, bring awareness to your breath. Do not interrupt what you are doing. Instead, simply shift your awareness and take five conscious breaths. Remember, the practice is of no judgement and simply noticing. The goal of daily practice is to train you to become aware right where you are in your life and not only during quiet meditation times.\r\n\r\n" +
                            "As an inspiration, there is a beautiful poem by the 13th century Persian poet Rumi about the breath. \r\n\r\nONLY BREATH \r\n\r\nNot Christian or Jew or Muslim, not Hindu, \r\nBuddhist, sufi, or zen. Not any religion\r\n\r\n or cultural system. I am not from the East\r\nor the West, not out of the ocean or up\r\n\r\n from the ground, not\r\nnatural or ethereal, not\r\n composed of elements at all. I do not exist,\r\n\r\n am not an entity in this world or the next,\r\n did not descend from Adam or Eve or any\r\n\r\n origin story. My place is placeless, a trace\r\n of the traceless. Neither body or soul.\r\n\r\n I belong to the beloved, have seen the two\r\n worlds as one and that one call to and know,\r\n\r\n first, last, outer, inner, only that\r\n breath breathing human being.\r\n\r\n" +
                            "Enjoy your practice!\r\n");
                break;
            case 2: exercise = new Exercise("WEEK 2: KNOW YOUR BODY",
                    "Awareness of your body is another tool that helps develop the skill of awareness. There are processes in your body happening at all times, whether you are aware of them or not. Those processes are felt as physical sensations. They are anything that you feel on the surface, under your skin or deep inside the body. Familiar examples are pain, hunger, your heart pounding, sweat, and dryness. But there are many more: cold, warmth, pressure, tingling, throbbing, pulsation, lightness, the touch of the atmosphere or your own clothes, contraction, expansion, shivering, and many other sensations that do not even have names attached to them.\r\n\r\n" +
                            "In your meditation, you will systematically bring awareness to each body part, one by one. You will practice objective observation. Patiently, intently, you will notice sensations that exist in each part of your body in a given moment.\r\n\r\n" +
                            "Sensations are constantly changing: sometimes staying for longer periods, sometimes shifting quickly. You might start developing expectations about particular types of sensations in particular body regions. For example, if you have chronic pain, you might expect to come across that pain. During meditation, notice that tendency and try to remain as objective as possible. Be curious what is true in that particular moment. You might be surprised that, besides pain, you could come across pulsation or heat, or anything else. Stay open. The same is true for wanting positive sensations – do not get attached to them, simply notice and observe.\r\n\r\n" +
                            "There is no need to explain sensations: why they are there, what they mean, and whether they are good or bad. There is also no need to name them: feeling is all you need. Your tendency might be to identify with different types of sensations: maybe preferring some over others. Notice that. Remember an image of a friend who listens with great interest, indiscriminately, to what you have to say. Similarly, you are becoming aware of sensations, without preferences for one type or another.\r\n\r\n" +
                            "During your daily practice, bring your awareness to whatever activity you are doing in that moment and a body part involved in that activity. For example, if you are walking, notice your legs moving, sensations in the muscles, and soles of the feet striking the ground. If you are driving, notice sitting bones on the seat, hands on the wheel, and feet on pedals. If you are eating, notice the movement of the tongue, teeth and lips, food swallowing, and sensations in your stomach. If you are watching a TV, notice sensations from sitting, the posture, and facial muscles. Take a few moments to practice objective awareness in whatever you are doing during your day.\r\n\r\n" +
                            "A known German philosopher, Friedrich Nietzche (1844 – 1900), said about the body, 'There is more wisdom in your body than in your deepest philosophies.'\r\n\r\n" +
                            "Enjoy discovering yourself!");
                break;
            case 3: exercise = new Exercise("WEEK 3: TITLE","WEEK 3 CONTENT");
                break;
            case 4: exercise = new Exercise("WEEK 4: TITLE","WEEK 4 CONTENT");
                break;
            case 5: exercise = new Exercise("WEEK 5: TITLE","WEEK 5 CONTENT");
                break;
            case 6: exercise = new Exercise("WEEK 6: TITLE","WEEK 6 CONTENT");
                break;
            case 7: exercise = new Exercise("WEEK 7: TITLE","WEEK 7 CONTENT");
                break;
            case 8: exercise = new Exercise("WEEK 8: TITLE","WEEK 8 CONTENT");
                break;
        }

        return exercise;
    }

 public static int getCalendarDayId(int weekDayId){
        int calendarDayID = 0;
        switch(weekDayId){

            case 0: calendarDayID = 2; //monday
                break;
            case 1: calendarDayID = 3; //tuesday
                break;
            case 2: calendarDayID = 4; //wednesday
                break;
            case 3: calendarDayID = 5; //thursday
                break;
            case 4: calendarDayID = 6; //friday
                break;
            case 5: calendarDayID = 7; //saturday
                break;
            case 6: calendarDayID = 1; //sunday
                break;
        }

        return calendarDayID;
    }
}
