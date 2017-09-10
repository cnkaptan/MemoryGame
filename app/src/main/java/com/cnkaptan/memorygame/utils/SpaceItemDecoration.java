package com.cnkaptan.memorygame.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by cnkaptan on 10/09/2017.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration{
    private final int space;

    public SpaceItemDecoration(int verticalSpaceHeight) {
        this.space = verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.bottom = space;
        outRect.right = space;
    }
}
