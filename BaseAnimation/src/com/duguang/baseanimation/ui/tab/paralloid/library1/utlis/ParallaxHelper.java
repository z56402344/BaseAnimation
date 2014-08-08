package com.duguang.baseanimation.ui.tab.paralloid.library1.utlis;


import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

import com.duguang.baseanimation.ui.tab.paralloid.library1.graphics.ParallaxDrawable;
import com.duguang.baseanimation.ui.tab.paralloid.library1.measure.AbsListScrollSize;
import com.duguang.baseanimation.ui.tab.paralloid.library1.transform.Transformer;

/**
 * Created by chris on 02/10/2013
 * Project: Paralloid
 */
public final class ParallaxHelper {

    public static void scrollViewBy(final View view, final int x, final int y, final float factor) {
        if (view == null) return;
        view.scrollTo((int) (x * factor), (int) (y * factor));
    }

    public static void scrollViewBy(final View view, final int x, final int y, final Transformer transformer, final float factor) {
        if (view == null || transformer == null) return;
        final int[] transform = transformer.scroll(x, y, factor);
        view.scrollTo(transform[0], transform[1]);
    }

    public static void scrollParallaxDrawableBy(final ParallaxDrawable drawable, final int scrollX, final int scrollY) {
        if (drawable == null) return;
        drawable.setScrollTo(scrollX, scrollY);
    }


    public static ParallaxDrawable getParallaxDrawable(final Drawable drawable, float factor) {
        if(drawable == null) return null;
        return new ParallaxDrawable(drawable, factor);
    }


    public static void setParallaxBackground(final View view, final ParallaxDrawable parallaxDrawable) {
        if (view == null || parallaxDrawable == null) return;
        // We request the size before attaching just incase the view has drawn we can pre populate the drawable with the extra height/width
        requestScrollableWidthHeight(view, parallaxDrawable.getFactor(), new ParallaxHelper.ScrollableWidthHeightCallback() {
            @Override
            public void onScrollableWidthHeight(final float width, final float height) {
                // This is called back when the view has (hopefully) the correct width/height
                parallaxDrawable.setParallaxExtraWidthHeight(width, height);
            }
        });
        view.setBackgroundDrawable(parallaxDrawable);
        fixParallaxBackgroundPreJBMR1(view, parallaxDrawable);
    }

    static void requestScrollableWidthHeight(final View view, final float multiplier, final ScrollableWidthHeightCallback callback) {
        if (callback == null) return;
        // Have we done a layout pass?
        if (view.getHeight() > 0 || view.getWidth() > 0) {
            final float[] xy = calculateScrollableWidthHeightFromView(view, multiplier);
            callback.onScrollableWidthHeight(xy[0], xy[1]);
        } else {
            // Call back to self when we have laid out
            view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    view.getViewTreeObserver().removeOnPreDrawListener(this);
                    requestScrollableWidthHeight(view, multiplier, callback);
                    return true;
                }
            });
        }
    }

    static float[] calculateScrollableWidthHeightFromView(View view, float factor) {
        if (view == null) return new float[2];
        if (view instanceof ScrollView) {
            // Get the ScrollView's only child
            final View child = ((ScrollView) view).getChildAt(0);

            return new float[]{view.getWidth(), calculateExtraScroll(view.getHeight(), child.getHeight(), factor)};
        }
        if (view instanceof HorizontalScrollView) {
            // Get the ScrollView's only child
            final View child = ((HorizontalScrollView) view).getChildAt(0);
            return new float[]{calculateExtraScroll(view.getWidth(), child.getWidth(), factor), view.getHeight()};
        }
        if (view instanceof AbsListView) {
            final AbsListScrollSize absListScrollSize = new AbsListScrollSize((AbsListView) view);
            float estimatedHeight = calculateExtraScroll(
                    view.getHeight(),
                    absListScrollSize.getMaxScrollY(),
                    factor);
            // Log.d("Parallax", "Est Height: " + estimatedHeight);
            return new float[]{absListScrollSize.getMaxScrollX(), estimatedHeight};
        }
        // Not sure what it is? Just use the width/height
        return new float[]{
                calculateExtraScroll(view.getWidth(), view.getWidth(), factor),
                calculateExtraScroll(view.getHeight(), view.getHeight(), factor)
        };
    }

    static float calculateExtraScroll(float parent, float child, float factor) {
        return parent + (child - parent) * factor;
    }

    /**
     * Hack to fix pre JB MR1 Kudos to @cyrilmottier
     *
     * @param view     view drawable attached too.
     * @param drawable drawable which gets invalidated
     */
    static void fixParallaxBackgroundPreJBMR1(final View view, final Drawable drawable) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            drawable.setCallback(new Drawable.Callback() {
                @Override
                public void invalidateDrawable(Drawable who) {
                    view.setBackgroundDrawable(who);
                }

                @Override
                public void scheduleDrawable(Drawable who, Runnable what, long when) {
                }

                @Override
                public void unscheduleDrawable(Drawable who, Runnable what) {
                }
            });
        }
    }

    static interface ScrollableWidthHeightCallback {
        void onScrollableWidthHeight(float width, float height);
    }

}
