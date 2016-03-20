package justbe.mindfulness;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;

/**
 * Receives assessment and pebble alarms
 */
public class AlarmReceiver extends BroadcastReceiver {

    /**
     * Performs an action after receiving an alarm
     * @param context The app context
     * @param intent The intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        System.out.println("Crash Received: "+action);
        // Make sure we have a action and it is not a boot message
        if (action != null && !action.equals("android.intent.action.BOOT_COMPLETED")) {
            String[] actionInfo = action.split("##");
            Calendar calendar = Calendar.getInstance();
            System.out.println("Crash: "+actionInfo[1]);
            calendar.setTimeInMillis(Long.parseLong(actionInfo[1]));
            System.out.println("**********  Receive 1 ****************");
            // Assessment notification
            if (actionInfo[0] == "assessment") {
                if (calendar.get(Calendar.HOUR_OF_DAY) < 12) {
                    createAssessmentNotification(true, context);
                }
                else {
                    createAssessmentNotification(false, context);
                }
            } else if (actionInfo[0] == "pebble") { // Pebble Notification
                PebbleCommunicator comms = PebbleCommunicator.getInstance();
                comms.sendPebbleMessage(context.getString(R.string.app_name), context.getString(R.string.timeForAssessment));
            } else {
                // Got an action that we don't care about so throw it away
            }
        }
    }

    private void createAssessmentNotification(Boolean isMorningAssessment, Context context) {
        int notificationID = 001;

        // Set assessment specific variables
        int pendingIntentID = isMorningAssessment ? 0 : 1;
        String notificationMessage = isMorningAssessment ?
                context.getString(R.string.pendingMorning) :
                context.getString(R.string.pendingAfternoon);

        System.out.println("**********  Notify 1 ****************");
        // Build notification
        Intent startAssessmentIntent = new Intent(context, StartAssessmentActivity.class);
        startAssessmentIntent.putExtra("isMorningAssessment", isMorningAssessment);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder.setSmallIcon(R.drawable.lotus_2x);
        notificationBuilder.setContentTitle(context.getString(R.string.app_name));
        notificationBuilder.setContentText(notificationMessage);
        PendingIntent contentIntent = PendingIntent.getActivity(context, pendingIntentID,
               startAssessmentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(contentIntent);
        System.out.println("**********  Notify 2 ****************");
        // Display the notification
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID, notificationBuilder.build());

        System.out.println("**********  Notify 3 ****************");
    }


}
