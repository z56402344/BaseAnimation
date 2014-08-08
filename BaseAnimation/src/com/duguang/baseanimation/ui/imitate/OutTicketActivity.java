package com.duguang.baseanimation.ui.imitate;

import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.base.BaseActivity;

public class OutTicketActivity extends BaseActivity{

	private RelativeLayout rl_payInfo;
	private Animation anim;
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				startAnimation();
				break;

			default:
				break;
			}

		};
	};

	
	@Override
	public void setView() {
		setContentView(R.layout.activity_imitate_outticket);
		
	}

	@Override
	public void initView() {
		rl_payInfo = (RelativeLayout) findViewById(R.id.rl_payInfo);
		
	}

	@Override
	public void setListener() {
		newThread() ;
		
	}
	
	
	/**
	 * 测试用的,开启子线程
	 */
	private void newThread() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);

			}
		}).start();
	}

	/**
	 * 启动打印小票动画
	 */
	private void startAnimation() {
		anim = AnimationUtils.loadAnimation(this, R.anim.slide_down_in);
		anim.setDuration(1000);
		anim.setFillAfter(true);
		rl_payInfo.startAnimation(anim);
	}

}
