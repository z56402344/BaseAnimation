package com.duguang.baseanimation.ui.customview;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.base.BaseActivity;

/**
 * 菜单式PopWindow 主页面
 * @author Administrator
 *
 */
public class TransparentPopWindowMenu extends BaseActivity {
	
	private String TAG = this.getClass().getSimpleName();
	
	private int[] resArray = new int[] {
			R.drawable.icon_menu_addto, R.drawable.icon_menu_audioinfo,
			R.drawable.icon_menu_findlrc, R.drawable.icon_menu_scan
	};
	
	private String[] title = new String[]{
			"添加歌曲", "歌曲信息", "查找歌词", "搜索歌词"
	};
	
	private static boolean show_flag = false;
	
	private PopupWindow pw = null;
	
	@Override
	public void setView() {
		 setContentView(R.layout.activity_custom_transparent_popwindow_main);
		
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.e(TAG, "------  onCreateOptionsMenu ------");
		
		//用AlertDialog弹出menu
/*		View view = LayoutInflater.from(this).inflate(R.layout.menu, null);
		GridView grid1 = (GridView)view.findViewById(R.id.menuGridChange);
		grid1.setAdapter(new ImageAdapter(this));
		Builder build = new AlertDialog.Builder(this);
		build.setView(view);
		build.show();*/
		
		LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);      
		View view = inflater.inflate(R.layout.activity_custom_transparent_popwindow_menu, null);
		GridView grid1 = (GridView)view.findViewById(R.id.menuGridChange);
		grid1.setAdapter(new ImageAdapter(this));
		
		//用Popupwindow弹出menu
		pw = new PopupWindow(view,LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		
		//NND, 第一个参数， 必须找个View
		pw.showAtLocation(findViewById(R.id.tv), Gravity.CENTER, 0, 300);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}


	public class ImageAdapter extends BaseAdapter {
		
		private Context context;
		
		public ImageAdapter(Context context) {
			this.context = context;
		}
		
		@Override
		public int getCount() {
			return resArray.length;
		}

		@Override
		public Object getItem(int arg0) {
			return resArray[arg0];
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			LinearLayout linear = new LinearLayout(context);
			LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			linear.setOrientation(LinearLayout.VERTICAL);
			
			ImageView iv = new ImageView(context);
			iv.setImageBitmap(((BitmapDrawable)context.getResources().getDrawable(resArray[arg0])).getBitmap());
			LinearLayout.LayoutParams params2 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params2.gravity=Gravity.CENTER;
			linear.addView(iv, params2);
			
			TextView tv = new TextView(context);
			tv.setText(title[arg0]);
			LinearLayout.LayoutParams params3 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params3.gravity=Gravity.CENTER;
			
			linear.addView(tv, params3);
			
			return linear;
		}
	}
	
}