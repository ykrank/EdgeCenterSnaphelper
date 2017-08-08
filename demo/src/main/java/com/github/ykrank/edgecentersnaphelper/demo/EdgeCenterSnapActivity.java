package com.github.ykrank.edgecentersnaphelper.demo;

import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.ykrank.edgecentersnaphelper.EdgeCenterSnapHelper;
import com.github.ykrank.edgecentersnaphelper.LinearCenterSnapItemScrolledListener;
import com.github.ykrank.edgecentersnaphelper.demo.databinding.ActivitySnapHelperBinding;

/**
 * Created by ykrank on 2017/8/8.
 */

public class EdgeCenterSnapActivity extends AppCompatActivity {
    private ActivitySnapHelperBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_snap_helper);

        binding.recycleView0.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recycleView0.setAdapter(new SnapHelperAdapter(this));

        binding.recycleView0.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(40, 0, 40, 0);
            }
        });
        EdgeCenterSnapHelper snapHelper = new EdgeCenterSnapHelper();
        snapHelper.attachToRecyclerView(binding.recycleView0);
        snapHelper.setItemScrolledListener(new LinearCenterSnapItemScrolledListener() {
            @Override
            public void onVisibleItemViewScrolled(View itemView, int distance) {
                float halfWidth = binding.recycleView0.getWidth() / 2.0F;
                float scale = 1 - Math.abs(distance) * 0.5F / halfWidth;
                itemView.setScaleX(scale);
                itemView.setScaleY(scale);
                L.d("Scale:" + scale);
            }
        });

        binding.recycleView1.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.HORIZONTAL, false));
        binding.recycleView1.setAdapter(new SnapHelperAdapter(this));
        EdgeCenterSnapHelper snapHelper1 = new EdgeCenterSnapHelper();
        snapHelper1.attachToRecyclerView(binding.recycleView1);
        snapHelper1.setItemScrolledListener(new LinearCenterSnapItemScrolledListener() {
            @Override
            public void onVisibleItemViewScrolled(View itemView, int distance) {
                float halfWidth = binding.recycleView0.getWidth() / 2.0F;
                float scale = 1 - Math.abs(distance) * 0.5F / halfWidth;
                itemView.setScaleX(scale);
                itemView.setScaleY(scale);
                L.d("Scale:" + scale);
            }
        });

        binding.recycleView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recycleView2.setAdapter(new SnapHelperAdapter2(this));
        binding.recycleView2.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(100, 100, 100, 100);
            }
        });
        EdgeCenterSnapHelper snapHelper2 = new EdgeCenterSnapHelper();
        snapHelper2.attachToRecyclerView(binding.recycleView2);
        snapHelper2.setItemScrolledListener(new LinearCenterSnapItemScrolledListener() {
            @Override
            public void onVisibleItemViewScrolled(View itemView, int distance) {
                float halfWidth = binding.recycleView0.getWidth() / 2.0F;
                float scale = 1 - Math.abs(distance) * 0.3F / halfWidth;
                itemView.setScaleX(scale);
                itemView.setScaleY(scale);
                Drawable drawable = itemView.getBackground().mutate();
                drawable.setAlpha((int) (255 * scale));
                itemView.setBackgroundDrawable(drawable);
                L.d("Scale:" + scale);
            }
        });
    }
}
