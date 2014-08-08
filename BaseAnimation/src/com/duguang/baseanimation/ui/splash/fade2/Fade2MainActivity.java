package com.duguang.baseanimation.ui.splash.fade2;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.MainActivity;
import com.duguang.baseanimation.ui.base.BaseActivity;
import com.duguang.baseanimation.ui.splash.fade.FadeMainActivity;

public class Fade2MainActivity extends BaseActivity {
	private long splashDelay = 5000; //5 seconds
	
    /** Called when the activity is first created. */
	@Override
	public void setView() {
		setContentView(R.layout.activity_splash_fade2_main);
		
	}

	@Override
	public void initView() {
		  TimerTask task = new TimerTask()
	        {

				@Override
				public void run() {
					finish();
					Intent mainIntent = new Intent().setClass(Fade2MainActivity.this, FadeMainActivity.class);
					startActivity(mainIntent);
					overridePendingTransition(android.R.anim.fade_in,
							android.R.anim.fade_out);
				}
	        	
	        };
	        
	        Timer timer = new Timer();
	        timer.schedule(task, splashDelay);
		
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		
	}
}