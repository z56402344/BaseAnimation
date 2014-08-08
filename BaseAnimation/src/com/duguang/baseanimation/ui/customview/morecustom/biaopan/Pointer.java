package com.duguang.baseanimation.ui.customview.morecustom.biaopan;

public class Pointer {
	private float x;
	private float y;

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Pointer [x=" + x + ", y=" + y + "]";
	}

	public Pointer(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}

}
