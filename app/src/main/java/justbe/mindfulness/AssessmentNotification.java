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
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.RemoteViews;
import java.util.Calendar;

public class AssessmentNotification extends BroadcastReceiver {
    static  int notificationID = 1001;

     @Override
    public void onReceive(Context context, Intent intent) {


        Log.v("Assessment: ", " inside " + Calendar.getInstance().getTime());

        int icon = R.drawable.lotus_2x;
        Notification notification = new Notification(icon, "Pending Daily Assessment", System.currentTimeMillis());


        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);



        Intent okIntent = new Intent(context, okButtonListener.class);
        okIntent.putExtra("NotificationID",notificationID);
        PendingIntent pendingOkIntent = PendingIntent.getBroadcast(context, 0, okIntent, 0);


        Intent snoozeIntent = new Intent(context, snoozeButtonListener.class);
        snoozeIntent.putExtra("NotificationID", notificationID);
        PendingIntent pendingSnoozeIntent = PendingIntent.getBroadcast(context, 0, snoozeIntent, 0);

        RemoteViews contentView = new RemoteViews(context.getApplicationContext().getPackageName(), R.layout.custom_notification);
        /*contentView.setTextViewText(R.id.title, (context.getString(R.string.app_name)));*/
        contentView.setImageViewResource(R.id.imageView, R.drawable.lotus_2x);
        contentView.setTextViewText(R.id.text, "Pending daily assessment");
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

        Log.v("Assessment: ", " finished");


    }

    public static class okButtonListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("Assessment Notification", "ok button pressed");
            // Integer notificationID = Integer.parseInt(intent.getStringExtra("NotificationID"));

            NotificationManager nMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nMgr.cancelAll();
            //nMgr.cancel(notificationID);
            Intent notificationIntent = new Intent();
            notificationIntent.setClassName("justbe.mindfulness", "justbe.mindfulness.StartAssessmentActivity");
            notificationIntent.putExtra("meditationNotificationIntent", "true");
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(notificationIntent);

            Log.d("Assessment Notification", "Main Activity invoked");

        }
    }

    public static class snoozeButtonListener extends BroadcastReceiver {
        static int snoozeCount = 1;
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("Assessment Notification", "snooze button pressed");

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
            min+=5;
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
                Intent snoozeIntent = new Intent(context, StartAssessmentActivity.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        context, 0, snoozeIntent,0);
                alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                /*alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);*/

            }catch(Exception e){
                e.printStackTrace();
            }

        }
    }

}


