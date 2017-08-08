package com.github.ykrank.edgecentersnaphelper;

import android.view.View;

/**
 * Callback when visible recyclerView item scrolled
 */
public interface LinearCenterSnapItemScrolledListener {
    /**
     * Visible item scroll callback
     *
     * @param itemView target item view
     * @param distance item view center - recycleView center in scroll orientation
     */
    void onVisibleItemViewScrolled(View itemView, int distance);
}
