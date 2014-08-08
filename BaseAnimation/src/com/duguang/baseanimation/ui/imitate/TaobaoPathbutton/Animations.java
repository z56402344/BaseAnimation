package com.duguang.baseanimation.ui.imitate.TaobaoPathbutton;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.view.ViewPropertyAnimator;

public class Animations {
	public final int R; // 半径
	public static byte RIGHTBOTTOM = 1, CENTERBOTTOM = 2, LEFTBOTTOM = 3,
			LEFTCENTER = 4, LEFTTOP = 5, CENTERTOP = 6, RIGHTTOP = 7,
			RIGHTCENTER = 8;

	private int pc; // 位置代号
	private ViewGroup clayout; // 父laoyout
	private final int amount; // 有幾多個按鈕
	private double fullangle = 180.0;// 在幾大嘅角度內排佈
	private byte xOri = 1, yOri = 1; // x、y值嘅方向，即系向上還是向下
	private boolean isOpen = false;// 记录是已经打开还是关闭
	private List<ViewPropertyAnimator> viewAnimators = new ArrayList<ViewPropertyAnimator>();

	/**
	 * 構造函數
	 * 
	 * @param comlayout
	 *            包裹彈出按鈕嘅layout
	 * @param poscode
	 *            位置代號，分別對應RIGHTBOTTOM、CENTERBOTTOM、LEFTBOTTOM、LEFTCENTER、
	 *            LEFTTOP、CENTERTOP、RIGHTTOP、RIGHTCENTER
	 * @param radius
	 *            半徑
	 */
	public Animations(ViewGroup comlayout, int poscode, int radius) {
		this.pc = poscode;
		this.clayout = comlayout;
		this.amount = clayout.getChildCount();
		this.R = radius;

		// 初始化动画，每个view对应一个animator
		for (int i = 0; i < amount; i++) {
			View childAt = clayout.getChildAt(i);
			ViewPropertyAnimator anim = animate(childAt);
			viewAnimators.add(anim);
		}

		if (poscode == RIGHTBOTTOM) { // 右下角
			fullangle = 90;
			xOri = -1;
			yOri = -1;
		} else if (poscode == CENTERBOTTOM) {// 中下
			fullangle = 180;
			xOri = -1;
			yOri = -1;
		} else if (poscode == LEFTBOTTOM) { // 左下角
			fullangle = 90;
			xOri = 1;
			yOri = -1;
		} else if (poscode == LEFTCENTER) { // 左中
			fullangle = 180;
			xOri = 1;
			yOri = -1;
		} else if (poscode == LEFTTOP) { // 左上角
			fullangle = 90;
			xOri = 1;
			yOri = 1;
		} else if (poscode == CENTERTOP) { // 中上
			fullangle = 180;
			xOri = -1;
			yOri = 1;
		} else if (poscode == RIGHTTOP) { // 右上角
			fullangle = 90;
			xOri = -1;
			yOri = 1;
		} else if (poscode == RIGHTCENTER) { // 右中
			fullangle = 180;
			xOri = -1;
			yOri = -1;
		}
	}

	private class AnimListener implements AnimatorListener {

		private View target;

		public AnimListener(View _target) {
			target = _target;
		}

		@Override
		public void onAnimationStart(Animator animation) {

		}

		@Override
		public void onAnimationEnd(Animator animation) {
			if (!isOpen) {
				target.setVisibility(View.INVISIBLE);
			}
		}

		@Override
		public void onAnimationCancel(Animator animation) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationRepeat(Animator animation) {
			// TODO Auto-generated method stub

		}
	}

	/**
	 * 彈幾個按鈕出嚟
	 * 
	 * @param durationMillis
	 *            用幾多時間
	 */
	public void startAnimationsIn(int durationMillis) {
		isOpen = true;
		for (int i = 0; i < clayout.getChildCount(); i++) {
			final LinearLayout inoutimagebutton = (LinearLayout) clayout
					.getChildAt(i);

			double offangle = fullangle / (amount - 1);

			final double deltaY, deltaX;
			if (pc == LEFTCENTER || pc == RIGHTCENTER) {
				deltaX = Math.sin(offangle * i * Math.PI / 180) * R;
				deltaY = Math.cos(offangle * i * Math.PI / 180) * R;
			} else {
				deltaY = Math.sin(offangle * i * Math.PI / 180) * R;
				deltaX = Math.cos(offangle * i * Math.PI / 180) * R;
			}

			ViewPropertyAnimator viewPropertyAnimator = viewAnimators.get(i);
			viewPropertyAnimator.setListener(null);

			inoutimagebutton.setVisibility(View.VISIBLE);
			viewPropertyAnimator.x(
					(float) (inoutimagebutton.getLeft() + xOri * deltaX)).y(
					(float) (inoutimagebutton.getTop() + yOri * deltaY));

		}
	}

	/**
	 * 收埋幾個按鈕入去
	 * 
	 * @param durationMillis
	 *            用幾多時間
	 */
	public void startAnimationsOut(int durationMillis) {
		isOpen = false;
		for (int i = 0; i < clayout.getChildCount(); i++) {
			final LinearLayout inoutimagebutton = (LinearLayout) clayout
					.getChildAt(i);
			ViewPropertyAnimator viewPropertyAnimator = viewAnimators.get(i);
			viewPropertyAnimator.setListener(null);
			viewPropertyAnimator.x((float) inoutimagebutton.getLeft()).y(
					(float) inoutimagebutton.getTop());
			viewPropertyAnimator
					.setListener(new AnimListener(inoutimagebutton));

		}

	}

	/**
	 * 獲取位置代碼（其實貌似都冇乜用）
	 */
	public int getPosCode() {
		return this.pc;
	}

	/**
	 * 自轉函數（原本就有嘅靜態函數，未實體化都可以調用）
	 * 
	 * @param fromDegrees
	 *            從幾多度
	 * @param toDegrees
	 *            到幾多度
	 * @param durationMillis
	 *            轉幾耐
	 */
	public static Animation getRotateAnimation(float fromDegrees,
			float toDegrees, int durationMillis) {
		RotateAnimation rotate = new RotateAnimation(fromDegrees, toDegrees,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		rotate.setDuration(durationMillis);
		rotate.setFillAfter(true);
		return rotate;
	}
}