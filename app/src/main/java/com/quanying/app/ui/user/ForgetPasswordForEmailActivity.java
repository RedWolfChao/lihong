package com.quanying.app.ui.user;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.quanying.app.R;
import com.quanying.app.ui.base.BaseActivity;
import com.quanying.app.weburl.WebUri;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import okhttp3.Call;

public class ForgetPasswordForEmailActivity extends BaseActivity {

    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;
    @BindView(R.id.finish_btn)
    LinearLayout finish_btn;
    @BindView(R.id.forget_tel)
    EditText  forget_tel;
    @BindView(R.id.forget_newpass)
    EditText  forget_newpass;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_forget_password_for_email;
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

        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getPassWordForEMS();

            }
        });

    }
    @Override
    protected void initData() {

    }

    public void getPassWordForEMS() {

        String username = forget_tel.getText().toString().trim();
        String ems = forget_newpass.getText().toString().trim();



        if(TextUtils.isEmpty(username)||TextUtils.isEmpty(ems)){

            showBaseDialog("请检查输入","好");
            return;
        }

        OkHttpUtils
                .post()
                .url(WebUri.GETPASSFOREMS)
                .addParams("username",username)
                .addParams("email",ems)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("errcode").equals("200")){
                                showBaseDialog(jsonObject.getString("msg"),"好");
                            }else{
                                showBaseDialog(jsonObject.getString("msg"),"好");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}