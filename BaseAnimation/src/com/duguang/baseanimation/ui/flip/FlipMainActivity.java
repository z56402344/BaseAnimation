/*
Copyright 2012 Aphid Mobile

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
 
   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.duguang.baseanimation.ui.flip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.AboutActivity;
import com.duguang.baseanimation.ui.base.BaseActivity;
import com.umeng.fb.FeedbackAgent;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.UMSsoHandler;
import com.umeng.socialize.controller.UMWXHandler;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;

/**
 * Filp折叠效果主页面
 * 继承BaseActivity获取ActionBar
 * @author duguang 博客地址:http://blog.csdn.net/duguang77
 */
public class FlipMainActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData();
		setListAdapter(new SimpleAdapter(this, getData(),
				android.R.layout.simple_list_item_1, new String[] { "title" },
				new int[] { android.R.id.text1 }));
		getListView().setScrollbarFadingEnabled(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.optionsmenu, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_about:
			Intent intent = new Intent(FlipMainActivity.this,AboutActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.small_2_big, R.anim.fade_out);
			return true;
		case R.id.menu_feedback:
			FeedbackAgent agent = new FeedbackAgent(this);
		    agent.startFeedbackActivity();
		    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		    return true;
		case R.id.menu_share:
			final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share",RequestType.SOCIAL);
			mController.openShare(FlipMainActivity.this, false);
			 overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override 
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share",RequestType.SOCIAL);
			    
	    /**使用SSO授权必须添加如下代码 */
	    UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
	    if(ssoHandler != null){
	       ssoHandler.authorizeCallBack(requestCode, resultCode, data);
	    }
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Map<String, Object> map = (Map<String, Object>) l
				.getItemAtPosition(position);
		Intent intent = new Intent(this,
				(Class<? extends Activity>) map.get("activity"));
		startActivity(intent);
	}

	private List<? extends Map<String, ?>> getData() {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		addItem(data, "TextViews", FlipTextViewActivity.class);
		addItem(data, "Buttons", FlipButtonActivity.class);
		addItem(data, "Complex Layouts", FlipComplexLayoutActivity.class);
		addItem(data, "Async Content", FlipAsyncContentActivity.class);
		addItem(data, "Event Listener", FlipTextViewAltActivity.class);
		addItem(data, "Horizontal", FlipHorizontalLayoutActivity.class);
		addItem(data, "Issue #5", Issue5Activity.class);
		addItem(data, "XML Configuration", FlipTextViewXmlActivity.class);
		addItem(data, "Fragment", FlipFragmentActivity.class);
		addItem(data, "Dynamic Adapter Size", FlipDynamicAdapterActivity.class);
		addItem(data, "WebView", FlipWebViewActivity.class);
		addItem(data, "Delete page", FlipDeleteAdapterActivity.class);
		addItem(data, "Issue #51", Issue51Activity.class);

		return data;
	}

	private void addItem(List<Map<String, Object>> data, String title,
			Class<? extends Activity> activityClass) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", data.size() + ". " + title);
		map.put("activity", activityClass);
		data.add(map);
	}
	
	private void initData() {
		SocializeConstants.APPKEY = "52c4c16956240bce2e08eeb0";
		// 首先在您的Activity中添加如下成员变量
		final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share",RequestType.SOCIAL);
		                                                                             
		// 设置分享内容
		mController.setShareContent("Android开发者必备BaseAnimation应用,一些想要的效果能过快速找到,并添加到自己的应用中,作者博客地址:http://blog.csdn.net/duguang77");
		
		
		// wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
		String appID = "wx88818f8c48a95eb4";
		// 微信图文分享必须设置一个url 
		String contentUrl = "http://www.umeng.com/social";
		// 添加微信平台，参数1为当前Activity, 参数2为用户申请的AppID, 参数3为点击分享内容跳转到的目标url
		UMWXHandler wxHandler = mController.getConfig().supportWXPlatform(this,appID, contentUrl);
		//设置分享标题
		wxHandler.setWXTitle("Android开发者必备BaseAnimation");
		// 支持微信朋友圈
		UMWXHandler circleHandler = mController.getConfig().supportWXCirclePlatform(this,appID, contentUrl) ;
		circleHandler.setCircleTitle("一些想要的效果能过快速找到,并添加到自己的应用中,BaseAnimation还不错哦...");
		
	//  参数1为当前Activity， 参数2为用户点击分享内容时跳转到的目标地址
		mController.getConfig().supportQQPlatform(this, "http://www.umeng.com/social");   
		
		mController.getConfig().setSsoHandler(new QZoneSsoHandler(this));
		
		//设置腾讯微博SSO handler
		mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
		
		
		
	}
}
