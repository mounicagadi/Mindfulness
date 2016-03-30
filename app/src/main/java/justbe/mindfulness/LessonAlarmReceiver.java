package justbe.mindfulness;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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

        Intent notificationIntent = new Intent(context, LessonActivity.class);
        notificationIntent.putExtra("week", intent.getStringExtra("currentWeek"));
        Log.v("Lesson Receiver", "lesson intent data: week = " + intent.getStringExtra("currentWeek"));

        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentIntent(contentIntent)
                //.setContent(contentView)
                .setContentTitle("Mindfulness")
                .setContentText("Pending Weekly Exercise Notification")
                .setSmallIcon(icon)
                .setAutoCancel(true);

        notificationManager.notify(notificationID, builder.build());
        notificationID++;

    }
}
