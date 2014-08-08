package com.duguang.baseanimation.ui.imitate.gallery;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.ImageView;

public class CustomGallery extends Gallery {
	
	private int galleryCenterPoint;		// gallery�����ĵ�
	private final int maxRotateAngle = 70;	// �����ת�Ƕ�
	private Camera camera;

	public CustomGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		setStaticTransformationsEnabled(true);
		camera = new Camera();
	}

	/**
	 * ��ĳһ��ͼƬ��Ҫ�任ʱ�ص�
	 */
	@Override
	protected boolean getChildStaticTransformation(View child, Transformation t) {
		int viewCenterPoint = getViewCenterPoint(child);
		int rotateAngle = 0;
		
		if(galleryCenterPoint != viewCenterPoint) {		// ��?ǰ�����ߵ�ͼƬ
			
			float scale = (float) (galleryCenterPoint - viewCenterPoint) / (float) child.getWidth();
//			Log.i("CustomGallery", "����: " + scale);
			rotateAngle = (int) (scale * maxRotateAngle);
			
			if(Math.abs(rotateAngle) > maxRotateAngle) {		// -51 -60  55  66
				rotateAngle = rotateAngle > 0 ? maxRotateAngle : -maxRotateAngle;
			}
		}
		
		// ���֮ǰ�任������
		t.clear();
		// ���ñ任������Ϊ��������
		t.setTransformationType(Transformation.TYPE_MATRIX);
		
		transformationItem((ImageView) child, rotateAngle, t);
		return true;
	}
	
	/**
	 * ����ÿһ��item�ı任
	 * @param iv
	 * @param rotateAngle
	 * @param t
	 */
	private void transformationItem(ImageView iv, int rotateAngle, Transformation t) {
		camera.save();		// ���������֮ǰ��״̬
		camera.translate(0, 0, 100f);
		int absRotateAngle = Math.abs(rotateAngle);		// ȡ����ת�Ƕȵľ��ֵ
		
		/**
		 * �Ŵ�ͼƬ
		 * �м��ͼƬ��zoomֵΪ: -250
		 * 45 * 1.5 = 70 - 250 = -180
		 * 50 * 1.5 = 75 - 250 = -175
		 */
		int zoom = (int) (absRotateAngle * 1.5 - 250);
		camera.translate(0, 0, zoom);
		
		/** 
		 * ����ͼƬ��͸����
		 * setAlpha ��ȡֵ��Χ: 1 ~ 255
		 * 1 	��ȫ͸��
		 * 255	��ȫ��ʾ
		 * 
		 * �м��ͼƬ: 255 - 0 * 2.5 = 255
		 * ���ߵ�ͼƬ: 255 - 50 * 2.5 = 130
		 */
		int alpha = (int) (255 - absRotateAngle * 2.5);
		iv.setAlpha(alpha);
		
		// ������ת
		camera.rotateY(rotateAngle);
		
		Matrix matrix = t.getMatrix();
		camera.getMatrix(matrix);		// ������һЩ�еĲ�����װ��������
		
		// ִ�����б任֮ǰ��ԭͼ�ƶ���: ���, һ���ȵľ���, �ϱ�, һ��߶ȵľ���
		matrix.preTranslate(-iv.getWidth() / 2, -iv.getHeight() / 2);

		// ִ�����б任֮���ԭͼ�ƶ�����ʼλ��: �ұ�, һ���ȵľ���, �±߶�, һ��߶ȵľ���
		matrix.postTranslate(iv.getWidth() / 2, iv.getHeight() / 2);
		
		camera.restore();	// �ָ�������֮ǰ��״̬
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		galleryCenterPoint = getGalleryCenterPoint();
	}
	
	/**
	 * ���Gallery�����ĵ�
	 * @return
	 */
	private int getGalleryCenterPoint() {
		return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
	}
	
	/**
	 * ���ͼƬ�����ĵ�
	 * @param v
	 * @return
	 */
	private int getViewCenterPoint(View v) {
		return v.getWidth() / 2 + v.getLeft();
	}
}
