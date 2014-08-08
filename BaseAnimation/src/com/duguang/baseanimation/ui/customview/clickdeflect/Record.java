package com.duguang.baseanimation.ui.customview.clickdeflect;

public class Record{
	

	public int getDegrees(float a, float b) {
		float dx = a;
		float x = b / 4;
		float x2 = 3 * b / 4;
		if (dx < x / 10)
			return 10;
		else if (dx < x / 5)
			return 9;
		else if (dx < x * 3 / 10)
			return 8;
		else if (dx < x * 2 / 5)
			return 7;
		else if (dx < x * 5 / 10)
			return 6;
		else if (dx < x * 3 / 5)
			return 5;
		else if (dx < x * 7 / 10)
			return 4;
		else if (dx < x * 4 / 5)
			return 3;
		else if (dx < x * 9 / 10)
			return 2;
		else if (dx < x)
			return 2;
		else if (dx - x2 > x * 9 / 10)
			return 10;
		else if (dx - x2 > x * 4 / 5)
			return 9;
		else if (dx - x2 > x * 7 / 10)
			return 8;
		else if (dx - x2 > x * 3 / 5)
			return 7;
		else if (dx - x2 > x * 5 / 10)
			return 6;
		else if (dx - x2 > x * 2 / 5)
			return 5;
		else if (dx - x2 > x * 3 / 10)
			return 4;
		else if (dx - x2 > x / 5)
			return 3;
		else if (dx - x2 > x / 10)
			return 2;
		return 10;
	}
}