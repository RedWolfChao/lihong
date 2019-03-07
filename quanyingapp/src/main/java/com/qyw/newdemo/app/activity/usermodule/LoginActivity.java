package com.qyw.newdemo.app.activity.usermodule;

import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import com.qyw.newdemo.R;
import com.qyw.newdemo.app.activity.MainActivity;
import com.qyw.newdemo.app.base.SlidingActivity;
import com.qyw.newdemo.app.view.StateButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends SlidingActivity {

    @BindView(R.id.et_username)
    EditText et_Username;//用户名输入框
    @BindView(R.id.register_button)
    TextView register_Button;//注册按钮
    @BindView(R.id.forget_button)
    TextView forget_Button;//忘记密码
    @BindView(R.id.login_button)
    StateButton login_Button;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
    }

    //    注册
    @OnClick(R.id.register_button)
    public void Register() {
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }
    //    忘记密码
    @OnClick(R.id.forget_button)
    public void ForgetPassword() {
        startActivity(new Intent(LoginActivity.this,ForgetPwActivity.class));
    }
    //    登录
    @OnClick(R.id.login_button)
    public void Login() {
        startActivity(new Intent(context,MainActivity.class));
        finish();
    }




}
