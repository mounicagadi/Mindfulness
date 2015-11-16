package justbe.mindfulnessapp;

import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.security.KeyStore;
import java.util.*;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import android.content.Context;
import android.os.AsyncTask;

class PostTask extends AsyncTask<Object, Void, Integer>{
    private Exception exception;
    final Context context;

    public PostTask(Context context) {
        this.context = context;
    }

    protected Integer doInBackground(Object... params){
        try {
            String url_string = (String) params[0];
            Map<String, String> json_params = (Map<String,String>) params[1];

            KeyStore ks  = KeyStore.getInstance("BKS");
            final InputStream in = context.getResources().openRawResource(R.raw.mystore);
            try {
                ks.load(in, "ez24get".toCharArray());
            } finally {
                in.close();
            }

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(ks);

            SSLContext sslCtx = SSLContext.getInstance("TLS");
            sslCtx.init(null, tmf.getTrustManagers(), null);

            // Open the connection and set parameters
            URL url = new URL(url_string);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setSSLSocketFactory(sslCtx.getSocketFactory());
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            // Put the parameters into a JSON object
            JSONObject jsonParam = new JSONObject();
            for (Map.Entry<String, String> json_param : json_params.entrySet()) {
                jsonParam.put(json_param.getKey(), json_param.getValue());
            }

            // Send the POST
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(jsonParam.toString());
            out.close();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            this.exception = e;
            return null;
        }
    }

    protected void onPostExecute(Integer result) {

    }
}
