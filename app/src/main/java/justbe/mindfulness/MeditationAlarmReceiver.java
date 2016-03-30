package justbe.mindfulness;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import java.util.Calendar;
import java.util.Date;

import justbe.mindfulness.models.User;

/**
 * Created by amit on 27-03-2016.
 */
public class MeditationAlarmReceiver extends BroadcastReceiver {
    static  int notificationID = 001;
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.v("Med: ", " inside " + Calendar.getInstance().getTime());

        int icon = R.drawable.lotus_2x;
        //Notification notification = new Notification(icon, "Custom Notification", System.currentTimeMillis());

        System.out.println("Notification 1");
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        System.out.println("Notification 2");

        RemoteViews contentView = new RemoteViews(context.getApplicationContext().getPackageName(), R.layout.custom_notification);
        System.out.println("Notification 2-1");
        contentView.setTextViewText(R.id.title, (context.getString(R.string.app_name)));
        contentView.setTextViewText(R.id.text, "Pending daily meditation");
        //contentView.setPendingIntentTemplate(R.id.okButton,);
        //notification.contentView = contentView;

        System.out.println("Notification 3");
/*

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setAction("play");
        notificationIntent.putExtra("meditationNotificationIntent", "true");
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
*/

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.putExtra("meditationNotificationIntent", "true");
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        System.out.println("Notification 4");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentIntent(contentIntent)
                //.setContent(contentView)
                .setContentTitle("Mindfulness")
                .setContentText("Pending Meditation Notification")
                .setSmallIcon(icon)
                .setAutoCancel(true);

        System.out.println("Notification 5");
        notificationManager.notify(notificationID, builder.build());
        notificationID++;


        Log.v("Med: ", " finished");

    }



    public static class SnoozeMeditation extends BroadcastReceiver {

        static int snoozeCount = 0;
        @Override
        public void onReceive(Context context, Intent intent) {
            snoozeCount++;
            if(!(snoozeCount>3)){
                Log.v("Snooze Meditation","Count: "+snoozeCount);

                Calendar calendar = Calendar.getInstance();
                //schedule next notification at 30 minutes from now
                calendar.add(Calendar.MINUTE,30);
                String currentTime = calendar.getTime().toString();

                String time = currentTime.split(" ")[3];
                int hour = Integer.parseInt(time.split(":")[0]);
                int min = Integer.parseInt(time.split(":")[1]);
                int sec = Integer.parseInt(time.split(":")[2]);

                calendar.set(Calendar.HOUR_OF_DAY,hour);
                calendar.set(Calendar.MINUTE,min);
                calendar.set(Calendar.SECOND,sec);

                Intent snoozeIntent = new Intent(context, MeditationAlarmReceiver.class);
                PendingIntent pendingSnoozeIntent = PendingIntent.getBroadcast(
                        context, 0, snoozeIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, calendar.getTimeInMillis(), pendingSnoozeIntent);

            }else
                Log.v("Snooze Meditation","Meditation snoozed 3 times. Dismissed");

        }

    }

    public static class TakeMeditation extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.v("Meditation Notification","Accepted meditation session");


            Intent startMeditationIntent = new Intent(context, MainActivity.class);
            startMeditationIntent.putExtra("meditationNotificationIntent","play");
            context.startActivity(startMeditationIntent);

        }

    }



}


