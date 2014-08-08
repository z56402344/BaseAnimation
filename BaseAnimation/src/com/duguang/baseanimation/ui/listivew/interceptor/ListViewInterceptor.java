package com.duguang.baseanimation.ui.listivew.interceptor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.duguang.baseanimation.R;

public class ListViewInterceptor extends ListView {

	private DropListener mDropListener;

	private ImageView mDragView;
	private int mDragPos; // which item is being dragged
	private int mFirstDragPos; // where was the dragged item originally
	private int mDragPoint; // at what offset inside the item did the user grab
							// it
	private int mCoordOffset; // the difference between screen coordinates and
								// coordinates in this view

	private Rect mTempRect = new Rect();
	private final int mTouchSlop;
	private int mHeight;
	private int mUpperBound;
	private int mLowerBound;
	private WindowManager mWindowManager;
	private WindowManager.LayoutParams mWindowParams;
	private int dragndropBackgroundColor = 0x00000000;
	private Bitmap mDragBitmap;
	private int mItemHeightHalf = 32;
	private int mItemHeightNormal = 64;
	private int mItemHeightExpanded = 128;

	public ListViewInterceptor(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public ListViewInterceptor(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		Log.v(">>>>>>>>>>onTouchEvent", ">>>>>>>>>>onTouchEvent");
		if ((mDropListener != null) && mDragView != null) {
			int action = ev.getAction();
			switch (action) {
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				Rect r = mTempRect;
				mDragView.getDrawingRect(r);
				stopDragging();
				if (mDropListener != null && mDragPos >= 0
						&& mDragPos < getCount()) {
					mDropListener.drop(mFirstDragPos, mDragPos);
				}
				unExpandViews(false);
				break;

			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_MOVE:
				int x = (int) ev.getX();
				int y = (int) ev.getY();
				dragView(x, y);

				int itemnum = getItemForPosition(y);
				if (itemnum >= 0) {
					if (action == MotionEvent.ACTION_DOWN
							|| itemnum != mDragPos) {

						mDragPos = itemnum;
						doExpansion();
						Log.v(">>>doExpansion", ">>>>>>>>>>doExpansion");
					}
//					int speed = 0;
//					adjustScrollBounds(y);
//					if (y > mLowerBound) {
//						// scroll the list up a bit
//						speed = y > (mHeight + mLowerBound) / 2 ? 16 : 4;
//					} else if (y < mUpperBound) {
//						// scroll the list down a bit
//						speed = y < mUpperBound / 2 ? -16 : -4;
//					}
//					if (speed != 0) {
//						int ref = pointToPosition(0, mHeight / 2);
//						if (ref == AdapterView.INVALID_POSITION) {
//							// we hit a divider or an invisible view, check
//							// somewhere else
//							ref = pointToPosition(0, mHeight / 2
//									+ getDividerHeight() + 64);
//						}
//						View v = getChildAt(ref - getFirstVisiblePosition());
//						if (v != null) {
//							int pos = v.getTop();
//							setSelectionFromTop(ref, pos - speed);
//
//						}
//					}
				}
				break;
			}
			return true;
		}
		return super.onTouchEvent(ev);

	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (mDropListener != null) {
			switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				int x = (int) ev.getX();
				int y = (int) ev.getY();
				int itemnum = pointToPosition(x, y);
				Log.v("itemnum>>>", ">>>>>>>>" + itemnum);
				if (itemnum == AdapterView.INVALID_POSITION) {
					break;
				}
				ViewGroup item = (ViewGroup) getChildAt(itemnum
						- getFirstVisiblePosition());
				Log.v("itemnum>>>", ">>>>>>>>" + getFirstVisiblePosition()
						+ "---" + ev.getRawY() + "----" + ev.getY()+"-----"+item.getTop());
				mDragPoint = y - item.getTop();
				mCoordOffset = ((int) ev.getRawY()) - y;
				View dragger = item.findViewById(R.id.img);
				Rect r = mTempRect;
				// dragger.getDrawingRect(r);

				r.left = dragger.getLeft();
				r.right = dragger.getRight();
				r.top = dragger.getTop();
				r.bottom = dragger.getBottom();

				if ((r.left < x) && (x < r.right)) {
					item.setDrawingCacheEnabled(true);
					// Create a copy of the drawing cache so that it does not
					// get recycled
					// by the framework when the list tries to clean up memory
					Bitmap bitmap = Bitmap.createBitmap(item.getDrawingCache());
					startDragging(bitmap, y);
					mDragPos = itemnum;
					mFirstDragPos = mDragPos;
					mHeight = getHeight();
					int touchSlop = mTouchSlop;
					mUpperBound = Math.min(y - touchSlop, mHeight / 3);
					mLowerBound = Math.max(y + touchSlop, mHeight * 2 / 3);
					return false;
				}
				mDragView = null;
				break;
			}
		}
		return super.onInterceptTouchEvent(ev);
	}

