package com.duguang.baseanimation.ui.tab.paralloid.library1;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.duguang.baseanimation.ui.tab.paralloid.library1.transform.Transformer;

/**
 * Suggests that this view will let you parallax others.
 */
public interface Parallaxor {
    /**
     * Which view we want to move by.
     * <p/>
     * The view that implements this should call too {@link ParallaxViewController#parallaxViewBy(android.view.View, float)}
     *
     * @param view       View to move when this moves
     * @param multiplier 1.0f is the normal amount, a 1:1 ratio, 0.5f would move at half the distance of this view etc..
     */
    public void parallaxViewBy(View view, float multiplier);

    /**
     * Which view we want to move by.
     * <p/>
     * The view that implements this should call too {@link ParallaxViewController#parallaxViewBy(android.view.View, float)}
     *
     * @param view        View to move when this moves
     * @param transformer how do we want to move the view? (null default to {@link uk.co.chrisjenx.paralloid.transform.LinearTransformer})
     * @param multiplier  1.0f is the normal amount, a 1:1 ratio, 0.5f would move at half the distance of this view etc..
     */
    public void parallaxViewBy(View view, Transformer transformer, float multiplier);

    /**
     * This does a bit more than move the background, it will make it fit the view in a way that
     * it will reach out side its bounds to take into account
     *
     * @param drawable   Drawable to Parallax
     * @param multiplier How much to move it by, 1 would be a 1:1 relationship. 0.5 would move the
     *                   background and half the rate of the scrolling.
     */
    public void parallaxViewBackgroundBy(View view, Drawable drawable, float multiplier);
}