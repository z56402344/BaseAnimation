package com.duguang.baseanimation.ui.customview.gallerylistview;


import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.TextView;

import com.duguang.baseanimation.R;

public class GalleryItem extends Gallery {
	private float mLastMotionX;// 滑动过程中，x方向的初始坐标
	private float mLastMotionY;// 滑动过程中，y方向的初始坐标
	private int mTouchSlop;// 手指大小的距离
	public ArrayList<String> arraylist;
	public ImageAdapter adapter;
	
	public GalleryItem(Context context) {
		super(context);
		final ViewConfiguration configuration = ViewConfiguration
				.get(getContext());
		mTouchSlop = configuration.getScaledTouchSlop();
		this.setStaticTransformationsEnabled(true);
	}

	public GalleryItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.setStaticTransformationsEnabled(true);
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return super.onScroll(e1, e2, distanceX, distanceY);
	}


	public boolean onInterceptTouchEvent(MotionEvent ev) {
		final int action = ev.getAction();// 获取触摸事件类型
		final float x = ev.getX();// 每次触摸事件的x坐标
		final float y = ev.getY();// 每次触摸事件的y坐标
		switch (action) {
		case MotionEvent.ACTION_DOWN:// 按下事件
			mLastMotionX = x;// 初始化每次触摸事件的x方向的初始坐标，即手指按下的x方向坐标
			mLastMotionY = y;// 初始化每次触摸事件的y方向的初始坐标，即手指按下的y方向坐标
			break;

		case MotionEvent.ACTION_MOVE:
			final int deltaX = (int) (mLastMotionX - x);// 每次滑动事件x方向坐标与触摸事件x方向初始坐标的距离
			final int deltaY = (int) (mLastMotionY - y);// 每次滑动事件y方向坐标与触摸事件y方向初始坐标的距离
			boolean xMoved = Math.abs(deltaX) > mTouchSlop
					&& Math.abs(deltaY / deltaX) < 1;
			// 判断触摸事件处理的传递方向，该业务中是，
			// x方向的距离大于手指，并且y方向滑动的距离小于x方向的滑动距离时，Gallery消费掉此次触摸事件
			// 如果需要，请在您的业务中，改变判断的逻辑
			if (xMoved) {// Gallery需要消费掉此次触摸事件
			 return true;// 返回true就不会将此次触摸事件传递给子View了，我的业务中是ListView
			}
			break;
		case MotionEvent.ACTION_UP:
			if (this.getSelectedItemPosition() < 1) {
				this.setSelection(1);
				return true;
			}
			
			break;
		case MotionEvent.ACTION_OUTSIDE:
			break;
		case MotionEvent.ACTION_CANCEL:
			break;
		case MotionEvent.ACTION_MASK:
			break;
		}
		return false;// 将此次触摸事件传递给子View，即ListView
	}

	public void initAdapter(Context context, int[] item) {
		this.adapter = new ImageAdapter(context, item);
	}

	public class ImageAdapter extends BaseAdapter {
		private Context mContext;

		private int[] item;

		public ImageAdapter(Context context, int[] item) {
			this.mContext = context;
			this.item = item;
			
			
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return arraylist.size();
		}

		@Override
		public Object getItem(int i) {
			// TODO Auto-generated method stub
			return i;
		}

		@Override
		public long getItemId(int i) {
			// TODO Auto-generated method stub
			return i;
		}

		@Override
		public View getView(int position, View view, ViewGroup viewgroup) {
			View retval = LayoutInflater.from(mContext).inflate(
					R.layout.activity_custom_gallery_listview_allvideo_item_horiitem, null);
			if (retval != null) {
				for (int i = 0; i < item.length; i++) {
					TextView title = (TextView) retval.findViewById(item[i]);
					title.setText(arraylist.get(position));
				}
			}
			return retval;
		}

	}
}
