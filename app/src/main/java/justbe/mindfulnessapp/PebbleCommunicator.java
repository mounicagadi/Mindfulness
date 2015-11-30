package justbe.mindfulnessapp;

import android.content.Intent;

import com.getpebble.android.kit.PebbleKit;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import justbe.mindfulnessapp.App;

/**
 * Created by Sanders on 11/29/2015.
 */
public class PebbleCommunicator {
    private static PebbleCommunicator instance = null;

    protected PebbleCommunicator() {

    }

    public static PebbleCommunicator getInstance() {
        if (instance == null) {
            instance = new PebbleCommunicator();
        }
        return instance;
    }

    public void sendPebbleMessage(String title, String body) {
        if (PebbleKit.isWatchConnected(App.context())) {
            final Intent i = new Intent("com.getpebble.action.SEND_NOTIFICATION");

            final Map data = new HashMap();
            data.put("title", title);
            data.put("body", body);
            final JSONObject jsonData = new JSONObject(data);
            final String notificationData = new JSONArray().put(jsonData).toString();

            i.putExtra("messageType", "PEBBLE_ALERT");
            i.putExtra("sender", "PebbleKit Android");
            i.putExtra("notificationData", notificationData);
            App.context().sendBroadcast(i);
        }
    }
}
