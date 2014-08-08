package com.duguang.baseanimation.ui.imitate;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.base.BaseActivity;
import com.duguang.baseanimation.utils.Utils;

public class YouKuActivity extends BaseActivity implements OnClickListener {

	 private final String TAG = "MainActivity";
	    private boolean isDisplayMenu = false;		// 菜单的显示状态, 默认为不显示
	    private boolean isDisplayLevel2 = false;	// 二级菜单的显示状态, 默认为不显示
	    private boolean isDisplayLevel3 = false;	// 三级菜单的显示状态, 默认为不显示
	    
		private RelativeLayout rlLevel1;
		private RelativeLayout rlLevel2;
		private RelativeLayout rlLevel3;
		
		private Button bt_show;
		private TextView tv_text;

		
		@Override
		public void setView() {
			setContentView(R.layout.activity_imitate_youku);
			
		}

		@Override
		public void initView() {
			rlLevel1 = (RelativeLayout) findViewById(R.id.rl_level1);
			rlLevel2 = (RelativeLayout) findViewById(R.id.rl_level2);
			rlLevel3 = (RelativeLayout) findViewById(R.id.rl_level3);
			
			bt_show = (Button) findViewById(R.id.bt_show);
			tv_text = (TextView) findViewById(R.id.tv_text);
			
			
			rlLevel1.setVisibility(View.GONE);
			rlLevel2.setVisibility(View.GONE);
			rlLevel3.setVisibility(View.GONE);
			
		}

		@Override
		public void setListener() {
			findViewById(R.id.ib_home).setOnClickListener(this);
			findViewById(R.id.ib_menu).setOnClickListener(this);
			bt_show.setOnClickListener(this);
			
		}
	
		
		@Override
		public void onClick(View arg0) {
			if(Utils.isRunningAnimation()) return;
			
			switch (arg0.getId()) {
			case R.id.ib_home:
				
				if(isDisplayLevel2) {
					
					long startOffset = 0;
					if(isDisplayLevel3) {
						Utils.startOutRotateAnimation(rlLevel3, startOffset);
						isDisplayLevel3 = !isDisplayLevel3;
						startOffset = 200;
					}
					
					// 隐藏二级菜单
					Utils.startOutRotateAnimation(rlLevel2, startOffset);
				} else {
					// 显示二级菜单
					Utils.startInRotateAnimation(rlLevel2, 0);
				}
				isDisplayLevel2 = !isDisplayLevel2;
				break;
			case R.id.ib_menu:
				if(isDisplayLevel3) {
					// 隐藏三级菜单
					Utils.startOutRotateAnimation(rlLevel3, 0);
				} else {
					// 显示三级菜单
					Utils.startInRotateAnimation(rlLevel3, 0);
				}
				isDisplayLevel3 = !isDisplayLevel3;
				break;
				
			case R.id.bt_show:
				if(showMeun()){
					bt_show.setText("关闭优酷菜单");
				}else{
					bt_show.setText("出现优酷菜单");
				}
				startAnimation();
				break;
			default:
				break;
			}
		}
		
		/**
		 * 优酷菜单是否显示
		 * @return
		 */
		private boolean showMeun() {
			if(Utils.isRunningAnimation()) return true;
			Log.i(TAG, "菜单键被点击了..");
			if(isDisplayMenu) {		
				long startOffset = 0;
				// 隐藏菜单
				if(isDisplayLevel2) {		// 二级菜单显示状态, 隐藏
					
					if(isDisplayLevel3) {	// 三级菜单显示状态, 隐藏它
						Utils.startOutRotateAnimation(rlLevel3, startOffset);
						startOffset = 200;
						isDisplayLevel3 = !isDisplayLevel3;
					}
					
					Utils.startOutRotateAnimation(rlLevel2, startOffset);
					startOffset += 200;
					isDisplayLevel2 = !isDisplayLevel2;
				}
				
				// 隐藏一级菜单
				Utils.startOutRotateAnimation(rlLevel1, startOffset);
			} else {		
				// 显示菜单
				// 判断菜单是否是为View.GONE的状态
				if(rlLevel1.getVisibility() == View.GONE
						&& rlLevel2.getVisibility() == View.GONE
						&& rlLevel3.getVisibility() == View.GONE) {
					// 设置菜单显示
					rlLevel1.setVisibility(View.VISIBLE);
					rlLevel2.setVisibility(View.VISIBLE);
					rlLevel3.setVisibility(View.VISIBLE);
				}
				
				Utils.startInRotateAnimation(rlLevel1, 0);
				Utils.startInRotateAnimation(rlLevel2, 200);
				Utils.startInRotateAnimation(rlLevel3, 400);
				
				isDisplayLevel2 = !isDisplayLevel2;
				isDisplayLevel3 = !isDisplayLevel3;
			}
			
			return isDisplayMenu = !isDisplayMenu;
		}
		
		public void startAnimation(){
			Animation anim = AnimationUtils.loadAnimation(this, R.anim.small_2_big);
			tv_text.startAnimation(anim);
		}

	
}
