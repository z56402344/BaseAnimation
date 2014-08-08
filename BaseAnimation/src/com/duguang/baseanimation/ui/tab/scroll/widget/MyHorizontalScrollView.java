package com.duguang.baseanimation.ui.tab.scroll.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

public class MyHorizontalScrollView extends HorizontalScrollView{
	private OnComputeScrollListener listener;
	
	public MyHorizontalScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public MyHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void computeScroll() {
		// TODO Auto-generated method stub
		super.computeScroll();
		if(listener != null){
			listener.computeScroll(this);
		}
	}
	
	public void setOnComputeScrollListener(
			OnComputeScrollListener onComputeScrollListener){
		this.listener = onComputeScrollListener;
	}
	
	interface OnComputeScrollListener {
		public void computeScroll(View view);
	}
	
}
