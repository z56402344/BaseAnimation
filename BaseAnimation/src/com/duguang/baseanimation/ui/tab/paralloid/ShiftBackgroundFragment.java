package com.duguang.baseanimation.ui.tab.paralloid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.tab.paralloid.library1.Parallaxor;

/**
 * A dummy fragment representing a section of the app, but that simply
 * displays dummy text.
 */
public class ShiftBackgroundFragment extends Fragment {

    public ShiftBackgroundFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_tab_paralloid__fragment_parallax_background, container, false);
        ScrollView scrollView = (ScrollView) rootView.findViewById(R.id.scroll_view);
        if (scrollView instanceof Parallaxor) {
            ((Parallaxor) scrollView).parallaxViewBackgroundBy(scrollView, getResources().getDrawable(R.drawable.tab_paralloid_example_rainbow), 1f);
        }

        return rootView;
    }
}