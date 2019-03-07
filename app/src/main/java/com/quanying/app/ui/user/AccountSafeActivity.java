package com.quanying.app.ui.user;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quanying.app.R;
import com.quanying.app.app.MyApplication;
import com.quanying.app.ui.base.BaseActivity;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import butterknife.BindView;

public class AccountSafeActivity extends BaseActivity {

    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;
    @BindView(R.id.qy_nub_text)
    TextView qy_nub_text;
    @BindView(R.id.qy_phone_text)
    TextView qy_phone_text;
    @BindView(R.id.changupdatepnub_button)
    LinearLayout changupdatepnub_button;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_account_safe;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

        titlebar.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    finish();
                }
            }
        });

        qy_nub_text.setText(MyApplication.getUserCode());
        qy_phone_text.setText(MyApplication.getUserPhone());

        changupdatepnub_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setIntentClass(ChangePhoneNubActivity.class);

            }
        });

    }
}
