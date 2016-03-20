package justbe.mindfulness;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by amit on 20-03-2016.
 */
public class PageContentsFragmentStartProgram extends PageContentsFragmentBase {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment, container, false);
        TextView contentTextView = (TextView) rootView.findViewById(R.id.text);
        String contents = ((StartProgramActivity)getActivity()).getContents(mPageNumber);
        contentTextView.setText(contents);
        contentTextView.setMovementMethod(new ScrollingMovementMethod());
        return rootView;
    }

}