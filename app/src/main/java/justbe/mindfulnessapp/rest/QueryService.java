package justbe.mindfulnessapp.rest;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.ResultReceiver;

import java.util.ArrayList;

/**
 * Created by eddiehurtig on 11/20/15.
 */
public class QueryService extends IntentService {

    public QueryService(String name) {
        super(name);
    }

    protected void onHandleIntent(Intent intent) {
        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
        String command = intent.getStringExtra("command");
        Bundle b = new Bundle();
        if(command.equals("query")) {
            receiver.send(QueryStatus.STATUS_RUNNING, Bundle.EMPTY);
            try {
                // get some data or something
                b.putParcelableArrayList("results", new ArrayList<Parcelable>());
                receiver.send(QueryStatus.STATUS_FINISHED, b);
            } catch(Exception e) {
                b.putString(Intent.EXTRA_TEXT, e.toString());
                receiver.send(QueryStatus.STATUS_ERROR, b);
            }
        }
    }
}