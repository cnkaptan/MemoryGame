package com.cnkaptan.memorygame.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by cnkaptan on 31/08/2017.
 */

public class SquareView extends AppCompatImageView {
    public SquareView(Context context) {
        this(context,null);
    }

    public SquareView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SquareView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = width > height ? width : height;
        setMeasuredDimension(size, size);
    }
}
