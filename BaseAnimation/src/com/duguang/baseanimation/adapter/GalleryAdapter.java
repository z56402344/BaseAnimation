package com.duguang.baseanimation.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;

import com.duguang.baseanimation.utils.ImageUtils;

/**
 * 高仿效果中3D图片浏览的适配器
 * @author Administrator
 *
 */
public class GalleryAdapter extends BaseAdapter {
	
	private Context context;
	private int[] imageResIDs;
	
	public GalleryAdapter(Context context, int[] imageResIDs) {
		this.context = context;
		this.imageResIDs = imageResIDs;
	}

	@Override
	public int getCount() {
		return imageResIDs.length;
	}

	@Override
	public Object getItem(int position) {
		return imageResIDs[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView iv = null;
		if(convertView == null) {
			iv = new ImageView(context);
		} else {
			iv = (ImageView) convertView;
		}
		
		Bitmap bitmap = ImageUtils.getBitmap(context, imageResIDs[position], position);
		BitmapDrawable bd = new BitmapDrawable(bitmap);
		bd.setAntiAlias(true);
		iv.setImageDrawable(bd);
		iv.setLayoutParams(new LayoutParams(160, 240));
		return iv;
	}

}
