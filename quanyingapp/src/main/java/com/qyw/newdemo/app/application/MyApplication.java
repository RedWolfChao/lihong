package com.qyw.newdemo.app.application;

import android.app.Application;

/**
 * Created by Administrator on 2017/8/2 0002.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //崩溃处理
       /* CrashHandlerUtil crashHandlerUtil = CrashHandlerUtil.getInstance();
        crashHandlerUtil.init(this);
        crashHandlerUtil.setCrashTip("很抱歉，程序出现异常，即将退出！");*/
    }

}
