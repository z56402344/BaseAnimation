package com.duguang.baseanimation.ui.gesturepassword.homekey.homekey;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.app.MainApplication;
import com.duguang.baseanimation.common.Constants;
import com.duguang.baseanimation.ui.gesturepassword.homekey.LocusPassWordView;
import com.duguang.baseanimation.ui.gesturepassword.homekey.LocusPassWordView.OnCompareListener;
import com.duguang.baseanimation.utils.SharedPreferenceUtil;

/**
 * HomeKey 手势解锁登陆Activity
 * @author cindy
 *
 */

public class HomeLoginActivity extends Activity {
	private LocusPassWordView lpwv;
	public static final int OAUTHERROR = 210;
	private String defaultPassword = "";
	public static int currWidth = 400,currHight = 800;
	/**
	 * 判断是否是1>点击home键，手势密码开启且手势密码不为空时才进入到该页面
	 * 也有可能是2>刚打开该软件，modified by liuweina
	 * 若是情况1，值为true；若是情况2，值为false；若是true，则直接关闭该页面，否则，跳转到“我的最爱”界面
	 */
	private boolean fromHome = false;
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		
		//modified by liuweina,添加下面5行代码
		fromHome = getIntent().getBooleanExtra("isHome", false);
		Display display = getWindowManager().getDefaultDisplay(); // 为获取屏幕宽、高
		currWidth = display.getWidth();
		currHight = display.getHeight();
		
		/**
		 * 当前页面是HomeLoginActivity，此时再点击home键，将不跳转到该页面，即只有一个输入手势密码进入系统的界面
		 */
		MainApplication.getInstance().setLogin(true);
		
		lpwv = (LocusPassWordView) this.findViewById(R.id.mLocusPassWordView);
		lpwv.setFirst(true);
		lpwv.setOnCompareListener(new OnCompareListener() {
			
			@Override
			public void onCompare(String password) {
				if (lpwv.verifyPassword(password)) {
					defaultPassword = password;
					
					MainApplication.getInstance().setLogin(false);
					if(fromHome) {//点击home键进入该页面，直接关闭该页面，进入到点home前的界面
						HomeLoginActivity.this.finish();
					}
				} else {
					Toast.makeText(HomeLoginActivity.this, R.string.passwordInputError,
							Toast.LENGTH_SHORT).show();
					lpwv.clearPassword();
				}
			}
		});
		TextView forgetPassword = (TextView) this.findViewById(R.id.tvForgetPassword);
		//忘记密码
		forgetPassword.setOnClickListener(forgetPasswordListener);

	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	//	忘记密码监听器
	private OnClickListener forgetPasswordListener=new OnClickListener(){

		@Override
		public void onClick(View v) {
			//下面应该是请求，这里只写了请求正确作的结果
			HashMap<String, Object> keyValueMap = new HashMap<String, Object>();
			keyValueMap.put("password", "");
			keyValueMap.put("cleared", true);
			SharedPreferenceUtil.setDataIntoSP(HomeLoginActivity.this,Constants.GPNAMEINSHARED, keyValueMap);
			lpwv.clearPassword();
			SharedPreferenceUtil.setBooleanDataIntoSP(HomeLoginActivity.this,Constants.GSNAMEINSHARED, "state", false);
			MainApplication.getInstance().setLogin(false);
			if(fromHome) {//点击home键进入该页面，直接关闭该页面，进入到点home前的界面
				HomeLoginActivity.this.finish();
			}
		}
		
	};
	
	/**
	 * 虚拟home键，按返回键，返回到桌面
	 * added by liuweina
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addCategory(Intent.CATEGORY_HOME);
			this.startActivity(intent);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
