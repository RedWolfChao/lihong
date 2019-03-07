package com.quanying.app.ui.home;

import android.os.Bundle;

import com.quanying.app.R;
import com.quanying.app.ui.base.BaseActivity;
import com.quanying.app.ui.fragment.HomeActivity;

import java.util.Timer;
import java.util.TimerTask;

public class FlashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_flash;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        Timer timer=new Timer();

        TimerTask task=new TimerTask()
        {
            @Override
            public void run(){
                //需要执行的代码
                setIntentClass(HomeActivity.class);
                finish();
            }
        };

        timer.schedule(task,3000);
    }
}
