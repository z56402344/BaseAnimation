package com.duguang.baseanimation.ui.customview.progressbar;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.base.BaseActivity;

/**
 * <一句话功能简述>首页面，包含三个按钮<BR>
 * <功能详细描述>
 * 
 * @author chenli
 * @version [版本号, 2011-4-8]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
/**
 * 自定义ProgressBar(4种样式) 主页面
 * @author Administrator
 *
 */
public class ProgressBarMain extends BaseActivity implements OnClickListener
{

    /**
     * 按钮点击进入自定义颜色的进度条页面
     */
    private Button color;

    /**
     * 按钮点击进入左方向进度条页面
     */
    private Button left;

    /**
     * 按钮点击进入右方向进度条页面
     */
    private Button right;

    /**
     * 按钮点击进入不定进度条页面
     */
    private Button indeter;

    /**
     * <一句话功能简述>初始化页面组件及事件<br>
     * <功能详细描述>
     * 
     * @see [类、类#方法、类#成员]
     */
    
	@Override
	public void setView() {
		 setContentView(R.layout.activity_custom_progress_main);

	        initProperty();
		
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		
	}
	
    private void initProperty()
    {
        color = (Button) findViewById(R.id.color);
        left = (Button) findViewById(R.id.left);
        right = (Button) findViewById(R.id.right);
        indeter = (Button) findViewById(R.id.indeter);

        color.setOnClickListener(this);
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        indeter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.color:
                showIntent(ProgressBar_Color.class);
                break;
            case R.id.left:
                showIntent(ProgressBar_Left.class);
                break;
            case R.id.right:
                showIntent(ProgressBar_Right.class);
                break;
            case R.id.indeter:
                showIntent(ProgressBar_Indeter.class);
                break;
            default:
                break;
        }
    }

    /**
     * <一句话功能简述>Intent页面跳转<br>
     * <功能详细描述>
     * 
     * @param mclass
     * @see [类、类#方法、类#成员]
     */
    private void showIntent(Class<?> mclass)
    {
        IntentUtils.getInstance().intentForward(this, mclass);
    }

}
