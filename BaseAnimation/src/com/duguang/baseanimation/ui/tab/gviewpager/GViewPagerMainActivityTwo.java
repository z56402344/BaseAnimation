package com.duguang.baseanimation.ui.tab.gviewpager;

import java.util.ArrayList;
import java.util.HashMap;

import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.base.BaseActivity;

public class GViewPagerMainActivityTwo extends BaseActivity {
	private GridView gridview;
	
	@Override
	public void setView() {
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_tab_gviewpager_two);

	}

	@Override
	public void initView() {

		gridview = (GridView) findViewById(R.id.gridview);
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 1; i < 5; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (i == 1) {
				map.put("ItemImage", R.drawable.tab_gviewpager_item1);
				map.put("ItemText", getResources()
						.getString(R.string.gridview1));
			}
			if (i == 2) {
				map.put("ItemImage", R.drawable.tab_gviewpager_item2);
				map.put("ItemText", getResources()
						.getString(R.string.gridview2));
			}
			if (i == 3) {
				map.put("ItemImage", R.drawable.tab_gviewpager_item3);
				map.put("ItemText", getResources()
						.getString(R.string.gridview3));
			}
			if (i == 4) {
				map.put("ItemImage", R.drawable.tab_gviewpager_item4);
				map.put("ItemText", getResources()
						.getString(R.string.gridview4));
			}
			lstImageItem.add(map);

		}
		
		SimpleAdapter saImageItems = new SimpleAdapter(this, lstImageItem,
				R.layout.activity_tab_gviewpager_grid_item, new String[] {
						"ItemImage", "ItemText" }, new int[] { R.id.ItemImage,
						R.id.ItemText });
		gridview.setAdapter(saImageItems);
		

	}

	@Override
	public void setListener() {
		gridview.setOnItemClickListener(new ItemClickListener());

	}
	
	class ItemClickListener implements OnItemClickListener {

		@SuppressWarnings("unchecked")
		public void onItemClick(AdapterView<?> arg0,// The AdapterView where the
													// click happened
				View arg1,// The view within the AdapterView that was clicked
				int arg2,// The position of the view in the adapter
				long arg3// The row id of the item that was clicked
		) {

			HashMap<String, Object> item = (HashMap<String, Object>) arg0
					.getItemAtPosition(arg2);

			if (item.get("ItemText").equals(
					getResources().getString(R.string.gridview1))) {
				Toast.makeText(GViewPagerMainActivityTwo.this,
						R.string.gridview1, Toast.LENGTH_LONG).show();
			}
			if (item.get("ItemText").equals(
					getResources().getString(R.string.gridview2))) {
				Toast.makeText(GViewPagerMainActivityTwo.this,
						R.string.gridview2, Toast.LENGTH_LONG).show();
			}
			if (item.get("ItemText").equals(
					getResources().getString(R.string.gridview3))) {
				Toast.makeText(GViewPagerMainActivityTwo.this,
						R.string.gridview3, Toast.LENGTH_LONG).show();
			}
			if (item.get("ItemText").equals(
					getResources().getString(R.string.gridview4))) {
				Toast.makeText(GViewPagerMainActivityTwo.this,
						R.string.gridview4, Toast.LENGTH_LONG).show();
			}
		}
	}

}
