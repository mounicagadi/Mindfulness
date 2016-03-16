package justbe.mindfulness;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import justbe.mindfulness.models.Excercise;
import justbe.mindfulness.models.User;

/**
 * Activity that displays weekly lessons
 */
public class LessonActivity extends AppCompatActivity {

    /***********************************************************************************************
     * LessonActivty Life Cycle Functions
     **********************************************************************************************/
    /**
     * Called when the view is created
     * @param savedInstanceState Saved Instance State
     */

    TextView lessonTitle, lessonContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        // Create the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.title_activity_lesson));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        lessonTitle =  (TextView) findViewById(R.id.lesson_title_text);
        lessonContent =  (TextView) findViewById(R.id.lesson_text);

        User user = App.getSession().getUser();

        // Creates exercise session in database if it does not exist yet
        Boolean completed = getIntent().getExtras().getBoolean("completed");
        Map<Integer, Excercise> excerciseMap =  new HashMap<Integer, Excercise>();
        // week-1
        excerciseMap.put(1, new Excercise("WEEK 1: KNOW YOUR BREATH",
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
                        "Enjoy your practice!\r\n"));

        // week 2
        excerciseMap.put(2, new Excercise("WEEK 2: KNOW YOUR BODY",
                "Awareness of your body is another tool that helps develop the skill of awareness. There are processes in your body happening at all times, whether you are aware of them or not. Those processes are felt as physical sensations. They are anything that you feel on the surface, under your skin or deep inside the body. Familiar examples are pain, hunger, your heart pounding, sweat, and dryness. But there are many more: cold, warmth, pressure, tingling, throbbing, pulsation, lightness, the touch of the atmosphere or your own clothes, contraction, expansion, shivering, and many other sensations that do not even have names attached to them.\r\n\r\n"+
                "In your meditation, you will systematically bring awareness to each body part, one by one. You will practice objective observation. Patiently, intently, you will notice sensations that exist in each part of your body in a given moment.\r\n\r\n"+
                "Sensations are constantly changing: sometimes staying for longer periods, sometimes shifting quickly. You might start developing expectations about particular types of sensations in particular body regions. For example, if you have chronic pain, you might expect to come across that pain. During meditation, notice that tendency and try to remain as objective as possible. Be curious what is true in that particular moment. You might be surprised that, besides pain, you could come across pulsation or heat, or anything else. Stay open. The same is true for wanting positive sensations – do not get attached to them, simply notice and observe.\r\n\r\n"+
        "There is no need to explain sensations: why they are there, what they mean, and whether they are good or bad. There is also no need to name them: feeling is all you need. Your tendency might be to identify with different types of sensations: maybe preferring some over others. Notice that. Remember an image of a friend who listens with great interest, indiscriminately, to what you have to say. Similarly, you are becoming aware of sensations, without preferences for one type or another.\r\n\r\n"+
        "During your daily practice, bring your awareness to whatever activity you are doing in that moment and a body part involved in that activity. For example, if you are walking, notice your legs moving, sensations in the muscles, and soles of the feet striking the ground. If you are driving, notice sitting bones on the seat, hands on the wheel, and feet on pedals. If you are eating, notice the movement of the tongue, teeth and lips, food swallowing, and sensations in your stomach. If you are watching a TV, notice sensations from sitting, the posture, and facial muscles. Take a few moments to practice objective awareness in whatever you are doing during your day.\r\n\r\n"+
        "A known German philosopher, Friedrich Nietzche (1844 – 1900), said about the body, 'There is more wisdom in your body than in your deepest philosophies.'\r\n\r\n"+
        "Enjoy discovering yourself!"));

                lessonTitle.setText(excerciseMap.get(user.getCurrent_week()).getExcerciseName());
        lessonContent.setText(excerciseMap.get(user.getCurrent_week()).getExcerciseContent());
        if (!completed) {
            // TODO: get currently selected week instead of current program week
            int week = user.getCurrent_week();
            ServerRequests.completeExerciseSession(week, this);
        }
    }
}
