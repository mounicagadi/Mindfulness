package justbe.mindfulness;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RunWith(AndroidJUnit4.class)
public class CalendarGeneratorTest {

    @Test
    public void testCalendarGeneration() {
        boolean userCreated = false;
        try {
            userCreated = App.getSession().authenticate("sanders_test_2", "testtest");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        if (userCreated) {
            Calendar[] calendars = CalenderGenerator.generateAwakeCalendars(5);
            for (int i = 1; i < calendars.length; i++) {
                Assert.assertTrue(calendars[i].getTimeInMillis() - calendars[i - 1].getTimeInMillis() > 1800000);
            }
        }
        else {
            Assert.assertTrue(false);
        }
    }
}
