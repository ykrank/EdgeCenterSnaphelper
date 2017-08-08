package com.github.ykrank.edgecentersnaphelper.demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.ykrank.edgecentersnaphelper.demo.databinding.ItemSnapHelper2Binding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ykrank on 2017/8/8.
 */

public class SnapHelperAdapter2 extends RecyclerView.Adapter<SnapHelperViewModel2> {
    private final LayoutInflater inflater;
    private List<String> data = new ArrayList<>();

    {
        for (int i = 0; i < 30; i++) {
            data.add("Text: " + i);
        }
    }

    SnapHelperAdapter2(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public SnapHelperViewModel2 onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SnapHelperViewModel2(ItemSnapHelper2Binding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(SnapHelperViewModel2 holder, int position) {
        if (holder != null) {
            holder.binding.text.setText(data.get(position));
            holder.binding.setPosition(position);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
