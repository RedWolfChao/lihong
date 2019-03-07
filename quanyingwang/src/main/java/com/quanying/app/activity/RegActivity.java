package com.quanying.app.activity;

import com.quanying.app.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class RegActivity extends Activity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg_layout);
		findViewById(R.id.bt_reg).setOnClickListener(this);
		findViewById(R.id.ll_reg_login).setOnClickListener(this);
		findViewById(R.id.iv_title_share).setOnClickListener(this);
		findViewById(R.id.iv_title_back).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_title_back:
			finish();
			break;
		case R.id.iv_title_share:
			break;
		case R.id.bt_reg:
			System.out.println("login");
			break;
		case R.id.ll_reg_login:
			finish();
			break;

		}
	}

}
