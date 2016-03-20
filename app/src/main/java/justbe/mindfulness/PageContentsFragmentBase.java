package justbe.mindfulness;

/**
 * Created by amit on 19-03-2016.
 */
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class PageContentsFragmentBase extends Fragment {

    public static final String ARG_PAGE = "page";
    protected int mPageNumber;

    protected List<Object> listItems = new ArrayList<Object>();
    protected AlertDialog optionsDialog = null;

    public PageContentsFragmentBase() {
    }


    public static PageContentsFragmentBase create(int pageNumber, String targetFragmentBase) {
        PageContentsFragmentBase fragment = null;

        if(targetFragmentBase.equals("LessonActivity"))
            fragment = new PageContentsFragment();
        else if(targetFragmentBase.equals("StartProgramActivity"))
            fragment = new PageContentsFragmentStartProgram();

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }

}
