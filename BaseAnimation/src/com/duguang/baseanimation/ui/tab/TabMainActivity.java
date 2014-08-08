package com.duguang.baseanimation.ui.tab;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.duguang.baseanimation.ContantValue;
import com.duguang.baseanimation.R;
import com.duguang.baseanimation.adapter.AnimationAdapter;
import com.duguang.baseanimation.ui.base.BaseActivity;
import com.duguang.baseanimation.ui.tab.PagerTabStrip.PagerTabMainActivity;
import com.duguang.baseanimation.ui.tab.gviewpager.GViewPagerMainActivity;
import com.duguang.baseanimation.ui.tab.paralloid.ParalloidHomeActivity;
import com.duguang.baseanimation.ui.tab.scroll.ScrollViewMainActivity;
import com.duguang.baseanimation.ui.tab.tabhost.TabHostMoveMainActivity;
import com.duguang.baseanimation.ui.tab.viewflow.AsyncDataFlowActivity;
import com.duguang.baseanimation.ui.tab.viewflow.CircleViewFlowActivity;
import com.duguang.baseanimation.ui.tab.viewflow.DiffViewFlowActivity;
import com.duguang.baseanimation.ui.tab.viewflow.TitleViewFlowActivity;

/**
 * Tab动画主页面
 * 继承BaseActivity获取ActionBar
 * @author duguang 博客地址:http://blog.csdn.net/duguang77
 */
public class TabMainActivity extends BaseActivity implements OnItemClickListener {

	private AnimationAdapter adapter;
	private ListView listView_anim_complex;
	@Override
	public void setView() {
		setContentView(R.layout.activity_anim_complex);
		
	}

	@Override
	public void initView() {
		listView_anim_complex = (ListView) findViewById(R.id.listView_anim_complex);

		adapter = new AnimationAdapter(this, ContantValue.tabName);
		
		
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
			startIntent(WinterMainActivity.class);
			break;
		case 1:
			startIntent(ScrollViewMainActivity.class);
			break;
		case 2:
			startIntent(GViewPagerMainActivity.class);
			break;
		case 3:
			startIntent(TabHostMoveMainActivity.class);
			break;
		case 4:
			startIntent(PagerTabMainActivity.class);
			break;
		case 5:
			startIntent(ParalloidHomeActivity.class);
			break;
		case 6:
			startIntent(CircleViewFlowActivity.class);
			break;
		case 7:
			startIntent(TitleViewFlowActivity.class);
			break;
		case 8:
			startIntent(DiffViewFlowActivity.class);
			break;
		case 9:
			startIntent(AsyncDataFlowActivity.class);
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
		Intent intent = new Intent(TabMainActivity.this,class1);
		startActivity(intent);
	}

}
