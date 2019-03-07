package com.qyw.newdemo.app.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.qyw.newdemo.R;

import java.io.Serializable;

public abstract class SlidingActivity extends AppCompatActivity {

    public static final String INTENTTAG = "intentTag";
    private boolean isBackExit;

    public Context context;
    private ImmersionBar mImmersionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置取消AppCompatActivity 的自带标题栏
/*        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);*/
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();   //所有子类都将继承这些相同的属性
        setContentView(getLayoutResId());//把设置布局文件的操作交给继承的子类
        context = this;
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
        overridePendingTransition(R.anim.anim_slide_in, R.anim.anim_slide_out);
    }

    /*
     * Activity的跳转-带参数
     */
    public void setIntentClass(Class<?> cla, Object obj) {
        Intent intent = new Intent();
        intent.setClass(this, cla);
        intent.putExtra(INTENTTAG, (Serializable) obj);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in, R.anim.anim_slide_out);
    }

    public void showToast(String text){

        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();
    }

    /*
     */
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
}
