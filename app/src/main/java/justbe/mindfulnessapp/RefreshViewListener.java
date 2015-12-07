package justbe.mindfulnessapp;

import java.util.Date;

public interface RefreshViewListener {
    public void refreshView();
    public void saveTimes(int buttonID, Date time);
}