	private void startDragging(Bitmap bm, int y) {
		stopDragging();

		mWindowParams = new WindowManager.LayoutParams();
		mWindowParams.gravity = Gravity.TOP;
		mWindowParams.x = 0;
		mWindowParams.y = y - mDragPoint + mCoordOffset;

		mWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		mWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		mWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
				| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
		mWindowParams.format = PixelFormat.TRANSLUCENT;
		mWindowParams.windowAnimations = 0;

		ImageView v = new ImageView(getContext());
		// int backGroundColor =
		// getContext().getResources().getColor(R.color.dragndrop_background);
		v.setBackgroundColor(dragndropBackgroundColor);
		v.setImageBitmap(bm);
		mDragBitmap = bm;

		mWindowManager = (WindowManager) getContext()
				.getSystemService("window");
		mWindowManager.addView(v, mWindowParams);
		mDragView = v;
	}

	private void stopDragging() {
		if (mDragView != null) {
			WindowManager wm = (WindowManager) getContext().getSystemService(
					"window");
			wm.removeView(mDragView);
			mDragView.setImageDrawable(null);
			mDragView = null;
		}
		if (mDragBitmap != null) {
			mDragBitmap.recycle();
			mDragBitmap = null;
		}
	}

	private void dragView(int x, int y) {
		float alpha = 1.0f;
		mWindowParams.alpha = alpha;
		// }
		mWindowParams.y = y - mDragPoint + mCoordOffset;
		mWindowManager.updateViewLayout(mDragView, mWindowParams);
	}

	private int getItemForPosition(int y) {
		int adjustedy = y - mDragPoint - mItemHeightHalf;
		int pos = myPointToPosition(0, adjustedy);
		if (pos >= 0) {
			if (pos <= mFirstDragPos) {
				pos += 1;
			}
		} else if (adjustedy < 0) {
			pos = 0;
		}
		return pos;
	}

	private void adjustScrollBounds(int y) {
		if (y >= mHeight / 3) {
			mUpperBound = mHeight / 3;
		}
		if (y <= mHeight * 2 / 3) {
			mLowerBound = mHeight * 2 / 3;
		}
	}

	/*
	 * Restore size and visibility for all listitems
	 */
	private void unExpandViews(boolean deletion) {
		for (int i = 0;; i++) {
			View v = getChildAt(i);
			if (v == null) {
				if (deletion) {
					// HACK force update of mItemCount
					int position = getFirstVisiblePosition();
					int y = getChildAt(0).getTop();
					setAdapter(getAdapter());
					setSelectionFromTop(position, y);
					// end hack
				}
				layoutChildren(); // force children to be recreated where needed
				v = getChildAt(i);
				if (v == null) {
					break;
				}
			}
			ViewGroup.LayoutParams params = v.getLayoutParams();
			params.height = mItemHeightNormal;
			v.setLayoutParams(params);
			v.setVisibility(View.VISIBLE);
		}
	}

	/*
	 * Adjust visibility and size to make it appear as though an item is being
	 * dragged around and other items are making room for it: If dropping the
	 * item would result in it still being in the same place, then make the
	 * dragged listitem's size normal, but make the item invisible. Otherwise,
	 * if the dragged listitem is still on screen, make it as small as possible
	 * and expand the item below the insert point. If the dragged item is not on
	 * screen, only expand the item below the current insertpoint.
	 */
	private void doExpansion() {
		int childnum = mDragPos - getFirstVisiblePosition();
		if (mDragPos > mFirstDragPos) {
			childnum++;
		}

		View first = getChildAt(mFirstDragPos - getFirstVisiblePosition());

		for (int i = 0;; i++) {
			View vv = getChildAt(i);
			if (vv == null) {
				break;
			}
			int height = mItemHeightNormal;
			int visibility = View.VISIBLE;
			if (vv.equals(first)) {
				// processing the item that is being dragged
				if (mDragPos == mFirstDragPos) {
					// hovering over the original location
					visibility = View.INVISIBLE;
				} else {
					// not hovering over it
					height = 1;
				}
			} else if (i == childnum) {
				if (mDragPos < getCount() - 1) {
					height = mItemHeightExpanded;
				}
			}
			ViewGroup.LayoutParams params = vv.getLayoutParams();
			params.height = height;
			vv.setLayoutParams(params);
			vv.setVisibility(visibility);
		}
	}

	/*
	 * pointToPosition() doesn't consider invisible views, but we need to, so
	 * implement a slightly different version.
	 */
	private int myPointToPosition(int x, int y) {
		Rect frame = mTempRect;
		final int count = getChildCount();
		for (int i = count - 1; i >= 0; i--) {
			final View child = getChildAt(i);
			child.getHitRect(frame);
			if (frame.contains(x, y)) {
				return getFirstVisiblePosition() + i;
			}
		}
		return INVALID_POSITION;
	}

	public interface DropListener {
		void drop(int from, int to);
	}

	public void setDropListener(DropListener onDrop) {
		// TODO Auto-generated method stub
		mDropListener = onDrop;
	}

}
