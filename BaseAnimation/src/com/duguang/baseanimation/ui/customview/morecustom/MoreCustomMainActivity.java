package com.duguang.baseanimation.ui.customview.morecustom;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.base.BaseActivity;
import com.duguang.baseanimation.ui.customview.morecustom.biaopan.BiaoPanData;
import com.duguang.baseanimation.ui.customview.morecustom.biaopan.BiaoPanview;

/**
 * ViewPager+ScrollView+ListView效果 主页面
 * @author Administrator
 *
 */
public class MoreCustomMainActivity extends BaseActivity {

	private View view;
	private String[] lxs;
	private String[] titles;
	private NonScrollListView lv;
	private TextView aqzs;
	private ImageView img;
	private ImageView img_init_1;
	private ImageView img_init_2;
	private TextView jkzs;
	private ImageView jt;
	private ImageView jt2;
	private LinearLayout ll_xq;
	private LinearLayout ll_xy;
	private RelativeLayout relative1;
	private RelativeLayout rt_1;
	private RelativeLayout rt_2;
	private RelativeLayout rt_title;
	private TextView tv_days;
	private TextView tv_days2;
	private TextView tv_init_1;
	private TextView tv_init_2;
	private TextView tv_init_3;
	private TextView tv_init_4;
	private TextView tv_name;
	private TextView tv_nr;
	private TextView tv_song;
	private TextView tv_title2;
	private int[] vs;
	private int[] imgs;
	private TextView tv_title;
	private List<String> lx;

	// private ViewPager viewpager;
	private MyPageAdapter viewpageAdapter;
	private ArrayList<View> viewList;
	private ListView lv1;
	private ListView lv2;
	private ListView lv3;
	private int dataNum = 10;


	@Override
	public void setView() {
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_custom_morecustom_main);
		
	}

	@Override
	public void initView() {
		prepareData();
		ViewPager viewpager = (ViewPager)findViewById(R.id.viewpager);
		viewpageAdapter = new MyPageAdapter();
		viewpager.setAdapter(viewpageAdapter);
		
		lv1 = (ListView) findViewById(R.id.lv1);
		MyListAdapter1 adapter1 = new MyListAdapter1();
		lv1.setAdapter(adapter1);
		new ListUtils(this).setListViewHeightBasedOnChildren(lv1);
		lv1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println("click"+position);
				
			}
		});
		lv2 = (ListView) findViewById(R.id.lv2);
		MyListAdapter2 adapter2 = new MyListAdapter2();
		lv2.setAdapter(adapter2);
		new ListUtils(this).setListViewHeightBasedOnChildren(lv2);
		
		lv3 = (ListView) findViewById(R.id.lv3);
		MyListAdapter3 adapter3 = new MyListAdapter3();
		lv3.setAdapter(adapter3);
		int totalHeight = dip2px(this, 35) * 3;
		ViewGroup.LayoutParams params = lv3.getLayoutParams();
		params.height = totalHeight
				+ (lv3.getDividerHeight() * (lv3.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		lv3.setLayoutParams(params);
		
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		
	}
	
	private class MyListAdapter3 extends BaseAdapter {

		@Override
		public int getCount() {
			return dataNum;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView view = new TextView(MoreCustomMainActivity.this);
			view.setText("理想丰满回复你发的《生如夏花》"+position);
			view.setTextSize(14);
			AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, dip2px(getApplicationContext(), 35));
			view.setGravity(Gravity.CENTER_VERTICAL);
			view.setLayoutParams(layoutParams);
			view.setBackgroundResource(android.R.drawable.editbox_background);
			return view;
		}
	}
	private class MyListAdapter1 extends BaseAdapter {

		@Override
		public int getCount() {
			return dataNum;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(getApplicationContext(),
					R.layout.activity_custom_morecustom_lv1_item, null);
			return view;
		}

	}
	
	private class MyListAdapter2 extends BaseAdapter {

		@Override
		public int getCount() {
			return dataNum;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(getApplicationContext(),
					R.layout.activity_custom_morecustom_lv1_item, null);
			return view;
		}

	}

	private void prepareData() {
		viewList = new ArrayList<View>();
		ImageView view1 = new ImageView(this);
		view1.setImageResource(R.drawable.icon_morecustom_launcher);
		viewList.add(view1);
		LinearLayout view2 = (LinearLayout) View.inflate(
				getApplicationContext(), R.layout.activity_custom_morecustom_biaopan_view, null);
		BiaoPanview biaopan = (BiaoPanview) view2.findViewById(R.id.biaopan);
		BiaoPanData data = new BiaoPanData();
		data.setTianqi("晴");
		data.setChengdu("空气正常");
		data.setCity("北京");
		data.setQiwen(9);
		data.setZhiliang(101);
		data.setTishi("可以多参加户外活动,呼吸空气");
		biaopan.setBiaopanData(data);
		viewList.add(view2);

	}

	class MyPageAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return viewList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(viewList.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(viewList.get(position));
			return viewList.get(position);
		}
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

}
