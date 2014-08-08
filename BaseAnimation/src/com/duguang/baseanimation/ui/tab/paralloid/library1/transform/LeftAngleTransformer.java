package com.duguang.baseanimation.ui.tab.paralloid.library1.transform;

/**
 * Put simply turns up into left, left into down, down into right, right into up.
 *
 * Created by chris on 23/10/2013
 * Project: Paralloid
 */
public class LeftAngleTransformer implements Transformer {

    @Override
    public int[] scroll(float x, float y, float factor) {
        return new int[]{(int) (y * factor), (int) (x * factor)};
    }

}
