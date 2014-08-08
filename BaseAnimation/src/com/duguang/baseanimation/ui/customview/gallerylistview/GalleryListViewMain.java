package com.duguang.baseanimation.ui.customview.gallerylistview;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.base.BaseActivity;

/**
 * 自定义Gallery+ListView效果 主页面
 * @author Administrator
 *
 */
public class GalleryListViewMain extends BaseActivity {
	private ListView listview;
	private List<GalleryItem> galleryitem = new ArrayList<GalleryItem>();
	private ArrayList<String> titlenamearraylist = new ArrayList<String>();
    /** Called when the activity is first created. */
	
	@Override
	public void setView() {
		 setContentView(R.layout.activity_custom_gallery_listview_main);
		
	}
	@Override
	public void initView() {
		  listview = (ListView) findViewById(R.id.listview);
	        for (int i = 0; i < 7; i++) {
	        	titlenamearraylist.add("List"+i);
			}
	        
	        initItems();
	        setlistview();
		
	}
	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		
	}
	
    private void initItems() {		
    	GalleryItem item = null;
		for (int i = 0; i < titlenamearraylist.size(); i++) {
			item = new GalleryItem(this);
			item.arraylist=titlenamearraylist;
			item.initAdapter(this, new int[] { R.id.tv2 });
			galleryitem.add(item);
		}
	}
    private void setlistview() {
		SpecialAdapter	mAdapter = new SpecialAdapter(this, galleryitem, titlenamearraylist,
				new int[] { R.id.tv1 });
		listview.setAdapter(mAdapter);
	}
    
    
    /**
     * 设配器
     * @author Administrator
     *
     */
    public class SpecialAdapter extends BaseAdapter {
		private ArrayList<String> titlenamearraylist;
		Context context;
		private int[] item;
		View retval;
		int k = 0;
		private List<GalleryItem> galleryitems;

		public SpecialAdapter(Context context, List<GalleryItem> galleryitems,
				ArrayList<String> dataObjects, int[] item) {
			super();
			this.context = context;
			this.titlenamearraylist = dataObjects;
			this.item = item;
			this.galleryitems = galleryitems;
		}

		@Override
		public int getCount() {
			return titlenamearraylist.size();
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
			retval = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.activity_custom_gallery_listview_allvideo_item, null);
			for (int i = 0; i < item.length; i++) {
				TextView title = (TextView) retval.findViewById(item[i]);
				title.setText(titlenamearraylist.get(position));
			}
			GalleryItem items = this.galleryitems.get(position);
			GalleryItem gallery = (GalleryItem) retval.findViewById(R.id.item_gallery);
			// ,datas.get(position),new int[] {R.id.tv1, R.id.tv2, R.id.tv3}

			gallery.setAdapter(items.adapter);
			gallery.setSelection(1);
			return retval;
		}

	}
	
}