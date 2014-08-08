package com.duguang.baseanimation.ui.property;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

public class MyPointEvaluator implements TypeEvaluator<PointF> {
	public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
		PointF startPoint = (PointF) startValue;
		PointF endPoint = (PointF) endValue;
		return new PointF(
				startPoint.x + fraction * (endPoint.x - startPoint.x),
				startPoint.y + fraction * (endPoint.y - startPoint.y));
	}
}
