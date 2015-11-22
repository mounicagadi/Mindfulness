package justbe.mindfulnessapp.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

/**
 * Static utilities for Rest API Requests
 *
 * @author edhurtig
 */
public class RestUtil {
    /**
     * Checks whether the given Response was successfully fufulled, if not then a
     * UserPresentableException is thrown
     * @param response
     * @param <T>
     */
    public static <T> void checkResponseHazardously(ResponseEntity<ResponseWrapper<T>> response)
            throws UserDataError, RestClientException {
        if (response == null) {
            throw new RestClientException(
                    "We couldn't create your account at this time. Please try again later.");
        } else if (response.getStatusCode() != HttpStatus.CREATED) {
            if (response.getBody().getError() != null) {
                throw response.getBody().getError();
            }
        }
    }
}
