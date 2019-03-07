package com.quanying.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.quanying.app.R;

public class WelcomeActivity extends Activity {
	ViewPager viewPager;
	ImageView[] ivPages = { null, null, null };
	Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_layout);

		viewPager = (ViewPager) findViewById(R.id.vp_welcome);
		ImageView iv1 = new ImageView(this);
		iv1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		iv1.setScaleType(ScaleType.FIT_XY);
		iv1.setImageResource(R.drawable.welcome1);
		ivPages[0] = iv1;
		ImageView iv2 = new ImageView(this);
		iv2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		iv2.setScaleType(ScaleType.FIT_XY);
		iv2.setImageResource(R.drawable.welcome2);
		ivPages[1] = iv2;
		ImageView iv3 = new ImageView(this);
		iv3.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		iv3.setScaleType(ScaleType.FIT_XY);
		iv3.setImageResource(R.drawable.welcome3);
		ivPages[2] = iv3;
		viewPager.setAdapter(adapter);
		iv3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent in = new Intent(WelcomeActivity.this, MainActivity.class);
				in.putExtra("URL", "http://m.7192.com/");
				startActivity(in);
				finish();
			}
		});
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				handler.removeMessages(1);
				handler.sendEmptyMessageDelayed(1, 2500);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if (msg.what == 1) {
					int index = viewPager.getCurrentItem();
					if (index < 2) {
						viewPager.setCurrentItem(index + 1);
					} else {
						Intent in = new Intent(WelcomeActivity.this, MainActivity.class);
						in.putExtra("URL", "http://m.7192.com/");
						startActivity(in);
						finish();
					}
				}
			}

		};
		handler.sendEmptyMessageDelayed(1, 2500);
	}

	PagerAdapter adapter = new PagerAdapter() {

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 3;
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(ivPages[arg1]);
			return ivPages[arg1];
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(ivPages[arg1]);
		}
	};
}
