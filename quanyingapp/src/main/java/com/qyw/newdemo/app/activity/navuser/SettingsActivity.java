package com.qyw.newdemo.app.activity.navuser;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

import com.qyw.newdemo.R;
import com.qyw.newdemo.app.base.SlidingActivity;
import com.qyw.newdemo.app.view.TitleBar;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends SlidingActivity {

    @BindView(R.id.setting_item1)
    LinearLayout setting_item1;//账户与安全
    @BindView(R.id.titlebar_view)
    TitleBar mTitleBar;

    @BindString(R.string.setting_title)
    String title_Str;//sting


    private void initBindView() {
        mTitleBar.setLeftImageResource(R.mipmap.back_img);
        mTitleBar.setTitle(title_Str);
        mTitleBar.setTitleColor(Color.WHITE);
        mTitleBar.setLeftTextColor(Color.WHITE);
        // 获取颜色资源文件
//        int mycolor = getResources().getColor(R.color.colorBackground);

        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        initBindView();
    }

    //    登录
    @OnClick(R.id.setting_item1)
    public void AccountSafe() {
        startActivity(new Intent(context,AccountSafeActivity.class));
    }

}
