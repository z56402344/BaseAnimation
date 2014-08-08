package com.duguang.baseanimation.ui.tab.paralloid.library1.transform;

/**
 * Put simply turns up into right, right into down, down into left, left into up.
 *
 * Created by chris on 23/10/2013
 * Project: Paralloid
 */
public class RightAngleTransformer implements Transformer {

    @Override
    public int[] scroll(float x, float y, float factor) {
        return new int[]{(int) -(y * factor), (int) -(x * factor)};
    }

}
