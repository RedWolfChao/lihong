package com.quanying.app.ui.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.quanying.app.manager.ActivityManage;

import java.io.Serializable;

import butterknife.ButterKnife;

/*
 * Activity的基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static final String INTENTTAG = "intentTag";
	private boolean isBackExit;

	public Context context;
	private ImmersionBar mImmersionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置取消AppCompatActivity 的自带标题栏
		supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

		mImmersionBar = ImmersionBar.with(this);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		mImmersionBar.init();   //所有子类都将继承这些相同的属性

		setContentView(getLayoutResId());//把设置布局文件的操作交给继承的子类
		context = this;

		ButterKnife.bind(this);
		initView();
        initData();
		ActivityManage.getInstance().addActivity(this);
	}


	/**
	 * 返回当前Activity布局文件的id
	 *
	 * @return
	 */

	abstract protected int getLayoutResId();

	/*
	 * Activity的跳转
	 */
	public void setIntentClass(Class<?> cla) {
		Intent intent = new Intent();
		intent.setClass(this, cla);
		startActivity(intent);

	}

	/*
	 * Activity的跳转-带参数
	 */
	public void setIntentClass(Class<?> cla, Object obj) {
		Intent intent = new Intent();
		intent.setClass(this, cla);
		intent.putExtra(INTENTTAG, (Serializable) obj);
		startActivity(intent);
	}

	public void showToast(String text){

		Toast.makeText(this,text, Toast.LENGTH_SHORT).show();

	}


	/**
	 * @param title
	 * @param buttitle
	 */
	public void showBaseDialog(String title,String buttitle){



		AlertDialog dialog = new AlertDialog.Builder(this)
				.setMessage(title)//设置对话框的标题
				//设置对话框的按钮
				.setPositiveButton(buttitle, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).create();

		/*Button zbbutton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
		zbbutton.setTextColor(getResources().getColor(R.color.colorBackground));
*/

		dialog.show();

	}

	/**
	 * 获取edittext内容
	 * @param mEdit
	 * @return
	 */
	public String getEdit(EditText mEdit){

		return mEdit.getText().toString().trim();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mImmersionBar != null)
			mImmersionBar.destroy();
	}

/**
	 * [是否连续两次返回退出]
	 * 
	 *//*

	public void setBackExit(boolean isBackExit) {
		this.isBackExit = isBackEx	it;
	}

	private long exitTime;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if (isBackExit) {
				if ((System.currentTimeMillis() - exitTime) > 2000) {
					Toast.makeText(getApplicationContext(), "再按一次退出程序",
							Toast.LENGTH_SHORT).show();
					exitTime = System.currentTimeMillis();
				} else {
					System.exit(0);
				}
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
*/

	/**
	 * 初始化事件
	 */
	protected abstract void initData();

	/**
	 * 初始化视图
	 */
	protected abstract void initView();

}