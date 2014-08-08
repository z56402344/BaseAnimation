package com.duguang.baseanimation.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
/**
 * 自定义滑动开关
 * @author 
 *
 */
public class SlideToggle extends View {

	private Bitmap switch_on_bkg;
	private Bitmap switch_off_bkg;
	private Bitmap btn_slip;
	private Rect rect_on;
	private Rect rect_off;
	private boolean isSwitchOn;//记录当前开关的状态
	private OnSwitchStateListener switchStateListener;//开关监听器
	private boolean isSwitchStateListenerOn = false; // 记录是否使用了开关监听器
	private float currentX;
	private boolean isSlipping;//记录开关当前是否处于滑动状态

	public SlideToggle(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SlideToggle(Context context) {
		super(context);
	}

	/**
	 * 设置开关样式
	 * @param bkgSwitchOn  开启的背景图
	 * @param bkgSwitchOff  关闭的背景图
	 * @param btnSlip   滑动块
	 */
	public void setImageResIDs(int bkgSwitchOn, int bkgSwitchOff, int btnSlip) {
		switch_on_bkg = BitmapFactory.decodeResource(getResources(), bkgSwitchOn);
		switch_off_bkg = BitmapFactory.decodeResource(getResources(), bkgSwitchOff);
		btn_slip = BitmapFactory.decodeResource(getResources(), btnSlip);
		
		//记录开关开启状态时  开关的位置
		rect_on = new Rect(switch_on_bkg.getWidth() - btn_slip.getWidth(), 0, switch_off_bkg.getWidth(), btn_slip.getHeight());
		//记录开关关闭状态时  开关的位置
		rect_off = new Rect(0, 0, btn_slip.getWidth(), btn_slip.getHeight());
	}

	//设置开关当前状态
	public void setSwitchState(boolean state) {
		isSwitchOn = state;
	}

	public interface OnSwitchStateListener{
		abstract void onSwitch(boolean state);
	}

	//开关监听器
	public void setOnSwitchStateListener(OnSwitchStateListener listener){
		switchStateListener = listener;
		isSwitchStateListenerOn  = true; 
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			currentX = event.getX();
			isSlipping = true;
			break;
		case MotionEvent.ACTION_MOVE:
			currentX = event.getX();
			break;
		case MotionEvent.ACTION_UP:
			isSlipping = false;
			//松开前开关的状态
			boolean previousSwitchState  = isSwitchOn;
			if(currentX < switch_off_bkg.getWidth() /2){
				isSwitchOn = false;
			} else {
				isSwitchOn = true;
			}
			if(isSwitchOn != previousSwitchState && isSwitchStateListenerOn){
				switchStateListener.onSwitch(isSwitchOn);
				previousSwitchState = isSwitchOn;
			}
			Logger.d("onTouch", isSwitchOn + ", " + previousSwitchState);
			break;
		}
		invalidate();//页面重新绘制
		return true;
//		return super.onTouchEvent(event);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(switch_off_bkg.getWidth(), switch_off_bkg.getHeight());
	}

	//绘制开关
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Logger.e("onTouch onDraw", ""+isSwitchOn);
		Matrix matrix = new Matrix();//图片的风格  样式
		Paint paint = new Paint();// 图片的效果
		//根据开关的状态 ，设置开关的背景图片
		if(currentX < switch_off_bkg.getWidth()/2){
			//关闭
			canvas.drawBitmap(switch_off_bkg, matrix, paint);
		} else {
			//开启
			canvas.drawBitmap(switch_on_bkg, matrix, paint);
		}
		float left_slip = 0;
		if(isSlipping){//处于滑动状态
			if(currentX > switch_off_bkg.getWidth()){// 超过背景图片的宽度
				left_slip = switch_off_bkg.getWidth() - btn_slip.getWidth();
			} else {//中间滑动s
				left_slip = currentX - btn_slip.getWidth()/2;
			}
		} else {//不处于滑动状态
			if(isSwitchOn){
				//开启
				left_slip = rect_on.left;
				canvas.drawBitmap(switch_on_bkg, matrix, paint);
			} else {
				//关闭
				left_slip = rect_off.left;
				canvas.drawBitmap(switch_off_bkg, matrix, paint);
			}
		}
		if(left_slip < 0){
			left_slip = 0;
		} else if(left_slip > switch_off_bkg.getWidth() - btn_slip.getWidth()){
			left_slip = switch_off_bkg.getWidth() - btn_slip.getWidth();
		}
		canvas.drawBitmap(btn_slip, left_slip, 0, paint);
	}
}
