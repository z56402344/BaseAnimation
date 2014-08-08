package com.duguang.baseanimation.ui.customview.hscrollview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.base.BaseActivity;
import com.duguang.baseanimation.ui.customview.hscrollview.MyScrollView.OnScrollListener1;

/**
 * 自定义ScrollView主页面
 * @author Administrator
 *
 */
public class ScrollviewMainActivity extends BaseActivity {
	
	final String[] arr ={"骨科","妇科","普外科","神经内科","神经科","神经外科","普外","普内","呼吸科","消化科","儿科","心内"};
	private MyScrollView myView;// 自定义的滑动view
	private LinearLayout sortliner;// 滑动条
	private List<TextView> sortTextViews = new ArrayList<TextView>();// 显示二级种类的TextView

	@Override
	public void setView() {
		//去 头
//    	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_custom_hscrollview_main);
		
	}

	@Override
	public void initView() {
		 myView = (MyScrollView) findViewById(R.id.myView);
	        sortliner = (LinearLayout) findViewById(R.id.sortliner);
	        
			LayoutParams params = new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			sortliner.removeAllViews();
			initData(params);
	        
		
	}

	
	@Override
	public void setListener() {
		myView.setOnScrollListener(new OnScrollListener1(){
			
			@Override
			public void onScroll() {
				Log.d("tao", "在中间滑动");
			}
			
			@Override
			public void onRight() {
				Log.d("tao", "滑动到了最右边");
			}
			
			@Override
			public void onLeft() {
				Log.d("tao", "滑动到了最左边");
			}
		});
		
	}
	
	/**
	 * 准备数据
	 * @param params
	 */
	private void initData(LayoutParams params) {
		for (int i = 0; i < arr.length ; i++) {// 往二级分类中加载数据
			Button sort = new Button(ScrollviewMainActivity.this);
			// 给二级分类赋值
			sort.setText("测试"+i);
			// 设置字体大小
			sort.setTextSize(15);
			// 设置二级分类的最小高度
			sort.setMinHeight(30);
			// 设置二级分类的周边
			sort.setPadding(20, 5, 20, 5);
			// 把TextView添加到滑动条内
			sortliner.addView(sort, i, params);
			sortTextViews.add(sort);
		}
	}

}