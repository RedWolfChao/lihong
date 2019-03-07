package com.quanying.app.view;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created By RedWolf on 2018/9/22 10:07
 * SquareImageView.java
 * 正方形View  由于是按照Width分的比例 所以重写onMeasure
 * //   imageView中的layout_height 就没有用了
 */
public class SquareImageView extends AppCompatImageView {

    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //  只用宽度就是了
        //  同理 如果按照高度分比例 就是
        //  super.onMeasure(heightMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
