package com.duguang.baseanimation.ui.customview.circlemenu;

/*
 * Copyright 2013 Csaba Szugyiczki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.duguang.baseanimation.R;

/**
 * 
 * @author Szugyi
 * Creates a rotatable circle menu which can be parameterized by custom attributes.
 * Handles touches and gestures to make the menu rotatable, and to make the 
 * menu items selectable and clickable.
 * 
 */
public class CircleLayout extends ViewGroup {
	// Event listeners
	private OnItemClickListener mOnItemClickListener = null;
	private OnItemSelectedListener mOnItemSelectedListener = null;
	private OnCenterClickListener mOnCenterClickListener = null;
	
	// Background image
	private Bitmap imageOriginal, imageScaled;
	private Matrix matrix;

	private int mTappedViewsPostition = -1;
	private View mTappedView = null;
	private int selected = 0;

	// Child sizes
	private int mMaxChildWidth = 0;
	private int mMaxChildHeight = 0;
	private int childWidth = 0;
	private int childHeight = 0;

	// Sizes of the ViewGroup
	private int circleWidth, circleHeight;
	private int radius = 0;

	// Touch detection
	private GestureDetector mGestureDetector;
	// needed for detecting the inversed rotations
	private boolean[] quadrantTouched;

	// Settings of the ViewGroup
	private boolean allowRotating = true;
	private float angle = 90;
	private float firstChildPos = 90;
	private boolean rotateToCenter = true;
	private boolean isRotating = true;

	/**
	 * @param context
	 */
	public CircleLayout(Context context) {
		this(context, null);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public CircleLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public CircleLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}

	/**
	 * Initializes the ViewGroup and modifies it's default behavior by the passed attributes
	 * @param attrs	the attributes used to modify default settings
	 */
	protected void init(AttributeSet attrs) {
		mGestureDetector = new GestureDetector(getContext(),
				new MyGestureListener());
		quadrantTouched = new boolean[] { false, false, false, false, false };

		if (attrs != null) {
			TypedArray a = getContext().obtainStyledAttributes(attrs,
					R.styleable.Circle);
			
			// The angle where the first menu item will be drawn
			angle = a.getInt(R.styleable.Circle_firstChildPosition, 90);
			firstChildPos = angle;

			rotateToCenter = a.getBoolean(R.styleable.Circle_rotateToCenter,
					true);			
			isRotating = a.getBoolean(R.styleable.Circle_isRotating, true);
			
			// If the menu is not rotating then it does not have to be centered
			// since it cannot be even moved
			if (!isRotating) {
				rotateToCenter = false;
			}

			if (imageOriginal == null) {
				int picId = a.getResourceId(
						R.styleable.Circle_circleBackground, -1);
				
				// If a background image was set as an attribute, 
				// retrieve the image
				if (picId != -1) {
					imageOriginal = BitmapFactory.decodeResource(
							getResources(), picId);
				}
			}

			a.recycle();

			// initialize the matrix only once
			if (matrix == null) {
				matrix = new Matrix();
			} else {
				// not needed, you can also post the matrix immediately to
				// restore the old state
				matrix.reset();
			}
			// Needed for the ViewGroup to be drawn
			setWillNotDraw(false);
		}
	}

