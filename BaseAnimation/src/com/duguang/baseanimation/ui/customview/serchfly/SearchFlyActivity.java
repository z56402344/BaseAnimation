package com.duguang.baseanimation.ui.customview.serchfly;

import java.util.Random;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.base.BaseActivity;

/**
 * 自定义FramLayout文字飞入飞出效果
 * 主页面
 * @author Administrator
 *
 */
public class SearchFlyActivity extends BaseActivity implements OnClickListener {
	public static final String[] keywords = { "QQ", "BaseAnimation", "APK",
			"GFW", "铅笔",//
			"短信", "桌面精灵", "MacBook Pro", "平板电脑", "雅诗兰黛",//
			"Base", "笔记本", "SPY Mouse", "Thinkpad E40", "捕鱼达人",//
			"内存清理", "地图", "导航", "闹钟", "主题",//
			"通讯录", "播放器", "CSDN leak", "安全", "Animation",//
			"美女", "天气", "4743G", "戴尔", "联想",//
			"欧朋", "浏览器", "愤怒的小鸟", "mmShow", "网易公开课",//
			"iciba", "油水关系", "网游App", "互联网", "365日历",//
			"脸部识别", "Chrome", "Safari", "中国版Siri", "苹果",//
			"iPhone5S", "摩托 ME525", "魅族 MX3", "小米" };
	private KeywordsFlow keywordsFlow;
	private Button btnIn, btnOut;


	@Override
	public void onClick(View v) {
		if (v == btnIn) {
			keywordsFlow.rubKeywords();
			// keywordsFlow.rubAllViews();
			feedKeywordsFlow(keywordsFlow, keywords);
			keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);
		} else if (v == btnOut) {
			keywordsFlow.rubKeywords();
			// keywordsFlow.rubAllViews();
			feedKeywordsFlow(keywordsFlow, keywords);
			keywordsFlow.go2Show(KeywordsFlow.ANIMATION_OUT);
		} else if (v instanceof TextView) {
			String keyword = ((TextView) v).getText().toString();
			// Intent intent = new Intent();
			// intent.setAction(Intent.ACTION_VIEW);
			// intent.addCategory(Intent.CATEGORY_DEFAULT);
			// intent.setData(Uri.parse("http://www.google.com.hk/#q=" +
			// keyword));
			// startActivity(intent);
			Log.e("Search", keyword);
		}
	}

	@Override
	public void setView() {
		setContentView(R.layout.activity_custom_serch_fly_main);
		
		
	}

	@Override
	public void initView() {
		btnIn = (Button) findViewById(R.id.button1);
		btnOut = (Button) findViewById(R.id.button2);
		btnIn.setOnClickListener(this);
		btnOut.setOnClickListener(this);
		keywordsFlow = (KeywordsFlow) findViewById(R.id.frameLayout1);
		keywordsFlow.setDuration(800l);
		keywordsFlow.setOnItemClickListener(this);
		// 添加
		feedKeywordsFlow(keywordsFlow, keywords);
		keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);
		
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		
	}
	
	private static void feedKeywordsFlow(KeywordsFlow keywordsFlow, String[] arr) {
		Random random = new Random();
		for (int i = 0; i < KeywordsFlow.MAX; i++) {
			int ran = random.nextInt(arr.length);
			String tmp = arr[ran];
			keywordsFlow.feedKeyword(tmp);
		}
	}
}
