package com.github.ykrank.edgecentersnaphelper;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class EdgeCenterSnapHelper extends LinearSnapHelper {
    protected RecyclerView mRecyclerView;
    protected LinearLayoutManager mLayoutManager;
    // Visible item in recycleView
    private List<View> visibleItems = new ArrayList<>();

    // Orientation helpers are lazily created per LayoutManager.
    protected OrientationHelper helper;

    private LinearCenterSnapItemScrolledListener mItemScrolledListener;

    private int spanCount = 1;

    //Visible item callback when recycleView scroll
    private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        private int oldDx = Integer.MAX_VALUE;
        private int oldDy = Integer.MAX_VALUE;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                calculateVisibleItemViewPosition();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (oldDx != dx || oldDy != dy) {
                calculateVisibleItemViewPosition();
                oldDx = dx;
                oldDy = dy;
            }
        }
    };

    //Update #visibleItems when item attached or detached
    private RecyclerView.OnChildAttachStateChangeListener mChildStateChangeListener = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(View view) {
            visibleItems.add(view);
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {
            visibleItems.remove(view);
        }
    };

    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) throws IllegalStateException {
        if (mRecyclerView == recyclerView) {
            return;  // nothing to do
        }
        if (mRecyclerView != null) {
            destroyCallbacks(mRecyclerView);
        }

        super.attachToRecyclerView(recyclerView);

        mRecyclerView = recyclerView;

        if (mRecyclerView != null) {
            setupCallbacks(mRecyclerView);
        }
    }

    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        int[] out = new int[2];

        int distance = distanceToCenter(layoutManager, targetView, helper);
        if (layoutManager.canScrollHorizontally()) {
            out[0] = distance;
        } else {
            out[0] = 0;
        }

        if (layoutManager.canScrollVertically()) {
            out[1] = distance;
        } else {
            out[1] = 0;
        }
        return out;
    }

    private void calculateVisibleItemViewPosition() {
        if (mLayoutManager == null || helper == null) {
            return;
        }
        if (mItemScrolledListener != null) {
            for (View itemView : visibleItems) {
                mItemScrolledListener.onVisibleItemViewScrolled(itemView, distanceToCenter(mLayoutManager, itemView, helper));
            }
        }
    }

    /**
     * Called when an instance of a [RecyclerView] is attached.
     */
    private void setupCallbacks(RecyclerView recyclerView) throws IllegalStateException {
        if (!(recyclerView.getLayoutManager() instanceof LinearLayoutManager)) {
            throw new IllegalStateException("An instance of LinearLayoutManager should be set.");
        }
        final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else {
            spanCount = 1;
        }

        mLayoutManager = layoutManager;

        helper = OrientationHelper.createOrientationHelper(layoutManager, layoutManager.getOrientation());

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (helper != null) {
                    outRect.set(itemOffsets(layoutManager, view, helper));
                }
            }
        });
        recyclerView.addOnScrollListener(mScrollListener);
        recyclerView.addOnChildAttachStateChangeListener(mChildStateChangeListener);
    }

    /**
     * Called when the instance of a [RecyclerView] is detached.
     */
    private void destroyCallbacks(RecyclerView recyclerView) {
        recyclerView.removeOnScrollListener(mScrollListener);
        recyclerView.removeOnChildAttachStateChangeListener(mChildStateChangeListener);
        visibleItems.clear();
    }

    /**
     * Item offsets rect
     */
    public Rect itemOffsets(RecyclerView.LayoutManager layoutManager, View targetView,
                            OrientationHelper helper) {
        Rect outRect = new Rect(0, 0, 0, 0);

        int position = ((RecyclerView.LayoutParams) (targetView.getLayoutParams())).getViewLayoutPosition();

        if (layoutManager.canScrollHorizontally()) {
            if (itemStartSide(layoutManager, position)) {
                outRect.set(distanceBothEnds(layoutManager, targetView, helper), 0, 0, 0);
            } else if (itemEndSide(layoutManager, position)) {
                outRect.set(0, 0, distanceBothEnds(layoutManager, targetView, helper), 0);
            }
        } else if (layoutManager.canScrollVertically()) {
            if (itemStartSide(layoutManager, position)) {
                outRect.set(0, distanceBothEnds(layoutManager, targetView, helper), 0, 0);
            } else if (itemEndSide(layoutManager, position)) {
                outRect.set(0, 0, 0, distanceBothEnds(layoutManager, targetView, helper));
            }
        }

        return outRect;
    }

    /**
     * Is item at start position
     */
    boolean itemStartSide(RecyclerView.LayoutManager layoutManager, int position) {
        return position / spanCount == 0;
    }

    /**
     * Is item at end position
     */
    boolean itemEndSide(RecyclerView.LayoutManager layoutManager, int position) {
        return position / spanCount == (layoutManager.getItemCount() - 1) / spanCount;
    }

    /**
     * Item in start or end offsets to container when show center
     */
    int distanceBothEnds(RecyclerView.LayoutManager layoutManager,
                         View targetView, OrientationHelper helper) {
        Boolean firstMeasure = (Boolean) targetView.getTag(R.id.view_first_measure);
        if (firstMeasure == null) {
            //Not measured
            targetView.setTag(R.id.view_first_measure, true);
            checkMeasureChild(layoutManager, targetView);
            targetView.setTag(R.id.view_first_measure, false);
        } else if (firstMeasure) {
            //First measure
            targetView.setTag(R.id.view_first_measure, false);
            return 0;
        }
        return containerCenter(layoutManager, helper) - helper.getDecoratedMeasurement(targetView) / 2;
    }

    private int containerCenter(RecyclerView.LayoutManager layoutManager, OrientationHelper helper) {
        int containerCenter;
        if (layoutManager.getClipToPadding()) {
            containerCenter = helper.getStartAfterPadding() + helper.getTotalSpace() / 2;
        } else {
            containerCenter = helper.getEnd() / 2;
        }
        return containerCenter;
    }

    private int distanceToCenter(RecyclerView.LayoutManager layoutManager,
                                 View targetView, OrientationHelper helper) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) targetView.getLayoutParams();
        if (layoutManager.canScrollHorizontally()) {
            return (targetView.getLeft() - params.leftMargin + targetView.getRight() + params.rightMargin) / 2 - containerCenter(layoutManager, helper);
        } else {
            return (targetView.getTop() - params.topMargin + targetView.getBottom() + params.bottomMargin) / 2 - containerCenter(layoutManager, helper);
        }
    }

    /**
     * Measure item before recycleView measure it in RecyclerView.LayoutManager#measureChildWithMargins
     */
    private void checkMeasureChild(RecyclerView.LayoutManager layoutManager, View child) {
        if (child.getMeasuredHeight() != 0 || child.getMeasuredWidth() != 0) {
            return;
        }

        //This method call #checkMeasureChild, should avoid infinite loops call
        layoutManager.measureChildWithMargins(child, 0, 0);
    }


    public LinearCenterSnapItemScrolledListener getItemScrolledListener() {
        return mItemScrolledListener;
    }

    public void setItemScrolledListener(LinearCenterSnapItemScrolledListener mItemScrolledListener) {
        this.mItemScrolledListener = mItemScrolledListener;
    }
}
