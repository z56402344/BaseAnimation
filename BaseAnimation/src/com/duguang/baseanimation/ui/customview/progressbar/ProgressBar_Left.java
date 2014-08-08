package com.duguang.baseanimation.ui.customview.progressbar;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;

import com.duguang.baseanimation.R;

/**
 * <一句话功能简述>从左向右运动的进度条<BR>
 * <功能详细描述>
 * 
 * @author chenli
 * @version [版本号, 2011-4-8]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ProgressBar_Left extends Activity
{

    /**
     * 进度条
     */
    private ProgressBar mLeft = null;

    /**
     * 当前进度的值
     */
    private int mCount = 0;

    /**
     * Handler消息处理
     */
    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if (msg.what == IntentUtils.HANDLER_LEFT)
            {
                finish();
            }
            super.handleMessage(msg);
        }
    };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_progress_mypage_left);

        showIndeterDialog();
    }

    /**
     * <一句话功能简述>展示进度条的进度<BR>
     * <功能详细描述>
     * 
     * @see [类、类#方法、类#成员]
     */
    private void showIndeterDialog()
    {
        mCount = 0;

        mLeft = (ProgressBar) findViewById(R.id.progress_horizontal_left);
        mLeft.setMax(100);
        mLeft.setProgress(0);
        mLeft.setIndeterminate(false);
        new Thread()
        {
            public void run()
            {
                try
                {
                    while (mCount <= 100)
                    {
                        mLeft.setProgress(mCount++);
                        Thread.sleep(100);
                    }
                    if (mCount > 100)
                    {
                        mHandler.sendEmptyMessage(IntentUtils.HANDLER_LEFT);
                    }
                }
                catch (Exception ex)
                {
                }
            }
        }.start();
    }
}
