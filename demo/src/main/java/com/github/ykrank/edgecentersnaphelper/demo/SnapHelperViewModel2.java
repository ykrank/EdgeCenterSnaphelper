package com.github.ykrank.edgecentersnaphelper.demo;

import android.support.v7.widget.RecyclerView;

import com.github.ykrank.edgecentersnaphelper.demo.databinding.ItemSnapHelper2Binding;
import com.github.ykrank.edgecentersnaphelper.demo.databinding.ItemSnapHelperBinding;

/**
 * Created by ykrank on 2017/8/8.
 */

public class SnapHelperViewModel2 extends RecyclerView.ViewHolder {
    final ItemSnapHelper2Binding binding;

    public SnapHelperViewModel2(ItemSnapHelper2Binding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
