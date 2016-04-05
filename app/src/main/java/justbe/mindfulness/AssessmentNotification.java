package justbe.mindfulness;

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

public class AssessmentNotification extends BroadcastReceiver {
    static  int notificationID = 001;

    @Override
    public void onReceive(Context context, Intent intent) {

        int icon = R.drawable.lotus_2x;
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, StartAssessmentActivity.class);
        notificationIntent.putExtra("isMorningAssessment", false);

        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        long[] pattern = {500,500,500,500,500,500,500,500,500};
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentIntent(contentIntent)
                //.setContent(contentView)
                .setContentTitle("Mindfulness")
                .setContentText("Pending Daily Assessment Notification")
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


