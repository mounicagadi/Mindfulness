package justbe.mindfulness.rest;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.internal.runners.JUnit44RunnerImpl;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

import justbe.mindfulness.BuildConfig;
import justbe.mindfulness.models.User;
import org.springframework.http.HttpStatus;

/**
 * Tests for the GenericHTTPRequestTask
 * @author edhurtig
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class GenericHttpRequestTaskTest {

//    @Before
//    public void setUpClass() {
//        App.setSession(new Session(RuntimeEnvironment.application));
//
//        try {
//            App.getSession().authenticate("test", "testtest");
//        } catch (Exception e) {
//            e.printStackTrace();
//            Assert.fail(e.getMessage());
//            System.exit(1); // Couldn't find a fail everything OMG WTF BBQ action
//        }
//    }
//
//    @After
//    public void tearDownClass() {
//        try {
//            App.getSession().invalidate();
//        } catch (Exception e) {
//            e.printStackTrace();
//            Assert.fail(e.getMessage());
//            System.exit(1);  // Again, couldn't find a fail everything OMG WTF BBQ action
//        }
//
//        App.setSession(null);
//    }

//    @Test
//    public void testGetAssessment() {
//        GenericHttpRequestTask<String, Assessment> request = new GenericHttpRequestTask<String, Assessment>(String.class, Assessment.class);
//
//        ResponseEntity<Assessment> response = request.doInBackground("/api/v1/assessment/5964/", HttpMethod.GET);
//
//        Assert.assertNotNull(response);
//        Assert.assertNotNull(response.getBody());
//
//        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
//
//        Assert.assertEquals(new Integer(5964), response.getBody().getId());
//        Assert.assertEquals("/api/v1/user/1/", response.getBody().getUser());
//    }

    @Test
    public void testGetUser() {
        GenericHttpRequestTask<String, User> request = new GenericHttpRequestTask(String.class, User.class);

        ResponseEntity<User> response = request.doInBackground("/api/v1/user/1/", HttpMethod.GET);

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(new Integer(1), response.getBody().getId());
        assertEquals("admin", response.getBody().getUsername());
        assertEquals("/api/v1/user/1/", response.getBody().getResource_uri());
    }
}

