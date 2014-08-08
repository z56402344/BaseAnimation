package com.duguang.baseanimation.ui.gesturepassword.homekey;

/**
 * 点位置
 * @author zhangzhp
 *
 */
public class Point
{
	public static int STATE_NORMAL = 0; // 未选中
	public static int STATE_CHECK = 1; // 选中 e
	public static int STATE_CHECK_ERROR = 2; // 已经选中,但是输错误
	public float x;
	public float y;
	public int state = 0;
	public int index = 0;// 下标

	public Point()
	{
		
	}

	public Point(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

}
