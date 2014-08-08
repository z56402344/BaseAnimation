package com.duguang.baseanimation.ui.tab.paralloid.library;



import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

import com.duguang.baseanimation.ui.tab.paralloid.library1.ParallaxViewController;
import com.duguang.baseanimation.ui.tab.paralloid.library1.Parallaxor;
import com.duguang.baseanimation.ui.tab.paralloid.library1.transform.Transformer;

/**
 * Created by chris on 02/10/2013
 * Project: Paralloid
 */
public class ParallaxHorizontalScrollView extends HorizontalScrollView implements Parallaxor {

    ParallaxViewController mParallaxViewController;

    public ParallaxHorizontalScrollView(Context context) {
        super(context);
        init();
    }

    public ParallaxHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ParallaxHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mParallaxViewController = ParallaxViewController.wrap(this);

    }

    @Override
    public void parallaxViewBy(View view, float multiplier) {
        mParallaxViewController.parallaxViewBy(view, multiplier);
    }

    @Override
    public void parallaxViewBy(View view, Transformer transformer, float multiplier) {
        mParallaxViewController.parallaxViewBy(view, transformer, multiplier);
    }

    @Override
    public void parallaxViewBackgroundBy(View view, Drawable drawable, float multiplier) {
        mParallaxViewController.parallaxViewBackgroundBy(view, drawable, multiplier);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mParallaxViewController.onScrollChanged(this, l, t, oldl, oldt);
    }
}
