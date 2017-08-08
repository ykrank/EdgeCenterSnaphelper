package com.github.ykrank.edgecentersnaphelper.demo;

import android.support.v7.widget.RecyclerView;

import com.github.ykrank.edgecentersnaphelper.demo.databinding.ItemSnapHelperBinding;

/**
 * Created by ykrank on 2017/8/8.
 */

public class SnapHelperViewModel extends RecyclerView.ViewHolder {
    final ItemSnapHelperBinding binding;

    public SnapHelperViewModel(ItemSnapHelperBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
