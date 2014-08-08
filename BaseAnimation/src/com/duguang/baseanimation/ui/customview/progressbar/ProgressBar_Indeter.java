package com.duguang.baseanimation.ui.customview.progressbar;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.duguang.baseanimation.R;

/**
 * <一句话功能简述>不定进度的进度条<BR>
 * <功能详细描述>
 * 
 * @author chenli
 * @version [版本号, 2011-4-8]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ProgressBar_Indeter extends Activity
{

    /**
     * Handler消息处理
     */
    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if (msg.what == IntentUtils.HANDLER_INDETER)
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
        setContentView(R.layout.activity_custom_progress_mypage_indeter);

        // 延时发送消息
        mHandler.sendEmptyMessageDelayed(IntentUtils.HANDLER_INDETER, 3000);
    }
}
