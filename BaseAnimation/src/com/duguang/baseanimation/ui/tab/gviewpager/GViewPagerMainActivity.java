package com.duguang.baseanimation.ui.tab.gviewpager;

import java.util.ArrayList;

import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.duguang.baseanimation.R;

public class GViewPagerMainActivity extends ActivityGroup implements OnClickListener {
	private RadioGroup radioGroup;
	private String title[] = { "我 爱 学 习", "其 他 相 关"};
	private LinearLayout linearLayout;
	private final int height = 50;
	private ArrayList<TextView> textViews;
	private ViewPager viewPager;
	private ArrayList<View> pageViews;
	private HorizontalScrollView horizontalScrollView;
    
	/***
	 * init view
	 */
	void InItView() {
		pageViews = new ArrayList<View>();
		View view01 = getLocalActivityManager().startActivity("activity01",
				new Intent(this, GViewPagerMainActivityOne.class)).getDecorView();
		View view02 = getLocalActivityManager().startActivity("activity02",
				new Intent(this, GViewPagerMainActivityTwo.class)).getDecorView();
		pageViews.add(view01);
		pageViews.add(view02);
	}
    /***
     * init title
     */
	void InItTitle() {
		int width = getWindowManager().getDefaultDisplay().getWidth() / 2;
		for (int i = 0; i < title.length; i++) {
			RadioButton radioButton = new RadioButton(this, null,
					R.style.radioButton);
			radioButton.setText(title[i]);
			radioButton.setTextSize(14);
			radioButton.setTextColor(000000);
			radioButton.setWidth(width);
			radioButton.setHeight(height);
			radioButton.setGravity(Gravity.CENTER);
			radioGroup.addView(radioButton);
		}
	}
	 /***
     * init title
     */
	void InItTitle1() {
		textViews = new ArrayList<TextView>();
		int width = getWindowManager().getDefaultDisplay().getWidth() / 2;
		int height = 50;
		for (int i = 0; i < title.length; i++) {
			TextView textView = new TextView(this);
			textView.setText(title[i]);
			textView.setTextSize(14);
			textView.setTextColor(000000);
			textView.setWidth(width);
			textView.setHeight(height);
			textView.setGravity(Gravity.CENTER);
			textView.setId(i);
			textView.setOnClickListener(this);
			textViews.add(textView);
			// 分割线
			View view = new View(this);
			LinearLayout.LayoutParams layoutParams = new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			layoutParams.width = 1;
			layoutParams.height = height;
			layoutParams.gravity = Gravity.CENTER;
			view.setLayoutParams(layoutParams);
			view.setBackgroundColor(000000);
			linearLayout.addView(textView);
			if (i != title.length - 1) {
				linearLayout.addView(view);
			}

		}
	}

	/***
	 * 选中效果
	 */
	public void setSelector(int id) {
		for (int i = 0; i < title.length; i++) {
			if (id == i) {
				Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
						R.drawable.tab_gviewpager_grouplist_item_bg_normal);
				textViews.get(id).setBackgroundDrawable(
						new BitmapDrawable(bitmap));
				textViews.get(id).setTextColor(Color.BLACK);
				viewPager.setCurrentItem(i);
			}else {
				textViews.get(i).setBackgroundDrawable(new BitmapDrawable());
				textViews.get(i).setTextColor(getResources().getColor(R.color.black));
			}
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_tab_gviewpager_main);
		linearLayout = (LinearLayout) findViewById(R.id.ll_main);
		viewPager = (ViewPager) findViewById(R.id.pager);
		horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
		InItTitle1();
		setSelector(0);
		InItView();
		viewPager.setAdapter(new myPagerView());
		viewPager.clearAnimation();
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				setSelector(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	@Override
	public void onClick(View v) {
		setSelector(v.getId());
	}

	class myPagerView extends PagerAdapter {
		// 显示数目
		@Override
		public int getCount() {
			return pageViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return super.getItemPosition(object);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			// TODO Auto-generated method stub
			((ViewPager) arg0).removeView(pageViews.get(arg1));
		}

		/***
		 * 获取每一个item， 类于listview中的getview
		 */
		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(pageViews.get(arg1));
			return pageViews.get(arg1);
		}

	}

}