package com.duguang.baseanimation.ui.imitate.waterfall;

import java.io.File;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.base.BaseActivity;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class WaterfallMainActivity extends BaseActivity {

	public static final String SERVER_IP = "http://10.198.229.62:8080/ImageServer";
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	private ListView lv1;
	private ListView lv2;
	private ListView lv3;

	private ImageView tempIv;
	private PopupWindow pop;
	private MyLinearLayout mll;
	
	@Override
	public void setView() {
		setImageLoader();
		setContentView(R.layout.activity_imitate_waterfall_main);

		ConstantValue.SCREEN_WIDTH = getWindowManager().getDefaultDisplay()
				.getWidth();
		ConstantValue.SCREEN_HEIGHT = getWindowManager().getDefaultDisplay()
				.getHeight();

		lv1 = (ListView) findViewById(R.id.lv1);
		lv2 = (ListView) findViewById(R.id.lv2);
		lv3 = (ListView) findViewById(R.id.lv3);
		mll = (MyLinearLayout) findViewById(R.id.mll);

		imageLoader = ImageLoader.getInstance();
		initOptions();

		lv1.setAdapter(new MyAdapter1());
		lv2.setAdapter(new MyAdapter2());
		lv3.setAdapter(new MyAdapter3());

		lv1.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (ConstantValue.CONTENTVIEW_TOP_MARGIN == 0) {
					View contentView = getWindow().findViewById(
							Window.ID_ANDROID_CONTENT);
					ConstantValue.CONTENTVIEW_TOP_MARGIN = ConstantValue.SCREEN_HEIGHT
							- contentView.getHeight();
				}

				ImageView tempIv = getTempIV();

				String url = (String) parent.getItemAtPosition(position);
				imageLoader.displayImage(url, tempIv);

				showPop(tempIv);
				return true;
			}
		});
		lv2.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (ConstantValue.POPFLAG2 == true) {
					ConstantValue.POPFLAG2 = false;
					return true;
				}
				ImageView tempIv = getTempIV();
				String url = (String) parent.getItemAtPosition(position);
				imageLoader.displayImage(url, tempIv);

				showPop(tempIv);
				return true;
			}
		});
		lv3.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (ConstantValue.POPFLAG3 == true) {
					ConstantValue.POPFLAG3 = false;
					return true;
				}
				ImageView tempIv = getTempIV();
				String url = (String) parent.getItemAtPosition(position);
				imageLoader.displayImage(url, tempIv);

				showPop(tempIv);
				return true;
			}
		});
		
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		
	}

	protected void showPop(ImageView tempIv) {
		PopupWindow pop = getPopWindow();
		pop.setContentView(tempIv);
		pop.setBackgroundDrawable(new ColorDrawable());
		pop.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0,
				ConstantValue.CONTENTVIEW_TOP_MARGIN / 2);
		pop.setAnimationStyle(R.style.mypopwindow_anim_style);
		pop.update();
		AlphaAnimation alpha = new AlphaAnimation(1.0f, 0.3f);
		alpha.setDuration(400);
		alpha.setFillAfter(true);
		mll.startAnimation(alpha);

		pop.setOnDismissListener(new OnDismissListener() {

			public void onDismiss() {
				mll.clearAnimation();
			}
		});

	}

	protected PopupWindow getPopWindow() {
		if (pop == null) {
			pop = new PopupWindow(tempIv, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT, true);
		}
		return pop;
	}

	protected ImageView getTempIV() {
		tempIv = (ImageView) View.inflate(getApplicationContext(),
				R.layout.activity_imitate_waterfall_listview_item, null);
		return tempIv;
	}

	private void initOptions() {
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.icon_imitate_waterfall_default1) // resource or drawable
				.showImageForEmptyUri(R.drawable.icon_imitate_waterfall_default1) // resource or
															// drawable
				.showImageOnFail(R.drawable.icon_imitate_waterfall_default1) // resource or drawable
				.resetViewBeforeLoading(false) // default
				.delayBeforeLoading(0).cacheInMemory(true) // default
				.cacheOnDisc(true) // default
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
				.bitmapConfig(Bitmap.Config.ARGB_8888) // default
				.displayer(new SimpleBitmapDisplayer()) // default
				.handler(new Handler()) // default
				.build();

	}

	class MyAdapter1 extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 3000;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return SERVER_IP+"/" + position % 50
					+ ".jpg";
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(getApplicationContext(),
						R.layout.activity_imitate_waterfall_listview_item, null);
				holder = new ViewHolder();
				holder.iv = (ImageView) convertView.findViewById(R.id.iv);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			String imageUri = SERVER_IP+"/"
					+ position % 50 + ".jpg";
			imageLoader.displayImage(imageUri, holder.iv, options,
					new ImageLoadingListener() {
						// 监听中的几种方法不进行复写, 即可把相应图片进行自动填充;
						public void onLoadingStarted(String imageUri, View view) {
						}

						public void onLoadingFailed(String imageUri, View view,
								FailReason failReason) {
						}

						public void onLoadingComplete(String imageUri,
								View view, Bitmap loadedImage) {
						}

						public void onLoadingCancelled(String imageUri,
								View view) {
						}
					});

			return convertView;
		}
	}

	class MyAdapter2 extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 3000;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return SERVER_IP+"/a" + position % 20
					+ ".jpg";
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(getApplicationContext(),
						R.layout.activity_imitate_waterfall_listview_item, null);
				holder = new ViewHolder();
				holder.iv = (ImageView) convertView.findViewById(R.id.iv);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			String imageUri = SERVER_IP+"/a"
					+ position % 20 + ".jpg";
			imageLoader.displayImage(imageUri, holder.iv, options,
					new ImageLoadingListener() {
						public void onLoadingStarted(String imageUri, View view) {
						}

						public void onLoadingFailed(String imageUri, View view,
								FailReason failReason) {
						}

						public void onLoadingComplete(String imageUri,
								View view, Bitmap loadedImage) {
						}

						public void onLoadingCancelled(String imageUri,
								View view) {
						}
					});

			return convertView;
		}
	}

	class MyAdapter3 extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 3000;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return SERVER_IP+"/b" + position % 20
					+ ".jpg";
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(getApplicationContext(),
						R.layout.activity_imitate_waterfall_listview_item, null);
				holder = new ViewHolder();
				holder.iv = (ImageView) convertView.findViewById(R.id.iv);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			String imageUri = SERVER_IP+"/b"
					+ position % 20 + ".jpg";
			imageLoader.displayImage(imageUri, holder.iv, options,
					new ImageLoadingListener() {
						public void onLoadingStarted(String imageUri, View view) {
						}

						public void onLoadingFailed(String imageUri, View view,
								FailReason failReason) {
						}

						public void onLoadingComplete(String imageUri,
								View view, Bitmap loadedImage) {
						}

						public void onLoadingCancelled(String imageUri,
								View view) {
						}
					});

			return convertView;
		}
	}

	static class ViewHolder {
		ImageView iv;
	}
	
	/**
	 * 配置ImageLoader框架
	 * 一般应用应该放在Application子类当中
	 */
	public void setImageLoader() {
		File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());
		// 可使用默认的配置, 如下:
		// ImageLoaderConfiguration
		// config=ImageLoaderConfiguration.createDefault(this);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this)
				.memoryCacheExtraOptions(480, 800)
				// default = device screen dimensions
				.discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75, null)
				.threadPoolSize(3)
				// default
				.threadPriority(Thread.NORM_PRIORITY - 1)
				// default
				.tasksProcessingOrder(QueueProcessingType.FIFO)
				// default
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13)
				// default
				.discCache(new UnlimitedDiscCache(cacheDir))
				// default
				.discCacheSize(50 * 1024 * 1024).discCacheFileCount(100)
				.discCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
				.imageDownloader(new BaseImageDownloader(this)) // default
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())// default
				.writeDebugLogs().build();

		ImageLoader.getInstance().init(config);
	}


}
