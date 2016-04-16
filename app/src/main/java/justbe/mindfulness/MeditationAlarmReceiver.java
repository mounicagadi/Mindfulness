package justbe.mindfulness;

import android.app.Activity;
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
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import java.util.Calendar;
import java.util.Date;

import justbe.mindfulness.models.User;

/**
 * Created by amit on 27-03-2016.
 */
public class MeditationAlarmReceiver extends BroadcastReceiver {
    static  int notificationID = 101;
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.v("Med: ", " inside " + Calendar.getInstance().getTime());

        int icon = R.drawable.lotus_2x;
        Notification notification = new Notification(icon, "Pending Daily Meditation", System.currentTimeMillis());


        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);


        Intent okIntent = new Intent(context, okButtonListener.class);
        okIntent.putExtra("NotificationID", "" + notificationID);
        PendingIntent pendingOkIntent = PendingIntent.getBroadcast(context, 0, okIntent, PendingIntent.FLAG_UPDATE_CURRENT);



        Intent snoozeIntent = new Intent(context, snoozeButtonListener.class);




        snoozeIntent.putExtra("NotificationID", "" + notificationID);
        PendingIntent pendingSnoozeIntent = PendingIntent.getBroadcast(context, 0, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT);






        RemoteViews contentView = new RemoteViews(context.getApplicationContext().getPackageName(), R.layout.custom_notification);
        /*contentView.setTextViewText(R.id.title, (context.getString(R.string.app_name)));*/
        contentView.setImageViewResource(R.id.imageView,R.drawable.lotus_2x);
        contentView.setTextViewText(R.id.text, "Pending daily meditation");
        //contentView.setPendingIntentTemplate(R.id.okButton,);
        contentView.setOnClickPendingIntent(R.id.okButton, pendingOkIntent);
        contentView.setOnClickPendingIntent(R.id.snoozeButton, pendingSnoozeIntent);

        notification.contentView = contentView;

        long[] pattern = {500, 500, 500, 500, 500, 500, 500, 500, 500};
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        /*notification.flags |= Notification.FLAG_AUTO_CANCEL; //clear the notification
        notification.flags |= Notification.COLOR_DEFAULT; // LED*/
        notification.vibrate = pattern; //Vibration
        notification.sound = alarmSound; // Sound


        notificationManager.notify(notificationID, notification);
        notificationID++;


        Log.v("Med: ", " finished");

    }






    public static class okButtonListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {



            Log.d("Med Notification", "ok button pressed");


            String notID = intent.getExtras().getString("NotificationID");
            Log.v("Notification ID",": "+notID);




            NotificationManager nMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            //nMgr.cancelAll();
            if(null == notID)
                nMgr.cancelAll();
            else{
                Integer notificationId = Integer.parseInt(notID);
                nMgr.cancel(notificationId);
            }

        //nMgr.cancel(notificationID);
            Intent notificationIntent = new Intent();
            notificationIntent.setClassName("justbe.mindfulness", "justbe.mindfulness.MainActivity");
            notificationIntent.putExtra("meditationNotificationIntent", "true");
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(notificationIntent);
          Log.d("Med Notification", "Main Activity invoked");




        }

    }

    public static class snoozeButtonListener extends BroadcastReceiver {

        static int snoozeCount = 1;
        @Override
        public void onReceive(Context snoozeContext, Intent intent) {
            Log.d("Med Notification", "snooze button pressed");


            String notID = intent.getExtras().getString("NotificationID");
            Log.v("Notification ID", ": " + notID);

            NotificationManager nMgr = (NotificationManager) snoozeContext.getSystemService(Context.NOTIFICATION_SERVICE);
            if(null == notID)
                nMgr.cancelAll();
            else{
                Integer notificationId = Integer.parseInt(notID);
                Log.v("Notification ID int ", notID);
                nMgr.cancel(notificationId);
            }




            Log.v("Snooze count", ""+snoozeCount);
            snoozeCount++;
            if(snoozeCount>3) {
             Log.v("Snooze","3rd time snooze. Dismissing notification");
                nMgr.cancelAll();
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


            min=min+2;
            Log.v("Med snooze hour",""+hour);
            Log.v("Med snooze min",""+min);

            try{

                AlarmManager snoozeAlarmManager = (AlarmManager)snoozeContext.getSystemService(Context.ALARM_SERVICE);

                //schedule the alarm
                Calendar snoozeTime = Calendar.getInstance();
                Log.v("Snooze Time Before", "" + snoozeTime.getTimeInMillis());
                snoozeTime.set(Calendar.HOUR_OF_DAY,hour);
                snoozeTime.set(Calendar.MINUTE, min);
                snoozeTime.set(Calendar.SECOND, 0);


                Log.v("Snooze Time", "" + snoozeTime.getTime());

                Log.v("Snooze Time After",""+snoozeTime.getTimeInMillis());
                Intent snooze = new Intent(snoozeContext, MeditationAlarmReceiver.class);
                PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(
                        snoozeContext, notificationID, snooze,0);
                snoozeAlarmManager.set(AlarmManager.RTC_WAKEUP, snoozeTime.getTimeInMillis(), snoozePendingIntent);

            }catch(Exception e){
                e.printStackTrace();
            }

        }
    }
}


