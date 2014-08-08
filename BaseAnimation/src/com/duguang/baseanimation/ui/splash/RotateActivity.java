package com.duguang.baseanimation.ui.splash;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.anim.RotateAnimation;
import com.duguang.baseanimation.ui.base.BaseActivity;

/**
 * 页面翻转
 * @author duguang
 *
 */
public class RotateActivity extends BaseActivity implements OnClickListener {

	private RelativeLayout rl_layout01;
	private RelativeLayout rl_layout02;
	private ViewGroup mContainer;
	@Override
	public void setView() {
		setContentView(R.layout.activity_splash_rotate);
	}

	@Override
	public void initView() {
		
		mContainer = (ViewGroup) findViewById(R.id.container);
		mContainer.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);
        
		rl_layout01 = (RelativeLayout) findViewById(R.id.rl_layout01);
		rl_layout02 = (RelativeLayout) findViewById(R.id.rl_layout02);
	}

	@Override
	public void setListener() {
		rl_layout01.setOnClickListener(this);
		rl_layout02.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_layout01:
			applyRotation(0,0,90);
			break;
		case R.id.rl_layout02:
			applyRotation(-1, 180, 90);
			break;

		default:
			break;
		}
		
	}
	
	/**
     * Setup a new 3D rotation on the container view.
     *
     * @param position the item that was clicked to show a picture, or -1 to show the list
     * @param start the start angle at which the rotation must begin
     * @param end the end angle of the rotation
     */
    private void applyRotation(int position, float start, float end) {
        // Find the center of the container
        final float centerX = mContainer.getWidth() / 2.0f;
        final float centerY = mContainer.getHeight() / 2.0f;

        // Create a new 3D rotation with the supplied parameter
        // The animation listener is used to trigger the next animation
        final RotateAnimation rotation =
                new RotateAnimation(start, end, centerX, centerY, 310.0f, true);
        rotation.setDuration(500);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new DisplayNextView(position));

        mContainer.startAnimation(rotation);
    }
	
	  /**
     * This class listens for the end of the first half of the animation.
     * It then posts a new action that effectively swaps the views when the container
     * is rotated 90 degrees and thus invisible.
     */
    private final class DisplayNextView implements Animation.AnimationListener {
        private final int mPosition;

        private DisplayNextView(int position) {
            mPosition = position;
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            mContainer.post(new SwapViews(mPosition));
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    /**
     * This class is responsible for swapping the views and start the second
     * half of the animation.
     */
    private final class SwapViews implements Runnable {
        private final int mPosition;

        public SwapViews(int position) {
            mPosition = position;
        }

        public void run() {
            final float centerX = mContainer.getWidth() / 2.0f;
            final float centerY = mContainer.getHeight() / 2.0f;
            RotateAnimation rotation;
            
            if (mPosition > -1) {
            	rl_layout01.setVisibility(View.GONE);
            	rl_layout02.setVisibility(View.VISIBLE);

                rotation = new RotateAnimation(90,180, centerX, centerY, 310.0f, false);
            } else {
            	rl_layout02.setVisibility(View.GONE);
            	rl_layout01.setVisibility(View.VISIBLE);
                rotation = new RotateAnimation(90, 0, centerX, centerY, 310.0f, false);
            }

            rotation.setDuration(500);
            rotation.setFillAfter(true);
            rotation.setInterpolator(new DecelerateInterpolator());

            mContainer.startAnimation(rotation);
        }
    }

	
}
