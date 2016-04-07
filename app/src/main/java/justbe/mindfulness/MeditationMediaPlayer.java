package justbe.mindfulness;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import justbe.mindfulness.models.MeditationSession;
import justbe.mindfulness.models.User;

/**
 * Media Player for meditations. Handles the media player and updating of meditaiton sessions
 */
public class MeditationMediaPlayer {

    /**
     * Fields
     */
    private MediaPlayer mediaPlayer;
    private User user;
    private View containerView;
    private Context context;
    private boolean audioPlaying;
    private SeekBar seekBar;
    private TextView currentAudioTimeText, totalAudioTimeText;
    private ImageButton audioButton;
    private double currentTime;
    private double totalTime;
    private Integer selectedDay;
    private Handler audioInfoUpdater;
    private MeditationSession meditationSession;
    private Integer selectedWeek;
    private Integer mediaID;
    ArrayList<Integer> ar = new ArrayList<Integer>();

    /**
     * MeditationMediaPlayer Constructor
     * @param context
     * @param mediaID
     * @param selectedWeek
     */
    public MeditationMediaPlayer(Context context, int mediaID, int selectedWeek) {

        this.context = context;
        this.mediaID = mediaID;
        this.selectedWeek = selectedWeek;
        this.containerView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);

        // create media player
        mediaPlayer = MediaPlayer.create(context, mediaID);


        // Initialize parts from view
        seekBar = (SeekBar) containerView.findViewById(R.id.volumeBar);
        currentAudioTimeText = (TextView) containerView.findViewById(R.id.currentTime);
        totalAudioTimeText = (TextView) containerView.findViewById(R.id.totalTime);
        audioButton = (ImageButton) containerView.findViewById(R.id.audioButton);

        // set meditation
        meditationSession = new MeditationSession();
        updatedDaysList();
        meditationSession.setPercent_completed(1.0);
        selectedDay = ar.indexOf(Util.getCurrentDayOfTheWeek());
        updateSelectedDay(selectedDay);
        setMeditationCompletion(selectedWeek);
    }

    /**
     * Start the media player
     */
   public void start() {
        audioPlaying = true;
        if(null == mediaPlayer)
            initMediaPlayer();

        mediaPlayer.start();
        /*Log.v("Med Player","start");
        Log.v("Med Player", "start isPlaying  = " + mediaPlayer.isPlaying());*/
    }

    /**
     * Stops the media player
     */
    public void stop(){
      currentTime = mediaPlayer.getCurrentPosition();

        if(currentTime < totalTime){
            mediaPlayer.pause();
            /*Log.v("Med Player","paused");
            Log.v("Med Player", "paused isPlaying  = " + mediaPlayer.isPlaying());*/

        }else{
            mediaPlayer.stop();

            /*Log.v("Med Player","stopped");
            Log.v("Med Player", "stop isPlaying  = " + mediaPlayer.isPlaying());
*/
        }
        audioPlaying  = false;





    }

    /**
     * Getter and Setter for audioPlaying
     */
    public boolean getAudioPlaying() { return this.audioPlaying; }
    public void setAudioPlaying(boolean audioPlaying) { this.audioPlaying = audioPlaying; }

