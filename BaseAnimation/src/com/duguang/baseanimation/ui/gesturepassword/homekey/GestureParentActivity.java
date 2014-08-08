package com.duguang.baseanimation.ui.gesturepassword.homekey;

import android.R;
import android.content.Intent;

import com.duguang.baseanimation.app.MainApplication;
import com.duguang.baseanimation.ui.base.BaseActivity;
import com.duguang.baseanimation.ui.gesturepassword.homekey.homekey.HomeLoginActivity;

/**
 * 含手势密码功能的父类，本类本该是所有Activity的父类，但这里为了不影响其他的案例，只被几个类继承
 * 如果手势密码不为空且手势密码为开启状态，点击home键出去，再次打开应用时，则跳转到HomeLoginActivity界面，要求输入手势密码
 * 并且输入正确，方可进入应用；如果忘记手势密码，一般的处理是重新登录，登录成功后也可进入应用
 * 
 *
 */
public class GestureParentActivity extends BaseActivity {
	@Override
	protected void onResume() {
		super.onResume();
		if (MainApplication.getInstance().isPass()) {
			MainApplication.getInstance().setPass(false);
			Intent i = new Intent(GestureParentActivity.this, HomeLoginActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			i.putExtra("isHome", true);
			startActivity(i);
		}
	}

	@Override
	public void setView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initView() {
		
		
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		
	}
}
