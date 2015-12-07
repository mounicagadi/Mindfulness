package justbe.mindfulnessapp;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;

import java.util.Calendar;

@RunWith(AndroidJUnit4.class)
public class CalendarGeneratorTest {

    @Test
    public void testCalendarGeneration() {
        boolean userCreated = App.getSession().authenticate("", "");
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
