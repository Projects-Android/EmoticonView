package com.ev.library.view;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 方形ImageView
 * Created by EV on 2018/4/23.
 */
public class SquareImageView extends ImageView {
    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
