package com.duguang.baseanimation.ui.notification;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class BaseTools {
	/**
	 * 获取当前应用版本号
	 * @param context
	 * @return version
	 * @throws Exception
	 */
	public static String getAppVersion(Context context) throws Exception {
		// 获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
		String versionName = packInfo.versionName;
		return versionName;
	}
	
	/**
	 * 获取当前系统SDK版本号
	 */
	public static int getSystemVersion(){
		/*获取当前系统的android版本号*/
		int version= android.os.Build.VERSION.SDK_INT;
		return version;
	}
}
