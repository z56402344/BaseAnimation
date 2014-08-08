package com.duguang.baseanimation.ui.imitate.wave;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.duguang.baseanimation.R;


public class WaveAnimView extends FrameLayout {

    private static final int DURATION_DIFF = -1000;
    private static final int DELAY_DIFF = -100;
    private ImageView[] mImages = new ImageView[4];
    private boolean mWaving = false;
    private int mDuration = 4000;
    private int mDelay = 700;
    private boolean mFlag = true;

    public WaveAnimView(Context paramContext) {
        super(paramContext);
        init();
    }

    public WaveAnimView(Context paramContext,
            AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init();
    }

    public WaveAnimView(Context paramContext,
            AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(
                R.layout.activity_imitate_wave_view_pay_anima, this);
        mImages[0] = ((ImageView) findViewById(R.id.radar_ray_1));
        mImages[1] = ((ImageView) findViewById(R.id.radar_ray_2));
        mImages[2] = ((ImageView) findViewById(R.id.radar_ray_3));
        mImages[3] = ((ImageView) findViewById(R.id.radar_ray_4));
    }

    // ���ã�ֹͣ����
    public final void stopAnimation() {
        if (!mWaving) return;
        mWaving = false;
        for (int i = 0; i < mImages.length; i++) {
        	mImages[i].clearAnimation();
            ImageView localImageView = mImages[i];
            localImageView.setVisibility(View.GONE);
        }
    }

    // ��ʼ����
    public void startAnimation(boolean flag) {
        if (mWaving) return;
        mWaving = true;
        mFlag = flag;
        startNext(flag, 0);
    }

    private void startNext(boolean flag, int index) {
        ImageView image = mImages[index];
        image.setVisibility(View.VISIBLE);
        ScaleAnimation scale;
        if (flag) {
            //scale = new ScaleAnimation(1.0F, 14.0F, 1.0F, 14.0F, 1, 0.5F, 1, 0.5F);
        	scale = new ScaleAnimation(1.0F, 4.0F, 1.0F, 4.0F, 1, 0.5F, 1, 0.5F);
        } else {
            /*scale = new ScaleAnimation(4.0f, 0.0f, 4.0f, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);*/
        	scale = new ScaleAnimation(3.0f, 0.0f, 3.0f, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        }
        AlphaAnimation alpha = new AlphaAnimation(1.0F, 0.0F);
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(scale);
        set.addAnimation(alpha);
        set.setDuration(flag ? mDuration : mDuration + DURATION_DIFF);
        set.setFillAfter(true);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.setAnimationListener(new WTSearchAnimationHandler(this, image, index));
        image.startAnimation(set);
    }

    final class WTSearchAnimationHandler implements Animation.AnimationListener {
        private ImageView mImage;
        private int mIndex;

        public WTSearchAnimationHandler(
                WaveAnimView paramImageView, ImageView imageView, int index) {
            mImage = imageView;
            mIndex = index;
        }

        public final void onAnimationEnd(Animation paramAnimation) {
            mImage.setVisibility(View.GONE);
        }

        public final void onAnimationRepeat(Animation paramAnimation) {
        }

        public final void onAnimationStart(Animation paramAnimation) {
            Message msg = new Message();
            msg.what = mIndex == mImages.length - 1 ? 0 : mIndex + 1;
            mHandler.sendMessageDelayed(msg, mFlag ? mDelay : mDelay + DELAY_DIFF);
        }
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (mWaving) {
                startNext(mFlag, msg.what);
            }
        };
    };

    public boolean isWaving() {
        return mWaving;
    }
}
