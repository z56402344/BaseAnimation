package com.duguang.baseanimation.ui.splash;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.duguang.baseanimation.ContantValue;
import com.duguang.baseanimation.R;
import com.duguang.baseanimation.adapter.AnimationAdapter;
import com.duguang.baseanimation.ui.base.BaseActivity;
import com.duguang.baseanimation.ui.splash.BackGroundColor.BackGroundColorAnimationActivity;
import com.duguang.baseanimation.ui.splash.fade.FadeSplashScreenActivity;
import com.duguang.baseanimation.ui.splash.fade2.Fade2MainActivity;
import com.duguang.baseanimation.ui.splash.splitActivity.Activity1;
import com.duguang.baseanimation.ui.tab.viewflow.CircleViewFlowActivity;

/**
 * Splash主页面
 * 继承BaseActivity获取ActionBar
 * @author duguang 博客地址:http://blog.csdn.net/duguang77
 */
public class SplashMainActivity extends BaseActivity implements OnItemClickListener {

	private AnimationAdapter adapter;
	private ListView listView_anim_complex;
	@Override
	public void setView() {
		setContentView(R.layout.activity_anim_complex);
		
	}

	@Override
	public void initView() {
		listView_anim_complex = (ListView) findViewById(R.id.listView_anim_complex);
		adapter = new AnimationAdapter(this, ContantValue.splashName);
		
	}

	@Override
	public void setListener() {
		listView_anim_complex.setAdapter(adapter);
		listView_anim_complex.setOnItemClickListener(this);
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {
		case 0:
			startIntent(ZakerActivity.class);
			break;
		case 1:
			startIntent(LensFocusActivity.class);
			break;
		case 2:
			startIntent(RotateActivity.class);
			break;
		case 3:
			startIntent(Rotate3DActivity.class);
			break;
		case 4:
			startIntent(ViewPagerActivity.class);
			break;
		case 5:
			startIntent(Activity1.class);
			break;
		case 6:
			startIntent(FadeSplashScreenActivity.class);
			break;
		case 7:
			startIntent(Fade2MainActivity.class);
			break;
		case 8:
			//背景颜色渐变的Splash引导
			startIntent(BackGroundColorAnimationActivity.class);
			break;
		case 9:
			//顶部标记和选项卡切换状态保持一致的Splash
			startIntent(CircleViewFlowActivity.class);
			break;

		default:
			break;
		}
		
	}
	
	/**
	 * 切换Activity
	 * @param class1
	 */
	public void startIntent(Class class1){
		Intent intent = new Intent(SplashMainActivity.this,class1);
		startActivity(intent);
	}

}
