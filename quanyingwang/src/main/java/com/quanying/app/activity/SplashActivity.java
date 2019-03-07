package com.quanying.app.activity;

import com.quanying.app.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import cn.jpush.android.api.JPushInterface;

public class SplashActivity extends Activity {
	long SLEEP_TIME = 2000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_layout);
		final SharedPreferences p = getSharedPreferences("config", 0);
		if (p.getBoolean("firstInstall", true)) {
			p.edit().putBoolean("firstInstall", false).commit();

			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {

					Intent in = new Intent(SplashActivity.this, WelcomeActivity.class);
					startActivity(in);
					finish();

				}
			}, SLEEP_TIME);
		} else {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {

					Intent in = new Intent(SplashActivity.this, MainActivity.class);
					in.putExtra("URL", "http://m.7192.com/");
					startActivity(in);
					finish();

				}

			}, SLEEP_TIME);
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		JPushInterface.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		JPushInterface.onPause(this);
	}

}
