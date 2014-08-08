package com.duguang.baseanimation.ui.tab.paralloid.library1.transform;

/**
 * Created by chris on 23/10/2013
 * Project: Paralloid
 */
public class InvertTransformer implements Transformer {
    @Override
    public int[] scroll(float x, float y, float factor) {
        return new int[]{(int) -(x * factor), (int) -(y * factor)};
    }
}
