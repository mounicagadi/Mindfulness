package justbe.mindfulnessapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {
    /**
     * Performs an action after receiving an alarm
     * @param context The app context
     * @param intent The intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null && action.equals("android.intent.action.BOOT_COMPLETED")) {

        }
        else {
            if (action != null) {
                String[] actionInfo = action.split("|");
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.parseLong(actionInfo[1]));
                if (actionInfo[0] == "assessment") {
                    if (calendar.get(Calendar.HOUR_OF_DAY) < 12) {
                        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                                .setSmallIcon(R.drawable.lotus_2x)
                                .setContentTitle("Mindfulness")
                                .setContentText("Pending morning assessment");
                        // TODO: go to the correct morning assessment
                        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                                new Intent(context, MainActivity.class), 0);
                        notificationBuilder.setContentIntent(contentIntent);
                        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(001, notificationBuilder.build());
                    }
                    else {
                        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                                .setSmallIcon(R.drawable.lotus_2x)
                                .setContentTitle("Mindfulness")
                                .setContentText("Pending afternoon assessment");
                        // TODO: go to the correct afternoon assessment
                        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                                new Intent(context, MainActivity.class), 0);
                        notificationBuilder.setContentIntent(contentIntent);
                        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(001, notificationBuilder.build());
                    }
                }
                else if (actionInfo[0] == "pebble") {
                    PebbleCommunicator comms = PebbleCommunicator.getInstance();
                    comms.sendPebbleMessage("Mindfulness", "It's time for a momentary evaluation.");
                }
            }
        }
    }
}
