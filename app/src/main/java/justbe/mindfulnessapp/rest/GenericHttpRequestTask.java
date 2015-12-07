package justbe.mindfulnessapp.rest;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.core.ParameterizedTypeReference;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import justbe.mindfulnessapp.App;
import justbe.mindfulnessapp.models.BaseModel;


/**
 * Generic HTTP Request Async Task. Used to interact with the API
 *
 * <p>
 *     To get a basic understanding of how this class works check out this tutorial from Spring
 *     https://spring.io/guides/gs/consuming-rest-android/
 *
 *     To learn more about how I abstracted this out with Generics and to understand the
 *     {@link ParameterizedTypeReference} class check out this SO article
 *     http://stackoverflow.com/questions/21987295/
 * </p>
 *
 * @author edhurtig
 */
public class GenericHttpRequestTask<S, T extends BaseModel> extends AsyncTask<Object, Void, ResponseEntity<T>> {

    Class provides;

    Class yields;


    public GenericHttpRequestTask() {

    }

    public GenericHttpRequestTask(Class provides, Class yields) {
        this.provides = provides;
        this.yields = yields;
    }

    public GenericHttpRequestTask(Object provides, Object yields) {
        this.provides = provides.getClass();
        this.yields = yields.getClass();
    }

    protected ResponseEntity<T> doInBackground(Object... params) {

        String url = (String) params[0];
        if (!url.startsWith("http")) {
            url = "https://secure-headland-8362.herokuapp.com" + url;
        }

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

        if (String.class == this.yields) {
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        } else {
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        }

        restTemplate.setErrorHandler(new SuppressHttpErrorHandler());
        List<MediaType> acceptableMediaTypes = new ArrayList();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(acceptableMediaTypes);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // If we are logged in provide auth header
        if (App.getSession().getSessionId() != null) {
            headers.set("Cookie", "sessionid=" + App.getSession().getSessionId());
        }

        // body
        HttpEntity<S> entity = new HttpEntity<S>(body, headers);

        Map<String, Object> uriVariables = new HashMap<String, Object>();
        ResponseEntity<T> response;
        try {
            // Send the request
            response = restTemplate.exchange(
                    url,
                    method,
                    entity,
                    this.yields,
                    uriVariables);
            Log.i("REST", url + " " + response.getStatusCode().toString());
            return response;
        } catch (Exception e) {
            Log.i("REST", url + " " + e.getMessage());
            return null;
        }
    }

    /**
     * Waits for a response from the API for a consistent amount of time
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    public ResponseEntity<T> waitForResponse() throws InterruptedException, ExecutionException, TimeoutException {
        return this.get(25, TimeUnit.SECONDS);
    }
}
