package justbe.mindfulnessapp.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.ResponseErrorHandler;
import java.io.IOException;
import org.springframework.http.client.ClientHttpResponse;


/**
 * Created by eddiehurtig on 11/21/15.
 */
public class SuppressHttpErrorHandler implements ResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        // Could HTTP Errors things here
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        HttpStatus.Series series = response.getStatusCode().series();
        return (HttpStatus.Series.CLIENT_ERROR.equals(series)
                || HttpStatus.Series.SERVER_ERROR.equals(series));
    }
}