	/**
	 * Returns the currently selected menu
	 * @return the view which is currently the closest to the start position
	 */
	public View getSelectedItem() {
		return (selected >= 0) ? getChildAt(selected) : null;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// the sizes of the ViewGroup
		circleHeight = getHeight();
		circleWidth = getWidth();

		if (imageOriginal != null) {
			// Scaling the size of the background image
			if (imageScaled == null) {
				matrix = new Matrix();
				float sx = (((radius + childWidth / 4) * 2) / (float) imageOriginal
						.getWidth());
				float sy = (((radius + childWidth / 4) * 2) / (float) imageOriginal
						.getHeight());
				matrix.postScale(sx, sy);
				imageScaled = Bitmap.createBitmap(imageOriginal, 0, 0,
						imageOriginal.getWidth(), imageOriginal.getHeight(),
						matrix, false);
			}

			if (imageScaled != null) {
				// Move the background to the center
				int cx = (circleWidth - imageScaled.getWidth()) / 2;
				int cy = (circleHeight - imageScaled.getHeight()) / 2;

				Canvas g = canvas;
				canvas.rotate(0, circleWidth / 2, circleHeight / 2);
				g.drawBitmap(imageScaled, cx, cy, null);

			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		mMaxChildWidth = 0;
		mMaxChildHeight = 0;

		// Measure once to find the maximum child size.
		int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
				MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.AT_MOST);
		int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
				MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.AT_MOST);

		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			if (child.getVisibility() == GONE) {
				continue;
			}

			child.measure(childWidthMeasureSpec, childHeightMeasureSpec);

			mMaxChildWidth = Math.max(mMaxChildWidth, child.getMeasuredWidth());
			mMaxChildHeight = Math.max(mMaxChildHeight,
					child.getMeasuredHeight());
		}

