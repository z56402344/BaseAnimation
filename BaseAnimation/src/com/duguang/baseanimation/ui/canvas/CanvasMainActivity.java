package com.duguang.baseanimation.ui.canvas;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.duguang.baseanimation.ContantValue;
import com.duguang.baseanimation.R;
import com.duguang.baseanimation.adapter.AnimationAdapter;
import com.duguang.baseanimation.ui.base.BaseActivity;
import com.duguang.baseanimation.ui.canvas.chart.charts.ChartMainActivity;
import com.duguang.baseanimation.ui.canvas.olympics.OlympicMainActivity;

/**
 * 画笔绘制效果集合主页面
 * @author Administrator
 *
 */
public class CanvasMainActivity extends BaseActivity implements OnItemClickListener {
	
	private AnimationAdapter adapter;
	private ListView listView_anim_complex;
	@Override
	public void setView() {
		setContentView(R.layout.activity_anim_complex);
		
	}

	@Override
	public void initView() {
		listView_anim_complex = (ListView) findViewById(R.id.listView_anim_complex);
		adapter = new AnimationAdapter(this, ContantValue.CanvasName);
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
			startIntent(OlympicMainActivity.class);
			break;
		case 1:
			startIntent(ChartMainActivity.class);
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
		Intent intent = new Intent(CanvasMainActivity.this,class1);
		startActivity(intent);
	}
}
