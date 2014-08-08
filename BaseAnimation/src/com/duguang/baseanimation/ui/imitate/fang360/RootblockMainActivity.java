package com.duguang.baseanimation.ui.imitate.fang360;

import java.util.ArrayList;
import java.util.List;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.base.BaseActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;

/**
 * 高仿360首页
 * 
 * @author Administrator
 * 
 */
public class RootblockMainActivity extends BaseActivity {

	private ImageView ivOne;
	private ImageView ivTwo;

	@Override
	public void setView() {
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_imitate_main);

	}

	@Override
	public void initView() {
		ivOne = (ImageView) findViewById(R.id.iv_one);
		ivTwo = (ImageView) findViewById(R.id.iv_two);
		vpMain = (ViewPager) findViewById(R.id.vp_main);
		views = new ArrayList<View>();
		views.add(View.inflate(this,
				R.layout.activity_imitate_rootblock_main_first, null));
		views.add(View.inflate(this, R.layout.activity_imitate_main_second,
				null));
		MyAdapter adapter = new MyAdapter();
		MyListener listener = new MyListener();
		vpMain.setAdapter(adapter);
		vpMain.setOnPageChangeListener(listener);

	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub

	}

	public void next(View v) {
		flipit(ivOne, ivTwo);
		vpMain.setCurrentItem((vpMain.getCurrentItem() + 1) % views.size());

	}

	private Interpolator accelerator = new AccelerateInterpolator();
	private Interpolator decelerator = new DecelerateInterpolator();
	private ViewPager vpMain;
	private List<View> views;

	/**
	 * ��ת����
	 * 
	 * @param one
	 * @param two
	 */
	private void flipit(View one, View two) {
		final View visible;
		final View invisible;
		if (one.getVisibility() == View.GONE) {
			visible = two;
			invisible = one;

		} else {
			invisible = two;
			visible = one;

		}
		ObjectAnimator visToInvis = ObjectAnimator.ofFloat(visible,
				"rotationY", 0f, 90f);
		visToInvis.setDuration(500);
		visToInvis.setInterpolator(accelerator);
		final ObjectAnimator invisToVis = ObjectAnimator.ofFloat(invisible,
				"rotationY", -90f, 0f);
		invisToVis.setDuration(500);
		invisToVis.setInterpolator(decelerator);
		visToInvis.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator anim) {
				visible.setVisibility(View.GONE);
				invisToVis.start();
				invisible.setVisibility(View.VISIBLE);
			}
		});
		visToInvis.start();
	}

	class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(views.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(views.get(position));
			return views.get(position);
		}

	}

	class MyListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int arg0) {
			flipit(ivOne, ivTwo);
		}

	}

}