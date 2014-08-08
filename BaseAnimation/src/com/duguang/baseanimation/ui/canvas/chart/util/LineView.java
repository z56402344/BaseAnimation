package com.duguang.baseanimation.ui.canvas.chart.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.duguang.baseanimation.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Dacer on 11/4/13.
 */
public class LineView extends View {
	private int mViewHeight;
	// drawBackground
	private String[] colors=new String[]{"#f27302","#9dcd19","#0096e0","#843dfa","#eeb445","#dd1ae9","#07a616","#036ad5","#fa2942","#04bdae"};
//	private boolean autoSetDataOfGird = false;
	private boolean autoSetGridWidth = true;
//	private int dataOfAGird = 10;
	private int bottomTextHeight = 0;
	private ArrayList<String> bottomTextList;
//	private ArrayList<Integer> dataList;
	private ArrayList<ArrayList<Integer>> dataLists=new ArrayList<ArrayList<Integer>>();
	private ArrayList<Integer> xCoordinateList = new ArrayList<Integer>();

	private ArrayList<ArrayList<Integer>> yCoordinateLists = new ArrayList<ArrayList<Integer>>();
	
//	private ArrayList<Dot> drawDotList = new ArrayList<Dot>();
	private ArrayList<ArrayList<Dot>> drawDotLists=new ArrayList<ArrayList<Dot>>();
	private Paint bottomTextPaint = new Paint();
	private int bottomTextDescent;
	private Context c = getContext();
	// popup
	private Paint popupTextPaint = new Paint();
	private final int bottomTriangleHeight = 12;
	private boolean showPopup = false;
	private Dot selectedDot;

	private int topLineLength = MyUtils.dip2px(getContext(), 2); // | | �?this
	// -+-+-
	private int sideLineLength = MyUtils.dip2px(getContext(), 20) / 3 * 2;// --+--+--+--+--+--+--
	// �?this �?
	private int backgroundGridWidth =0;

	// Constants
	private final int popupTopPadding = MyUtils.dip2px(getContext(), 2);
	private final int popupBottomMargin = MyUtils.dip2px(getContext(), 5);
	private final int bottomTextTopMargin = MyUtils.sp2px(getContext(), 5);
	private final int bottomLineLength = MyUtils.sp2px(getContext(), 22);
	private final int DOT_INNER_CIR_RADIUS = MyUtils.dip2px(getContext(), 0);
	private final int DOT_OUTER_CIR_RADIUS = MyUtils.dip2px(getContext(), 2);
	private final int MIN_TOP_LINE_LENGTH = MyUtils.dip2px(getContext(), 12);
	private final int MIN_VERTICAL_GRID_NUM = 4;
	private final int MIN_HORIZONTAL_GRID_NUM = 1;
	private int BACKGROUND_LINE_COLOR = Color.parseColor("#EEEEEE");
	private int BOTTOM_TEXT_COLOR = Color.parseColor("#9B9A9B");
	private int biggestData=0;
	private int middleHight;
	private int marginTop=MyUtils.dip2px(getContext(), 22);

	private Runnable animator = new Runnable() {
		@Override
		public void run() {
			boolean needNewFrame = false;
			for (ArrayList<Dot> drawDotList : drawDotLists) {
				for (Dot dot : drawDotList) {
					dot.update();
					if (!dot.isAtRest()) {
						needNewFrame = true;
					}
				}
			}
			if (needNewFrame) {
				postDelayed(this, 20);
			}
			invalidate();
		}
	};

	public LineView(Context context) {
		this(context, null);
	}

