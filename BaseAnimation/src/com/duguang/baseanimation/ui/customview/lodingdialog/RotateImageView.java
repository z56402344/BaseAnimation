package com.duguang.baseanimation.ui.customview.lodingdialog;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RotateImageView extends ImageView {
	
	public RotateImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public RotateImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public RotateImageView(Context context) {
		super(context);
		init();
	}

	private void init() {
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.rotate(70);
		
		
		
	/*	Matrix matrix = new Matrix();
		matrix.setRotate(10, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
		canvas.setMatrix(matrix);*/
		
		//super.onDraw(canvas);
	}
	
	
	public void start() {
		
	}
	public void stop() {
		
	}
}
