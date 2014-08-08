package com.duguang.baseanimation.ui.gesturepassword.homekey;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.duguang.baseanimation.R;

public class GridAdapter extends BaseAdapter {
	private Context mContext;
	private String[] gridList;
	private LayoutInflater mInflater;
	
	public GridAdapter(Context context, String[] lists) {
		this.mContext = context;
		this.gridList = lists;
		mInflater = LayoutInflater.from(mContext);
	}
	
	@Override
	public int getCount() {
		return gridList.length;
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
		ImageView image;
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.gridview_image, null);
			image = (ImageView) convertView.findViewById(R.id.imageView_grid);
			convertView.setTag(image);
		} else {
			image = (ImageView)convertView.getTag();
		}
		if("0".equals(gridList[position])){
			image.setBackgroundResource(R.drawable.gesturepassword_grid_imit);
		} else {
			image.setBackgroundResource(R.drawable.gesturepassword_grid_checked);
		}
		return convertView;
	}
}
