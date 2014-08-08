package com.duguang.baseanimation.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.duguang.baseanimation.ContantValue;
import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.base.BaseActivity;
import com.duguang.baseanimation.ui.canvas.CanvasMainActivity;
import com.duguang.baseanimation.ui.customview.CustomViewMainActivity;
import com.duguang.baseanimation.ui.flip.FlipMainActivity;
import com.duguang.baseanimation.ui.gesturepassword.homekey.GesturePasswordDemoActivity;
import com.duguang.baseanimation.ui.imitate.ImitateMainActivity;
import com.duguang.baseanimation.ui.listivew.ListViewMani;
import com.duguang.baseanimation.ui.listivew.refresh.RefreshMainActivity;
import com.duguang.baseanimation.ui.nineold.NineOldMani;
import com.duguang.baseanimation.ui.notification.NotificationMainActivity;
import com.duguang.baseanimation.ui.property.PropertyAnimationActivity;
import com.duguang.baseanimation.ui.splash.SplashMainActivity;
import com.duguang.baseanimation.ui.tab.TabMainActivity;
import com.duguang.baseanimation.utils.ExampleUtil;
import com.umeng.fb.FeedbackAgent;
import com.umeng.socialize.bean.LIKESTATUS;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SocializeClientListener;
import com.umeng.socialize.view.ActionBarView;
import com.umeng.update.UmengUpdateAgent;

/**
 * 主页面 继承BaseActivity获取ActionBar
 * 
 * @author duguang 博客地址:http://blog.csdn.net/duguang77
 */
public class MainActivity extends BaseActivity implements OnItemClickListener {

	private static final String tag = "MainActivity";
	private ListView listView_animation;
	private ArrayAdapter<String> itemAdapter;
	private TextView tvHint;



	@Override
	public void setView() {
		FeedbackAgent agent = new FeedbackAgent(this);
		agent.sync();
		UmengUpdateAgent.update(this);// 友盟更新
		UmengUpdateAgent.setUpdateOnlyWifi(false);// 在非Wifi情况下也检测版本更新
		getWindow().requestFeature(Window.FEATURE_PROGRESS); // 去标题栏
		setContentView(R.layout.activity_splash);
		init();
		registerMessageReceiver(); // used for receive msg
	}

	private void init() {

		UMSocialService controller = UMServiceFactory.getUMSocialService(
				"com.umeng.like", RequestType.SOCIAL);
		controller.getEntity().getLikeStatus();
		controller.likeChange(this, new SocializeClientListener() {
			@Override
			public void onStart() {
			}

			@Override
			public void onComplete(int status, SocializeEntity entity) {
				if (entity != null) {
					// 获取当前的“喜欢”状态
					LIKESTATUS likeStatus = entity.getLikeStatus();
				}
			}

		});

		// 用于集成ActionBar 的ViewGroup ActionBar 将填充提供的ViewGroup(需要开发者自己在布局文件或代码中创建
		// ，建议使用RelativeLayout)
		RelativeLayout parent = (RelativeLayout) findViewById(R.id.RelativeLayout);
		// 创建ActionBar des参数是ActionBar的唯一标识，请确保不为空
		ActionBarView socializeActionBar = new ActionBarView(this, "111");

		LayoutParams layoutParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		socializeActionBar.setLayoutParams(layoutParams);
		// 添加ActionBar
		parent.addView(socializeActionBar);
	}

	@Override
	public void initView() {
		listView_animation = (ListView) findViewById(R.id.listView_animation);
		tvHint = (TextView) findViewById(R.id.tv_hint);
		itemAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, ContantValue.mainItem);

		Animation ani = new AlphaAnimation(0f, 1f);
		ani.setDuration(1500);
		ani.setRepeatMode(Animation.REVERSE);
		ani.setRepeatCount(Animation.INFINITE);
		tvHint.startAnimation(ani);
	}

	@Override
	public void setListener() {
		listView_animation.setAdapter(itemAdapter);
		listView_animation.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Log.i(tag, String.valueOf(position));
		// Animation anim = AnimationUtils.loadAnimation(MainActivity.this,
		// R.anim.out_translate_top);
		// listView_animation.startAnimation(anim);

		Intent intent = null;
		switch (position) {
		case 0:
			//简单动画
			startIntent(SimpleActivity.class);
			overridePendingTransition(R.anim.in_translate_top,
					R.anim.out_translate_top);
			break;
		case 1:
			//复杂动画
			startIntent(ComplexActivity.class);
			overridePendingTransition(R.anim.block_move_right,
					R.anim.small_2_big);
			break;
		case 2:
			//Splash引导动画
			startIntent(SplashMainActivity.class);
			overridePendingTransition(R.anim.small_2_big,
					R.anim.block_move_right);
			break;
		case 3:
			//Flip效果集合
			startIntent(FlipMainActivity.class);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case 4:
			//NineOld效果
			startIntent(NineOldMani.class);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case 5:
			//Property效果
			startIntent(PropertyAnimationActivity.class);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case 6:
			//高仿动画
			startIntent(ImitateMainActivity.class);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case 7:
			//ListView效果集合
			startIntent(ListViewMani.class);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case 8:
			//自定义控件效果集合
			startIntent(CustomViewMainActivity.class);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case 9:
			//页面滑动切换效果集合
			startIntent(TabMainActivity.class);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case 10:
			//画笔绘制效果集合
			startIntent(CanvasMainActivity.class);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case 11:
			//手势效果集合
			startIntent(GesturePasswordDemoActivity.class);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case 12:
			//Notification所有效果集合
			startIntent(NotificationMainActivity.class);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		default:
			break;
		}

	}
	
	/**
	 * 切换Activity
	 * @param class1
	 */
	public void startIntent(Class class1){
		Intent intent = new Intent(MainActivity.this,class1);
		startActivity(intent);
	}

	// for receive customer msg from jpush server
	private MessageReceiver mMessageReceiver;
	public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_EXTRAS = "extras";
	public static boolean isForeground = false;

	public void registerMessageReceiver() {
		mMessageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction(MESSAGE_RECEIVED_ACTION);
		registerReceiver(mMessageReceiver, filter);
	}

	public class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
				String messge = intent.getStringExtra(KEY_MESSAGE);
				String extras = intent.getStringExtra(KEY_EXTRAS);
				StringBuilder showMsg = new StringBuilder();
				showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
				if (!ExampleUtil.isEmpty(extras)) {
					showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
				}
			}
		}
	}

	@Override
	protected void onResume() {
		isForeground = true;
		super.onResume();
	}

	@Override
	protected void onPause() {
		isForeground = false;
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(mMessageReceiver);
		super.onDestroy();
	}

}
