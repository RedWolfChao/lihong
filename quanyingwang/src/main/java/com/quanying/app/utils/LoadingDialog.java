package com.quanying.app.utils;

import com.ant.liao.GifView;
import com.ant.liao.GifView.GifImageType;
import com.quanying.app.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class LoadingDialog extends Dialog {
	private Context context;
	// private String message = "正在加载，请稍候...";

	public LoadingDialog(Context context) {
		super(context, R.style.MyDialogStyle);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.setContentView(R.layout.loading);

		GifView gf1 = (GifView) findViewById(R.id.gif);
		// 设置Gif图片源
		gf1.setGifImage(R.drawable.loading_alpha);
		// 添加监听器
		// gf1.setOnClickListener(this);
		// 设置显示的大小，拉伸或者压缩
		gf1.setShowDimension(dp2px(context, 100), dp2px(context, 100));
		// 设置加载方式：先加载后显示、边加载边显示、只显示第一帧再显示
		gf1.setGifImageType(GifImageType.COVER);
	}

	public static int dp2px(Context c, float dp) {
		final float scale = c.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	public LoadingDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
}
