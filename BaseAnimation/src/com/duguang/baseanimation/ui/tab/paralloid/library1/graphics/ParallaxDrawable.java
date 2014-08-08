package com.duguang.baseanimation.ui.tab.paralloid.library1.graphics;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;

/**
 * Created by chris on 20/10/2013
 * Project: Paralloid
 */
public class ParallaxDrawable extends Drawable {

    private final Drawable mWrappedDrawable;
    private float mFactor;
    private int mWidth;
    private int mHeight;
    private float mOffsetX;
    private float mOffsetY;

    public ParallaxDrawable(Drawable wrappedDrawable, float factor) {
        mWrappedDrawable = wrappedDrawable;
        mFactor = factor;
    }

    public Drawable getWrappedDrawable() {
        return mWrappedDrawable;
    }

    public void setFactor(float factor) {
        mFactor = factor;
    }

    public float getFactor() {
        return mFactor;
    }

    public void setParallaxExtraWidthHeight(float width, float height) {
        mWidth = (int) width;
        mHeight = (int) height;
    }

    public void setScrollTo(float x, float y) {
        mOffsetX = x * mFactor;
        mOffsetY = y * mFactor;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        // Changed the bounds of the Wrapped Drawable
        mWrappedDrawable.setBounds(bounds.left, bounds.top, bounds.left + mWidth, bounds.top + mHeight);
    }

    @Override
    public void draw(final Canvas canvas) {
        canvas.translate(-mOffsetX, -mOffsetY);
        mWrappedDrawable.draw(canvas);
        canvas.translate(mOffsetX, mOffsetY);
    }

    // ----
    // Wrapped methods
    // ----

    @Override
    public void setAlpha(int alpha) {
        mWrappedDrawable.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mWrappedDrawable.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return mWrappedDrawable.getOpacity();
    }

    @Override
    public boolean setState(int[] stateSet) {
        super.setState(stateSet);
        return mWrappedDrawable.setState(stateSet);
    }

    @Override
    public ConstantState getConstantState() {
        return mWrappedDrawable.getConstantState();
    }

    @Override
    public void invalidateSelf() {
        super.invalidateSelf();
        mWrappedDrawable.invalidateSelf();
    }

    @Override
    public boolean isStateful() {
        return mWrappedDrawable.isStateful();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void jumpToCurrentState() {
        mWrappedDrawable.jumpToCurrentState();
    }

    @Override
    public int[] getState() {
        return mWrappedDrawable.getState();
    }

    @Override
    public Drawable mutate() {
        return mWrappedDrawable.mutate();
    }

    @Override
    public int getIntrinsicHeight() {
        return 0;
    }

    @Override
    public int getIntrinsicWidth() {
        return 0;
    }

    @Override
    public int getMinimumWidth() {
        return 0;
    }

    @Override
    public int getMinimumHeight() {
        return 0;
    }

    @Override
    public boolean getPadding(Rect padding) {
        return mWrappedDrawable.getPadding(padding);
    }
}
