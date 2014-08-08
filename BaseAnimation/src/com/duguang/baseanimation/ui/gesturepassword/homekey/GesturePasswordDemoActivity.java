package com.duguang.baseanimation.ui.gesturepassword.homekey;

import java.util.HashMap;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.common.Constants;
import com.duguang.baseanimation.ui.gesturepassword.homekey.LocusPassWordView.OnCompareListener;
import com.duguang.baseanimation.ui.gesturepassword.homekey.LocusPassWordView.OnCompleteListener;
import com.duguang.baseanimation.ui.gesturepassword.homekey.LocusPassWordView.onCheckListener;
import com.duguang.baseanimation.utils.Logger;
import com.duguang.baseanimation.utils.SharedPreferenceUtil;
import com.duguang.baseanimation.utils.SlideToggle;
import com.duguang.baseanimation.utils.SlideToggle.OnSwitchStateListener;

public class GesturePasswordDemoActivity extends GestureParentActivity {
	private SlideToggle toggle;
	private GridView gridview;
	/**
	 * 九宫格显示布局，文本框提示布局，九宫格设置布局的总体
	 */
	private LinearLayout llPassword;
	private TextView tvShow;
	private LocusPassWordView locusPasswordView;
	private GridAdapter adapter;
	private SharedPreferences sp;
	/**
	 * 九宫格显示布局，0为灰色，1为亮色
	 */
	private String[] gridLists = { "0", "0", "0", "0", "0", "0", "0", "0", "0" };
	/**
	 * defaultPassword-原始密码，resultPassword-新密码 忘记手势密码吗？
	 * 如果没有设置成功（点返回键时），设置原密码为最终密码，否则设置新密码为最终密码
	 */
	private String defaultPassword = "", resultPassword = "";
	/**
	 * 手势密码的开关状态
	 */
	private boolean stateFlag = true;
	/**
	 * 判断是否是忘记密码后清空手势密码，保存手势密码的
	 */
	private boolean cleared = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gesture_password_demo);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		initViews();
		init();
		setEventListener();
	}

	private void initViews() {
		toggle = (SlideToggle) findViewById(R.id.toggle); 
		gridview = (GridView) findViewById(R.id.gridview);
		llPassword = (LinearLayout) findViewById(R.id.ll_password);
		tvShow = (TextView) findViewById(R.id.tv_show);
		locusPasswordView = (LocusPassWordView) findViewById(R.id.locus_password_view);
	}

	public void init() {
		toggle.setImageResIDs(R.drawable.open_toggle_bg,
				R.drawable.clost_toggle_bg, R.drawable.del_btn);
		stateFlag = sp.getBoolean("state", true);
		stateFlag = SharedPreferenceUtil.getBooleanValueFromSP(this,
				Constants.GSNAMEINSHARED, "state");
		if (!stateFlag) {// 忘记密码，登录后，即使为开启状态也要改为关闭状态
			Logger.d("Logger", "flag为假 " + stateFlag);
			llPassword.setVisibility(View.GONE);
			toggle.setSwitchState(false);
		} else {
			Logger.d("Logger", "flag为真 " + stateFlag);
			llPassword.setVisibility(View.VISIBLE);
			toggle.setSwitchState(true);
			if (!"".equals(defaultPassword)) {
				tvShow.setText(R.string.setDefaultPassword);
			} else {
				tvShow.setText(R.string.setpassword);
			}
		}

		defaultPassword = SharedPreferenceUtil.getStringValueFromSP(this,
				Constants.GPNAMEINSHARED, "password");
		SharedPreferenceUtil.setBooleanDataIntoSP(this,Constants.GPNAMEINSHARED,
				"cleared", false);
		// 原始密码为空，直接输入新密码，否则判断原始密码是否正确，点击home键再次进来时，接着上次的运行，特殊情况除外
		if ("".equals(defaultPassword)) {
			locusPasswordView.setFirst(false);
			locusPasswordView.setSecond(true);
		} else {
			locusPasswordView.setFirst(true);
		}
		adapter = new GridAdapter(this, gridLists);
		gridview.setAdapter(adapter);
	}

	/**
	 * 将手势密码的开关状态判断，由onCreate移到onResume，在极端情况下：
	 * 当前页面是修改手势密码且开关状态为打开状态，点击home键，再次进入该系统却忘记密码，重新登陆后手势密码清空，将开关状态更改为关闭状态
	 * modified by liuweina，2013/10/14
	 */
	@Override
	protected void onResume() {
		super.onResume();
			cleared = SharedPreferenceUtil.getBooleanValueFromSP(this,
					Constants.GPNAMEINSHARED, "cleared");
		Logger.d("onResume-----", "defaultPassword");
		if (cleared) {// 从手势密码界面，点击home，忘记密码再次进入时，更改其状态为关闭状态
			llPassword.setVisibility(View.GONE);
			toggle.setSwitchState(false);
			// 手势密码清空，下面开关的状态改变时，影响判断
			defaultPassword = SharedPreferenceUtil.getStringValueFromSP(this,
					Constants.GPNAMEINSHARED, "password");
			// 进入该页面，更改手势密码状态为false，区分开刚设置还是以前设置过忘记密码进来的情况
			SharedPreferenceUtil.setBooleanDataIntoSP(this,Constants.GPNAMEINSHARED,
					"cleared", false);
		}
	}

	public void setEventListener() {
		toggle.setOnSwitchStateListener(onSwitchListener);
		locusPasswordView.setOnCompareListener(lpwvOnCompareListener);
		locusPasswordView.setOnCheckListener(lpwvOnCheckListener);
		locusPasswordView.setOnCompleteListener(lpwvOnCompleteListener);
	}

	private OnSwitchStateListener onSwitchListener = new OnSwitchStateListener() {

		@Override
		public void onSwitch(boolean state) {
			if (state) {// 打开手势密码，更新开关状态值为true，开关改变值也为true，
				llPassword.setVisibility(View.VISIBLE);
				SharedPreferenceUtil.setBooleanDataIntoSP(GesturePasswordDemoActivity.this,
						Constants.GPNAMEINSHARED, "state", true);
				// 如果已经设置了部分手势密码，恢复为初始状态
				refreshPassStr();
				// 原始密码为空，直接输入新密码，否则判断原始密码是否正确
				if ("".equals(defaultPassword)) {
					locusPasswordView.setFirst(false);
					locusPasswordView.setSecond(true);
					tvShow.setText(R.string.setpassword);
				} else {
					locusPasswordView.setFirst(true);
					tvShow.setText(R.string.setDefaultPassword);
				}
				adapter.notifyDataSetChanged();
				gridview.invalidate();
			} else {
				llPassword.setVisibility(View.INVISIBLE);
				SharedPreferenceUtil.setBooleanDataIntoSP(GesturePasswordDemoActivity.this,
						Constants.GSNAMEINSHARED, "state", false);
			}
		}
	};

	/**
	 * 将显示密码归为全0
	 * 
	 * @param passStr
	 */
	private void refreshPassStr() {
		for (int i = 0; i < 9; i++) {
			gridLists[i] = "0";
		}
	}

	/**
	 * 原始密码不为空时，输入原始密码后，比较原始密码输入正确与否 modified by liuweina，2013/10/14
	 */
	private OnCompareListener lpwvOnCompareListener = new OnCompareListener() {

		@Override
		public void onCompare(String password) {
			Logger.e("compare", password);
			if (password.contains(",")) {// password为本次输入的手势密码值
				refreshPassStr();
				String[] passStr = password.split(",");
				for (int i = 0; i < passStr.length; i++) {
					gridLists[Integer.parseInt(passStr[i])] = "1";
				}
				adapter.notifyDataSetChanged();
				gridview.postInvalidate();
				if (locusPasswordView.verifyPassword(password)) {// 输入原始密码正确
					defaultPassword = password;
					tvShow.setText(R.string.newPasswordInput);
					// 修改判断条件，下一步可输入新密码
					locusPasswordView.setFirst(false);
					locusPasswordView.setSecond(true);
				} else {
					tvShow.setText(R.string.originalPasswordInputError);
				}
			}
		}

	};

	/**
	 * 第一次输入新密码后，更新上方的小显示框
	 */
	private onCheckListener lpwvOnCheckListener = new onCheckListener() {

		@Override
		public void onCheck(String password) {
			Logger.e("check", password);
			if (password.contains(",")) {
				refreshPassStr();
				String[] passStr = password.split(",");
				for (int i = 0; i < passStr.length; i++) {
					gridLists[Integer.parseInt(passStr[i])] = "1";
				}
				gridview.postInvalidate();
				adapter.notifyDataSetChanged();
				tvShow.setText(R.string.newPasswordInputAgain);
				// 第一次输入新密码后，更改状态为判断两次新密码是否正确
				locusPasswordView.setFirst(false);
				locusPasswordView.setSecond(false);
				locusPasswordView.setThird(true);
			}
		}
	};

	/**
	 * 第二次输入新密码后，比较两次密码是否一致
	 */
	private OnCompleteListener lpwvOnCompleteListener = new OnCompleteListener() {

		@Override
		public void onComplete(String firstPassword, String secondPassword) {
			Logger.e("complete", firstPassword + ", " + secondPassword);
			if (firstPassword.equals(secondPassword)) {
				resultPassword = secondPassword;
				HashMap<String, Object> keyValueMap = new HashMap<String, Object>();
				keyValueMap.put("password", secondPassword);
				keyValueMap.put("cleared", false);
				SharedPreferenceUtil.setDataIntoSP(GesturePasswordDemoActivity.this,Constants.GPNAMEINSHARED,
						keyValueMap);
				GesturePasswordDemoActivity.this.finish();
			} else {
				tvShow.setText(R.string.newPasswordInputError);
			}
		}
	};

	/**
	 * 点击页面左上角的“返回”按钮
	 */
	private OnClickListener backOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Logger.e("back", "defaultPass = " + defaultPassword
					+ ", newPass = " + resultPassword);
			// 点返回按钮，先判断手势密码是否为空，若为空，保持开关状态为关；否则，开关状态不变
			if ("".equals(defaultPassword)) {// 初始密码为空，手势密码未设置成功过，保持关闭状态
				SharedPreferenceUtil.setBooleanDataIntoSP(GesturePasswordDemoActivity.this,
						Constants.GSNAMEINSHARED, "state", false);
			}
			finish();
		}

	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Logger.e(
				"onKeyDown",
				"defaultPass = "
						+ defaultPassword
						+ ", newPass = "
						+ resultPassword
						+ ", state = "
						+ SharedPreferenceUtil.getBooleanValueFromSP(this,
								Constants.GSNAMEINSHARED, "state"));
		// 点返回键，先判断手势密码是否为空，若为空，保持开关状态为关；否则，开关状态不变
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ("".equals(defaultPassword)) {// 初始密码为空，手势密码未设置成功过，保持关闭状态
				SharedPreferenceUtil.setBooleanDataIntoSP(this,
						Constants.GSNAMEINSHARED, "state", false);
			}
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
