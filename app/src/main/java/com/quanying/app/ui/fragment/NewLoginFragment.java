package com.quanying.app.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quanying.app.R;
import com.quanying.app.app.MyApplication;
import com.quanying.app.event.MessageEvent;
import com.quanying.app.ui.base.BaseFragment;
import com.quanying.app.ui.user.BindPhoneForTXActivity;
import com.quanying.app.ui.user.ForgetPasswordActivity;
import com.quanying.app.ui.user.LoginActivity;
import com.quanying.app.ui.user.RegistActivity;
import com.quanying.app.utils.AppSharePreferenceMgr;
import com.quanying.app.utils.ButtonUtils;
import com.quanying.app.weburl.WebUri;
import com.tsy.sdk.social.PlatformType;
import com.tsy.sdk.social.SocialApi;
import com.tsy.sdk.social.listener.AuthListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * create by kevin on 2019/3/7.
 * ░░░░░░░░░░░░░░░░░░░░░░░░▄░░
 * ░░░░░░░░░▐█░░░░░░░░░░░▄▀▒▌░
 * ░░░░░░░░▐▀▒█░░░░░░░░▄▀▒▒▒▐
 * ░░░░░░░▐▄▀▒▒▀▀▀▀▄▄▄▀▒▒▒▒▒▐
 * ░░░░░▄▄▀▒░▒▒▒▒▒▒▒▒▒█▒▒▄█▒▐
 * ░░░▄▀▒▒▒░░░▒▒▒░░░▒▒▒▀██▀▒▌
 * ░░▐▒▒▒▄▄▒▒▒▒░░░▒▒▒▒▒▒▒▀▄▒▒
 * ░░▌░░▌█▀▒▒▒▒▒▄▀█▄▒▒▒▒▒▒▒█▒▐
 * ░▐░░░▒▒▒▒▒▒▒▒▌██▀▒▒░░░▒▒▒▀▄
 * ░▌░▒▄██▄▒▒▒▒▒▒▒▒▒░░░░░░▒▒▒▒
 * ▀▒▀▐▄█▄█▌▄░▀▒▒░░░░░░░░░░▒▒▒
 * <p>
 * 写这段代码的时候，只有上帝和我知道它是干嘛的
 * 现在，只有上帝知道
 */
public class NewLoginFragment extends BaseFragment {


    @BindView(R.id.forget_button)
    TextView forget_button ;

    @BindView(R.id.et_username)
    EditText etUserName ;
    @BindView(R.id.userdel)
    ImageView userdel ;
    @BindView(R.id.et_password)
    EditText etPassWord ;
    @BindView(R.id.passdel)
    ImageView passdel ;
    @BindView(R.id.wx_login)
    ImageView wx_login ;
    @BindView(R.id.qq_login)
    ImageView qq_login ;
    @BindView(R.id.login_btn)
    LinearLayout login_btn;

