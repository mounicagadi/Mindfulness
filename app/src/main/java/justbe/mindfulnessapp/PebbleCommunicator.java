package justbe.mindfulnessapp;

import android.content.Intent;

import com.getpebble.android.kit.PebbleKit;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import justbe.mindfulnessapp.App;

public class PebbleCommunicator {
    private static PebbleCommunicator instance = null;

    protected PebbleCommunicator() {

    }

    /**
     * Gets an instance of the PebbleCommunicator
     * @return The instance of the PebbleCommunicator
     */
    public static PebbleCommunicator getInstance() {
        if (instance == null) {
            instance = new PebbleCommunicator();
        }
        return instance;
    }

    /**
     * Checks to see if there is a Pebble connected to the phone
     * @return If a Pebble is connected to the phone
     */
    public boolean checkPebbleConnection() {
        return PebbleKit.isWatchConnected(App.context());
    }

    /**
     * Sends the specified notification to the connected Pebble
     * A Pebble must be connected to the phone for this to work
     * @param title The notification title
     * @param body The notification body
     */
    public void sendPebbleMessage(String title, String body) {
        if (checkPebbleConnection()) {
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