	public LineView(Context context, AttributeSet attrs) {
		super(context, attrs);
		popupTextPaint.setAntiAlias(true);
		popupTextPaint.setColor(Color.WHITE);
		popupTextPaint.setTextSize(MyUtils.sp2px(c, 13));
		popupTextPaint.setStrokeWidth(5);
		popupTextPaint.setTextAlign(Paint.Align.CENTER);

		bottomTextPaint.setAntiAlias(true);
		bottomTextPaint.setTextSize(MyUtils.sp2px(c, 12));
		bottomTextPaint.setTextAlign(Paint.Align.CENTER);
		bottomTextPaint.setStyle(Paint.Style.FILL);
		bottomTextPaint.setColor(BOTTOM_TEXT_COLOR);
		
	}

	/**
	 * dataList will be reset when called is method.
	 * 
	 * @param bottomTextList
	 *            The String ArrayList in the bottom.
	 */
	public void setBottomTextList(ArrayList<String> bottomTextList) {
//		this.dataList = null;
		this.bottomTextList = bottomTextList;

		Rect r = new Rect();
		int longestWidth = 0;
		String longestStr = "";
		bottomTextDescent = 0;
		for (String s : bottomTextList) {
			bottomTextPaint.getTextBounds(s, 0, s.length(), r);
			if (bottomTextHeight < r.height()) {
				bottomTextHeight = r.height();
			}
			if (autoSetGridWidth && (longestWidth < r.width())) {
				longestWidth = r.width();
				longestStr = s;
			}
			if (bottomTextDescent < (Math.abs(r.bottom))) {
				bottomTextDescent = Math.abs(r.bottom);
			}
		}

		if (autoSetGridWidth) {
				int size = bottomTextList.size();
				if(size<6){
					WindowManager  wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
					int width=wm.getDefaultDisplay().getWidth();
					backgroundGridWidth=(int) ((float)width/size);
				}else{
					backgroundGridWidth = longestWidth+ (int) bottomTextPaint.measureText(longestStr, 0, 1);
				}
				Log.i("backgroundGridWidth=", backgroundGridWidth+"");
			if (sideLineLength < longestWidth / 2) {
				sideLineLength = longestWidth / 2;
			}
		}

//		refreshXCoordinateList(getHorizontalGridNum());
	}

	public void addDataList(ArrayList<ArrayList<Integer>> all){
		for (ArrayList<Integer> dataList : all) {
			dataLists.add(dataList);
			if (dataLists.get(0).size() != bottomTextList.size()) {
				throw new RuntimeException("纵横数量不一致");
			}
//			if (autoSetDataOfGird) {
//				for (Integer i : dataList) {
//					if (biggestData < i) {
//						biggestData = i;
//					}
//				}
//				while (biggestData / 10 > dataOfAGird) {
//					dataOfAGird *= 10;
//				}
//			}
		}
//		refreshAfterDataChanged();
		showPopup = false;
		setMinimumWidth(0); // It can help the LineView reset the Width,I don't know the better way..
		postInvalidate();
	}
	/**
	 * 
	 * @param dataList
	 *            The Integer ArrayList for showing, dataList.size() must <
	 *            bottomTextList.size()
	 */
//	public void setDataList(ArrayList<Integer> dataList) {
//		this.dataList = dataList;
//		if (dataList.size() > bottomTextList.size()) {
//			throw new RuntimeException("dacer.LineView error:"
//					+ " dataList.size() > bottomTextList.size() !!!");
//		}
//		if (autoSetDataOfGird) {
//			int biggestData = 0;
//			for (Integer i : dataList) {
//				if (biggestData < i) {
//					biggestData = i;
//				}
//			}
//			dataOfAGird = 1;
//			while (biggestData / 10 > dataOfAGird) {
//				dataOfAGird *= 10;
//			}
//		}
//		refreshAfterDataChanged();
//		showPopup = false;
//		setMinimumWidth(0); // It can help the LineView reset the Width,
//							// I don't know the better way..
//		postInvalidate();
//	}

