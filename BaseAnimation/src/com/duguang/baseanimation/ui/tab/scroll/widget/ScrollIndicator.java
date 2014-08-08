package com.duguang.baseanimation.ui.tab.scroll.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.duguang.baseanimation.R;

public class ScrollIndicator extends FrameLayout{

	private ImageView leftArrow;
	private ImageView rightArrow;
	
	public ScrollIndicator(Context context) {
		super(context);
		init(context);
	}

	public ScrollIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public ScrollIndicator(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	private void init(Context context){
		leftArrow = new ImageView(context);
		rightArrow = new ImageView(context);
		View view = View.inflate(context, R.layout.activity_tab_scrollview_indicator, this);
		leftArrow = (ImageView)view.findViewById(R.id.imgview_arrow_left);
		rightArrow = (ImageView)view.findViewById(R.id.imgview_arrow_right);
	}
	// �������Ҽ�ͷ������¼�
	
	public void setLeftArrowClickListener(OnClickListener listener){
		 leftArrow.setOnClickListener(listener);
	}
	
	public void setRightArrowClickListener(OnClickListener listener){
		rightArrow.setOnClickListener(listener);
	}
	
	public void setLeftArrowImageSource(int  resId){
		leftArrow.setImageResource(resId);
	}
	
	public void setRightArrowImageSource(int resId){
		rightArrow.setImageResource(resId);
	}
	
	public void showLeftArrow(){
		leftArrow.setVisibility(View.VISIBLE);
	}
	
	public void showRightArrow(){
		rightArrow.setVisibility(View.VISIBLE);
	}
	
	public void hideLeftArrow(){
		leftArrow.setVisibility(View.INVISIBLE);
	}
	
	public void hideRightArrow(){
		rightArrow.setVisibility(View.INVISIBLE);
	}
}
