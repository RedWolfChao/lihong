package com.qyw.newdemo.app.activity;

import com.qyw.newdemo.R;
import com.qyw.newdemo.app.base.SlidingActivity;

public class NormalActivity extends SlidingActivity {

    @Override
    protected int getLayoutResId() {
        //onCreate的方法中不需要写setContentView(),直接把当前activity的布局文件在这里返回就行了！
        return R.layout.activity_normal;
    }

    @Override
    protected void initData() {

    }
}
