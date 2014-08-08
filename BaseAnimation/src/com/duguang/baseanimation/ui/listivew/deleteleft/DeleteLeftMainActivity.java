package com.duguang.baseanimation.ui.listivew.deleteleft;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Transformation;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.duguang.baseanimation.R;

/**
 * 左侧按钮删除条目ListView主页面
 * @author Administrator
 *
 */
public class DeleteLeftMainActivity extends Activity {

	static final int ANIMATION_DURATION = 200;
	private static List<MyCell> mAnimList = new ArrayList<MyCell>();
	private MyAnimListAdapter mMyAnimListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview_delete_left_main);

		for (int i=0;i<50;i++) {
			MyCell cell = new MyCell();
			cell.name = "Cell No."+Integer.toString(i);
			mAnimList.add(cell);
		}

		mMyAnimListAdapter = new MyAnimListAdapter(this, R.layout.activity_listview_delete_left_chain_cell, mAnimList);
		ListView myListView = (ListView) findViewById(R.id.chainListView);
		myListView.setAdapter(mMyAnimListAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void deleteCell(final View v, final int index) {
		AnimationListener al = new AnimationListener() {
			@Override
			public void onAnimationEnd(Animation arg0) {
				mAnimList.remove(index);

				ViewHolder vh = (ViewHolder)v.getTag();
				vh.needInflate = true;

				mMyAnimListAdapter.notifyDataSetChanged();
			}
			@Override public void onAnimationRepeat(Animation animation) {}
			@Override public void onAnimationStart(Animation animation) {}
		};

		collapse(v, al);
	}

	private void collapse(final View v, AnimationListener al) {
		final int initialHeight = v.getMeasuredHeight();

		Animation anim = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				if (interpolatedTime == 1) {
					v.setVisibility(View.GONE);
				}
				else {
					v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
					v.requestLayout();
				}
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		if (al!=null) {
			anim.setAnimationListener(al);
		}
		anim.setDuration(ANIMATION_DURATION);
		v.startAnimation(anim);
	}


	private class MyCell {
		public String name;
	}

	private class ViewHolder {
		public boolean needInflate;
		public TextView text;
		ImageButton imageButton;
	}

	public class MyAnimListAdapter extends ArrayAdapter<MyCell> {
		private LayoutInflater mInflater;
		private int resId;

		public MyAnimListAdapter(Context context, int textViewResourceId, List<MyCell> objects) {
			super(context, textViewResourceId, objects);
			this.resId = textViewResourceId;
			this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			final View view;
			ViewHolder vh;
			MyCell cell = (MyCell)getItem(position);

			if (convertView==null) {
				view = mInflater.inflate(R.layout.activity_listview_delete_left_chain_cell, parent, false);
				setViewHolder(view);
			}
			else if (((ViewHolder)convertView.getTag()).needInflate) {
				view = mInflater.inflate(R.layout.activity_listview_delete_left_chain_cell, parent, false);
				setViewHolder(view);
			}
			else {
				view = convertView;
			}

			vh = (ViewHolder)view.getTag();
			vh.text.setText(cell.name);
			vh.imageButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					deleteCell(view, position);
				}
			});

			return view;
		}

		private void setViewHolder(View view) {
			ViewHolder vh = new ViewHolder();
			vh.text = (TextView)view.findViewById(R.id.cell_name_textview);
			vh.imageButton = (ImageButton) view.findViewById(R.id.cell_trash_button);
			vh.needInflate = false;
			view.setTag(vh);
		}
	}
}
