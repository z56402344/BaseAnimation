package com.duguang.baseanimation.ui.tab.paralloid.library1;

/**
 * Created by chris on 20/10/2013
 * Project: Paralloid
 */
public interface ParallaxorListener extends Parallaxor, OnScrollChangedListener {

    /**
     * Add a scroll listener you can listen too if you want to do something custom your end as well.
     *
     * @param onScrollChangedListener Null is valid (it will remove it if set).
     */
    public void setOnScrollListener(OnScrollChangedListener onScrollChangedListener);
}
