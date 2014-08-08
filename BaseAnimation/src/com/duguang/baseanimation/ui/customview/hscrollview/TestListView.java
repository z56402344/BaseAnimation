package com.duguang.baseanimation.ui.customview.hscrollview;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class TestListView extends ListView{
		public TestListView(Context context) {
			super(context);
		}
		public TestListView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}
		public TestListView(Context context, AttributeSet attrs, int
				defStyle) {
			super(context, attrs, defStyle);
		}
		@Override
		protected void onMeasure(int widthMeasureSpec, int
				heightMeasureSpec) {
			int expandSpec = MeasureSpec.makeMeasureSpec(
					Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
			super.onMeasure(widthMeasureSpec, expandSpec);
		}
	}