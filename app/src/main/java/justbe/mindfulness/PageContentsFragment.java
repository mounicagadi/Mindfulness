package justbe.mindfulness;

/**
 * Created by amit on 19-03-2016.
 */
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PageContentsFragment extends PageContentsFragmentBase {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment, container, false);
        TextView contentTextView = (TextView) rootView.findViewById(R.id.text);
        contentTextView.setText("");
        String contents = ((LessonActivity)getActivity()).getContents(mPageNumber);
        contentTextView.setText(contents);
        contentTextView.setMovementMethod(new ScrollingMovementMethod());
        return rootView;
    }

}