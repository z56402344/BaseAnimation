package com.duguang.baseanimation.ui.customview.ribbomenu;

import android.view.MenuItem;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.base.BaseActivity;

/**
 * 自定义LinearLayout侧边栏效果主页面
 * @author Administrator
 *
 */
public class RibbonMainActivity extends BaseActivity implements
		iRibbonMenuCallback {
	/** Called when the activity is first created. */

	private RibbonMenuView rbmView;

	@Override
	public void setView() {
		setContentView(R.layout.activity_custom_ribbon_main);

		rbmView = (RibbonMenuView) findViewById(R.id.ribbonMenuView1);
		rbmView.setMenuClickCallback(this);
		rbmView.setMenuItems(R.menu.ribbon_menu);

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == android.R.id.home) {

			rbmView.toggleMenu();

			return true;

		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void RibbonMenuItemClick(int itemId) {

	}
}