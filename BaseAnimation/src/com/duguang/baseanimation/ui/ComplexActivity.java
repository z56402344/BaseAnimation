package com.duguang.baseanimation.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.duguang.baseanimation.ContantValue;
import com.duguang.baseanimation.R;
import com.duguang.baseanimation.R.anim;
import com.duguang.baseanimation.R.id;
import com.duguang.baseanimation.R.layout;
import com.duguang.baseanimation.adapter.AnimationAdapter;
import com.duguang.baseanimation.ui.base.BaseActivity;

/**
 * 复杂动画页面
 * @author duguang
 * 博客地址:http://blog.csdn.net/duguang77
 */
public class ComplexActivity extends BaseActivity implements
		OnItemClickListener {

	private AnimationAdapter adapter;
	private ListView listView_anim_complex;

	@Override
	public void setView() {
		setContentView(R.layout.activity_anim_complex);

	}

	@Override
	public void initView() {
		listView_anim_complex = (ListView) findViewById(R.id.listView_anim_complex);

		adapter = new AnimationAdapter(this, ContantValue.animName);
	}

	@Override
	public void setListener() {
		listView_anim_complex.setAdapter(adapter);
		listView_anim_complex.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Animation anim = AnimationUtils.loadAnimation(ComplexActivity.this,
				ContantValue.complex[position]);
		listView_anim_complex.startAnimation(anim);
	}

}
