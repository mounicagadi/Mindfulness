package justbe.mindfulness;

/**
 * Created by amit on 19-03-2016.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class TestPagerAdapter extends FragmentPagerAdapter {

    int pages;
    String targetActivity; // either LessonActivity or StartProgramActivity
    public TestPagerAdapter(FragmentManager fragmentManager, int totalPages, String targetActivity) {
        super(fragmentManager);
        this.pages = totalPages;
        this.targetActivity = targetActivity;
    }

    @Override
    public Fragment getItem(int position) {
        return PageContentsFragmentBase.create(position,targetActivity);
    }

    @Override
    public int getCount() {
        return pages;
    }

}