    private final static int REQUESTCODE = 1; // 返回的结果码
    private SocialApi mSocialApi;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.f_login;
    }

    @Override
    protected void initView() {
        mSocialApi = SocialApi.get(MyApplication.context);
        etUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && userdel.getVisibility() == View.GONE) {
                    userdel.setVisibility(View.VISIBLE);
                    etUserName.setCursorVisible(true);
                } else if (TextUtils.isEmpty(s)) {
                    userdel.setVisibility(View.GONE);
                    etUserName.setCursorVisible(false);
                }
            }
        });
        userdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etUserName.setText(null);
            }
        });


        etPassWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && passdel.getVisibility() == View.GONE) {
                    passdel.setVisibility(View.VISIBLE);
                    etPassWord.setCursorVisible(true);
                } else if (TextUtils.isEmpty(s)) {
                    passdel.setVisibility(View.GONE);
                    etPassWord.setCursorVisible(false);
                }
            }
        });

        passdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etPassWord.setText(null);
            }
        });

    }

    /*
     * 忘记密码
     * */
    @OnClick(R.id.forget_button)
    public void jumpForgetPassword(){
        setIntentClass(ForgetPasswordActivity.class);
    }


    /*
     * 微信登录
     * */
    @OnClick(R.id.wx_login)
    public void wxLogin(){

        mSocialApi.doOauthVerify(getActivity(), PlatformType.WEIXIN, new MyAuthListener());
//        setIntentClass(RegistActivity.class);
    }
    /*
     * QQ登录
     * */
    @OnClick(R.id.qq_login)
    public void qqLogin(){

        mSocialApi.doOauthVerify(getActivity(), PlatformType.QQ, new MyAuthListener());

    }

    public class MyAuthListener implements AuthListener {
        @Override
        public void onComplete(PlatformType platform_type, Map<String, String> map) {
//            Toast.makeText(context, platform_type + " login onComplete", Toast.LENGTH_SHORT).show();
            Log.i("tsy", "login onComplete:" + map.toString());
            if(platform_type.equals(PlatformType.WEIXIN)){

//                微信登录逻辑处理
                Log.e("tsy", "wx" + map.toString());

                OkHttpUtils
                        .get()
                        .url("https://api.weixin.qq.com/sns/oauth2/access_token?appid="+MyApplication.WX_APPID+"&secret="+MyApplication.WX_SECRET+"&code="+map.get("code")+"&grant_type=authorization_code")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {

                                Log.e("wxresp",response);
                                doCheckWxLogin(response);
                            }
                        });

            }

            if(platform_type.equals(PlatformType.QQ)){
                Log.e("tsy", "QQ" + map.toString());

                doCheckQQLogin(map.get("openid"));

            }

        }

        @Override
        public void onError(PlatformType platform_type, String err_msg) {
//            Toast.makeText(context, platform_type + " login onError:" + err_msg, Toast.LENGTH_SHORT).show();
            Log.i("tsy", "login onError:" + err_msg);

        }

        @Override
        public void onCancel(PlatformType platform_type) {
//            Toast.makeText(context, platform_type + " login onCancel", Toast.LENGTH_SHORT).show();
            Log.i("tsy", "login onCancel");
        }
    }

    private void doCheckQQLogin(final String openid) {

        OkHttpUtils
                .post()
                .url(WebUri.QQLOGIN)
                .addParams("openid",openid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Log.e("dowxres",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("errcode").equals("40001")){

                                Intent intent = new Intent(getActivity(),BindPhoneForTXActivity.class);
                                intent.putExtra("openid",""+openid);
                                intent.putExtra("unionid","");
                                startActivity(intent);

                            }else  if(jsonObject.getString("errcode").equals("200")){

                                String token = jsonObject.getString("access_token");
                                AppSharePreferenceMgr.put(getActivity(),"token",token);
                                setIntentClass(HomeActivity.class);
                                EventBus.getDefault().post(new MessageEvent("","5"));
                                EventBus.getDefault().post(new MessageEvent("shuaxinrcw"));
                                getActivity().finish();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });


    }

    public void doCheckWxLogin(final String res){

        try {
            JSONObject jsonObject = new JSONObject(res);
            final String openid = jsonObject.getString("openid");
            final String unionid = jsonObject.getString("unionid");

            OkHttpUtils
                    .post()
                    .url(WebUri.WXLOGIN)
                    .addParams("openid",openid)
                    .addParams("unionid",unionid)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {

                            Log.e("dowxres",response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if(jsonObject.getString("errcode").equals("40001")){

                                    Intent intent = new Intent(getActivity(),BindPhoneForTXActivity.class);
                                    intent.putExtra("openid",""+openid);
                                    intent.putExtra("unionid",""+unionid);
                                    startActivity(intent);

                                }else  if(jsonObject.getString("errcode").equals("200")){

                                    String token = jsonObject.getString("access_token");
                                    AppSharePreferenceMgr.put(getActivity(),"token",token);
//                                    setIntentClass(HomeActivity.class);
                                    MyApplication.BACKURL ="reloadurl";
                                    EventBus.getDefault().post(new MessageEvent("shuaxinrcw"));
                                    EventBus.getDefault().post(new MessageEvent("","5"));
                                    getActivity().finish();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    });


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /*
     * 登录
     * */
    @OnClick(R.id.login_btn)
    public void doLogin(){

        if (!ButtonUtils.isFastDoubleClick(R.id.login_btn)) {

            final String userName = etUserName.getText().toString();
            String passWord = etPassWord.getText().toString();

            if(TextUtils.isEmpty(userName)||TextUtils.isEmpty(passWord)){
                Toast.makeText(getActivity(), "账号或密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            Log.d("yh","username"+userName+"pass"+passWord);
            OkHttpUtils
                    .post()
                    .url(WebUri.LOGIN)
                    .addParams("uname", userName)
                    .addParams("password", passWord)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {

                            Log.e("login",""+response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String errCode = jsonObject.getString("errcode");
                                if(errCode.equals("200")){

                                    String token = jsonObject.getString("access_token");
                                    AppSharePreferenceMgr.put(getActivity(),"token",token);
//                                    setIntentClass(HomeActivity.class);
                                    MyApplication.BACKURL ="reloadurl";
                                    EventBus.getDefault().post(new MessageEvent("shuaxinrcw"));
                                    EventBus.getDefault().post(new MessageEvent("","5"));
                                    getActivity().finish();

                                }else if(errCode.equals("40002")){

                                    AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();//创建对话框
                                    dialog.setCanceledOnTouchOutside(false);
                                    dialog.setTitle("提示");//设置对话框标题
                                    dialog.setMessage("密码错误，请输入正确的密码，如果账号已绑定手机号可用手机号重置密码并登录!");//设置文字显示内容
                                    //分别设置三个button
                                    dialog.setButton(DialogInterface.BUTTON_POSITIVE,"重置密码并登录", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();//关闭对话框
                                            if(isNumeric(userName)){
                                                Intent intent = new Intent(getActivity(),ForgetPasswordActivity.class);
                                                intent.putExtra("intentTag",userName);
                                                getActivity().setIntent(intent);

                                            }
                                                else{
                                                Intent intent = new Intent(getActivity(),ForgetPasswordActivity.class);

                                                getActivity().setIntent(intent);

                                            }
                                        }
                                    });
                                    dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "重新输入密码", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();//关闭对话框
                                        }
                                    });
                                    dialog.show();//显示对话框

                                }else if(errCode.equals("40001")){
                                    etPassWord.setText("");
                                    AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();//创建对话框

                                    dialog.setTitle("提示");//设置对话框标题
                                    dialog.setMessage("账号不存在，请输入正确的账号，如果没有账号可立即注册! ");//设置文字显示内容
                                    dialog.setCanceledOnTouchOutside(false);
                                    //分别设置三个button
                                    dialog.setButton(DialogInterface.BUTTON_POSITIVE,"重新输入账号", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();//关闭对话框
                                        }
                                    });
                                    dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "立即注册", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();//关闭对话框
                                            setIntentClass(RegistActivity.class);
                                        }
                                    });
                                    dialog.show();//显示对话框

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });

        }

    }

    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() )
        {       return false;    }
        return true;
    }


    @Override
    protected void initData() {

    }
}
