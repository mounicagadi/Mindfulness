package justbe.mindfulnessapp.rest;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import justbe.mindfulnessapp.models.Assessment;


/**
 * Created by eddiehurtig on 12/7/15.
 */
@RunWith(AndroidJUnit4.class)
public class GenericHttpRequestTaskTest extends TestCase {
        protected int value1, value2;

        // assigning the values
        protected void setUp(){
            value1=3;
            value2=3;
        }

        // test method to add two values
        public void testAdd(){
            double result= value1 + value2;
            assertTrue(result == 6);
        }

        @Test
        public void testGetAssessment() {
            GenericHttpRequestTask<String, Assessment> request = new GenericHttpRequestTask<String, Assessment>(String.class, Assessment.class);

            ResponseEntity<Assessment> response = request.doInBackground("/api/v1/assessment/5964/", HttpMethod.GET);

            assertNotNull(response);
            assertNotNull(response.getBody());

            assertEquals(response.getStatusCode(), 200);

            assertEquals(response.getBody().getId(), new Integer(5964));
            assertEquals(response.getBody().getUser(), "/api/v1/user/1/");

        }
}

