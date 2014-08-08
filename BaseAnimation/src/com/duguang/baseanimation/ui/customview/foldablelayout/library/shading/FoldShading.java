package com.duguang.baseanimation.ui.customview.foldablelayout.library.shading;

import android.graphics.Canvas;
import android.graphics.Rect;

public interface FoldShading {
    void onPreDraw(Canvas canvas, Rect bounds, float rotation, int gravity);

    void onPostDraw(Canvas canvas, Rect bounds, float rotation, int gravity);
}
