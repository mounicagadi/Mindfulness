package justbe.mindfulnessapp.rest;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

import justbe.mindfulnessapp.models.Assessment;
import justbe.mindfulnessapp.models.User;

/**
 * Tests for the GenericHTTPRequestTask
 * @author edhurtig
 */
public class GenericHttpRequestTaskTest {

    @Test
    public void testGetAssessment() {
        GenericHttpRequestTask<String, Assessment> request = new GenericHttpRequestTask<String, Assessment>(String.class, Assessment.class);

        ResponseEntity<Assessment> response = request.doInBackground("/api/v1/assessment/5964/", HttpMethod.GET);

        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getBody());

        Assert.assertEquals(response.getStatusCode(), 200);

        Assert.assertEquals(response.getBody().getId(), new Integer(5964));
        Assert.assertEquals(response.getBody().getUser(), "/api/v1/user/1/");
    }

    @Test
    public void testGetUser() {
        GenericHttpRequestTask<String, User> request = new GenericHttpRequestTask(String.class, User.class);

        ResponseEntity<User> response = request.doInBackground("/api/v1/user/1/", HttpMethod.GET);

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(response.getStatusCode(), 200);

        assertEquals(response.getBody().getId(), new Integer(5964));
        assertEquals(response.getBody().getUsername(), "admin");
        assertEquals(response.getBody().getResource_uri(), "/api/v1/user/1/");
    }
}

