package com.duguang.baseanimation.ui.property;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.base.BaseActivity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.AnimatorSet.Builder;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

public class PropertyAnimationActivity extends BaseActivity {
	private TextView m_tv = null;
	private MyAnimatableView m_atv = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_property_main);
		gatherControls();
	}

	private void gatherControls() {
		m_tv = (TextView) this.findViewById(R.id.tv_id);
		m_atv = new MyAnimatableView(m_tv);
	}

	/*
	 * 单个动画
	 */
	public void testObjectAnimator(View btnView) {
		float width = m_tv.getWidth();
		if (m_tv.getX() == 0) {
			ObjectAnimator translationRight = ObjectAnimator.ofFloat(m_tv, "X",
					width);
			translationRight.setDuration(1500);
			translationRight.start();
		} else {
			ObjectAnimator translationLeft = ObjectAnimator.ofFloat(m_tv, "X",
					0f);
			translationLeft.setDuration(1500);
			translationLeft.start();
		}
	}

	/*
	 * 连续动画
	 */
	public void testAnimationSet(View v) {
		float width = m_tv.getWidth();
		float height = m_tv.getHeight();
		ObjectAnimator translationRight = ObjectAnimator.ofFloat(m_tv, "X", width);
		ObjectAnimator translationLeft = ObjectAnimator.ofFloat(m_tv, "X", 0f);
		ObjectAnimator translationDown = ObjectAnimator.ofFloat(m_tv, "Y",
				height);
		ObjectAnimator translationUp = ObjectAnimator.ofFloat(m_tv, "Y", 0);

		AnimatorSet as = new AnimatorSet();
		as.play(translationRight).before(translationLeft);
		as.play(translationRight).with(translationDown);
		as.play(translationLeft).with(translationUp);

		// 和上边四句等效，另一种写法
        /*
		AnimatorSet as = new AnimatorSet();
		as.playTogether(translationRight, translationDown);
		as.playSequentially(translationRight, translationLeft);
		as.playTogether(translationLeft, translationUp);
        */
		as.setDuration(1500);
		as.start();
	}

	/*
	 * XML，便于代码重用
	 */
	public void testAnimationXML(View bView) {
		m_tv.setAlpha(1f);
		AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this,
				R.animator.fadein);
		set.setTarget(m_tv);
		set.start();
	}

	/*
	 * 一个动画改变多个属性值
	 */
	public void testPropertyValuesHolder(View v) {
		m_tv.setAlpha(1f);
		float h = m_tv.getHeight();
		float w = m_tv.getWidth();
		float x = m_tv.getX();
		float y = m_tv.getY();

		m_tv.setX(w);
		m_tv.setY(h);
		PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("x", x);
		PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("y", y);

		ObjectAnimator oa = ObjectAnimator.ofPropertyValuesHolder(m_tv, pvhX,
				pvhY);
		oa.setDuration(3000);
		// oa.setInterpolator(new AccelerateDecelerateInterpolator());
		oa.setInterpolator(new BounceInterpolator());
		oa.start();
	}

	/*
	 * 一个View的多个属性进行动画，3.1中引入，对多属性动画进行了优化
	 */
	public void testViewPropertyAnimator(View v) {
		m_tv.setAlpha(1f);
		float h = m_tv.getHeight();
		float w = m_tv.getWidth();
		float x = m_tv.getX();
		float y = m_tv.getY();

		m_tv.setX(w);
		m_tv.setY(h);

		ViewPropertyAnimator vpa = m_tv.animate().x(x).y(y);

		vpa.setDuration(1500);
		vpa.setInterpolator(new BounceInterpolator());
	}

	/*
	 * 自定义Evaluator
	 */
	public void testTypeEvaluator(View v) {
		m_tv.setAlpha(1f);

		float h = m_tv.getHeight();
		float w = m_tv.getWidth();
		float x = m_tv.getX();
		float y = m_tv.getY();

		ObjectAnimator tea = ObjectAnimator.ofObject(m_atv, "point",
				new MyPointEvaluator(), new PointF(w, h), new PointF(x, y));
		tea.setDuration(2000);
		tea.setInterpolator(new OvershootInterpolator());
		tea.start();

	}

	/*
	 * 关键帧
	 */
	public void testKeyFrames(View v) {
		float h = m_tv.getHeight();
		float w = m_tv.getWidth();
		float x = m_tv.getX();
		float y = m_tv.getY();

		Keyframe kf0 = Keyframe.ofFloat(0.2f, 360);
		Keyframe kf1 = Keyframe.ofFloat(0.5f, 30);
		Keyframe kf2 = Keyframe.ofFloat(0.8f, 1080);
		Keyframe kf3 = Keyframe.ofFloat(1f, 0);
		PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe(
				"rotation", kf0, kf1, kf2, kf3);

		PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("x", w, x);
		PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("y", h, y);

		ObjectAnimator anim = ObjectAnimator.ofPropertyValuesHolder(m_tv,
				pvhRotation, pvhX, pvhY);
		anim.setDuration(2000);
		anim.start();
	}

	@Override
	public void setView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		
	}
}
