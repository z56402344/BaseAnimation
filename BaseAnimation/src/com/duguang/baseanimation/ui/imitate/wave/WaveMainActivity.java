package com.duguang.baseanimation.ui.imitate.wave;

import android.app.Activity;
import android.os.Bundle;

import com.duguang.baseanimation.R;

/**
 * 高仿支付宝声波动画页面
 * @author Administrator
 *
 */
public class WaveMainActivity extends Activity  {

	private WaveAnimView search_animation_wave;
	private WaveAnimView search_animation_wave2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imitate_wave_view_pay_wave);
		
		initView();
		
	}
	
	private void initView() {
		search_animation_wave = (WaveAnimView) findViewById(R.id.search_animation_wave);
		search_animation_wave2 = (WaveAnimView) findViewById(R.id.search_animation_wave2);
		search_animation_wave.startAnimation(true);
		search_animation_wave2.startAnimation(false);
	}
	


}
