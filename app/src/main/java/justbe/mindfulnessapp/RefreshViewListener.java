package justbe.mindfulnessapp;

import java.util.Date;

/**
 * Interface that must be implemented by activities that use the TimePickerFragment
 */
public interface RefreshViewListener {
    /**
     * Refreshes the fields changed by the time picker
     * Called when the TimePickerFragment is dismissed
     */
    public void refreshView();

    /**
     * Saves the values changed by the time picker
     * Called when the user accepts the time picker values
     * @param buttonID The ID of the butotn that created the time picker
     * @param time The new time
     */
    public void saveTimes(int buttonID, Date time);
}