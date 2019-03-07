package com.quanying.app.utils;

import com.quanying.app.R;
import com.quanying.app.R.id;
import com.quanying.app.R.layout;
import com.quanying.app.R.style;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

public class MyProgressDialog extends Dialog {
	private Context context;
	private String message = "正在加载，请稍候...";

	public MyProgressDialog(Context context) {
		super(context, R.style.MyDialogStyle);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.setContentView(R.layout.progress_dialog);
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void updateMessage(String msg) {
		this.message = msg;
		((Activity) context).runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				((TextView) findViewById(R.id.msg)).setText(message);
			}
		});
	}

	public MyProgressDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		TextView msg = (TextView) findViewById(R.id.msg);
		msg.setText(message);
	}
}
