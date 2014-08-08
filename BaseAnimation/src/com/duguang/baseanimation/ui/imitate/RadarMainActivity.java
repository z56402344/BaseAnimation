package com.duguang.baseanimation.ui.imitate;

import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.base.BaseActivity;

public class RadarMainActivity extends BaseActivity{

	protected static final String tag = "AntiVirusActivity";
	protected static final int SCAN_LODING = 1;
	protected static final int FINSH_SCAN = 2;
	private ImageView im_scan;
	private ImageView im_dian;
	private TextView tv_lodingApk;
	private TextView tv_count;
	private LinearLayout ll_scanText;
	private ProgressBar pb_loding;
	
	private int count;
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SCAN_LODING:
				tv_lodingApk.setText("正在扫描:第"+count+"个BaseAnimation动画");
				TextView tv = new TextView(RadarMainActivity.this);
				tv.setTextSize(14);
				tv_count.setText("已扫描:"+count+"个BaseAnimation动画");
				ll_scanText.addView(tv,0);
				break;
			case FINSH_SCAN:
				tv_lodingApk.setText("扫描完毕");
				im_scan.clearAnimation();//清除此ImageView身上的动画
				im_dian.clearAnimation();//清除此ImageView身上的动画
				break;
			}
		};
	};
	

	@Override
	public void setView() {
		setContentView(R.layout.activity_imitate_radar);
		
		im_scan = (ImageView) findViewById(R.id.im_scan);
		im_dian = (ImageView) findViewById(R.id.im_dian);
		tv_lodingApk = (TextView) findViewById(R.id.tv_lodingApk);
		ll_scanText = (LinearLayout) findViewById(R.id.ll_scanText);
		pb_loding = (ProgressBar) findViewById(R.id.pb_loding);
		tv_count = (TextView) findViewById(R.id.tv_count);
		
		RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(2000);
		animation.setRepeatCount(Animation.INFINITE);
		im_scan.startAnimation(animation);
		
		AlphaAnimation animation2 = new AlphaAnimation(0.0f, 1.0f);
		animation2.setDuration(3000);
		animation2.setRepeatCount(Animation.INFINITE);
		im_dian.startAnimation(animation2);
		
		count = 0;
		
		fillData();
		
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 初始化数据
	 */
	private void fillData() {
		tv_lodingApk.setText("开始准备释放空闲CPU线程");
		new Thread(){
			public void run() {
				PackageManager pm = getPackageManager();
				pb_loding.setMax(177);
				for (int i = 1; i <= 177; i++) {
					Message msg = Message.obtain();
					msg.what = SCAN_LODING;
					handler.sendMessage(msg);
					count =i;
					pb_loding.setProgress(count);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				Message msg = Message.obtain();
				msg.what = FINSH_SCAN;
				handler.sendMessage(msg);
			};
		}.start();
		
	}

	
}
