package com.qyw.newdemo.app.activity.usermodule;

import android.content.Intent;
import android.widget.Button;

import com.qyw.newdemo.R;
import com.qyw.newdemo.app.base.SlidingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends SlidingActivity {

    @BindView(R.id.registbtn_personage)
    Button registbtn_Personage;     //个人账号注册


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_regist;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
    }

    @OnClick(R.id.registbtn_personage)
    public void ForgetPassword() {
        startActivity(new Intent(RegisterActivity.this,PersonageRegisterActivity.class));
    }

}
