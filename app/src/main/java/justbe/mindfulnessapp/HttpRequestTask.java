package justbe.mindfulnessapp;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.core.ParameterizedTypeReference;

import java.lang.reflect.Type;
import java.util.Map;


/**
 * Generic HTTP Request Async Task. Takes Request parameters in doInBackground and a Type Parameter
 * to define the model to parse the expected JSON HTTP response as.
 */
public class HttpRequestTask<T> extends AsyncTask<Object, Void, ResponseEntity<ResponseWrapper<T>>> {
    @Override
    protected ResponseEntity<ResponseWrapper<T>> doInBackground(Object... params) {
        try {

            String url = (String) params[0];

            HttpMethod method = HttpMethod.GET;

            if (params.length >= 2) {
                method = HttpMethod.valueOf((String) params[1]);
            }

            String body = "";
            if (params.length >= 3) {
                if (params[2] instanceof Map<?, ?>) {
                    for (String key : ((Map<String, String>) params[2]).keySet()) {
                        body += key + ": " + ((Map<String, String>) params[2]).get(key);
                    }
                }
            }

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            Type t = String.class;

            ResponseEntity<ResponseWrapper<T>> response = restTemplate.exchange(
                    url,
                    method,
                    null,
                    new ParameterizedTypeReference<ResponseWrapper<T>>() {
                    });

            return response;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
            return null;
        }
    }
}
