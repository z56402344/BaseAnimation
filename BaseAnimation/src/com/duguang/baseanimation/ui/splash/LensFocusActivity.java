package com.duguang.baseanimation.ui.splash;

import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.base.BaseActivity;

public class LensFocusActivity extends BaseActivity{

	
	private ImageView imageView_pic;
	private TextView textView_desc ;
	
	/**
	 * 三个切换的动画
	 */
	private Animation mFadeIn;
	private Animation mFadeInScale;
	private Animation mFadeOut;
	
	/**
	 * 三个图片
	 */
	private Drawable mPicture_1;
	private Drawable mPicture_2;
	private Drawable mPicture_3;
	
	
	@Override
	public void setView() {
		setContentView(R.layout.activity_splash_lens_focus);
	}

	@Override
	public void initView() {
		imageView_pic = (ImageView) findViewById(R.id.imageView_pic);
		textView_desc = (TextView) findViewById(R.id.textView_desc);
		init();
	}

	private void init() {
		initAnim();
		initPicture();
		/**
		 * 界面刚开始显示的内容
		 */
		imageView_pic.setImageDrawable(mPicture_1);
		textView_desc.setText("儿时的我们...");
		imageView_pic.startAnimation(mFadeIn);
	}

	
	
	/**
	 * 初始化动画
	 */
	private void initAnim() {
		mFadeIn = AnimationUtils.loadAnimation(this,R.anim.welcome_fade_in);
		mFadeIn.setDuration(1000);
		mFadeInScale = AnimationUtils.loadAnimation(this,R.anim.welcome_fade_in_scale);
		mFadeInScale.setDuration(6000);
		mFadeOut = AnimationUtils.loadAnimation(this,R.anim.welcome_fade_out);
		mFadeOut.setDuration(1000);
	}

	/**
	 * 初始化图片
	 */
	
	private void initPicture() {
		mPicture_1 = getResources().getDrawable(R.drawable.pic_01);
		mPicture_2 = getResources().getDrawable(R.drawable.pic_02);
		mPicture_3 = getResources().getDrawable(R.drawable.pic_03);
	}
	
	
    /**
	 * 监听事件
	 */
	public void setListener() {
		/**
		 * 动画切换原理:开始时是用第一个渐现动画,当第一个动画结束时开始第二个放大动画,当第二个动画结束时调用第三个渐隐动画,
		 * 第三个动画结束时修改显示的内容并且重新调用第一个动画,从而达到循环效果
		 */
		mFadeIn.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {

			}

			public void onAnimationRepeat(Animation animation) {

			}

			public void onAnimationEnd(Animation animation) {
				imageView_pic.startAnimation(mFadeInScale);
			}
		});
		mFadeInScale.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {

			}

			public void onAnimationRepeat(Animation animation) {

			}

			public void onAnimationEnd(Animation animation) {
				imageView_pic.startAnimation(mFadeOut);
			}
		});
		mFadeOut.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {

			}

			public void onAnimationRepeat(Animation animation) {

			}

			public void onAnimationEnd(Animation animation) {
				/**
				 * 这里其实有些写的不好,还可以采用更多的方式来判断当前显示的是第几个,从而修改数据,
				 * 我这里只是简单的采用获取当前显示的图片来进行判断。
				 */
				if (imageView_pic.getDrawable().equals(mPicture_1)) {
					textView_desc.setText("懵懂的我们...");
					imageView_pic.setImageDrawable(mPicture_2);
				} else if (imageView_pic.getDrawable().equals(mPicture_2)) {
					textView_desc.setText("少年的我们...");
					imageView_pic.setImageDrawable(mPicture_3);
				} else if (imageView_pic.getDrawable().equals(mPicture_3)) {
					textView_desc.setText("儿时的我们...");
					imageView_pic.setImageDrawable(mPicture_1);
				}
				imageView_pic.startAnimation(mFadeIn);
			}
		});
		
	}

}
