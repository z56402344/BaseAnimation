package com.duguang.baseanimation.ui.customview.titlelistview;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.customview.titlelistview.PinnedHeaderListView.PinnedHeaderAdapter;

public class TestAdapter extends BaseAdapter implements PinnedHeaderAdapter,
		OnScrollListener {

	private LayoutInflater inflater;

	private ArrayList<Person> datas;
	private int lastItem = 0;

	public TestAdapter(final LayoutInflater inflater) {
		this.inflater = inflater;
		loadData();
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.activity_custom_title_listview_section_item, null);
		}
		final Person person = datas.get(position);
		final TextView header = (TextView) view.findViewById(R.id.header);
		final TextView textView = (TextView) view
				.findViewById(R.id.example_text_view);
		textView.setText(person.getNumber());
		header.setText(person.getName());

		if (lastItem == position) {
			header.setVisibility(View.INVISIBLE);
		} else {
			header.setVisibility(View.VISIBLE);
		}
		return view;
	}

	@Override
	public int getPinnedHeaderState(int position) {
		return PINNED_HEADER_PUSHED_UP;
	}

	@Override
	public void configurePinnedHeader(View header, int position) {
		if (lastItem != position) {
			notifyDataSetChanged();
		}
		((TextView) header.findViewById(R.id.header_text)).setText(datas.get(
				position).getName());
		lastItem = position;
	}

	private void loadData() {
		datas = new ArrayList<Person>();
		for (int i = 0; i < 50; i++) {
			Person p = new Person();
			p.setName("name-" + i);
			p.setNumber("100" + i);
			datas.add(p);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (view instanceof PinnedHeaderListView) {
			((PinnedHeaderListView) view).configureHeaderView(firstVisibleItem);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

}
