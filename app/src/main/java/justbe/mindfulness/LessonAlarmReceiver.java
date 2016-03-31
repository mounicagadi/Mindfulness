package justbe.mindfulness;

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

import justbe.mindfulness.models.User;

public class LessonAlarmReceiver extends BroadcastReceiver {

    static  int notificationID = 001;

    @Override
    public void onReceive(Context context, Intent intent) {



        int icon = R.drawable.lotus_2x;
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        String currentWeek = intent.getExtras().getString("currentWeek");
        Boolean checked = Boolean.parseBoolean(intent.getExtras().getString("checked"));
        Intent notificationIntent = new Intent(context, LessonActivity.class);
        notificationIntent.putExtra("week", currentWeek);
        notificationIntent.putExtra("completed", checked);
        Log.v("Lesson Receiver", "lesson intent data: week = " + currentWeek);

        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        long[] pattern = {500,500,500,500,500,500,500,500,500};
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentIntent(contentIntent)
                //.setContent(contentView)
                .setContentTitle("Mindfulness")
                .setContentText("Pending Weekly Exercise Notification")
                .setSmallIcon(icon)
                .setAutoCancel(true)
                .setLights(Color.BLUE, 500, 500)
                .setVibrate(pattern)
                .setStyle(new NotificationCompat.InboxStyle())
                .setSound(alarmSound);

        notificationManager.notify(notificationID, builder.build());
        notificationID++;

    }
}
