package com.duguang.baseanimation.ui.tab.paralloid.library1.measure;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by chris on 25/10/2013
 * Project: Paralloid
 */
public class AbsListScrollSize extends ViewScrollSize<AbsListView> {


    public AbsListScrollSize(AbsListView viewToSize) {
        super(viewToSize);
    }

    @Override
    public int getMaxScrollY() {
        return calculateApproximateHeight(viewToSize);
    }

    /**
     * This method is by no means accurate, and Will only work to any degree of accuracy if your list items
     * are the same height.
     * Otherwise it becomes vastly more difficult to calculate the correct height.
     *
     * @param listView listView to get height of, if no adapter is attached then nothing will happen.
     * @return 0 for failure.
     */
    public static int calculateApproximateHeight(AbsListView listView) {
        final ListAdapter adapter = listView.getAdapter();
        int onScreenHeight = 0, totalHeight = 0;
        final int totalCount = adapter.getCount();
        final int visibleCount = listView.getLastVisiblePosition() - listView.getFirstVisiblePosition();
        if (totalCount > 0) {
            View view;
            for (int i = 0; i < visibleCount; i++) {
//                final View view = adapter.getView(0, null, listView);
                view = listView.getChildAt(i);
//                view.measure(
//                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                onScreenHeight += view.getMeasuredHeight();
            }
            // Get the average on screen height, then multiply it up.
            totalHeight = (onScreenHeight / visibleCount) * totalCount;
            // Add the divider height.
            if (listView instanceof ListView) {
                totalHeight += ((ListView) listView).getDividerHeight() * (totalCount - 1);
            }
        }
        return totalHeight;
    }

    /**
     * Call from an AbsListView.OnScrollListener to calculate the incremental offset (change in scroll offset
     * since the last calculation).
     *
     * @return The  offset, or 0 if it wasn't possible to calculate the offset.
     */
    public static int calculateOffset(final AbsListView listView) {
        final View c = listView.getChildAt(0);
        if (c == null) return 0;
        return -c.getTop() + listView.getFirstVisiblePosition() * c.getHeight();
    }

}
