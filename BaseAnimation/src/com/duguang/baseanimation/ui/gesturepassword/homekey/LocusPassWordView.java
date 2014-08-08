package com.duguang.baseanimation.ui.gesturepassword.homekey;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.common.Constants;
import com.duguang.baseanimation.utils.SharedPreferenceUtil;

/**
 * 图形解锁自定义View
 * 
 * @author liuweina
 * 
 */
public class LocusPassWordView extends View {
	private float w = 0;
	private float h = 0;

	//
	private boolean isCache = false;
	//
	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

	//
	private Point[][] mPoints = new Point[3][3];
	// 圆的半径
	private float r = 0;
	// 选中的点
	private List<Point> sPoints = new ArrayList<Point>();
	private boolean checking = false;
	private Bitmap locus_round_original;
	private Bitmap locus_round_click;
	private Bitmap locus_round_click_error;
	private Bitmap locus_line;
	private Bitmap locus_line_semicircle;
	private Bitmap locus_line_semicircle_error;
	private Bitmap locus_arrow;
	private Bitmap locus_line_error;
	private long CLEAR_TIME = 800;
	private Context contexts;
	/**
	 * 手势密码的最短长度
	 */
	private int passwordMinLength = 4;
	// 是否可操作
	private boolean isTouch = true;
	private Matrix mMatrix = new Matrix();
	private int lineAlpha = 100;
	/**
	 * 判断是否是第一次输入原始密码，或者home键回来时输入手势密码，比对原始密码是否正确 modified by
	 * liuweina，2013/10/14
	 */
	private boolean isFirst = false;
	/**
	 * 修改手势密码时，需要输入三次手势，判断是否是第二次输入手势，即第一次输入新密码 modified by liuweina，2013/10/14
	 */
	private boolean isSecond = false;
	/**
	 * 判断是否是第三次输入手势，即第二次输入新密码 modified by liuweina，2013/10/14
	 */
	private boolean isThird = false;
	/**
	 * 原始密码，第一次和第二次输入的值
	 */
	private String defaultStr, firstStr, secondStr;
	private int currWidth;
	private int currHight;

