package com.duguang.baseanimation.ui.notification;

import java.io.IOException;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.duguang.baseanimation.R;

public class CustomActivity extends BaseNotificationActivity implements
		OnClickListener {
	/** TAG */
	private final static String TAG = "CustomActivity";
	/** 按钮：显示自定义通知 */
	private Button btn_show_custom;
	/** 按钮：显示自定义带按钮的通知 */
	private Button btn_show_custom_button;
	/** Notification 的ID */
	int notifyId = 101;
	/** NotificationCompat 构造器 */
	NotificationCompat.Builder mBuilder;
	/** 是否在播放 */
	public boolean isPlay = false;
	/** 通知栏按钮广播 */
	public ButtonBroadcastReceiver bReceiver;
	/** 通知栏按钮点击事件对应的ACTION */
	public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification_custom);
		initViews();
		initButtonReceiver();
	}

	private void initViews() {
		btn_show_custom = (Button) findViewById(R.id.btn_show_custom);
		btn_show_custom.setOnClickListener(this);
		btn_show_custom_button = (Button) findViewById(R.id.btn_show_custom_button);
		btn_show_custom_button.setOnClickListener(this);
	}

	public void shwoNotify() {
		// 先设定RemoteViews
		RemoteViews view_custom = new RemoteViews(getPackageName(),
				R.layout.activity_notification_view_custom);
		// 设置对应IMAGEVIEW的ID的资源图片
		view_custom.setImageViewResource(R.id.custom_icon,
				R.drawable.notification_icon);
		// view_custom.setInt(R.id.custom_icon,"setBackgroundResource",R.drawable.icon);
		view_custom.setTextViewText(R.id.tv_custom_title, "今日头条");
		view_custom.setTextViewText(R.id.tv_custom_content,
				"金州勇士官方宣布球队已经解雇了主帅马克-杰克逊，随后宣布了最后的结果。");
		// view_custom.setTextViewText(R.id.tv_custom_time,
		// String.valueOf(System.currentTimeMillis()));
		// 设置显示
		// view_custom.setViewVisibility(R.id.tv_custom_time, View.VISIBLE);
		// view_custom.setLong(R.id.tv_custom_time,"setTime",
		// System.currentTimeMillis());//不知道为啥会报错，过会看看日志
		// 设置number
		// NumberFormat num = NumberFormat.getIntegerInstance();
		// view_custom.setTextViewText(R.id.tv_custom_num,
		// num.format(this.number));
		mBuilder = new Builder(this);
		mBuilder.setContent(view_custom)
				.setContentIntent(
						getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
				.setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
				.setTicker("有新资讯").setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
				.setOngoing(false)// 不是正在进行的 true为正在进行 效果和.flag一样
				.setSmallIcon(R.drawable.notification_icon);
		// mNotificationManager.notify(notifyId, mBuilder.build());
		Notification notify = mBuilder.build();
		notify.contentView = view_custom;
		// Notification notify = new Notification();
		// notify.icon = R.drawable.icon;
		// notify.contentView = view_custom;
		// notify.contentIntent =
		// getDefalutIntent(Notification.FLAG_AUTO_CANCEL);
		mNotificationManager.notify(notifyId, notify);
	}

	/**
	 * 带按钮的通知栏
	 */
	public void showButtonNotify() {
		NotificationCompat.Builder mBuilder = new Builder(this);
		RemoteViews mRemoteViews = new RemoteViews(getPackageName(),
				R.layout.activity_notification_view_custom_button);
		mRemoteViews.setImageViewResource(R.id.custom_song_icon,
				R.drawable.notification_sing_icon);
		// API3.0 以上的时候显示按钮，否则消失
		mRemoteViews.setTextViewText(R.id.tv_custom_song_singer, "周杰伦");
		mRemoteViews.setTextViewText(R.id.tv_custom_song_name, "七里香");
		// 如果版本号低于（3。0），那么不显示按钮
		if (BaseTools.getSystemVersion() <= 9) {
			mRemoteViews.setViewVisibility(R.id.ll_custom_button, View.GONE);
		} else {
			mRemoteViews.setViewVisibility(R.id.ll_custom_button, View.VISIBLE);
			//
			if (isPlay) {
				mRemoteViews.setImageViewResource(R.id.btn_custom_play,
						R.drawable.notification_btn_pause);
			} else {
				mRemoteViews.setImageViewResource(R.id.btn_custom_play,
						R.drawable.notification_btn_play);
			}
		}

		// 点击的事件处理
		Intent buttonIntent = new Intent(ACTION_BUTTON);
		/* 上一首按钮 */
		buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_PREV_ID);
		// 这里加了广播，所及INTENT的必须用getBroadcast方法
		PendingIntent intent_prev = PendingIntent.getBroadcast(this, 1,
				buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_prev, intent_prev);
		/* 播放/暂停 按钮 */
		buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_PALY_ID);
		PendingIntent intent_paly = PendingIntent.getBroadcast(this, 2,
				buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_play, intent_paly);
		/* 下一首 按钮 */
		buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_NEXT_ID);
		PendingIntent intent_next = PendingIntent.getBroadcast(this, 3,
				buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_next, intent_next);

		mBuilder.setContent(mRemoteViews)
				.setContentIntent(
						getDefalutIntent(Notification.FLAG_ONGOING_EVENT))
				.setWhen(System.currentTimeMillis())
				// 通知产生的时间，会在通知信息里显示
				.setTicker("正在播放").setPriority(Notification.PRIORITY_DEFAULT)
				// 设置该通知优先级
				.setOngoing(true)
				.setSmallIcon(R.drawable.notification_sing_icon);
		Notification notify = mBuilder.build();
		notify.flags = Notification.FLAG_ONGOING_EVENT;
		// 会报错，还在找解决思路
		// notify.contentView = mRemoteViews;
		// notify.contentIntent = PendingIntent.getActivity(this, 0, new
		// Intent(), 0);
		mNotificationManager.notify(200, notify);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_show_custom:
			shwoNotify();
			break;
		case R.id.btn_show_custom_button:
			showButtonNotify();
			break;
		default:
			break;
		}
	}

	/** 带按钮的通知栏点击广播接收 */
	public void initButtonReceiver() {
		bReceiver = new ButtonBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ACTION_BUTTON);
		registerReceiver(bReceiver, intentFilter);
	}

	public final static String INTENT_BUTTONID_TAG = "ButtonId";
	/** 上一首 按钮点击 ID */
	public final static int BUTTON_PREV_ID = 1;
	/** 播放/暂停 按钮点击 ID */
	public final static int BUTTON_PALY_ID = 2;
	/** 下一首 按钮点击 ID */
	public final static int BUTTON_NEXT_ID = 3;

	/**
	 * 广播监听按钮点击时间
	 */
	public class ButtonBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if (action.equals(ACTION_BUTTON)) {
				// 通过传递过来的ID判断按钮点击属性或者通过getResultCode()获得相应点击事件
				int buttonId = intent.getIntExtra(INTENT_BUTTONID_TAG, 0);
				switch (buttonId) {
				case BUTTON_PREV_ID:
					Log.d(TAG, "上一首");
					Toast.makeText(getApplicationContext(), "上一首",
							Toast.LENGTH_SHORT).show();
					break;
				case BUTTON_PALY_ID:
					String play_status = "";
					isPlay = !isPlay;
					MediaPlayer play = null;
					if (isPlay) {
						play_status = "开始播放";
						playBackgroundMusic();
					} else {
						play_status = "已暂停";
						stopBackgroundMusic();

					}
					showButtonNotify();
					Log.d(TAG, play_status);
					Toast.makeText(getApplicationContext(), play_status,
							Toast.LENGTH_SHORT).show();
					break;
				case BUTTON_NEXT_ID:
					Log.d(TAG, "下一首");
					Toast.makeText(getApplicationContext(), "下一首",
							Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
				}
			}
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (bReceiver != null) {
			unregisterReceiver(bReceiver);
		}
		super.onDestroy();
	}

	private MediaPlayer mplayer;

	/*
	 * 后台播放背景音
	 */
	private void playBackgroundMusic() {
		if (mplayer == null) {
			mplayer = new MediaPlayer();
			try {
				AssetFileDescriptor afd = this.getAssets().openFd("angel.mp3");
				// 获取音乐数据源
				mplayer.setDataSource(afd.getFileDescriptor(),
						afd.getStartOffset(), afd.getLength());
				afd.close();
				mplayer.setLooping(true); // 设为循环播放
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			if (mplayer.isPlaying()) {
				return;
			}
			mplayer.prepare();
			mplayer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 停止播放背景音乐
	 */
	private void stopBackgroundMusic() {
		try {
			if (null != mplayer) {
				if (mplayer.isPlaying()) {
					mplayer.pause();
//					mplayer.release();
					mplayer = null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
