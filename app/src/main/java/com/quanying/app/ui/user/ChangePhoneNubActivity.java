package com.quanying.app.ui.user;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quanying.app.R;
import com.quanying.app.app.MyApplication;
import com.quanying.app.ui.base.BaseActivity;
import com.quanying.app.utils.AppUtils;
import com.quanying.app.utils.ButtonUtils;
import com.quanying.app.weburl.WebUri;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class ChangePhoneNubActivity extends BaseActivity {


    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;

    @BindView(R.id.change_phone)
    EditText change_phone;
    @BindView(R.id.change_ercode)
    EditText change_ercode;
    @BindView(R.id.getcode_btn)
    TextView getcode_btn;
    @BindView(R.id.change_finish)
    LinearLayout change_finish;
    @BindView(R.id.getcode_ll)
    LinearLayout getcode_ll;
    private String codeId;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_change_phone_nub;
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
    }

    @OnClick(R.id.change_finish)
    public void submitMsg(){

//        BINDPHONEMSG
        if (!ButtonUtils.isFastDoubleClick(R.id.finish_btn)) {

            final String phoneNub = getEdit(change_phone);
            final String code = getEdit(change_ercode);

            if(phoneNub.length()>5) {

                OkHttpUtils
                        .post()
                        .url(WebUri.BINDPHONEMSG)
                        .addParams("token", MyApplication.getToken())
                        .addParams("mobile", phoneNub)
                        .addParams("codeid", codeId)
                        .addParams("code", code)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {

                                Log.e("bindphone", response);

                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    String errCode = jsonObject.getString("errcode");
                                    if (errCode.equals("200")) {
                                        showBaseDialog("绑定成功", "好");
                                    } else {
                                        AppUtils.stopTimer();
                                        showBaseDialog(jsonObject.getString("errmsg"), "好");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        });
            }
        }

    }


    @OnClick(R.id.getcode_ll)
    public void doGetCode(){

        if (!ButtonUtils.isFastDoubleClick(R.id.finish_btn)) {

            final String phoneNub = getEdit(change_phone);
            if(phoneNub.length()>5){
                AppUtils.startCountdown(getcode_btn,getcode_ll);
                OkHttpUtils
                        .post()
                        .url(WebUri.BINDPHONE)
                        .addParams("token", MyApplication.getToken())
                        .addParams("mobile", phoneNub)
                        .build()
                        .execute(new StringCallback(){
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {

                                Log.e("bindphone",response);

                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    String errCode = jsonObject.getString("errcode");
                                    if(errCode.equals("200")){
                                        codeId = jsonObject.getString("codeid");
                                    }else{
                                        AppUtils.stopTimer();
                                        showBaseDialog(jsonObject.getString("errmsg"),"好");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        });

            }else{
                showBaseDialog("请填写正确的手机号码!","好");
            }
        }
    }

}
