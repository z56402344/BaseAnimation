package com.duguang.baseanimation.ui.imitate.viberation;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MotionEvent;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.base.BaseActivity;

/**
 * 模拟心跳页面首页
 * @author Administrator
 *
 */
public class ViberationMain extends BaseActivity {
	Vibrator vibrator;
 
	@Override
	public void setView() {
		  setContentView(R.layout.activity_imitate_viberation_main);
		
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		
	} 
	@Override
	protected void onStop() {
		if(null!=vibrator){
			vibrator.cancel();
		}
		super.onStop();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if(event.getAction() == MotionEvent.ACTION_DOWN){
			 vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		     long[] pattern = {800, 40,400, 30}; // OFF/ON/OFF/ON...
		     vibrator.vibrate(pattern, 2);//-1不重复，非-1为从pattern的指定下标开始重复
		}
		return super.onTouchEvent(event);
	}

}