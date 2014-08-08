package com.duguang.baseanimation.ui.customview.yzhou;

import android.widget.ImageView;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.base.BaseActivity;

/**
 * Y轴旋转切换效果主页面
 * @author Administrator
 *
 */
public class TwoSidedViewActivity extends BaseActivity {
    /** Called when the activity is first created. */

	@Override
	public void setView() {
		  
        ImageView img1=new ImageView(this);
        img1.setImageResource(R.drawable.icon_two_side_view_img1);
        ImageView img2=new ImageView(this);
        img2.setImageResource(R.drawable.icon_two_side_view_img2);
        TwoSidedView tsv=new TwoSidedView(this, img1, img2, 2500);
        
        setContentView(tsv);
		
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		
	}
}