package com.qyw.newdemo.app.activity.navuser;

import android.content.Intent;
import android.widget.LinearLayout;

import com.qyw.newdemo.R;
import com.qyw.newdemo.app.base.SlidingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountSafeActivity extends SlidingActivity {

    @BindView(R.id.changepwd_button)
    LinearLayout changepwd_Button;  //修改密码
    @BindView(R.id.changupdatepnub_button)
    LinearLayout changupdatepnub_Button;  //  更换手机号

    
    @OnClick(R.id.changepwd_button)
    public void changePwd(){
        startActivity(new Intent(context,ChangePasswordActivity.class));
    }
    @OnClick(R.id.changupdatepnub_button)
    public void updatePhoneNumber(){
        startActivity(new Intent(context,BindPhoneActivity.class));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_accountsafe;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
    }

}