	private void refreshAfterDataChanged() {
//		int verticalGridNum = getVerticalGridlNum();
//		refreshTopLineLength(verticalGridNum);
//		mViewHeight=measureHeight(MeasureSpec.EXACTLY);
		refreshXCoordinateList(getHorizontalGridNum());
		refreshYCoordinateList();
		refreshDrawDotList();
	}

//	private int getVerticalGridlNum() {
//		int verticalGridNum = MIN_VERTICAL_GRID_NUM;
//		if (dataLists != null && !dataLists.isEmpty()) {
//			for (ArrayList<Integer> list : dataLists) {
//				for (Integer integer : list) {
//					if (verticalGridNum < (integer + 1)) {
//						verticalGridNum = integer + 1;
//					}
//				}
//			}
//		}
//		return verticalGridNum;
//	}

	private int getHorizontalGridNum() {
		int horizontalGridNum = bottomTextList.size() - 1;
		if (horizontalGridNum < MIN_HORIZONTAL_GRID_NUM) {
			horizontalGridNum = MIN_HORIZONTAL_GRID_NUM;
		}
		return horizontalGridNum;
	}

	private void refreshXCoordinateList(int horizontalGridNum) {
		xCoordinateList.clear();
		for (int i = 0; i < (horizontalGridNum + 1); i++) {
			xCoordinateList.add(sideLineLength + backgroundGridWidth * i);
		}

	}

	private void refreshYCoordinateList() {
		middleHight = mViewHeight - topLineLength - bottomTextHeight- bottomTextTopMargin - bottomLineLength - bottomTextDescent;
		yCoordinateLists.clear();
		ArrayList<Integer> yCoordinateList;
		for (ArrayList<Integer> dataList : dataLists) {
			yCoordinateList = new ArrayList<Integer>();
			for (Integer integer : dataList) {
				yCoordinateList.add((100-integer)*middleHight/100+topLineLength+marginTop);
			}
			yCoordinateLists.add(yCoordinateList);
		}
//		for (int i = 0; i < verticalGridNum ; i++) {
//			yCoordinateList.add(topLineLength+ middleHight*i/100);
//		}
//		System.out.println(yCoordinateList);
	}

	private void refreshDrawDotList() {
		for (int k=0;k<dataLists.size();k++) {
			ArrayList<Integer> dataList=dataLists.get(k) ;
			if (drawDotLists.size()<dataLists.size()) {
				ArrayList<Dot> drawDotList = new ArrayList<Dot>();
				for (int i = 0; i < dataList.size(); i++) {
//					int drawDotSize=drawDotList.isEmpty() ? 0 : drawDotList.size();
					int x = xCoordinateList.get(i);
					int y = yCoordinateLists.get(k).get(i);
					drawDotList.add(new Dot(x, 0, x, y, dataList.get(i)));
//					if (i > drawDotSize - 1) {
//						drawDotList.add(new Dot(x, 0, x, y, dataList.get(i)));
//					} else {
//						drawDotList.set(i,drawDotList.get(i).setTargetData(x, y,dataList.get(i)));
//					}
				}
				drawDotLists.add(drawDotList);
			}
			removeCallbacks(animator);
			post(animator);
		}
//		Log.d("A", "drawDotLists"+drawDotLists.size());
//		Log.d("A", "dataLists"+dataLists.size());
	}

//	private void refreshTopLineLength(int verticalGridNum) {
//		if ((mViewHeight - topLineLength - bottomTextHeight - bottomTextTopMargin)
//				/ (verticalGridNum + 2) < getPopupHeight()) {
//			topLineLength = getPopupHeight() + DOT_OUTER_CIR_RADIUS
//					+ DOT_INNER_CIR_RADIUS + 2;
//		} else {
//			topLineLength = MIN_TOP_LINE_LENGTH;
//		}
//	}

	@Override
	protected void onDraw(Canvas canvas) {
		drawBackgroundLines(canvas);
		drawLines(canvas);
		drawDots(canvas);
		if (showPopup && selectedDot != null) {
			drawPopup(canvas, String.valueOf(selectedDot.data),selectedDot.getPoint());
		}
	}

