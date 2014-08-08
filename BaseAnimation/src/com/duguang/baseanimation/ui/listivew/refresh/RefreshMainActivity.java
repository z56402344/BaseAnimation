package com.duguang.baseanimation.ui.listivew.refresh;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.base.BaseActivity;
import com.duguang.baseanimation.ui.listivew.refresh.interf.OnRefreshListener;
import com.duguang.baseanimation.ui.listivew.refresh.view.RefreshListView;

/**
 * @author duguang
 *
 */
public class RefreshMainActivity extends BaseActivity {

    private List<String> listViewData;

	@Override
	public void setView() {
		  setContentView(R.layout.activity_listview_refresh_mani);
		
	}

	@Override
	public void initView() {

        
        final RefreshListView mRefreshListView = (RefreshListView) findViewById(R.id.refresh_listview);
        
        listViewData = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
			listViewData.add("这是一条ListView的数据" + i);
		}
        
        final MyAdapter mAdapter = new MyAdapter();
        mRefreshListView.setAdapter(mAdapter);
        mRefreshListView.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// 异步查询数据
				new AsyncTask<Void, Void, Void>(){

					@Override
					protected Void doInBackground(Void... params) {
						SystemClock.sleep(2000);
						listViewData.add(0, "这是下拉刷新出来BaseAnimation");
						return null;
					}
					
					protected void onPostExecute(Void result) {
						mAdapter.notifyDataSetChanged();
						
						// 隐藏头布局
						mRefreshListView.onRefreshFinish();
					}
				}.execute(new Void[]{});
			}

			@Override
			public void onLoadMoring() {
				new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						SystemClock.sleep(5000);
						listViewData.add("加载更多刷新出来BaseAnimation1");
						listViewData.add("加载更多刷新出来BaseAnimation2");
						listViewData.add("加载更多刷新出来BaseAnimation3");
						listViewData.add("加载更多刷新出来BaseAnimation4");
						listViewData.add("加载更多刷新出来BaseAnimation5");
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						super.onPostExecute(result);
						mAdapter.notifyDataSetChanged();
						mRefreshListView.onRefreshFinish();
					}
					
				}.execute(new Void[]{});
			}
		});
		
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		
	}
    
    /**
     * 填充适配器
     * @author dg
     *
     */
    class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return listViewData.size();
		}

		@Override
		public Object getItem(int arg0) {
			return listViewData.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView tv = null;
			if(convertView == null) {
				tv = new TextView(RefreshMainActivity.this);
			} else {
				tv = (TextView) convertView;
			}
			
			tv.setText(listViewData.get(position));
			tv.setTextSize(20);
			
			return tv;
		}
    	
    }


}