public double getCurrentTime(){
        return currentTime;
    }
    /**
     * Getter and Setter for mediaID
     */
    public Integer getMediaID() { return this.mediaID; }
    public void setMediaID(Integer mediaID) { this.mediaID = mediaID; }

    /**
     * Sets the media player play/pause button to a resource
     * @param resourceID The resource to set the audioButton to
     */
    public void setAudioButtonImageResource(int resourceID) {
        audioButton.setImageResource(resourceID);
    }

    public void updatedDaysList()
    {
        Calendar c = Calendar.getInstance();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", java.util.Locale.getDefault());
        Date date = null;
        int day;

        try {
            date = format.parse(user.getCreated_at());
            c.setTime(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int day_created = c.get(Calendar.DAY_OF_WEEK);
        if(day_created == 1)
            day = 6;
        else
            day = day_created - 2;
        for(int i = day; i<7; i++)
            ar.add(i);
        for(int i = 0; i<day; i++)
            ar.add(i);
    }

    /**
     * Colors the currently selected day, updates selectedDay and audio file
     * @param newDay the newly selected day
     */
    public void updateSelectedDay(Integer newDay){
        int currentTextViewId = context.getResources().getIdentifier(
                "MeditationText" + selectedDay, "id", context.getPackageName());
        int newTextViewId = context.getResources().getIdentifier(
                "MeditationText" + newDay, "id", context.getPackageName());


        // remove styling from current day
        TextView currentDayTextView = (TextView) containerView.findViewById(currentTextViewId);
        currentDayTextView.setTextColor(ContextCompat.getColor(context, R.color.transparentLightGreen));
        currentDayTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.bpTransparent)) ;

        // add styling to new day
        TextView newDayTextView = (TextView) containerView.findViewById(newTextViewId);
        newDayTextView.setTextColor(ContextCompat.getColor(context, R.color.bpWhite));
        newDayTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.transparentOrange));
        selectedDay = newDay;

        initMediaPlayer();
    }

    private void initMediaPlayer() {
        if(null!=mediaPlayer)
             mediaPlayer.release();
        // TODO: change the meditation file based on day selected
        mediaPlayer = MediaPlayer.create(context, mediaID);
        mediaPlayer.setOnCompletionListener(endOfMeditationListener);

        currentTime = mediaPlayer.getCurrentPosition();
        totalTime = mediaPlayer.getDuration();

        // Set up progress bar and make it usable
        seekBar.setMax((int) totalTime);
        seekBar.setProgress((int)currentTime);
        seekBar.setOnSeekBarChangeListener(seekBarListener);

        // Initialize time indicators
        Util.setTextViewToTime(currentAudioTimeText, currentTime);
        Util.setTextViewToTime(totalAudioTimeText, totalTime);

        audioInfoUpdater = new Handler();
        audioInfoUpdater.postDelayed(UpdateCurrentTime,100);

        audioPlaying = false;
        audioButton.setImageResource(R.drawable.play);
    }

    // Updates the song time info every second
    private Runnable UpdateCurrentTime = new Runnable() {
        public void run() {
            currentTime = mediaPlayer.getCurrentPosition();
            Log.v("M-PLAYER ","Inside UpdateCurrentTime");
            Log.v("Pos ",""+mediaPlayer.getCurrentPosition());
            if(mediaPlayer.isPlaying()){
                Log.v("M-PLAYER ","Play Time "+mediaPlayer.getCurrentPosition());
            }else
                Log.v("M-PLAYER ","Pause Time "+mediaPlayer.getCurrentPosition());


            //Log.v("M-PLAYER ", "In Thread Current TIME: " + currentTime);
            if(mediaPlayer!=null && audioPlaying){

                Log.v("M-Player", "play update : " + currentTime);
                Util.setTextViewToTime(currentAudioTimeText, currentTime);
                seekBar.setProgress((int) currentTime);

                audioInfoUpdater.postDelayed(this, 100);

                //currentTime = getCurrentTime();
                //Log.v("Med Player","isPlaying = "+mediaPlayer.isPlaying());
                //Log.v("Med Player", "cur pos = " + currentTime);

            }
          //audioInfoUpdater.postDelayed(UpdateCurrentTime, 100);

        }
    };

    // Called when the seekbar is interacted with
    private SeekBar.OnSeekBarChangeListener seekBarListener =
            new SeekBar.OnSeekBarChangeListener() {

                @Override
                // Update to position in song user seeks to
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                    if (fromUser) {
                        mediaPlayer.seekTo(progress);
                        Util.setTextViewToTime(currentAudioTimeText, progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    // TODO Auto-generated method stub
                }
            };

    // Called when the audioPlayer reaches the end of a meditation
    private final MediaPlayer.OnCompletionListener endOfMeditationListener =
            new MediaPlayer.OnCompletionListener(){

                public void onCompletion(MediaPlayer mp){
                    int currentImageViewId = context.getResources().getIdentifier(
                            "MeditationImage" + selectedDay, "id", context.getPackageName());
                    ImageView currentDayImageView = (ImageView) containerView.findViewById(currentImageViewId);
                    Boolean selectedCompleted = Boolean.valueOf(currentDayImageView.getTag().toString());

                    audioPlaying = false;
                    audioButton.setImageResource(R.drawable.play);
					mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;

                    if(!selectedCompleted) {
                        currentDayImageView.setImageResource(R.drawable.check_green_2x);
                        currentDayImageView.setTag("true");
                        meditationSession.setMeditation_id(selectedWeek, selectedDay);
                        ServerRequests.updateMeditationSession(meditationSession, context);
                    }
                }
            };

    /**
     * Determines and displays which meditations are complete
     */
    private void setMeditationCompletion(int selectedWeek) {
        Integer day;
        MeditationSession[] meditationSessions = ServerRequests.getMeditationSessions(context);
        User user  = App.getSession().getUser();
        //int currentWeek =  selectedWeek;
        for(int i=0;i<7;i++)
            updatedNotCompleted(i);

        if(null!=meditationSessions){
        for(MeditationSession m : meditationSessions) {
            int meditationCurrentWeekValue = (m.getMeditation_id() - (selectedWeek * 10));
            System.out.println("Current Week : " + user.getCurrent_week());

            if(m.getPercent_completed() == 1.0 && meditationCurrentWeekValue >= 0 && meditationCurrentWeekValue<=6) {
                //day = m.getMeditation_id() % 10;
                day = meditationCurrentWeekValue;
                System.out.println("Meditation completed 1 . ID: "+ m.getMeditation_id());
                completeMeditation(day);
            }

        }
        }
    }

    /**
     * Mark meditation as complete by changing the check to green for a day
     * @param day 0 Monday -> 6 Sunday to mark as complete
     */
    private void completeMeditation(Integer day){
        Log.v("Meditation complete for", " "+day);
        int currentImageViewId = context.getResources().getIdentifier(
                "MeditationImage" + day, "id", context.getPackageName());
        ImageView currentDayImageView = (ImageView) containerView.findViewById(currentImageViewId);
        System.out.println("Meditation completed 2 ");
        currentDayImageView.setTag("true");
        currentDayImageView.setImageResource(R.drawable.check_green_2x);
    }

    // previous week content selected by user and if any of meditations
    // are incomplete, update the image as incomplete
    private void updatedNotCompleted(Integer day){
        Log.v("Meditation incomplete for", " "+day);
        int currentImageViewId = context.getResources().getIdentifier(
                "MeditationImage" + day, "id", context.getPackageName());
        ImageView currentDayImageView = (ImageView) containerView.findViewById(currentImageViewId);
        System.out.println("Meditation completed 3 ");
        currentDayImageView.setTag("false");
        currentDayImageView.setImageResource(R.drawable.check_gray_2x);
    }

}