	public LocusPassWordView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public LocusPassWordView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public LocusPassWordView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		this.contexts = context;
		WindowManager manager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay(); // 为获取屏幕宽、高
		currWidth = display.getWidth();
		currHight = display.getHeight();
	}

	public boolean isSecond() {
		return isSecond;
	}

	public void setSecond(boolean isSecond) {
		this.isSecond = isSecond;
	}

	public boolean isFirst() {
		return isFirst;
	}

	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}

	public boolean isThird() {
		return isThird;
	}

	public void setThird(boolean isThird) {
		this.isThird = isThird;
	}

	@Override
	public void onDraw(Canvas canvas) {
		if (!isCache) {
			initCache();
		}
		drawToCanvas(canvas);
	}

	private void drawToCanvas(Canvas canvas) {

		// mPaint.setColor(Color.RED);
		// Point p1 = mPoints[1][1];
		// Rect r1 = new Rect(p1.x - r,p1.y - r,p1.x +
		// locus_round_click.getWidth() - r,p1.y+locus_round_click.getHeight()-
		// r);
		// canvas.drawRect(r1, mPaint);
		// 画所有点
		for (int i = 0; i < mPoints.length; i++) {
			for (int j = 0; j < mPoints[i].length; j++) {
				Point p = mPoints[i][j];
				if (p.state == Point.STATE_CHECK) {
					canvas.drawBitmap(locus_round_click, p.x - r, p.y - r, mPaint);
				} else if (p.state == Point.STATE_CHECK_ERROR) {
					canvas.drawBitmap(locus_round_click_error, p.x - r, p.y - r, mPaint);
				} else {
					canvas.drawBitmap(locus_round_original, p.x - r, p.y - r, mPaint);
				}
			}
		}
		// mPaint.setColor(Color.BLUE);
		// canvas.drawLine(r1.left+r1.width()/2, r1.top, r1.left+r1.width()/2,
		// r1.bottom, mPaint);
		// canvas.drawLine(r1.left, r1.top+r1.height()/2, r1.right,
		// r1.bottom-r1.height()/2, mPaint);

		// 画连线
		if (sPoints.size() > 0) {
			int tmpAlpha = mPaint.getAlpha();
			mPaint.setAlpha(lineAlpha);
			Point tp = sPoints.get(0);
			for (int i = 1; i < sPoints.size(); i++) {
				Point p = sPoints.get(i);
				drawLine(canvas, tp, p);
				tp = p;
			}
			if (this.movingNoPoint) {
				drawLine(canvas, tp, new Point((int) moveingX, (int) moveingY));
			}
			mPaint.setAlpha(tmpAlpha);
			lineAlpha = mPaint.getAlpha();
		}

	}

	/**
	 * 初始化Cache信息
	 * 
	 * @param canvas
	 */
	private void initCache() {
		/*
		 * w = this.getWidth(); h = this.getHeight();
		 */
		w = this.getWidth() - 20;
		h = this.getHeight() - 20;
		float x = 0;
		float y = 0;

		// 以最小的为准
		// 纵屏
		if (w > h) {
			x = (w - h) / 2;
			w = h;
		}
		// 横屏
		else {
			y = (h - w) / 2;
			h = w;
		}

		locus_round_original = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.locus_round_original);
		locus_round_click = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.locus_round_click);
		locus_round_click_error = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.locus_round_click_error);

		locus_line = BitmapFactory.decodeResource(this.getResources(), R.drawable.locus_line);
		locus_line_semicircle = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.locus_line_semicircle);

		locus_line_error = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.locus_line_error);
		locus_line_semicircle_error = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.locus_line_semicircle_error);

		locus_arrow = BitmapFactory.decodeResource(this.getResources(), R.drawable.locus_arrow);
		// Log.d("Canvas w h :", "w:" + w + " h:" + h);

		// 计算圆圈图片的大小
		float canvasMinW = w;
		if (w > h) {
			canvasMinW = h;
		}
		float roundMinW = canvasMinW / 8.0f * 2;
		float roundW = roundMinW / 2.f;
		//
		float deviation = canvasMinW % (8 * 2) / 2;
		x += deviation;
		x += deviation;

		if (locus_round_original.getWidth() > roundMinW) {
			// 取得缩放比例，将所有的图片进行缩放
			float sf = roundMinW * 1.0f / locus_round_original.getWidth();
			locus_round_original = BitmapMatrixUtil.zoom(locus_round_original, sf);
			locus_round_click = BitmapMatrixUtil.zoom(locus_round_click, sf);
			locus_round_click_error = BitmapMatrixUtil.zoom(locus_round_click_error, sf);

			locus_line = BitmapMatrixUtil.zoom(locus_line, sf);
			locus_line_semicircle = BitmapMatrixUtil.zoom(locus_line_semicircle, sf);

			locus_line_error = BitmapMatrixUtil.zoom(locus_line_error, sf);
			locus_line_semicircle_error = BitmapMatrixUtil.zoom(locus_line_semicircle_error, sf);
			locus_arrow = BitmapMatrixUtil.zoom(locus_arrow, sf);
			roundW = locus_round_original.getWidth() / 2;
		}

		if (currWidth > 320)
			roundW = roundW + 30;

		mPoints[0][0] = new Point(x + 0 + roundW, y + 0 + roundW);
		mPoints[0][1] = new Point(x + w / 2, y + 0 + roundW);
		mPoints[0][2] = new Point(x + w - roundW, y + 0 + roundW);
		mPoints[1][0] = new Point(x + 0 + roundW, y + h / 2);
		mPoints[1][1] = new Point(x + w / 2, y + h / 2);
		mPoints[1][2] = new Point(x + w - roundW, y + h / 2);
		mPoints[2][0] = new Point(x + 0 + roundW, y + h - roundW);
		mPoints[2][1] = new Point(x + w / 2, y + h - roundW);
		mPoints[2][2] = new Point(x + w - roundW, y + h - roundW);
		int k = 0;
		for (Point[] ps : mPoints) {
			for (Point p : ps) {
				p.index = k;
				k++;
			}
		}
		r = locus_round_original.getHeight() / 2;// roundW;
		isCache = true;
	}

	/**
	 * 画两点的连接
	 * 
	 * @param canvas
	 * @param a
	 * @param b
	 */
	private void drawLine(Canvas canvas, Point a, Point b) {
		float ah = (float) MathUtil.distance(a.x, a.y, b.x, b.y);
		float degrees = getDegrees(a, b);
		// Log.d("=============x===========", "rotate:" + degrees);
		canvas.rotate(degrees, a.x, a.y);

		if (a.state == Point.STATE_CHECK_ERROR) {
			mMatrix.setScale(
					(ah - locus_line_semicircle_error.getWidth()) / locus_line_error.getWidth(), 1);
			mMatrix.postTranslate(a.x, a.y - locus_line_error.getHeight() / 2.0f);
			canvas.drawBitmap(locus_line_error, mMatrix, mPaint);
			canvas.drawBitmap(locus_line_semicircle_error, a.x + locus_line_error.getWidth(), a.y
					- locus_line_error.getHeight() / 2.0f, mPaint);
		} else {
			mMatrix.setScale((ah - locus_line_semicircle.getWidth()) / locus_line.getWidth(), 1);
			mMatrix.postTranslate(a.x, a.y - locus_line.getHeight() / 2.0f);
			canvas.drawBitmap(locus_line, mMatrix, mPaint);
			canvas.drawBitmap(locus_line_semicircle, a.x + ah - locus_line_semicircle.getWidth(),
					a.y - locus_line.getHeight() / 2.0f, mPaint);
		}

		canvas.drawBitmap(locus_arrow, a.x, a.y - locus_arrow.getHeight() / 2.0f, mPaint);

		canvas.rotate(-degrees, a.x, a.y);

	}

	public float getDegrees(Point a, Point b) {
		float ax = a.x;// a.index % 3;
		float ay = a.y;// a.index / 3;
		float bx = b.x;// b.index % 3;
		float by = b.y;// b.index / 3;
		float degrees = 0;
		// y轴相等 90度或270
		if (bx == ax) {
			// 在y轴的下边 90
			if (by > ay) {
				degrees = 90;
			}
			// 在y轴的上边 270
			else if (by < ay) {
				degrees = 270;
			}
		}
		// y轴相等 0度或180
		else if (by == ay) {
			// 在y轴的下边 90
			if (bx > ax) {
				degrees = 0;
			}
			// 在y轴的上边 270
			else if (bx < ax) {
				degrees = 180;
			}
		} else {
			// 在y轴的右边 270~90
			if (bx > ax) {
				// 在y轴的下边 0 - 90
				if (by > ay) {
					degrees = 0;
					degrees = degrees + switchDegrees(Math.abs(by - ay), Math.abs(bx - ax));
				}
				// 在y轴的上边 270~0
				else if (by < ay) {
					degrees = 360;
					degrees = degrees - switchDegrees(Math.abs(by - ay), Math.abs(bx - ax));
				}

			}
			// 在y轴的左边 90~270
			else if (bx < ax) {
				// 在y轴的下边 180 ~ 270
				if (by > ay) {
					degrees = 90;
					degrees = degrees + switchDegrees(Math.abs(bx - ax), Math.abs(by - ay));
				}
				// 在y轴的上边 90 ~ 180
				else if (by < ay) {
					degrees = 270;
					degrees = degrees - switchDegrees(Math.abs(bx - ax), Math.abs(by - ay));
				}

			}

		}
		return degrees;
	}

	/**
	 * 1=30度 2=45度 4=60度
	 * 
	 * @param tan
	 * @return
	 */
	private float switchDegrees(float x, float y) {
		return (float) MathUtil.pointTotoDegrees(x, y);
	}

	/**
	 * 取得数组下标
	 * 
	 * @param index
	 * @return
	 */
	public int[] getArrayIndex(int index) {
		int[] ai = new int[2];
		ai[0] = index / 3;
		ai[1] = index % 3;
		return ai;
	}

	/**
	 * 
	 * 检查
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private Point checkSelectPoint(float x, float y) {
		for (int i = 0; i < mPoints.length; i++) {
			for (int j = 0; j < mPoints[i].length; j++) {
				Point p = mPoints[i][j];
				if (MathUtil.checkInRound(p.x, p.y, r, (int) x, (int) y)) {
					return p;
				}
			}
		}
		return null;
	}

	/**
	 * 重置
	 */
	private void reset() {
		for (Point p : sPoints) {
			p.state = Point.STATE_NORMAL;
		}
		sPoints.clear();
		this.enableTouch();
	}

	/**
	 * 判断点是否有交叉 返回 0,新点 ,1 与上一点重叠 2,与非最后一点重叠
	 * 
	 * @param p
	 * @return
	 */
	private int crossPoint(Point p) {
		// 重叠的不最后一个则 reset
		if (sPoints.contains(p)) {
			if (sPoints.size() > 2) {
				// 与非最后一点重叠
				if (sPoints.get(sPoints.size() - 1).index != p.index) {
					return 2;
				}
			}
			// 与最后一点重叠
			return 1;
		} else {
			// 新点
			return 0;
		}
	}

	/**
	 * 添加一个点
	 * 
	 * @param point
	 */
	private void addPoint(Point point) {
		this.sPoints.add(point);
	}

	/**
	 * 转换为String
	 * 
	 * @param points
	 * @return
	 */
	private String toPointString() {
		if (sPoints.size() >= passwordMinLength) {
			StringBuffer sf = new StringBuffer();
			for (Point p : sPoints) {
				sf.append(",");
				sf.append(p.index);
			}
			return sf.deleteCharAt(0).toString();
		} else {
			return "";
		}
	}

	boolean movingNoPoint = false;
	float moveingX, moveingY;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 不可操作
		if (!isTouch) {
			return false;
		}

		movingNoPoint = false;

		float ex = event.getX();
		float ey = event.getY();
		boolean isFinish = false;
		boolean redraw = false;
		Point p = null;
		switch (event.getAction()) {
		// 点下
		case MotionEvent.ACTION_DOWN:
			// 如果正在清除密码,则取消
			if (task != null) {
				task.cancel();
				task = null;
				Log.d("task", "touch cancel()");
			}
			// 删除之前的点
			reset();
			p = checkSelectPoint(ex, ey);
			if (p != null) {
				checking = true;
			}
			break;
		// 移动
		case MotionEvent.ACTION_MOVE:
			if (checking) {
				p = checkSelectPoint(ex, ey);
				if (p == null) {
					movingNoPoint = true;
					moveingX = ex;
					moveingY = ey;
				}
			}
			break;
		// 提起
		case MotionEvent.ACTION_UP:
			p = checkSelectPoint(ex, ey);
			checking = false;
			isFinish = true;
			break;
		}
		if (!isFinish && checking && p != null) {
			int rk = crossPoint(p);
			// 与非最后一重叠
			if (rk == 2) {
				// reset();
				// checking = false;

				movingNoPoint = true;
				moveingX = ex;
				moveingY = ey;

				redraw = true;
			} else if (rk == 0) {// 一个新点
				p.state = Point.STATE_CHECK;
				addPoint(p);
				redraw = true;
			}
			// rk == 1 不处理
		}

		// 是否重画
		if (redraw) {

		}
		if (isFinish) {
			if (this.sPoints.size() <= 1) {
				this.reset();
			} else if (this.sPoints.size() < passwordMinLength && this.sPoints.size() > 0) {
				// mCompleteListener.onPasswordTooMin(sPoints.size());
				error();
				clearPassword();
				// 采用配置文件读取的方式
				Toast.makeText(
						this.getContext(),
						this.getContext().getResources()
								.getString(R.string.passwordistooshortinputagain),
						Toast.LENGTH_SHORT).show();
			} else {
				// modified by liuweina，2013/10/14
				// 每次都是获取到九宫格的值，在监听的实现方法中作进一步操作
				if (isFirst()) {
					// 手势密码不为空，在1>修改手势密码第一步2>点击home键或者初次进入系统，确认手势密码两种情况下，执行此分支
					defaultStr = toPointString();
					clearPassword();
					mCompareListener.onCompare(defaultStr);
				} else if (isSecond()) {
					// 1>手势密码不为空,修改手势密码，已成功比对原始密码，第一次输入新密码；2>手势密码为空，设置手势密码时，第一次输入手势密码
					firstStr = toPointString();
					clearPassword();
					mCheckListener.onCheck(firstStr);
				} else if (isThird()) {
					// 1>手势密码不为空,修改手势密码，已成功比对原始密码，第二次输入新密码；2>手势密码为空，设置手势密码时，第二次输入手势密码
					secondStr = toPointString();
					clearPassword();
					mCompleteListener.onComplete(firstStr, secondStr);
				}
			}
		}
		this.postInvalidate();
		return true;
	}

	/**
	 * 设置已经选中的为错误
	 */
	private void error() {
		for (Point p : sPoints) {
			p.state = Point.STATE_CHECK_ERROR;
		}
	}

	/**
	 * 设置为输入错误
	 */
	public void markError() {
		markError(CLEAR_TIME);
	}

	/**
	 * 设置为输入错误
	 */
	public void markError(final long time) {
		for (Point p : sPoints) {
			p.state = Point.STATE_CHECK_ERROR;
		}
		this.clearPassword(time);
	}

	/**
	 * 设置为可操作
	 */
	public void enableTouch() {
		isTouch = true;
	}

	/**
	 * 设置为不可操作
	 */
	public void disableTouch() {
		isTouch = false;
	}

	private Timer timer = new Timer();
	private TimerTask task = null;

	/**
	 * 清除密码
	 */
	public void clearPassword() {
		clearPassword(CLEAR_TIME);
	}

	/**
	 * 清除密码
	 */
	public void clearPassword(final long time) {
		if (time > 1) {
			if (task != null) {
				task.cancel();
				Log.d("task", "clearPassword cancel()");
			}
			lineAlpha = 130;
			postInvalidate();
			task = new TimerTask() {
				public void run() {
					reset();
					postInvalidate();
				}
			};
			Log.d("task", " clearPassword schedule(" + time + ")");
			timer.schedule(task, time);
		} else {
			reset();
			postInvalidate();
		}

	}

	/**
	 * 比较手势密码是否正确
	 * @param password 原始密码
	 * @return
	 */
	public boolean verifyPassword(String password)
	{
		boolean verify = false;
		if (!TextUtils.isEmpty(password)) {
			// 超级密码
			//更改手势密码的获得位置，将其移动到util中，方便多处调用，modified by liuweina，2013/10/14
			if (password.equals(SharedPreferenceUtil.getStringValueFromSP(contexts,
					Constants.GPNAMEINSHARED, "password")))
			{
				verify = true;
			}
		}
		return verify;
	}
	
	/**
	 * 更新密码，输入两次密码时，比对两次密码是否一致
	 */
	private OnCompleteListener mCompleteListener;
	/**
	 * 登录时，输入密码，比对密码是否正确
	 */
	private OnCompareListener mCompareListener;
	/**
	 * 输入原始密码时，比对原始密码是否正确
	 */
	private onCheckListener mCheckListener;

	/**
	 * @param mCompleteListener
	 */
	public void setOnCompleteListener(OnCompleteListener mCompleteListener) {
		this.mCompleteListener = mCompleteListener;
	}

	public void setOnCompareListener(OnCompareListener mCompareListener) {
		this.mCompareListener = mCompareListener;
	}

	public void setOnCheckListener(onCheckListener mCheckListener) {
		this.mCheckListener = mCheckListener;
	}

	public int getPasswordMinLength() {
		return passwordMinLength;
	}

	public void setPasswordMinLength(int passwordMinLength) {
		this.passwordMinLength = passwordMinLength;
	}

	/**
	 * 修改手势密码，两次新密码都输入后，比对两次新密码是否一致
	 * 
	 * @author zhangzhp changed by liuweina，2013/10/14
	 * 
	 */
	public interface OnCompleteListener {
		public void onComplete(String firstPassword, String secondPassword);
	}

	/**
	 * 修改手势密码，输入原始密码后，判断原始密码是否输入正确
	 * 
	 * @author liuweina，2013/10/14
	 * 
	 */
	public interface OnCompareListener {
		public void onCompare(String password);
	}

	/**
	 * 修改手势密码，有原始密码且原始密码输入正确时，或者没有原始密码，第一次输入新密码
	 * 
	 * @author liuweina，2013/10/14
	 * 
	 */
	public interface onCheckListener {
		public void onCheck(String password);
	}
}
