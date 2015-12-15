package justbe.mindfulness;

import android.support.test.runner.AndroidJUnit4;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;

@RunWith(AndroidJUnit4.class)
public class UtilTest {
    @Test
    public void dateToUserProfileStringTest1() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 1, 1, 6, 23, 15);
        String result = Util.dateToUserProfileString(calendar.getTime());
        Assert.assertEquals("06:23", result);
    }

    @Test
    public void dateToUserProfileStringTest2() {
        Date date = new Date();
        String expected = Util.dateToUserProfileString(date);
        Assert.assertEquals(expected, Util.dateToUserProfileString(null));
    }

    @Test
    public void dateToDisplayStringTest1() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 1, 1, 6, 23, 15);
        String result = Util.dateToDisplayString(calendar.getTime());
        Assert.assertEquals("06:23 AM", result);
    }

    @Test
    public void dateToDisplayStringTest2() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 1, 1, 16, 23, 15);
        String result = Util.dateToDisplayString(calendar.getTime());
        Assert.assertEquals("04:23 PM", result);
    }

    @Test
    public void dateToDisplayStringTest3() {
        String result = Util.dateToDisplayString(null);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String ampm = "";
        if (calendar.get(Calendar.HOUR_OF_DAY) >= 12) {
            ampm = "PM";
        }
        else {
            ampm = "AM";
        }
        String expected = "";
        if (calendar.get(Calendar.HOUR) < 10) {
            expected += "0";
        }
        expected += calendar.get(Calendar.HOUR) + ":" +
                calendar.get(Calendar.MINUTE) + " " +
                ampm;
        Assert.assertEquals(expected, result);
    }

    @Test
    public void setTextViewToTimeTest1() {
        TextView tv = new TextView(App.context());
        // 1395000 = 23:15 in milliseconds
        Util.setTextViewToTime(tv, 1395000);
        String result = tv.getText().toString();
        Assert.assertEquals("23:15", tv.getText().toString());
    }

    @Test
    public void samePasswordTest1() {
        TextView p1 = new TextView(App.context());
        TextView p2 = new TextView(App.context());
        p1.setText("testtest");
        p2.setText("testtest");
        Assert.assertTrue(Util.samePassword(p1, p2));
    }

    @Test
    public void samePasswordTest2() {
        TextView p1 = new TextView(App.context());
        TextView p2 = new TextView(App.context());
        p1.setText("testtest");
        p2.setText("nottesttest");
        Assert.assertFalse(Util.samePassword(p1, p2));
    }

    @Test
    public void getCurrentDayOfTheWeekTest1() {
        // this is a dumb test
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        Integer expected = 0;
        if (day == 1) {
            expected = 6;
        }
        else if (day == 2) {
            expected = 0;
        }
        else if (day == 3) {
            expected = 1;
        }
        else if (day == 4) {
            expected = 2;
        }
        else if (day == 5) {
            expected = 3;
        }
        else if (day == 6) {
            expected = 4;
        }
        else if (day == 7) {
            expected = 5;
        }
        Assert.assertEquals(expected, Util.getCurrentDayOfTheWeek());
    }

    @Test
    public void getTextViewForWeekTest1() {
        LayoutInflater inflater = (LayoutInflater)App.context().getSystemService(App.context().LAYOUT_INFLATER_SERVICE);
        View pw_view = inflater.inflate(R.layout.check_progress_popup, null);
        Assert.assertEquals("Week 1", Util.getTextViewForWeek(pw_view, 1).getText());
    }

    @Test
    public void getTextViewForWeekTest2() {
        LayoutInflater inflater = (LayoutInflater)App.context().getSystemService(App.context().LAYOUT_INFLATER_SERVICE);
        View pw_view = inflater.inflate(R.layout.check_progress_popup, null);
        Assert.assertEquals("Week 2", Util.getTextViewForWeek(pw_view, 2).getText());
    }

    @Test
    public void getTextViewForWeekTest3() {
        LayoutInflater inflater = (LayoutInflater)App.context().getSystemService(App.context().LAYOUT_INFLATER_SERVICE);
        View pw_view = inflater.inflate(R.layout.check_progress_popup, null);
        Assert.assertEquals("Week 3", Util.getTextViewForWeek(pw_view, 3).getText());
    }

    @Test
    public void getTextViewForWeekTest4() {
        LayoutInflater inflater = (LayoutInflater)App.context().getSystemService(App.context().LAYOUT_INFLATER_SERVICE);
        View pw_view = inflater.inflate(R.layout.check_progress_popup, null);
        Assert.assertEquals("Week 4", Util.getTextViewForWeek(pw_view, 4).getText());
    }

    @Test
    public void getTextViewForWeekTest5() {
        LayoutInflater inflater = (LayoutInflater)App.context().getSystemService(App.context().LAYOUT_INFLATER_SERVICE);
        View pw_view = inflater.inflate(R.layout.check_progress_popup, null);
        Assert.assertEquals("Week 5", Util.getTextViewForWeek(pw_view, 5).getText());
    }

    @Test
    public void getTextViewForWeekTest6() {
        LayoutInflater inflater = (LayoutInflater)App.context().getSystemService(App.context().LAYOUT_INFLATER_SERVICE);
        View pw_view = inflater.inflate(R.layout.check_progress_popup, null);
        Assert.assertEquals("Week 6", Util.getTextViewForWeek(pw_view, 6).getText());
    }

    @Test
    public void getTextViewForWeekTest7() {
        LayoutInflater inflater = (LayoutInflater)App.context().getSystemService(App.context().LAYOUT_INFLATER_SERVICE);
        View pw_view = inflater.inflate(R.layout.check_progress_popup, null);
        Assert.assertEquals("Week 7", Util.getTextViewForWeek(pw_view, 7).getText());
    }

    @Test
    public void getTextViewForWeekTest8() {
        LayoutInflater inflater = (LayoutInflater)App.context().getSystemService(App.context().LAYOUT_INFLATER_SERVICE);
        View pw_view = inflater.inflate(R.layout.check_progress_popup, null);
        Assert.assertEquals("Week 8", Util.getTextViewForWeek(pw_view, 8).getText());
    }

    @Test
    public void getTextViewForWeekTest9() {
        LayoutInflater inflater = (LayoutInflater)App.context().getSystemService(App.context().LAYOUT_INFLATER_SERVICE);
        View pw_view = inflater.inflate(R.layout.check_progress_popup, null);
        Assert.assertNull(Util.getTextViewForWeek(pw_view, 0));
    }

    @Test
    public void getImageViewForWeekTest1() {
        LayoutInflater inflater = (LayoutInflater)App.context().getSystemService(App.context().LAYOUT_INFLATER_SERVICE);
        View pw_view = inflater.inflate(R.layout.check_progress_popup, null);
        ImageView iv = Util.getImageViewForWeek(pw_view, 1);
        Assert.assertNotNull(iv);
    }

    @Test
    public void getImageViewForWeekTest2() {
        LayoutInflater inflater = (LayoutInflater)App.context().getSystemService(App.context().LAYOUT_INFLATER_SERVICE);
        View pw_view = inflater.inflate(R.layout.check_progress_popup, null);
        ImageView iv = Util.getImageViewForWeek(pw_view, 0);
        Assert.assertNull(iv);
    }
}
