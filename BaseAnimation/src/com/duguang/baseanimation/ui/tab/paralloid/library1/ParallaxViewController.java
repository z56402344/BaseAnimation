package com.duguang.baseanimation.ui.tab.paralloid.library1;

import android.view.View;
import android.widget.AbsListView;

import com.duguang.baseanimation.ui.tab.paralloid.library1.measure.AbsListScrollSize;

/**
 * Created by chris on 02/10/2013
 * Project: Paralloid
 */
public class ParallaxViewController<T extends View & Parallaxor> extends ParallaxController<T> implements AbsListView.OnScrollListener {

    public static <T extends View & Parallaxor> ParallaxViewController wrap(T wrappedView) {
        return new ParallaxViewController<T>(wrappedView);
    }

    protected ParallaxViewController(T wrappedView) {
        super(wrappedView);
        init();
    }

    /**
     * Init this controller
     */
    private void init() {
        if (mWrapped == null)
            throw new IllegalArgumentException("The wrapped view cannot be null");

        if (mWrapped instanceof AbsListView) {
            ((AbsListView) mWrapped).setOnScrollListener(this);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    /**
     * Used internally by the AbsListView implementation, calling through to this is unnecessary, the controller
     * will happily set the OnScrollListener
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        final int offsetY = AbsListScrollSize.calculateOffset(view);
        mIgnoreOnScrollListener = false;
        onScrollChanged(getWrapped(), mWrapped.getScrollX(), offsetY, mLastScrollX, mLastScrollY);
        mIgnoreOnScrollListener = true;
    }

}
