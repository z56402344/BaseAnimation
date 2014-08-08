package com.duguang.baseanimation.ui.imitate.widget;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.base.BaseActivity;

public class WidgetMainActivity extends BaseActivity {
	// 一个有只有一个按钮的activity

	private Button btn;
	@Override
	public void setView() {
		setContentView(R.layout.activity_imitate_widget_main);
		
	}

	@Override
	public void initView() {
		btn = (Button) findViewById(R.id.btn);
	}

	@Override
	public void setListener() {
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				funClick();
			}
		});
		
	}
	
	public void funClick() {
		// 按钮被点击
		this.startService(new Intent(getApplicationContext(), MserServes.class));
		// new TableShowView(this).fun(); 如果只是在activity中启动
		// 当activity跑去后台的时候[暂停态，或者销毁态] 我们设置的显示到桌面的view也会消失
		// 所以这里采用的是启动一个服务，服务中创建我们需要显示到table上的view，并将其注册到windowManager上
		this.finish();
	}

}