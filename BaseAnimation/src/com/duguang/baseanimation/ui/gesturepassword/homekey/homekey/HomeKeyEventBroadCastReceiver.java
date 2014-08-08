package com.duguang.baseanimation.ui.gesturepassword.homekey.homekey;




import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.duguang.baseanimation.app.MainApplication;
import com.duguang.baseanimation.common.Constants;
import com.duguang.baseanimation.utils.Logger;
import com.duguang.baseanimation.utils.SharedPreferenceUtil;

/**
 * Home事件广播监听
 * 
 * @author cindy
 * 
 */
public class HomeKeyEventBroadCastReceiver extends BroadcastReceiver {

	static final String SYSTEM_REASON = "reason";
	static final String SYSTEM_HOME_KEY = "homekey";// home key
	static final String SYSTEM_RECENT_APPS = "recentapps";// long home key
	private Context contexts;

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		this.contexts = context;
		if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
			String reason = intent.getStringExtra(SYSTEM_REASON);
			if (reason != null) {
				if (reason.equals(SYSTEM_HOME_KEY)) {
					// home key处理点
					boolean openPassword = SharedPreferenceUtil.getBooleanValueFromSP(contexts,
							Constants.GSNAMEINSHARED, "state");
					//modified by liuweina，手势密码处于打开状态，并且手势密码不为空
					if (openPassword
							&& !"".equals(SharedPreferenceUtil.getStringValueFromSP(context,
									Constants.GPNAMEINSHARED, "password"))) {
						if (!MainApplication.getInstance().isLogin()) {
							MainApplication.getInstance().setPass(true);
						}
					}
				} else if (reason.equals(SYSTEM_RECENT_APPS)) {
					// long home key处理点
					Logger.d("HomeKeyEventBroadCastReceiver", "SYSTEM_RECENT_APPS");
				}
			}
		}
	}

}
