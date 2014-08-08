package com.duguang.baseanimation.ui.canvas.chart.util;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class MyHori extends HorizontalScrollView {

	private MyHoriListener ml;
	public interface MyHoriListener {
		void onScrollChanged(int dx);
	}
	public MyHori(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyHori(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MyHori(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		int dx=l-oldl;
		if(ml!=null){
			ml.onScrollChanged(dx);
		}
		super.onScrollChanged(l, t, oldl, oldt);
	}
	public void setMyListener(MyHoriListener l){  
        ml = l;    
    } 
}
