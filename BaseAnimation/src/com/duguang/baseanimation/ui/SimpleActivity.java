package com.duguang.baseanimation.ui;

import com.duguang.baseanimation.ContantValue;
import com.duguang.baseanimation.R;
import com.duguang.baseanimation.R.id;
import com.duguang.baseanimation.R.layout;
import com.duguang.baseanimation.adapter.AnimationAdapter;
import com.duguang.baseanimation.ui.base.BaseActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 简单动画页面
 * @author duguang
 * 博客地址:http://blog.csdn.net/duguang77
 */
public class SimpleActivity extends BaseActivity implements OnItemClickListener {

	private AnimationAdapter adapter;
	private ListView listView_anim_simple;


	@Override
	public void setView() {
		setContentView(R.layout.activity_anim_simple);
		
	}
	
	@Override
	public void initView() {
		listView_anim_simple = (ListView) findViewById(R.id.listView_anim_simple);

		adapter = new AnimationAdapter(this, ContantValue.simpleName);
	}

	@Override
	public void setListener() {
		listView_anim_simple.setAdapter(adapter);
		listView_anim_simple.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Animation anim = AnimationUtils.loadAnimation(SimpleActivity.this,
				ContantValue.simple[position]);
		listView_anim_simple.startAnimation(anim);
	}

}
