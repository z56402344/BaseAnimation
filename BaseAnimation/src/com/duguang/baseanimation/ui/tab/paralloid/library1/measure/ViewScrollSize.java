package com.duguang.baseanimation.ui.tab.paralloid.library1.measure;

import android.view.View;

/**
 * Created by chris on 23/10/2013
 * Project: Paralloid
 */

public class ViewScrollSize<T extends View> implements ScrollSize {

    final T viewToSize;

    public ViewScrollSize(T viewToSize) {
        this.viewToSize = viewToSize;
    }

    /**
     * Default impl returns the View width
     */
    @Override
    public int getMaxScrollX() {
        return viewToSize.getWidth();
    }

    /**
     * Default impl returns the View height
     */
    @Override
    public int getMaxScrollY() {
        return viewToSize.getHeight();
    }
}
