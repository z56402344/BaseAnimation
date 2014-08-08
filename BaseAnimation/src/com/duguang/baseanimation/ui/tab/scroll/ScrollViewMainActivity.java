package com.duguang.baseanimation.ui.tab.scroll;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.base.BaseActivity;
import com.duguang.baseanimation.ui.tab.scroll.adapter.ViewPagerAdapter;
import com.duguang.baseanimation.ui.tab.scroll.widget.PluginScrollView;

/**
 * 横向ScrollView首页
 * 
 * @author Administrator
 * 
 */
public class ScrollViewMainActivity extends BaseActivity {

	PluginScrollView mPluginScrollView;
	ViewPager viewPager;
	ViewPagerAdapter viewPagerAdapter;
	List<View> testList;
	
	@Override
	public void setView() {
		setContentView(R.layout.activity_tab_scrollview_main);
		preInit();

	}

	@Override
	public void initView() {
		viewPager = (ViewPager) findViewById(R.id.viewpagerLayout);
		viewPagerAdapter = new ViewPagerAdapter();
		viewPagerAdapter.setList(testList);
		viewPager.setAdapter(viewPagerAdapter);
		viewPager.setCurrentItem(0);
		// mPluginScrollView = new PluginScrollView(this, viewPager, testList);
		mPluginScrollView = (PluginScrollView) findViewById(R.id.horizontalScrollView);
		mPluginScrollView.setTestList(testList);
		mPluginScrollView.setViewPager(viewPager);
		postInit();

	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub

	}

	private void preInit() {

		TextView textView;
		testList = new ArrayList<View>();
		for (int i = 0; i < 10; i++) {
			textView = new TextView(ScrollViewMainActivity.this);
			textView.setText("ViewPager ==>" + i);
			testList.add(textView);
		}
		
	}

	private void postInit() {
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				Log.d("k", "onPageSelected - " + arg0);
				mPluginScrollView.buttonSelected(arg0);
				viewPager.setCurrentItem(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				Log.d("k", "onPageScrolled - " + arg0);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				Log.d("k", "onPageScrollStateChanged - " + arg0);
				// 状态有三个0空闲，1是增在滑行中，2目标加载完毕
			}
		});

	}

	
}
