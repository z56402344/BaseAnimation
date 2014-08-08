package com.duguang.baseanimation.ui.customview.lodingdialog;

import android.view.View;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.base.BaseActivity;

/**
 * 自定义加载LodingDialog样式
 * @author Administrator
 *
 */
public class LoadingDialogMainActivity extends BaseActivity {

	LoadingDialog dialog;

	@Override
	public void setView() {
		setContentView(R.layout.activity_custom_loding_dialog_main);
	}

	@Override
	public void initView() {
		dialog = new LoadingDialog(this);
		
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		
	}
	public void btnClick(View v) {
		dialog.show();
	}



}
