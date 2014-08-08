package com.duguang.baseanimation.ui.tab.tabhost;

import android.app.TabActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.duguang.baseanimation.R;

public class TabHostMoveMainActivity extends TabActivity {
	TabHost m_TabHost;
	Button upButton;
	Button nextButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_tabhostmovie_main);
		initView();
		initTabHost();

		TabWidget tabWidget = m_TabHost.getTabWidget();
		int count = tabWidget.getChildCount();

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		int screenWidth = dm.widthPixels;
		Log.i("test", "screenWidth=" + screenWidth);
		if (count > 3) {
			for (int i = 0; i < count; i++) {
				tabWidget.getChildTabViewAt(i).setMinimumWidth(
						(screenWidth) / 3);
			}
		}
	}

	private void initView() {
		m_TabHost = getTabHost();
//		upButton = (Button) findViewById(R.id.up_button);
//		nextButton = (Button) findViewById(R.id.next_button);
	}   

	private void initTabHost() {
		m_TabHost.addTab(m_TabHost.newTabSpec(0 + "").setIndicator("A  eoe")
				.setContent(R.id.textview01));
		m_TabHost.addTab(m_TabHost.newTabSpec(1 + "").setIndicator("B  eoe")
				.setContent(R.id.textview01));
		m_TabHost.addTab(m_TabHost.newTabSpec(2 + "").setIndicator("C  eoe")
				.setContent(R.id.textview01));
		m_TabHost.addTab(m_TabHost.newTabSpec(3 + "").setIndicator("D  eoe")
				.setContent(R.id.textview01));
		m_TabHost.addTab(m_TabHost.newTabSpec(4 + "").setIndicator("E  eoe")
				.setContent(R.id.textview01));
		m_TabHost.addTab(m_TabHost.newTabSpec(5 + "").setIndicator("F  eoe")
				.setContent(R.id.textview01));
		m_TabHost.addTab(m_TabHost.newTabSpec(6 + "").setIndicator("G  eoe")
				.setContent(R.id.textview01));
		
	}
}