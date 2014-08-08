package com.duguang.baseanimation.ui.tab.paralloid.library1;

import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.duguang.baseanimation.ui.tab.paralloid.library1.graphics.ParallaxDrawable;
import com.duguang.baseanimation.ui.tab.paralloid.library1.transform.Transformer;
import com.duguang.baseanimation.ui.tab.paralloid.library1.utlis.ParallaxHelper;

/**
 * Parallax Controller that take input from anything.
 * <p/>
 * Created by chris on 23/10/2013
 * Project: Paralloid
 */
public class ParallaxController<T extends Object> implements ParallaxorListener {

    public static final String TAG = ParallaxController.class.getSimpleName();
    /**
     * HashMap which contains the parallaxed views.
     */
    protected WeakHashMap<View, ParallaxViewInfo> mViewHashMap;
    /**
     * The Background of Views to Parallax
     */
    protected WeakHashMap<View, ParallaxDrawable> mParallaxDrawableMap;
    /**
     * The Optional Scroll Changed Listener for the user to listen to scroll events.
     */
    protected OnScrollChangedListener mScrollChangedListener;
    /**
     * Gets set to true if a View which has its own OnScrollListener
     */
    protected boolean mIgnoreOnScrollListener;
    //
    protected int mLastScrollX = 0;
    protected int mLastScrollY = 0;

    /**
     * The Wrapped Object
     */
    protected final T mWrapped;

    protected ParallaxController(T wrapped) {
        mWrapped = wrapped;
    }

    public T getWrapped() {
        return mWrapped;
    }

    /**
     * Add a view to be parallax'd by. If already set this will replace the current factor.
     *
     * @param view       passing in yourself will throw an exception.
     * @param multiplier passing in
     */
    public void parallaxViewBy(View view, float multiplier) {
        parallaxViewBy(view, null, multiplier);
    }

    @Override
    public void parallaxViewBy(View view, Transformer transformer, float multiplier) {
        if (view == null) return;
        if (view == mWrapped)
            throw new IllegalArgumentException("You can't parallax yourself, this would end badly, Parallax other Views");
        checkViewMap();

        mViewHashMap.put(view, new ParallaxViewInfo(multiplier, transformer));
        // We force this to update the view just added
        onScrollChanged(mLastScrollX, mLastScrollY, mLastScrollX, mLastScrollY, true);
    }

    /**
     * @param view       View which to attach the drawable to. (null will do nothing)
     * @param drawable   Drawable to Parallax (null will do nothing)
     * @param multiplier How much to move it by, 1 would be a 1:1 relationship. 0.5 would move the.
     *                   Passing in 0 will make the background not move at all.
     */
    @Override
    public void parallaxViewBackgroundBy(final View view, final Drawable drawable, final float multiplier) {
        if (view == null) return;
        checkBackgroundMap();

        final ParallaxDrawable parallaxBackground = ParallaxHelper.getParallaxDrawable(drawable, multiplier);
        ParallaxHelper.setParallaxBackground(view, parallaxBackground);
        mParallaxDrawableMap.put(view, parallaxBackground);
    }

    /**
     * Feel free to implement {@link OnScrollChangedListener} to get call
     * backs to the wrapped view for scroll changed events.
     * <p/>
     * <b>Note</b>: this will get called, AFTER any parallax modification.
     *
     * @param onScrollChangedListener Null is valid (it will remove it if set).
     */
    @Override
    public void setOnScrollListener(OnScrollChangedListener onScrollChangedListener) {
        mScrollChangedListener = onScrollChangedListener;
    }

    /**
     * Used to do parallax, Your implementation should call directly through to this. Some implementations ignore this
     * on purpose (e.g. the ListView)
     *
     * @param who if Who != getWrapped we ignore scrolling, only the object the controller is attached too can
     *            scroll it.
     */
    @Override
    public final void onScrollChanged(Object who, int l, int t, int oldl, int oldt) {
        if (who != getWrapped() || mIgnoreOnScrollListener) return;
        onScrollChanged(l, t, oldl, oldt, false);
    }

    protected final void onScrollChanged(int offsetX, int offsetY, int oldOffsetX, int oldOffsetY, boolean force) {
        if (offsetX != oldOffsetX || offsetY != oldOffsetY || force) {
            doScrollChanged(offsetX, offsetY, oldOffsetX, oldOffsetY);
        }
    }

    /**
     * Will do the scroll changed stuff.
     *
     * @param x    currentX of Parallaxor View
     * @param y    currentX of Parallaxor View
     * @param oldX Previous X
     * @param oldY Previous Y
     */
    protected final void doScrollChanged(final int x, final int y, final int oldX, final int oldY) {
        doScrollViews(x, y);
        //Parallax this background if we can
        doScrollBackground(x, y);
        // Scroll Changed Listener
        doScrollListener(getWrapped(), x, y, oldX, oldY);

        // Set new LastScrollPos
        mLastScrollX = x;
        mLastScrollY = y;
    }

    // --
    // doScrollChanged Pointers to keep memory consumption down during fast scrolling
    //
    private Set<View> keySetPointer;
    private Iterator<View> iteratorPointer;
    private ParallaxViewInfo parallaxInfoPointer;
    private ParallaxDrawable parallaxDrawablePointer;
    private View viewPointer;
    // --

    private final void doScrollViews(final int x, final int y) {
        if (mViewHashMap == null) return;
        keySetPointer = mViewHashMap.keySet();
        iteratorPointer = keySetPointer.iterator();
        while (iteratorPointer.hasNext()) {
            viewPointer = iteratorPointer.next();

            if (viewPointer == null)
                continue;

            // Get Value
            parallaxInfoPointer = mViewHashMap.get(viewPointer);

            // Parallax the other view
            ParallaxHelper.scrollViewBy(viewPointer, x, y, parallaxInfoPointer.interpolator, parallaxInfoPointer.factor);
        }
    }

    private final void doScrollBackground(final int x, final int y) {
        if (mParallaxDrawableMap == null) return;

        keySetPointer = mParallaxDrawableMap.keySet();
        iteratorPointer = keySetPointer.iterator();
        while (iteratorPointer.hasNext()) {
            viewPointer = iteratorPointer.next();

            if (viewPointer == null)
                continue;

            // Get Value
            parallaxDrawablePointer = mParallaxDrawableMap.get(viewPointer);

            // Parallax the drawable
            ParallaxHelper.scrollParallaxDrawableBy(parallaxDrawablePointer, x, y);
        }

    }

    private final void doScrollListener(final Object who, final int x, final int y, final int oldX, final int oldY) {
        if (mScrollChangedListener != null && (x != oldX || y != oldY)) {
            mScrollChangedListener.onScrollChanged(who, x, y, oldX, oldY);
        }
    }

    /**
     * Checks the viewMap is ready to use.
     */
    private final void checkViewMap() {
        if (mViewHashMap == null)
            mViewHashMap = new WeakHashMap<View, ParallaxViewInfo>();
    }

    /**
     * Checks the BackgroundMap is ready to use.
     */
    private final void checkBackgroundMap() {
        if (mParallaxDrawableMap == null)
            mParallaxDrawableMap = new WeakHashMap<View, ParallaxDrawable>();
    }
}
