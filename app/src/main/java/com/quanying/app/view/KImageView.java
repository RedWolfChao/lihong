package com.quanying.app.view;

import android.content.Context;

public class KImageView extends android.support.v7.widget.AppCompatImageView {

    public KImageView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
