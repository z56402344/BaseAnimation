package com.duguang.baseanimation.ui.customview.popwindow;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.base.BaseActivity;

/**
 * 自定义PopWindow 主页面
 * @author Administrator
 *
 */
public class PoupWindowMainActivity extends BaseActivity {

	private PopupWindow popupWindow;

	private ListView lv_group;

	private View view;

	private View top_title;

	private TextView tvtitle;

	private List<String> groups;

	/**
	 * 显示
	 * 
	 * @param parent
	 */
	private void showWindow(View parent) {

		if (popupWindow == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			view = layoutInflater.inflate(R.layout.activity_custom_popwindow_group_list, null);

			lv_group = (ListView) view.findViewById(R.id.lvGroup);
			// 加载数据
			groups = new ArrayList<String>();
			groups.add("我的微博");
			groups.add("好友");
			groups.add("亲人");
			groups.add("陌生人");

			GroupAdapter groupAdapter = new GroupAdapter(this, groups);
			lv_group.setAdapter(groupAdapter);
			// 创建一个PopuWidow对象
			popupWindow = new PopupWindow(view, 400, 450);
		}

		// 使其聚集
		popupWindow.setFocusable(true);
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);

		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		// 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
		int xPos = windowManager.getDefaultDisplay().getWidth() / 2
				- popupWindow.getWidth() / 2;

		Log.i("coder", "windowManager.getDefaultDisplay().getWidth()/2:"
				+ windowManager.getDefaultDisplay().getWidth() / 2);
		//
		Log.i("coder", "popupWindow.getWidth()/2:" + popupWindow.getWidth() / 2);

		Log.i("coder", "xPos:" + xPos);

		popupWindow.showAsDropDown(parent, xPos, 0);

		lv_group.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {

				Toast.makeText(PoupWindowMainActivity.this,
						"groups.get(position)" + groups.get(position), 1000)
						.show();

				if (popupWindow != null) {
					popupWindow.dismiss();
				}
			}
		});
	}

	@Override
	public void setView() {
		setContentView(R.layout.activity_custom_popwindow_main);

	}

	@Override
	public void initView() {
		top_title = this.findViewById(R.id.top_title);
		tvtitle = (TextView) top_title.findViewById(R.id.tvtitle);
		tvtitle.setText("做一个低调的码农");
		
	}

	@Override
	public void setListener() {
		tvtitle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showWindow(v);
			}
		});
		
	}
}