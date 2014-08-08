package com.duguang.baseanimation.ui.canvas.olympics;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.duguang.baseanimation.R;

/**
 * 这个是自定义的TextView. 至少需要重载构造方法和onDraw方法 对于自定义的View如果没有自己独特的属性，可以直接在xml文件中使用就可以了
 * 如果含有自己独特的属性，那么就需要在构造函数中获取属性文件attrs.xml中自定义属性的名称 并根据需要设定默认值，放在在xml文件中没有定义。
 * 如果使用自定义属性，那么在应用xml文件中需要加上新的schemas，
 * 比如这里是xmlns:my="http://schemas.android.com/apk/res/demo.view.my"
 * 其中xmlns后的“my”是自定义的属性的前缀，res后的是我们自定义View所在的包
 * 
 * @author Administrator
 * 
 */
public class CustomTextView extends View {

	private float sweepAngle;
	private Paint mPaint; // 画笔,包含了画几何图形、文本等的样式和颜色信息

	public CustomTextView(Context context) {
		super(context);
		mPaint = new Paint();
		mPaint.setColor(getResources().getColor(R.color.blue));
		mPaint.setStyle(Paint.Style.STROKE);// 设置空心
		mPaint.setStrokeWidth(6);

	}

	public CustomTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
		// TypedArray是一个用来存放由context.obtainStyledAttributes获得的属性的数组
		// 在使用完成后，一定要调用recycle方法
		// 属性的名称是styleable中的名称+“_”+属性名称
//		TypedArray array = context.obtainStyledAttributes(attrs,
//				R.styleable.MyView);
//		int textColor = array
//				.getColor(R.styleable.MyView_textColor, 0XFF00FF00); // 提供默认值，放置未指定
//		float textSize = array.getDimension(R.styleable.MyView_textSize, 36);
//		mPaint.setColor(textColor);
//		mPaint.setTextSize(textSize);
		mPaint.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了

//		array.recycle(); // 一定要调用，否则这次的设定会对下次的使用造成影响
	}

	public void setSweepAngle(float sweepAngle) {
		this.sweepAngle = sweepAngle;
	}

	public float getSweepAngle() {
		return sweepAngle;
	}

	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// Canvas中含有很多画图的接口，利用这些接口，我们可以画出我们想要的图形
		// mPaint = new Paint();
		// mPaint.setColor(Color.RED);
		// mPaint.setStyle(Style.FILL); // 设置填充
		// canvas.drawRect(10, 10, 100, 100, mPaint); //绘制矩形
		// mPaint.setStyle(Paint.Style.STROKE);//设置空心
		// canvas.drawCircle(200, 200, getSweepAngle(), mPaint) ;
		// RectF oval = new RectF();
		// oval.set(10, 10, 10, 10);
		// canvas.drawOval(oval , mPaint);
		// RectF oval1=new RectF(150,20,180,40);
		// canvas.drawArc(oval1, 180, 180, false, mPaint);//小弧形
		// oval1.set(190, 20, 220, 25);
		// canvas.drawArc(oval1, 180, 180, false, mPaint);//小弧形
		// oval1.set(160, 30, 210, 60);
		// canvas.drawArc(oval1, 0, 180, false, mPaint);//小弧形

		
		// 定义一个矩形区域(上一)
		RectF rf2 = new RectF(250, 400, 450, 600);
		// 画弧顺时针
		canvas.drawArc(rf2, 0, getSweepAngle(), false, mPaint);

		// 定义一个矩形区域(上二)
		RectF rf1 = new RectF(400, 400, 600, 600);
		// 画弧顺时针
		canvas.drawArc(rf1, 0, getSweepAngle(), false, mPaint);

		// 定义一个矩形区域(上三)
		RectF rf3 = new RectF(550, 400, 750, 600);
		// 画弧顺时针
		canvas.drawArc(rf3, 0, getSweepAngle(), false, mPaint);

		// 定义一个矩形区域(下四)
		RectF rf4 = new RectF(325, 500, 525, 700);
		// 画弧顺时针
		canvas.drawArc(rf4, 0, getSweepAngle(), false, mPaint);

		// 定义一个矩形区域(下五)
		RectF rf5 = new RectF(475, 500, 675, 700);
		// 画弧顺时针
		canvas.drawArc(rf5, 0, getSweepAngle(), false, mPaint);
		// canvas.drawCircle(400, 600, getSweepAngle(), mPaint);
		// //画线
		// canvas.drawLine(150, 10, 250, 110, mPaint);
		//
		// //定义一个矩形
		// RectF rf2 = new RectF(150, 130, 250, 230);
		// //画圆
		// canvas.drawOval(rf2, mPaint);

		// mPaint.setColor(Color.BLUE);
		// canvas.drawText("我是被画出来的", 10, 120, mPaint);
	}
}