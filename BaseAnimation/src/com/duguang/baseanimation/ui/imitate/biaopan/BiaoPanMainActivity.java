package com.duguang.baseanimation.ui.imitate.biaopan;

import java.util.Random;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.base.BaseActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.app.Activity;
import android.view.Window;


/**
 * 温度表盘主页面
 * @author Administrator
 *
 */
public class BiaoPanMainActivity extends BaseActivity {

	private  int random;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
				random = new Random().nextInt(400);
				System.out.println(random);
				setbiaopan();
		};
	};
	
	@Override
	public void setView() {
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_imitate_biaopan_main);
		drawCanvas();
		
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
	 * 设置表盘的参数值
	 */
	private void setbiaopan() {
		BiaoPanview biaopan = (BiaoPanview) findViewById(R.id.biaopan);
		BiaoPanData data = new BiaoPanData();
		data.setTianqi("晴");
		data.setCity("北京");
		data.setQiwen(9);
		data.setZhiliang(random);
		if(random>250){
			data.setTishi("空气质量很差,不适合户外运动");
			data.setChengdu("空气质量差");
		}else{
			data.setTishi("可以多参加户外活动,呼吸空气");
			data.setChengdu("空气正常");
		}
		
		biaopan.setBiaopanData(data );
	}
	
	/**
	 * 每隔3秒,重现绘制空气质量圆点的位置
	 * 
	 */
	public void drawCanvas(){
		new Thread(new Runnable() {
			
			public void run() {
				while (true) {
					SystemClock.sleep(3000);
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);
				}
			}
		}).start();
	}


}
