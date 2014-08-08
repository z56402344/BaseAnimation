package com.duguang.baseanimation.ui.canvas.olympics;

import com.duguang.baseanimation.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 用画笔绘制奥运五环的动画
 * @author Administrator
 *
 */
public class OlympicMainActivity extends Activity {

	private RelativeLayout rl;
	private CustomTextView view;
	private TextView textView1;
	private Button button1;
	private Button button2;
	private int n;
	private boolean isDraw = true;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			// rl.removeView(view);
			 if (n++ <= 361) {
				view = new CustomTextView(OlympicMainActivity.this);
				view.setSweepAngle(n);
				rl.addView(view);
				textView1.setText(n+"Ԫ");
			 }else{
				 isDraw = false;
			 }
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cancas_olympic_main);
		rl = (RelativeLayout) findViewById(R.id.rl);
		textView1 = (TextView) findViewById(R.id.textView1);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		n = 0;
		initView();
		startAnimation();
		
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startAnimation();
				
			}
		});

	}

	private void initView() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (isDraw) {
					SystemClock.sleep(20);
					handler.sendEmptyMessage(0);
					n+= 1;
				}
			}
		}).start();

	}
	
	public void startAnimation(){
		Animation anim = AnimationUtils.loadAnimation(getApplication(), R.anim.olympic_alpha_in2);
		anim.setFillAfter(true);
		button1.setAnimation(anim);
		button2.setAnimation(anim);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
