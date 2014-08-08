package com.duguang.baseanimation.ui.splash.fade;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Intent;
import android.os.Handler;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.base.BaseActivity;

public class FadeSplashScreenActivity extends BaseActivity {

	Class<?> activityClass;
	Class[] paramTypes = { Integer.TYPE, Integer.TYPE };

	Method overrideAnimation = null;

	@Override
	public void setView() {
		setContentView(R.layout.activity_splash_fade_main);

		
	}

	@Override
	public void initView() {
		try {
			activityClass = Class.forName("android.app.Activity");
			overrideAnimation = activityClass.getDeclaredMethod(
					"overridePendingTransition", paramTypes);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent i = new Intent(FadeSplashScreenActivity.this,
						FadeMainActivity.class);
				startActivity(i);
				finish();
				if (overrideAnimation != null) {
					try {
						overrideAnimation.invoke(FadeSplashScreenActivity.this, android.R.anim.fade_in,
								android.R.anim.fade_out);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}, 2000);
		
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		
	}
}
