package com.duguang.baseanimation.ui.imitate.waterfall;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

public class MyLinearLayout extends LinearLayout {

	private GestureDetector gestureDetector;
	private Context context;

	public MyLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		gestureDetector = new GestureDetector(context,new OnGestureListener() {
			
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void onShowPress(MotionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
					float distanceY) {
				
				return false;
			}
			
			@Override
			public void onLongPress(MotionEvent event) {
				View rootView = getRootView();
				float eventX = event.getX();
				int width=rootView.getWidth()/getChildCount();
				if (eventX>width && eventX<2*width){
					ConstantValue.POPFLAG2=true;
					ConstantValue.POPFLAG3=true;
				}
				
			}
			
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
					float velocityY) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean onDown(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}
		});
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return true;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return super.dispatchTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		View rootView = getRootView();
		int width=rootView.getWidth()/getChildCount();
		int height = rootView.getHeight();
		int count=getChildCount();
		
		gestureDetector.onTouchEvent(event);
		
		float eventX = event.getX();
		if (eventX<width){
			event.setLocation(width/2, event.getY());
			getChildAt(0).dispatchTouchEvent(event);
			return true;
		}else if (eventX>width && eventX<2*width){
			float eventY = event.getY();
			if (eventY<height/2){
				event.setLocation(width/2, event.getY());
				for (int i=0;i<count;i++){
					View child = getChildAt(i);
					child.dispatchTouchEvent(event);
				}
				return true;
			}else if (eventY>height/2){
				event.setLocation(width/2, event.getY());
				getChildAt(1).dispatchTouchEvent(event);
				return true;
			}
		}else if (eventX>2*width){
			event.setLocation(width/2, event.getY());
			getChildAt(2).dispatchTouchEvent(event);
			return true;
		}
		
		return true;
	}
	
	
	
}
