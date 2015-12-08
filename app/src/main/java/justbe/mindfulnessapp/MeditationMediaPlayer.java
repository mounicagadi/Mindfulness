package justbe.mindfulnessapp;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import justbe.mindfulnessapp.models.MeditationSession;

/**
 * Media Player for meditations. Handles the media player and updating of meditaiton sessions
 */
public class MeditationMediaPlayer {

    /**
     * Fields
     */
    private MediaPlayer mediaPlayer;
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
        meditationSession.setPercent_completed(1.0);
        selectedDay = Util.getCurrentDayOfTheWeek();
        updateSelectedDay(selectedDay);
        setMeditationCompletion();
    }

    /**
     * Start the media player
     */
    public void start() {
        mediaPlayer.start();
    }

    /**
     * Stops the media player
     */
    public void stop(){
        mediaPlayer.stop();
    }

    /**
     * Getter and Setter for audioPlaying
     */
    public boolean getAudioPlaying() { return this.audioPlaying; }
    public void setAudioPlaying(boolean audioPlaying) { this.audioPlaying = audioPlaying; }

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
            Util.setTextViewToTime(currentAudioTimeText, currentTime);

            seekBar.setProgress((int)currentTime);
            seekBar.setProgress((int)currentTime);
            audioInfoUpdater.postDelayed(this, 100);
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
                    completeMeditation(selectedDay);
                    meditationSession.setMeditation_id(selectedWeek, selectedDay);
                    ServerRequests.updateMeditationSession(meditationSession, context);
                }
            };

    /**
     * Determines and displays which meditations are complete
     */
    private void setMeditationCompletion() {
        Integer day;
        MeditationSession[] meditationSessions = ServerRequests.getMeditationSessions(context);
        for(MeditationSession m : meditationSessions) {
            if(m.getPercent_completed() == 1.0){
                day = m.getMeditation_id() % 10;

                completeMeditation(day);
            }
        }
    }

    /**
     * Mark meditation as complete by changing the check to green for a day
     * @param day 0 Monday -> 6 Sunday to mark as complete
     */
    private void completeMeditation(Integer day){
        int currentImageViewId = context.getResources().getIdentifier(
                "MeditationImage" + day, "id", context.getPackageName());
        ImageView currentDayImageView = (ImageView) containerView.findViewById(currentImageViewId);

        currentDayImageView.setImageResource(R.drawable.check_green_2x);
    }
}
