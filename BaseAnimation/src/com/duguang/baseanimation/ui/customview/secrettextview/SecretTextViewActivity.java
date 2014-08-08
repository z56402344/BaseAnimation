package com.duguang.baseanimation.ui.customview.secrettextview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.base.BaseActivity;

/**
 * 
 * Created by matt on 5/27/14.
 * 源码地址：https://github.com/matthewrkula/secretTextview
 */
public class SecretTextViewActivity extends BaseActivity {

	SecretTextView secretTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void setView() {
		setContentView(R.layout.activity_custom_secrettextview_main);

	}

	@Override
	public void initView() {
		secretTextView = (SecretTextView) findViewById(R.id.textview);
		secretTextView.setmDuration(3000);
		secretTextView.setIsVisible(true);

	}

	@Override
	public void setListener() {
		secretTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				secretTextView.toggle();
			}
		});

	}
}
