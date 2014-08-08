package com.duguang.baseanimation.ui.customview.switchs;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.base.BaseActivity;
import com.duguang.baseanimation.ui.customview.switchs.MySlipSwitch.OnSwitchListener;

public class SwitchMainActivity extends BaseActivity {

	private Button switch_Btn;
	private MySlipSwitch slipswitch_MSL;

	@Override
	public void setView() {
		setContentView(R.layout.activity_custom_switch_main);

	}

	@Override
	public void initView() {
		slipswitch_MSL = (MySlipSwitch) findViewById(R.id.main_myslipswitch);
		slipswitch_MSL.setImageResource(R.drawable.switch_bkg_switch,
				R.drawable.switch_bkg_switch, R.drawable.switch_btn_slip);
		slipswitch_MSL.setSwitchState(true);
		switch_Btn = (Button) findViewById(R.id.main_button_switch);
	}

	@Override
	public void setListener() {
		slipswitch_MSL.setOnSwitchListener(new OnSwitchListener() {

			@Override
			public void onSwitched(boolean isSwitchOn) {
				// TODO Auto-generated method stub
				if (isSwitchOn) {
					Toast.makeText(SwitchMainActivity.this, "开关已经开启", 0).show();
				} else {
					Toast.makeText(SwitchMainActivity.this, "开关已经关闭", 0).show();
				}
			}
		});

		switch_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				slipswitch_MSL.updateSwitchState(!slipswitch_MSL
						.getSwitchState());

				if (slipswitch_MSL.getSwitchState()) {
					Toast.makeText(SwitchMainActivity.this, "开关已经开启", 0).show();
				} else {
					Toast.makeText(SwitchMainActivity.this, "开关已经关闭", 0).show();
				}
			}
		});

	}
}