package justbe.mindfulnessapp.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import justbe.mindfulnessapp.models.BaseModel;

/**
 * Static utilities for Rest API Requests
 *
 * @author edhurtig
 */
public class RestUtil {
    /**
     * Checks whether the given Response was successfully fulfilled, if not then a
     * UserPresentableException is thrown
     * @param response The response from the server
     * @param <T> The type of the object in the {@link ResponseWrapper}
     */
    public static <T> void checkResponseHazardously(ResponseEntity<? extends BaseModel> response)
            throws UserDataError, RestClientException {
        if (response == null) {
            throw new RestClientException(
                    "We couldn't create your account at this time. Please try again later.");
        } else if (response.getStatusCode().value() >= HttpStatus.BAD_REQUEST.value() ||
                response.getStatusCode().value() < HttpStatus.SWITCHING_PROTOCOLS.value()) {
            if (response.getBody().getError() != null) {
                throw response.getBody().getError();
            }
        }
    }

    /**
     * Checks whether the given Response was successfully fulfilled
     *
     * @param response The Response from the server
     * @param <T> The type of the object in the {@link ResponseWrapper}
     * @return True if the response is deemed successful, otherwise false
     *
     */
    public static <T> boolean checkResponse(ResponseEntity<T> response) {
        if (response == null) {
            return false;
        } else if (response.getStatusCode().value() >= HttpStatus.BAD_REQUEST.value() ||
                response.getStatusCode().value() < HttpStatus.SWITCHING_PROTOCOLS.value()) {
            return false;
        } else {
            return true;
        }
    }
}
