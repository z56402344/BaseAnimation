package com.duguang.baseanimation.ui.listivew;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.duguang.baseanimation.ContantValue;
import com.duguang.baseanimation.R;
import com.duguang.baseanimation.adapter.AnimationAdapter;
import com.duguang.baseanimation.ui.base.BaseActivity;
import com.duguang.baseanimation.ui.listivew.deletealpha.DeleteAlphaMainActivity;
import com.duguang.baseanimation.ui.listivew.deleteleft.DeleteLeftMainActivity;
import com.duguang.baseanimation.ui.listivew.deletelistview.DeleteListViewMainActivity;
import com.duguang.baseanimation.ui.listivew.indexlistview.IndexMainActivity;
import com.duguang.baseanimation.ui.listivew.interceptor.ListViewInterceptorMainActivity;
import com.duguang.baseanimation.ui.listivew.listforpage.ListForPageMainActivity;
import com.duguang.baseanimation.ui.listivew.listviews.GoogleCardsActivity;
import com.duguang.baseanimation.ui.listivew.listviews.GridViewActivity;
import com.duguang.baseanimation.ui.listivew.listviews.appearanceexamples.AppearanceExamplesActivity;
import com.duguang.baseanimation.ui.listivew.listviews.itemmanipulationexamples.AnimateDismissActivity;
import com.duguang.baseanimation.ui.listivew.listviews.itemmanipulationexamples.DragAndDropActivity;
import com.duguang.baseanimation.ui.listivew.listviews.itemmanipulationexamples.ExpandableListItemActivity;
import com.duguang.baseanimation.ui.listivew.listviews.itemmanipulationexamples.SwipeDismissActivity;
import com.duguang.baseanimation.ui.listivew.refresh.RefreshMainActivity;
import com.duguang.baseanimation.ui.listivew.sortlistview.SortListViewMainActivity;
import com.duguang.baseanimation.ui.tab.paralloid.ParalloidHomeActivity;

/**
 * ListView主页面
 * 继承BaseActivity获取ActionBar
 * @author duguang 博客地址:http://blog.csdn.net/duguang77
 */
public class ListViewMani extends BaseActivity implements OnItemClickListener {

	private AnimationAdapter adapter;
	private ListView listView_anim_complex;
	@Override
	public void setView() {
		setContentView(R.layout.activity_anim_complex);
		
	}

	@Override
	public void initView() {
		listView_anim_complex = (ListView) findViewById(R.id.listView_anim_complex);

		adapter = new AnimationAdapter(this, ContantValue.listViewName);
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
			startIntent(RefreshMainActivity.class);
			break;
		case 1:
			startIntent(DeleteListViewMainActivity.class);
			break;
		case 2:
			startIntent(SortListViewMainActivity.class);
			break;
		case 3:
			startIntent(IndexMainActivity.class);
			break;
		case 4:
			startIntent(DeleteLeftMainActivity.class);
			break;
		case 5:
			startIntent(DeleteAlphaMainActivity.class);
			break;
		case 6:
			startIntent(GoogleCardsActivity.class);
			break;
		case 7:
			startIntent(GridViewActivity.class);
			break;
		case 8:
			startIntent(AppearanceExamplesActivity.class);
			break;
		case 9:
			startIntent(DragAndDropActivity.class);
			break;
		case 10:
			startIntent(SwipeDismissActivity.class);
			break;
		case 11:
			startIntent(AnimateDismissActivity.class);
			break;
		case 12:
			startIntent(ExpandableListItemActivity.class);
			break;
		case 13:
			startIntent(ListViewInterceptorMainActivity.class);
			break;
		case 14:
			startIntent(ListForPageMainActivity.class);
			break;
		case 15:
			startIntent(ParalloidHomeActivity.class);
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
		Intent intent = new Intent(ListViewMani.this,class1);
		startActivity(intent);
	}
}
