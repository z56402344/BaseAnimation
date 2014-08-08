package com.duguang.baseanimation.ui.tab.paralloid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.tab.paralloid.library.ParallaxScrollView;

/**
 * A dummy fragment representing a section of the app, but that simply
 * displays dummy text.
 */
public class ParallaxBackgroundFragment extends Fragment {

    public ParallaxBackgroundFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_tab_paralloid__fragment_parallax_background, container, false);
        ParallaxScrollView scrollView = (ParallaxScrollView) rootView.findViewById(R.id.scroll_view);
        scrollView.parallaxViewBackgroundBy(scrollView, getResources().getDrawable(R.drawable.tab_paralloid_example_image), .2f);

        return rootView;
    }
}