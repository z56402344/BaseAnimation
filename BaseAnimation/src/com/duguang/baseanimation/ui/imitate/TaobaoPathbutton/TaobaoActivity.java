package com.duguang.baseanimation.ui.imitate.TaobaoPathbutton;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.base.BaseActivity;

/**
 * 高仿淘宝按钮主页面
 * @author 
 *
 */
public class TaobaoActivity extends BaseActivity {

	private ComposerLayout clayout;
	@Override
	public void setView() {
		setContentView(R.layout.activity_imitate_taobao);
		
	}

	@Override
	public void initView() {
		// 引用控件
		clayout = (ComposerLayout) findViewById(R.id.test);
		clayout.init(new int[] { R.drawable.composer_camera,
				R.drawable.composer_music, R.drawable.composer_place,
				R.drawable.composer_sleep, R.drawable.composer_thought,
				R.drawable.composer_with }, R.drawable.composer_button,
				R.drawable.composer_icn_plus, ComposerLayout.RIGHTCENTER, 180,
				300);
		
	}

	@Override
	public void setListener() {
		// 点击事件监听，100+0对应composer_camera，100+1对应composer_music……如此类推你有机个按钮就加几个按钮都行。
				OnClickListener clickit = new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (v.getId() == 100 + 0) {
							Toast.makeText(TaobaoActivity.this, "打开相机...", 0).show();
						} else if (v.getId() == 100 + 1) {
							Toast.makeText(TaobaoActivity.this, "打开音乐...", 0).show();
						} else if (v.getId() == 100 + 2) {
							Toast.makeText(TaobaoActivity.this, "打开地理位置...", 0).show();
						} else if (v.getId() == 100 + 3) {
							Toast.makeText(TaobaoActivity.this, "打开睡觉模式...", 0).show();
						} else if (v.getId() == 100 + 4) {
							Toast.makeText(TaobaoActivity.this, "打开对话模式...", 0).show();
						} else if (v.getId() == 100 + 5) {
							Toast.makeText(TaobaoActivity.this, "关于我..", 0).show();
						}
					}
				};
				clayout.setButtonsOnClickListener(clickit);

				//下面几个句纯粹测试下父控件,实际开发是可以去掉
				// 下面呢幾句純粹攞嚟測試下父控件點唔點倒，實際用嘅時候可以去掉。
				RelativeLayout rl = (RelativeLayout) findViewById(R.id.rlparent);
				rl.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Toast.makeText(TaobaoActivity.this, "父控件可以點擊的哦!!!", 0).show();
						System.out.println("父控件可以點擊就即系冇吡截咗。");
					}
				});
		
	}
	
}

