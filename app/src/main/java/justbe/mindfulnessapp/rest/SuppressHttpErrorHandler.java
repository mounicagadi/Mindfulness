package justbe.mindfulnessapp.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.ResponseErrorHandler;
import java.io.IOException;
import org.springframework.http.client.ClientHttpResponse;


/**
 * A custom error handler for the ResponseTemplate class from the spring framework.  Essentially
 * forces the framework to parse responses even if they have erronous HTTP status codes, 400 to 499,
 * which the framework normally refuses to parse and instead would raise an exception for.
 *
 * @author edhurtig
 */
public class SuppressHttpErrorHandler implements ResponseErrorHandler {

    /**
     * Handles an erroneous raw response
     * @param response the raw response
     * @throws IOException
     */
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        // Could HTTP Errors things here
    }

    /**
     * Determines whether the response is erroneous.
     * @param response The raw response
     * @return True if there is an unrecoverable client or server Error, otherwise false
     * @throws IOException
     */
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        HttpStatus.Series series = response.getStatusCode().series();
        return (HttpStatus.Series.CLIENT_ERROR.equals(series)
                || HttpStatus.Series.SERVER_ERROR.equals(series));
    }
}
