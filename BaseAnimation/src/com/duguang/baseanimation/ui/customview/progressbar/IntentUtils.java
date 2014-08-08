package com.duguang.baseanimation.ui.customview.progressbar;

import android.content.Context;
import android.content.Intent;

/**
 * <һ�仰���ܼ���>Intent������<BR>
 * <������ϸ����>
 * 
 * @author chenli
 * @version [�汾��, 2011-4-8]
 * @see [�����/����]
 * @since [��Ʒ/ģ��汾]
 */
public class IntentUtils
{
    /**
     * ��������
     */
    public static final int HANDLER_LEFT = 0;

    /**
     * ��������
     */
    public static final int HANDLER_RIGHT = 1;

    /**
     * ��ȷ�����
     */
    public static final int HANDLER_INDETER = 2;

    /**
     * IntentUtils����
     */
    private static IntentUtils mIntentUtils;

    /**
     * <һ�仰���ܼ���>��ȡIntentUtils����<BR>
     * <������ϸ����>
     * 
     * @return
     * @see [�ࡢ��#��������#��Ա]
     */
    public static IntentUtils getInstance()
    {
        if (mIntentUtils == null)
        {
            mIntentUtils = new IntentUtils();
        }
        return mIntentUtils;
    }

    /**
     * <һ�仰���ܼ���>Intentҳ����ת<BR>
     * <������ϸ����>
     * 
     * @param context
     * @param mclass
     * @see [�ࡢ��#��������#��Ա]
     */
    public void intentForward(Context context, Class<?> mclass)
    {
        Intent intent = new Intent();
        intent.setClass(context, mclass);
        context.startActivity(intent);
    }
}