	/**
	 * 
	 * @param canvas
	 *            The canvas you need to draw on.
	 * @param point
	 *            The Point consists of the x y coordinates from left bottom to
	 *            right top. Like is 锟� * 3 2 1 0 1 2 3 4 5
	 */
	private void drawPopup(Canvas canvas, String num, Point point) {
		boolean singularNum = (num.length() == 1);
		int sidePadding = MyUtils.dip2px(c, singularNum ? 8 : 5);
		int x = point.x;
		int y = point.y - MyUtils.dip2px(c, 5);
		Rect popupTextRect = new Rect();
		popupTextPaint.getTextBounds(num, 0, num.length(), popupTextRect);
		Rect r = new Rect(x - popupTextRect.width() / 2 - sidePadding, y
				- popupTextRect.height() - bottomTriangleHeight
				- popupTopPadding * 2 - popupBottomMargin, x
				+ popupTextRect.width() / 2 + sidePadding, y + popupTopPadding
				- popupBottomMargin);

		NinePatchDrawable popup = (NinePatchDrawable) getResources()
				.getDrawable(R.drawable.icon_canvas_charts_popup_red);
		popup.setBounds(r);
		popup.draw(canvas);
		canvas.drawText(num, x, y - bottomTriangleHeight - popupBottomMargin,
				popupTextPaint);
	}

	private int getPopupHeight() {
		Rect popupTextRect = new Rect();
		popupTextPaint.getTextBounds("9", 0, 1, popupTextRect);
		Rect r = new Rect(-popupTextRect.width() / 2, -popupTextRect.height()
				- bottomTriangleHeight - popupTopPadding * 2
				- popupBottomMargin, +popupTextRect.width() / 2,
				+popupTopPadding - popupBottomMargin);
		return r.height();
	}

	private void drawDots(Canvas canvas) {
		Paint bigCirPaint = new Paint();
		bigCirPaint.setAntiAlias(true);
		if (drawDotLists != null && !drawDotLists.isEmpty()) {
			for (int i=0;i<drawDotLists.size();i++) {
				ArrayList<Dot> drawDotList=drawDotLists.get(i);
				bigCirPaint.setColor(Color.parseColor(colors[i]));
				for (Dot dot : drawDotList) {
					canvas.drawCircle(dot.x, dot.y, DOT_OUTER_CIR_RADIUS,bigCirPaint);
				}
			}
		}
	}

	private void drawLines(Canvas canvas) {
		Paint linePaint = new Paint();
		linePaint.setAntiAlias(true);
		linePaint.setStrokeWidth(MyUtils.dip2px(c, 1));
		for (int j=0;j<drawDotLists.size();j++) {
			ArrayList<Dot> drawDotList=drawDotLists.get(j);
			linePaint.setColor(Color.parseColor(colors[j]));
			for (int i = 0; i < drawDotList.size() - 1; i++) {
				canvas.drawLine(drawDotList.get(i).x, drawDotList.get(i).y,
						drawDotList.get(i + 1).x, drawDotList.get(i + 1).y,
						linePaint);
			}
		}
	}