		// Measure again for each child to be exactly the same size.
		childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxChildWidth,
				MeasureSpec.EXACTLY);
		childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxChildHeight,
				MeasureSpec.EXACTLY);

		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			if (child.getVisibility() == GONE) {
				continue;
			}

			child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
		}

		setMeasuredDimension(resolveSize(mMaxChildWidth, widthMeasureSpec),
				resolveSize(mMaxChildHeight, heightMeasureSpec));
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int layoutWidth = r - l;
		int layoutHeight = b - t;

		// Laying out the child views
		final int childCount = getChildCount();
		int left, top;
		radius = (layoutWidth <= layoutHeight) ? layoutWidth / 3
				: layoutHeight / 3;
		
		childWidth = (int) (radius / 1.5);
		childHeight = (int) (radius / 1.5);
		
		float angleDelay = 360 / getChildCount();
		
		for (int i = 0; i < childCount; i++) {
			final CircleImageView child = (CircleImageView) getChildAt(i);
			if (child.getVisibility() == GONE) {
				continue;
			}

			if (angle > 360) {
				angle -= 360;
			} else {
				if (angle < 0) {
					angle += 360;
				}
			}

			child.setAngle(angle);
			child.setPosition(i);

			left = Math
					.round((float) (((layoutWidth / 2) - childWidth / 2) + radius
							* Math.cos(Math.toRadians(angle))));
			top = Math
					.round((float) (((layoutHeight / 2) - childHeight / 2) + radius
							* Math.sin(Math.toRadians(angle))));

			child.layout(left, top, left + childWidth, top + childHeight);
			angle += angleDelay;
		}
	}

	/**
	 * Rotate the buttons.
	 * 
	 * @param degrees The degrees, the menu items should get rotated.
	 */
	private void rotateButtons(float degrees) {
		int left, top, childCount = getChildCount();
		float angleDelay = 360 / childCount;
		angle += degrees;
		
		if (angle > 360) {
			angle -= 360;
		} else {
			if (angle < 0) {
				angle += 360;
			}
		}

		for (int i = 0; i < childCount; i++) {
			if (angle > 360) {
				angle -= 360;
			} else {
				if (angle < 0) {
					angle += 360;
				}
			}

			final CircleImageView child = (CircleImageView) getChildAt(i);
			if (child.getVisibility() == GONE) {
				continue;
			}
			left = Math
					.round((float) (((circleWidth / 2) - childWidth / 2) + radius
							* Math.cos(Math.toRadians(angle))));
			top = Math
					.round((float) (((circleHeight / 2) - childHeight / 2) + radius
							* Math.sin(Math.toRadians(angle))));

			child.setAngle(angle);

			if (Math.abs(angle - firstChildPos) < (angleDelay / 2)
					&& selected != child.getPosition()) {
				selected = child.getPosition();

				if (mOnItemSelectedListener != null && rotateToCenter) {
					mOnItemSelectedListener.onItemSelected(child, selected,
							child.getId(), child.getName());
				}
			}

			child.layout(left, top, left + childWidth, top + childHeight);
			angle += angleDelay;
		}
	}

	/**
	 * @return The angle of the unit circle with the image view's center
	 */
	private double getAngle(double xTouch, double yTouch) {
		double x = xTouch - (circleWidth / 2d);
		double y = circleHeight - yTouch - (circleHeight / 2d);

		switch (getQuadrant(x, y)) {
		case 1:
			return Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;

		case 2:
		case 3:
			return 180 - (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);

		case 4:
			return 360 + Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;

		default:
			// ignore, does not happen
			return 0;
		}
	}

	/**
	 * @return The selected quadrant.
	 */
	private static int getQuadrant(double x, double y) {
		if (x >= 0) {
			return y >= 0 ? 1 : 4;
		} else {
			return y >= 0 ? 2 : 3;
		}
	}

	private double startAngle;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (isEnabled()) {
			if (isRotating) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:

					// reset the touched quadrants
					for (int i = 0; i < quadrantTouched.length; i++) {
						quadrantTouched[i] = false;
					}

					allowRotating = false;

					startAngle = getAngle(event.getX(), event.getY());
					break;
				case MotionEvent.ACTION_MOVE:
					double currentAngle = getAngle(event.getX(), event.getY());
					rotateButtons((float) (startAngle - currentAngle));
					startAngle = currentAngle;
					break;
				case MotionEvent.ACTION_UP:
					allowRotating = true;
					rotateViewToCenter((CircleImageView) getChildAt(selected),
							false);
					break;
				}
			}

			// set the touched quadrant to true
			quadrantTouched[getQuadrant(event.getX() - (circleWidth / 2),
					circleHeight - event.getY() - (circleHeight / 2))] = true;
			mGestureDetector.onTouchEvent(event);
			return true;
		}
		return false;
	}

	private class MyGestureListener extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			if (!isRotating) {
				return false;
			}
			// get the quadrant of the start and the end of the fling
			int q1 = getQuadrant(e1.getX() - (circleWidth / 2), circleHeight
					- e1.getY() - (circleHeight / 2));
			int q2 = getQuadrant(e2.getX() - (circleWidth / 2), circleHeight
					- e2.getY() - (circleHeight / 2));

			// the inversed rotations
			if ((q1 == 2 && q2 == 2 && Math.abs(velocityX) < Math
					.abs(velocityY))
					|| (q1 == 3 && q2 == 3)
					|| (q1 == 1 && q2 == 3)
					|| (q1 == 4 && q2 == 4 && Math.abs(velocityX) > Math
							.abs(velocityY))
					|| ((q1 == 2 && q2 == 3) || (q1 == 3 && q2 == 2))
					|| ((q1 == 3 && q2 == 4) || (q1 == 4 && q2 == 3))
					|| (q1 == 2 && q2 == 4 && quadrantTouched[3])
					|| (q1 == 4 && q2 == 2 && quadrantTouched[3])) {

				CircleLayout.this.post(new FlingRunnable(-1
						* (velocityX + velocityY)));
			} else {
				// the normal rotation
				CircleLayout.this
						.post(new FlingRunnable(velocityX + velocityY));
			}

			return true;

		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			mTappedViewsPostition = pointToPosition(e.getX(), e.getY());
			if (mTappedViewsPostition >= 0) {
				mTappedView = getChildAt(mTappedViewsPostition);
				mTappedView.setPressed(true);
			} else {
				float centerX = circleWidth / 2;
				float centerY = circleHeight / 2;

				if (e.getX() < centerX + (childWidth / 2)
						&& e.getX() > centerX - childWidth / 2
						&& e.getY() < centerY + (childHeight / 2)
						&& e.getY() > centerY - (childHeight / 2)) {
					if (mOnCenterClickListener != null) {
						mOnCenterClickListener.onCenterClick();
						return true;
					}
				}
			}

			if (mTappedView != null) {
				CircleImageView view = (CircleImageView) (mTappedView);
				if (selected != mTappedViewsPostition) {
					rotateViewToCenter(view, false);
					if (!rotateToCenter) {
						if (mOnItemSelectedListener != null) {
							mOnItemSelectedListener.onItemSelected(mTappedView,
									mTappedViewsPostition, mTappedView.getId(), view.getName());
						}

						if (mOnItemClickListener != null) {
							mOnItemClickListener.onItemClick(mTappedView,
									mTappedViewsPostition, mTappedView.getId(), view.getName());
						}
					}
				} else {
					rotateViewToCenter(view, false);

					if (mOnItemClickListener != null) {
						mOnItemClickListener.onItemClick(mTappedView,
								mTappedViewsPostition, mTappedView.getId(), view.getName());
					}
				}
				return true;
			}
			return super.onSingleTapUp(e);
		}
	}

	/**
	 * Rotates the given view to the center of the menu.
	 * @param view			the view to be rotated to the center
	 * @param fromRunnable	if the method is called from the runnable which animates the rotation
	 * 						then it should be true, otherwise false 
	 */
	private void rotateViewToCenter(CircleImageView view, boolean fromRunnable) {
		if (rotateToCenter) {
			float velocityTemp = 1;
			float destAngle = (float) (firstChildPos - view.getAngle());
			float startAngle = 0;
			int reverser = 1;

			if (destAngle < 0) {
				destAngle += 360;
			}

			if (destAngle > 180) {
				reverser = -1;
				destAngle = 360 - destAngle;
			}

			while (startAngle < destAngle) {
				startAngle += velocityTemp / 75;
				velocityTemp *= 1.0666F;
			}

			CircleLayout.this.post(new FlingRunnable(reverser * velocityTemp,
					!fromRunnable));
		}
	}

	/**
	 * A {@link Runnable} for animating the menu rotation.
	 */
	private class FlingRunnable implements Runnable {

		private float velocity;
		float angleDelay;
		boolean isFirstForwarding = true;

		public FlingRunnable(float velocity) {
			this(velocity, true);
		}

		public FlingRunnable(float velocity, boolean isFirst) {
			this.velocity = velocity;
			this.angleDelay = 360 / getChildCount();
			this.isFirstForwarding = isFirst;
		}

		public void run() {
			if (Math.abs(velocity) > 5 && allowRotating) {
				if (rotateToCenter) {
					if (!(Math.abs(velocity) < 200 && (Math.abs(angle
							- firstChildPos)
							% angleDelay < 2))) {
						rotateButtons(velocity / 75);
						velocity /= 1.0666F;

						CircleLayout.this.post(this);
					}
				} else {
					rotateButtons(velocity / 75);
					velocity /= 1.0666F;

					CircleLayout.this.post(this);
				}
			} else {
				if (isFirstForwarding) {
					isFirstForwarding = false;
					CircleLayout.this.rotateViewToCenter(
							(CircleImageView) getChildAt(selected), true);
				}
			}
		}
	}

	private int pointToPosition(float x, float y) {

		for (int i = 0; i < getChildCount(); i++) {

			View item = (View) getChildAt(i);
			if (item.getLeft() < x && item.getRight() > x & item.getTop() < y
					&& item.getBottom() > y) {
				return i;
			}

		}
		return -1;
	}

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.mOnItemClickListener = onItemClickListener;
	}

	public interface OnItemClickListener {
		void onItemClick(View view, int position, long id, String name);
	}

	public void setOnItemSelectedListener(
			OnItemSelectedListener onItemSelectedListener) {
		this.mOnItemSelectedListener = onItemSelectedListener;
	}

	public interface OnItemSelectedListener {
		void onItemSelected(View view, int position, long id, String name);
	}

	public interface OnCenterClickListener {
		void onCenterClick();
	}

	public void setOnCenterClickListener(
			OnCenterClickListener onCenterClickListener) {
		this.mOnCenterClickListener = onCenterClickListener;
	}
}