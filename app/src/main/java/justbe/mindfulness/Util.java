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
            case 1: exercise = new Exercise("WEEK 1:\r\n KNOW YOUR BREATH",
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

            case 3: exercise = new Exercise("WEEK 3: NO RESISTANCE",
                    "There are two central insights that could have emerged from observing your own breath and body sensations. First, no two meditation experiences are the same. Breath and sensations appear and disappear, reflecting impermanence and constant change. Second - you could have noticed your own preference for positive and resistance to negative meditation experiences. At one time you may think, “Oh, this meditation is going so well, I am getting really good at this.” The next time, however, it may be more like “This feels quite uncomfortable, the last time it was way better. Meditation is supposed to be pleasant and transcendent … I am not sure this whole meditation business is for me.\r\n\r\n"+
                            "Both experiences teach you something about yourself. Breath or sensations are natural physiological processes outside of your control and you learn to let go. When you do not let go and have a preference for a particular experience, emotional pain is there. No pleasure is permanent: you may enjoy a delicious meal but it comes to an end or you go on a relaxing vacation but have to come back to work or you get into a romantic relationship but your partner leaves or you invest in your beauty and health but old age and sickness come. This is a part of a natural cycle of life. Emotional pain arises out of resistance towards this natural cycle. There is nothing wrong with enjoying what you have but resisting when it comes to an end brings pain. \r\n\r\n"+
                            "Your mind is conditioned to react all the time, whether you are aware of it or not. For example, if you don’t like the sensation of sweat on your body, then every time you feel this sensation, you become unhappy and think, “I need cold water, I need air conditioner”. Or if you feel squeezing in your stomach, then you often interpret this sensation as hunger and eat even when the body doesn’t ask for food. Many physical and psychological habits develop in this way - addictions to substances, sugar, food, TV, social media, relationships, emotions, fears. If not aware and discerning, you continue to reinforce many unhelpful patterns over your lifetime. \r\n\r\n"+

                            "So does that mean you should now withdraw from all pleasures and subject yourself to hardships and restrains? Some people have tried that and realized that it doesn’t work either. The middle way is to learn how to be aware, perceptive and discerning. Even though the mind will naturally want to impart judgment on something and label it as “good” or “bad,” pure awareness has no preference whatsoever. This approach will give you freedom to choose what is best for you in a moment not out of habit of running from negative or craving positive but with discernment. \r\n\r\n"+

                            "This principle of awareness without resistance applies to everything. In your daily life, when you remember, during eating, speaking, working or playing, pause and ask “Is this reaction true? Is this behavior needed? Is this the best for my or another person’s well-being RIGHT NOW?” First, PAUSE and become aware, without the need to change anything. Listen to your body just in that moment. Then, CHOICE to act or NOT to act. Sometimes NOT acting is more powerful than acting. Take it one moment at a time and start small. Practice choices in daily situations. Take a different route home, brush your teeth with your left hand (if you are right-handed), change your usual morning routine or the way you eat – practice making choices. \r\n\r\n"+
                            "Non-resistance is described in the following way in the ancient book of Chinese wisdom"+
                    "“The Tao Te Ching” (verse 29) \r\n\r\n"+

                            "There is a time for being ahead, \r\n\r\n"+

                            "A time for being behind; \r\n\r\n"+

                            "A time for being in motion, \r\n\r\n"+

                            "A time for being at rest; \r\n\r\n"+

                            "A time for being vigorous, \r\n\r\n"+

                            "A time for being exhausted; \r\n\r\n"+

                            "A time for being safe, \r\n\r\n"+

                            "A time for being in danger. \r\n\r\n"+

                            "A Master sees things as they are, \r\n\r\n"+

                            "Without trying to control them. \r\n\r\n"+

                            "She sets them go their own way, \r\n\r\n"+

                            "And resides in the Center of the circle. \r\n\r\n"+

                            "Be aware and choose! \r\n\r\n");

                break;

            case 4: exercise = new Exercise("WEEK 4: COMPASSION",
                    "Compassion or unconditional kindness or are not cheesy terms. They are practical and applied, yet not frequently practiced or even considered. The nature of your mind is such that it evaluates everything that comes into its sensory field – what you see, feel or do, because it is wired for survival. By extension, it evaluates you and others with the same level of criticism. At the end, you can NEVER win the game of improving yourself or your life conditions enough for your mind to be satisfied. It won’t get to that point, because it is against its own nature. Discernment is important; however, the tendency to criticize prevents from seeing beauty, abundance, joy, and love. \r\n\r\n"+

                            "By practicing and cultivating awareness, you begin to notice your own tendency to judge and evaluate. When aware, become not only an OBJECTIVE but also a COMPASSIONATE OBSERVER. You are not working against your own mind but balancing its tendency to criticize by cultivating UNCONDITIONAL KINDNESS and COMPASSION. You need both to have a peaceful and joyful life. \r\n\r\n"+

                            "Compassionate awareness allows for all life experiences - “positive” and “negative” in thoughts, emotions and actions. It has no preference towards one or another and simply allows everything to be exactly as it is. EVERYTHING has NO EXCEPTIONS, but only with time and repetitive practice you can build this level of openness and trust. Imagine a mother teaching a little child to make its first steps. She is infinitely patient and expects nothing more than a step. She is not critical and is satisfied either way – whether a child falls or takes a second step, both are cute. Similarly to a child, you are taking these first steps every day of your life. With the practice of compassion, you are also learning to see yourself through the eyes of a loving mother. \r\n\r\n"+

                            "During meditation this week, create a safe internal space where you can be with yourself in a state of acceptance and unconditional kindness. You are not giving up on yourself, but instead learning to see yourself as a child going through the experience called “LIFE” - learning, changing, trying, succeeding, and failing – all of it. If it helps, place your hands on areas of your heart or belly as a reminder of the physical space where love is usually felt. \r\n\r\n"+

                            "During your daily practice, several times a day, use the same gesture of compassion towards yourself. If it feels comfortable, place hands on your belly or heart. If not, mentally feel those areas along with feelings of compassion and acceptance. Take several breaths allowing sensations to grow. Times when you are critical, anxious, or judgmental are ideals for practicing compassion as a natural balance to your strong mental tendency to evaluate and judge. \r\n\r\n"+

                            "Mary Oliver, a contemporary American poet, speaks of this way of knowing and accepting oneself in her poem"+
                            "“Wild Geese” You do not have to be good. \r\n\r\n"+
                            "You do not have to walk on your knees"+
                            "for a hundred miles through the desert repenting."+
                            "You only have to let the soft animal of your body"+
                            "love what it loves. \r\n\r\n"+
                            "Tell me about despair, yours, and I will tell you mine. \r\n\r\n"+
                            "Meanwhile the world goes on. \r\n\r\n"+
                            "Meanwhile the sun and the clear pebbles of the rain are moving across the landscapes, over the prairies and the deep trees, the mountains and the rivers. \r\n\r\n"+
                            "Meanwhile the wild geese, high in the clean blue air, are heading home again. \r\n\r\n"+
                            "Whoever you are, no matter how lonely, the world offers itself to your imagination, calls to you like the wild geese, harsh and exciting - over and over announcing your place in the family of things. \r\n\r\n");
                break;

            case 5: exercise = new Exercise("WEEK 5:\r\n KNOW YOUR THOUGHTS",
                    "After a month of practicing breath and body awareness, you probably have noticed how scattered your mind is. When you try to focus, it doesn’t take too long before thoughts arise. Whether you are aware of it or not, your mind is constantly at work, trouble shooting and problem solving. As a human, you are gifted with the creative and powerful tool of your mind. However, if you don’t know how to keep it healthy, you can be overtaken by continuous stories, projections, judgments, fears, evaluations, and anxieties. The mind is naturally very uncomfortable with the unknown and is always looking for reasons and explanations to make sense of reality. It is directed outwards into the world with the “I”- “me” - “mine” orientation, concerned with your survival. \r\n\r\n"+

                            "This week you will practice awareness of thoughts. Remember that you are not trying to get rid of thoughts – they are a natural part of you. Instead, you are practicing to see the mind for what it is: a constant uncontrollable flow of thoughts. When you practice, there is no need to get involved with the content. Notice your thoughts the same way you notice body sensations: arising and passing. Awareness exists outside of thoughts. Like the quality of the vast sky, it has no limits – present and accepting, without preferences for one mental story or another. Think of thoughts as clouds, carrying temporary storms, yet the sky (your awareness) is unaffected. Changes in the weather are temporary, not dramatic, and there is no confusion between the sky and the clouds. \r\n\r\n"+

                            "One thought is not truer than another. Your thoughts don’t need to define who you are. If you have a tendency towards depressive or anxious thinking, try not to put yourself in a box called “I am a depressed person” or “I am an anxious person”. It is true that you have those thoughts but they are not YOU. Don’t’ get confused between being a cloud versus being the sky. Being a cloud or identifying yourself with one kind of thoughts limits your possibilities to experience life for what it is – constantly changing, with a wide range of emotions, thoughts, events, and possibilities. Human cognition is like that: if you have a predetermined idea or a bias, it is as if you are putting on glasses of a particular shade and looking into the world through them. For example, if you expect the world to be dangerous and painful, you will surely find many confirmations of that. Superficial positive thinking is equally disturbing, because life is a mix of experiences - positive, negative and neutral, and ignoring neutral and negative creates an internal dissonance, impacting health and well-being. \r\n\r\n"+
                            "During your meditation, learn to become aware of your thoughts without being drawn into their content. Notice the experience of thoughts rising and passing, in the same way that your breath moves from emptiness to fullness. Become curious of the awareness itself – WHAT is being aware? WHO is being aware? WHERE this awareness is? Remain curious and open. The purpose is not to get answers but to remain open to experiences in every moment rather than getting stuck in one story."+
                            "In your daily practice, whenever you feel overwhelmed or pulled into a story, USE BREATH to CREATE MENTAL SPACE, as if you are allowing wind to move clouds on the sky. Keep breathing until there is more space and physical connection to your breath and body. Use breath to “ventilate” your mind. \r\n\r\n"+

                            "Enjoy more mental space! \r\n\r\n");
                break;

            case 6: exercise = new Exercise("WEEK 6:\r\n KNOW YOUR EMOTIONS",
                    "Emotions are normal and there is no need to get rid of them. Through awareness, you are building a different relationship to your emotions. Emotions are only difficult if you get attached to the story behind them and start reasoning with yourself about why you should or should not feel in a particular way. Happiness is advertised everywhere and usually comes with a story of being in love, having good things, job, or family. Even if you have it all, at times, you still feel angry, sad, lonely, anxious or jealous. Or you may not have things you want and feel left out. Pushing emotions away, no matter what they are, can prolong or exacerbate the experience. It may also lead to psychological and physical distress and pain. \r\n\r\n"+

                            "Take anxiety, for example. The emotion of anxiety is powerful. Evolutionary, anxiety (or physiological excitation) is essential for survival. In situations of danger, activation is necessary for running away or fighting an enemy. The mind doesn’t differentiate, however, between real and imaginary threats, and the body automatically goes into the “fight or flight” mode. One way to manage this natural physiological response is through expression – physical or verbal, but it can get us into trouble. Physical activity - sprinting, jumping or dancing – is a good discharge but not always possible. \r\n\r\n"+

                            "A different way to express an emotion in a non-harmful way is by FEELING IT IN YOUR BODY and BEING WITH IT. When you are anxious or angry, it is difficult to observe them abstractly. They usually come with a story – someone has wronged you. BRING ATTENTION TO YOUR BODY, however, helps an emotion to live its natural cycle without you being in emotional pain. When angry, you may feel heat, heart racing, muscles contracting, sweating, or other body sensations. Observe those sensations without resistance, letting activated energy come up and pass. When you give yourself an open space to feel with kindness and acceptance, there is no emotional grip, danger, harm, shame, or pain. \r\n\r\n"+
                            "This approach is different from justifying your emotions. While apparent causes might be present (a person or a situation), it is difficult to find the ultimate origin, since it can be hidden in the personality, life history, past events, experiences of trauma, culture, or genetics. With this approach, no justification is necessary. You are simply attending to what is WITHOUT JUDGEMENT and WITH KINDNESS AND COMPASSION. \r\n\r\n"+

                            "In your daily practice, whenever you feel overwhelmed, triggered or reactive, bring attention to your body, notice sensations, and observe them objectively to the best of your ability. It is easier if you catch an emotion earlier than later. Some emotions, however, have deep roots and may reoccur. Be patient with yourself and keep practicing.\r\n\r\n"+

                            "The Persian poet Rumi expressed emotions as guests in a human house. \r\n\r\n"+

                            "THE GUESTHOUSE. \r\n\r\n"+

                            "This being human is a guest house. Every morning a new arrival. \r\n\r\n"+

                            "A joy, a depression, a meanness, some momentary awareness comes As an unexpected visitor. \r\n\r\n"+

                            "Welcome and entertain them all! Even if they're a crowd of sorrows, who violently sweep your house empty of its furniture, still treat each guest honorably. He may be clearing you out for some new delight. \r\n\r\n"+

                            "The dark thought, the shame, the malice, meet them at the door laughing, and invite them in. \r\n\r\n"+

                            "Be grateful for whoever comes, because each has been sent as a guide from beyond. \r\n\r\n"+

                            "Enjoy getting to know your emotions! \r\n\r\n");
                break;

            case 7: exercise = new Exercise("WEEK 7: DEEP LISTENING",
                    "As you become more present, accepting and compassionate in your daily life, you are also more comfortable with a range of daily experiences. Your view of yourself isn’t as strongly linked to other people’s opinions - after all, those are thoughts of others and they change as frequently as your own. You might also begin to question rigidity of social, cultural and personal rules, since being in a moment involves experiential rather than intellectual being. In this process, the skills of DEEP LISTENING are invaluable. Deep listening is your inner compass and intuition – different from general knowledge and calling for presence in a moment. \r\n\r\n"+

                            "Deep listening is directly linked to your body. It is not a mental judgement of right or wrong. It is not based on fear, guilt or punishment. It is a physical barometer of your body in a specific life situation. For example, when you say lies in communication with another person, your body may tense or your breath get uncomfortable. If repeatedly ignored, this experience may lead to continuous dissatisfaction or even depression. Or take another example, if you eat foods that your body rejects, you will have an upset, low energy level or an allergic reaction. If continuously ignored, in order for you to hear the signs, your body will speak louder and shows signs of digestive problems, inflammation, or other signs of failure. \r\n\r\n"+
                            "To practice deep listening start learning the language of your own body. Start differentiating how your body responds when you act out of fear, anxiety or habit and when you choose something that is in line with your inner compass. The goal of the practice is choose what is authentic and meaningful to you and, by extension, to the world around. \r\n\r\n"+

                            "In meditation, you will direct your attention to the HEART SPACE – areas of your heart and gut. Your intuition is strongly linked to your body. Cultivating the body connection strengthens your ability to feel and listen inwardly. Consider several qualities of intuition that help you recognize the language of your body. It isn’t based on logical deduction or facts. Intuition states simple facts, doesn’t reason or calculate pros and cons, doesn’t resist anything happening, and is in no rush of making decisions. Typically, fear, guilt and shame are not associated with intuition. \r\n\r\n"+

                            "In your daily practice, connect with your gut and heart space by directing your awareness to those regions. If helpful, place your hands on the space of your heart or belly for a physical reminder. Connecting to your body in a moment helps you listen inwardly to any signs. Examples might include your body asking for a short break, a stretch or a walk, interruption of an unproductive conversation or activity, stopping eating when full. Check with your body in different daily contexts and start learning the way your body communicates with you. Start with little shifts applied to what you do daily. \r\n\r\n"+

                            "Enjoy learning your inner language! \r\n\r\n"+

                            "Kahlil Gibran, a Lebanese-American artist, poet, and writer of the beginning of 20th century, wrote on Self-Knowledge (from his book “The Prophet”) \r\n\r\n"+

                            "Your hearts know in silence the secrets of the days and the nights. \r\n\r\n"+
                            "But your ears thirst for the sound of your heart's knowledge. \r\n\r\n"+
                            "You would know in words that which you have always know in thought. \r\n\r\n"+
                            "You would touch with your fingers the naked body of your dreams. \r\n\r\n"+
                            "And it is well you should. \r\n\r\n"+
                            "The hidden well-spring of your soul must needs rise and run murmuring to the sea; \r\n\r\n"+
                            "And the treasure of your infinite depths would be revealed to your eyes. \r\n\r\n"+
                            "But let there be no scales to weigh your unknown treasure; \r\n\r\n"+
                            "And seek not the depths of your knowledge with staff or sounding line. \r\n\r\n"+
                            "For self is a sea boundless and measureless. \r\n\r\n"+
                            "Say not, 'I have found the truth,' but rather, 'I have found a truth.' \r\n\r\n"+
                            "Say not, 'I have found the path of the soul.' \r\n\r\n"+
                            "Say rather, 'I have met the soul walking upon my path.' \r\n\r\n"+
                            "For the soul walks upon all paths. \r\n\r\n"+
                            "The soul walks not upon a line, neither does it grow like a reed. \r\n\r\n"+
                            "The soul unfolds itself, like a lotus of countless petals. \r\n\r\n");
                break;

            case 8: exercise = new Exercise("WEEK 8:\r\n PERSPECTIVE TAKING",
                    "Being in a moment brings a sense of relaxation and a gradual letting go of control, realizing that doing your best and staying authentic to each moment is not only good enough but really the only thing you can do. Think of an unpleasant situation in your life that transformed into something unexpected. \r\n\r\n"+

                            "There is an old story of a farmer whose barn, with all the working bulls, burned down. The neighbor came by crying, lamenting the loss, to which the farmer responded, “Good or bad, time will show.” One morning, walking through fields, a strong wild horse came the farmer’s way. Delighted, the neighbor came with congratulations, to which the farmer responded “Good or bad, time will show.” Riding, the older farmer’s son fell off the wild horse and broke his leg. With pity, the neighbor offered condolences, to which the farmer responded “Good or bad, time will show.” Shortly after, a king of the land drafted all able men into the army to fight in war, and the older son was spared. \r\n\r\n"+

                            "The moral of the story: with day-to-day ups and downs, you only see a limited strip of your life. Many dreadful or dramatic situations present opportunities to connect with inner values and shift priorities. Not that dramatic events are needed to shake you up. The more you listen inwardly, moment to moment, the more you are able to shift and adjust accordingly, riding waves of life and yet not being overthrown by them. Daily perturbations are taken as opportunities to experience life completely – not fighting to get it your way but to engage with moments that come, remaining curious about life and where it is taking you. \r\n\r\n"+

                            "Think of perspective taking as if you are adopting a BIRD’S EYE VIEW and seeing a bigger picture. For example, in conversations, you may hold an opinion, yet remain open and flexible to learn and change. When taking a walk, pay attention to your surroundings and notice if you are moved to shift. When eating, listen to how certain foods affect you: do you feel drowsy, energetic, bloaty or healthy. The bird’s eye view requires awareness, curiosity and an adventurous mode. \r\n\r\n"+

                            "The focus of the meditation this week is PERSPECTIVE and INTERCONNECTEDNESS: paying attention to yourself in the context of the whole system: not only your thoughts but your entire body; not only you but the interconnected with others; not only humans but the planet as a whole. \r\n\r\n"+

                            "In your daily practice, take a break and become aware of your breath and body. Pay attention to your senses and notice the surroundings - space, colors, and sounds. Notice yourself in the context of other people, the city, and the planet. The practice is NOT to make yourself insignificant, but to feel CONNECTION. With awareness, ANY DAILY ACTIVITY (cooking, walking, talking, or working) is an opportunity to engage not only mentally, but physically, with awareness. \r\n\r\n"+

                            "Cultivate the perspective! \r\n\r\n");
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


    public static int getMeditationFile(int weekId){

        int medFileId=0;
        switch(weekId){

            case 1: medFileId = R.raw.meditation1; //week 1
                break;
            case 2: medFileId = R.raw.meditation2; //week 2
                break;
            case 3: medFileId = R.raw.meditation3; //week 3
                break;
            case 4: medFileId = R.raw.meditation4; //week 4
                break;
            case 5: medFileId = R.raw.meditation5; //week 5
                break;
            case 6: medFileId = R.raw.meditation6; //week 6
                break;
            case 7: medFileId = R.raw.meditation7; //week 7
                break;
            case 8: medFileId = R.raw.meditation8; //week 8
                break;
        }

        return medFileId;

    }

}
