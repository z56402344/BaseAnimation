package com.duguang.baseanimation.ui.tab.paralloid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.tab.paralloid.library1.Parallaxor;

/**
 * A dummy fragment representing a section of the app, but that simply
 * displays dummy text.
 */
public class ParallaxViewUpFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";

    public ParallaxViewUpFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_tab_paralloid__fragment_home_dummy, container, false);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.image_view);
        ScrollView scrollView = (ScrollView) rootView.findViewById(R.id.scroll_view);
        if (scrollView instanceof Parallaxor) {
            ((Parallaxor) scrollView).parallaxViewBy(imageView, 0.5f);
        }

        return rootView;
    }
}