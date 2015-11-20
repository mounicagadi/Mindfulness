package justbe.mindfulnessapp;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.core.ParameterizedTypeReference;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Generic HTTP Request Async Task. Takes Request parameters in doInBackground and a Type Parameter
 * to define the model to parse the expected JSON HTTP response as.
 */
public class HttpRequestTask<S, T> extends AsyncTask<Object, Void, ResponseEntity<ResponseWrapper<T>>> {
    @Override
    protected ResponseEntity<ResponseWrapper<T>> doInBackground(Object... params) {
        try {

            String url = (String) params[0];

            HttpMethod method = HttpMethod.GET;

            if (params.length >= 2) {
                if (params[1] instanceof HttpMethod) {
                    method = (HttpMethod) params[1];
                } else {
                    method = HttpMethod.valueOf((String) params[1]);
                }
            }

            S body = null;
            if (params.length >= 3) {
                body = (S) params[2];
            }

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            List<MediaType> acceptableMediaTypes = new ArrayList();
            acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

            HttpHeaders headers = new HttpHeaders();

            headers.setAccept(acceptableMediaTypes);
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            // body
            HttpEntity<S> entity = new HttpEntity<S>(body, headers);

            Map<String, Object> uriVariables = new HashMap<String, Object>();
            uriVariables.put("content", "foobazzeee");

            // Send the request
            ResponseEntity<ResponseWrapper<T>> response = restTemplate.exchange(
                    url,
                    method,
                    entity,
                    new ParameterizedTypeReference<ResponseWrapper<T>>() {},
                    uriVariables);

            // Get the response
            return response;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
            return null;
        }
    }
}