	private void drawBackgroundLines(Canvas canvas) {
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(MyUtils.dip2px(c, 1f));
		paint.setColor(BACKGROUND_LINE_COLOR);
//		PathEffect effects = new DashPathEffect(new float[] { 10, 5, 10, 5 }, 1);
		int hight2bottom=mViewHeight - bottomTextTopMargin - bottomTextHeight- bottomTextDescent;
		// draw vertical lines
		for (int i = 0; i < xCoordinateList.size(); i++) {
			canvas.drawLine(xCoordinateList.get(i), 0, xCoordinateList.get(i),hight2bottom, paint);
		}
		for (float i = 0; i < 6; i++) {
			float y=topLineLength+marginTop+i/5*middleHight;
			canvas.drawLine(0, y, getWidth(),y, paint);
		}
//		for (int i = 0; i < yCoordinateList.size(); i++) {
//			if ((yCoordinateList.size() - 1 - i) % dataOfAGird == 0) {
//				canvas.drawLine(0, yCoordinateList.get(i), getWidth(),yCoordinateList.get(i), paint);
//			}
//		}

		// draw bottom text
		if (bottomTextList != null) {
			for (int i = 0; i < bottomTextList.size(); i++) {
//				if(i%7==0){
//					bottomTextPaint.setTextSize(MyUtils.sp2px(c, 12));
//				}else{
//					bottomTextPaint.setTextSize(MyUtils.sp2px(c, 0));
//				}
				canvas.drawText(bottomTextList.get(i), sideLineLength+ backgroundGridWidth * i, mViewHeight- bottomTextDescent, bottomTextPaint);
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int mViewWidth = measureWidth(widthMeasureSpec);
		mViewHeight = measureHeight(heightMeasureSpec);
		setMeasuredDimension(mViewWidth, mViewHeight);
		refreshAfterDataChanged();
	}

	private int measureWidth(int measureSpec) {
		int horizontalGridNum = getHorizontalGridNum();
		int preferred = backgroundGridWidth * horizontalGridNum
				+ sideLineLength * 2;
		return getMeasurement(measureSpec, preferred);
	}

	private int measureHeight(int measureSpec) {
		int preferred = 0;
		return getMeasurement(measureSpec, preferred);
	}

	private int getMeasurement(int measureSpec, int preferred) {
		int specSize = MeasureSpec.getSize(measureSpec);
		int measurement;
		switch (MeasureSpec.getMode(measureSpec)) {
		case MeasureSpec.EXACTLY:
			measurement = specSize;
			break;
		case MeasureSpec.AT_MOST:
			measurement = Math.min(preferred, specSize);
			break;
		default:
			measurement = preferred;
			break;
		}
		return measurement;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Point point = new Point();
		point.x = (int) event.getX();
		point.y = (int) event.getY();
		Region r = new Region();
		int width = backgroundGridWidth / 2;
		if (drawDotLists != null || !drawDotLists.isEmpty()) {
			for (ArrayList<Dot> drawDotList : drawDotLists) {
				for (Dot dot : drawDotList) {
					r.set(dot.x - width, dot.y - width, dot.x + width, dot.y
							+ width);
					if (r.contains(point.x, point.y)
							&& event.getAction() == MotionEvent.ACTION_DOWN) {
						selectedDot = dot;
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						if (r.contains(point.x, point.y)) {
							showPopup = true;
						}
					}
				}
			}
		}
		if (event.getAction() == MotionEvent.ACTION_DOWN
				|| event.getAction() == MotionEvent.ACTION_UP) {
			postInvalidate();
		}
		return true;
	}

	private int updateSelf(int origin, int target, int velocity) {
		if (origin < target) {
			origin += velocity;
		} else if (origin > target) {
			origin -= velocity;
		}
		if (Math.abs(target - origin) < velocity) {
			origin = target;
		}
		return origin;
	}

	class Dot {
		int x;
		int y;
		int data;
		int targetX;
		int targetY;
		int velocity = MyUtils.dip2px(c, 20);

		Dot(int x, int y, int targetX, int targetY, Integer data) {
			this.x = x;
			this.y = y;
			setTargetData(targetX, targetY, data);
		}

		Point getPoint() {
			return new Point(x, y);
		}

		Dot setTargetData(int targetX, int targetY, Integer data) {
			this.targetX = targetX;
			this.targetY = targetY;
			this.data = data;
			return this;
		}

		boolean isAtRest() {
			return (x == targetX) && (y == targetY);
		}

		void update() {
			x = updateSelf(x, targetX, velocity);
			y = updateSelf(y, targetY, velocity);
		}
	}
}
