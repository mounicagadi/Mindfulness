package justbe.mindfulness;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import java.util.Calendar;

import justbe.mindfulness.models.User;

public class LessonAlarmReceiver extends BroadcastReceiver {

    static  int notificationID = 501;

    
    @Override
    public void onReceive(Context context, Intent intent) {


        Log.v("Lesson: ", " inside " + Calendar.getInstance().getTime());

        int icon = R.drawable.lotus_2x;
        Notification notification = new Notification(icon, "Pending Weekly Exercise", System.currentTimeMillis());


        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);



        String week = intent.getExtras().getString("week");
        Intent okIntent = new Intent(context, okButtonListener.class);
        okIntent.putExtra("week",week);

        PendingIntent pendingOkIntent = PendingIntent.getBroadcast(context, 0, okIntent, 0);


        Intent snoozeIntent = new Intent(context, snoozeButtonListener.class);
        snoozeIntent.putExtra("week",week);
        PendingIntent pendingSnoozeIntent = PendingIntent.getBroadcast(context, 0, snoozeIntent, 0);

        RemoteViews contentView = new RemoteViews(context.getApplicationContext().getPackageName(), R.layout.custom_notification);
        /*contentView.setTextViewText(R.id.title, (context.getString(R.string.app_name)));*/
        contentView.setImageViewResource(R.id.imageView, R.drawable.lotus_2x);
        contentView.setTextViewText(R.id.text, "Pending weekly  exercise");
        //contentView.setPendingIntentTemplate(R.id.okButton,);
        contentView.setOnClickPendingIntent(R.id.okButton, pendingOkIntent);
        contentView.setOnClickPendingIntent(R.id.snoozeButton, pendingSnoozeIntent);

        notification.contentView = contentView;



/*

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setAction("play");
        notificationIntent.putExtra("meditationNotificationIntent", "true");
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
*/

        /*Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.putExtra("meditationNotificationIntent", "true");
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);*/





        long[] pattern = {500, 500, 500, 500, 500, 500, 500, 500, 500};
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        notification.flags |= Notification.FLAG_AUTO_CANCEL; //clear the notification
        notification.flags |= Notification.COLOR_DEFAULT; // LED
        notification.vibrate = pattern; //Vibration
        notification.sound = alarmSound; // Sound




        //notificationManager.notify(notificationID, builder.build());


        notificationManager.notify(notificationID, notification);
        notificationID++;

        Log.v("Lesson: ", " finished");


    }


    public static class okButtonListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("Lesson Notification", "ok button pressed");
            // Integer notificationID = Integer.parseInt(intent.getStringExtra("NotificationID"));

            NotificationManager nMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nMgr.cancelAll();
            //nMgr.cancel(notificationID);
            Intent notificationIntent = new Intent();
            String week = intent.getExtras().getString("week");
            notificationIntent.putExtra("week",week);
            notificationIntent.setClassName("justbe.mindfulness", "justbe.mindfulness.LessonActivity");
            notificationIntent.putExtra("meditationNotificationIntent", "true");
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(notificationIntent);

            Log.d("Lesson Notification", "complete");

        }
    }

    public static class snoozeButtonListener extends BroadcastReceiver {
        static int snoozeCount = 1;
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("Lesson Notification", "snooze button pressed");

            //Integer notificationID = Integer.parseInt(intent.getStringExtra("NotificationID"));
            NotificationManager nMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//            nMgr.cancel(notificationID);
            nMgr.cancelAll();

            Log.v("Snooze count", "" + snoozeCount);
            snoozeCount++;
            if(snoozeCount>3) {
                Log.v("Snooze","3rd time snooze. Dismissing notification");
                return;
            }

            String currentTime = Calendar.getInstance().getTime().toString();
            System.out.println("Current time: " + currentTime);
            int hour = 0, min = 0, sec = 0;
            if(currentTime.contains(" ")){
                String time = currentTime.split(" ")[3];
                hour = Integer.parseInt(time.split(":")[0]);
                min = Integer.parseInt(time.split(":")[1]);
            }
            min+=2;
            Log.v("Assessment snooze hour",""+hour);
            Log.v("Assessment snooze min",""+min);


            try{

                AlarmManager alarmManager = (AlarmManager)App.context().getSystemService(Context.ALARM_SERVICE);

                //schedule the alarm
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,hour);
                calendar.set(Calendar.MINUTE, min);
                //calendar.add(Calendar.MINUTE, 5);
                calendar.set(Calendar.SECOND,0);
                Calendar now = Calendar.getInstance();
                Log.v("Time before adding day", "" + calendar.getTime());
                Log.v("now",""+now);

                /*if(now.after(calendar)) {
                    System.out.println("Meditation time crossed. Skipping for the day");
                    calendar.add(Calendar.DATE, 1);
                }*/

                Log.v("Time after adding day",""+calendar.getTime());
                Intent snoozeIntent = new Intent(context, LessonAlarmReceiver.class);
                String week = intent.getExtras().getString("week");
                snoozeIntent.putExtra("week",week);
                PendingIntent pendingSnoozeIntent = PendingIntent.getBroadcast(
                        context, 0, snoozeIntent,0);
                alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingSnoozeIntent);
                /*alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);*/

            }catch(Exception e){
                e.printStackTrace();
            }

        }
    }
}
