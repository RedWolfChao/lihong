package com.quanying.app.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class Myview extends ImageView {
	private float mDownX, mDownY;
	private float mMoveX, mMoveY;

	//在代码里实例化
	public Myview(Context context) {
		this(context, null);
	}

	//在布局里声明，并且用到自定义属性的时候调用
	public Myview(Context context, AttributeSet attrs) {
		this(context, attrs, 0);

	}

	//在布局里声明，并且用到自定义样式的时候调用
	public Myview(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	/*@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);
	}*/

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
		int childWidthSize = getMeasuredWidth();
		//高度和宽度一样
		heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}


