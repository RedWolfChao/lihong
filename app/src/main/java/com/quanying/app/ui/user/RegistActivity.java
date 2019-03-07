package com.quanying.app.ui.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quanying.app.R;
import com.quanying.app.ui.base.BaseActivity;
import com.quanying.app.ui.fragment.HomeActivity;
import com.quanying.app.utils.AppSharePreferenceMgr;
import com.quanying.app.utils.AppUtils;
import com.quanying.app.utils.ButtonUtils;
import com.quanying.app.utils.SoftKeyBoardListener;
import com.quanying.app.weburl.WebUri;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class RegistActivity extends BaseActivity {

    @BindView(R.id.view)
    View view;

    @BindView(R.id.register_viewgroup)
    LinearLayout register_viewgroup;
    @BindView(R.id.getcode_btn)
    LinearLayout getcode_btn;
    @BindView(R.id.getcode_text)
    TextView getcode_text;
    @BindView(R.id.finish_btn)
    LinearLayout finish_btn;
    @BindView(R.id.register_instructions)
    LinearLayout register_instructions;
    @BindView(R.id.forget_tel)
    EditText forget_tel;
    @BindView(R.id.forget_code)
    EditText forget_code;
    @BindView(R.id.forget_newpass)
    EditText forget_newpass;
    @BindView(R.id.sytk_btn)
    TextView sytk_btn;
    @BindView(R.id.yszc_btn)
    TextView yszc_btn;
    private final static int REQUESTCODE = 1; // 返回的结果码
    private String codeId=""; // 二维码id默认空值


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_regist;
    }

    @Override
    protected void initView() {


        autoScrollView(register_viewgroup, register_instructions);//弹出软键盘时滚动视图

        SoftKeyBoardListener.setListener(RegistActivity.this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) view.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
                linearParams.height = 30;// 控件的宽强制设成30
                linearParams.weight = 0;// 控件的宽强制设成30
                view.setLayoutParams(linearParams); //使设置好的布局参数应用到控件

            }

            @Override
            public void keyBoardHide(int height) {
                LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) view.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
                linearParams.height = 30;// 控件的宽强制设成30
                linearParams.weight = 1;// 控件的宽强制设成30
                view.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppUtils.stopTimer();
    }

    @Override
    protected void initData() {

    }

    /*
     * 隐私条款
     * */
    @OnClick({R.id.sytk_btn,R.id.yszc_btn})
    public void jumpInstructions(){
        setIntentClass(WebInstructionsActivity.class);
    }


    @OnClick(R.id.finish_btn)
    public void doRegist(){
        if (!ButtonUtils.isFastDoubleClick(R.id.finish_btn)) {

            String phone = getEdit(forget_tel);
            String newpass = getEdit(forget_newpass);
            String code = getEdit(forget_code);

            if(TextUtils.isEmpty(phone)&&phone.length()>5){

                showBaseDialog("请输入手机号","好");
                return;

            }  if(TextUtils.isEmpty(newpass)){

                showBaseDialog("请输入新密码","好");

            }
                if(TextUtils.isEmpty(code)){

                showBaseDialog("请输入验证码","好");

            }
            OkHttpUtils
                    .post()
                    .url(WebUri.REGIST)
                    .addParams("mobile", phone)
                    .addParams("codeid", codeId)
                    .addParams("code", code)
                    .addParams("password", newpass)
                    .build()
                    .execute(new StringCallback(){
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {

                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);
                                String errCode = jsonObject.getString("errcode");
                                if(errCode.equals("200")){

                                    String token = jsonObject.getString("access_token");
                                    AppSharePreferenceMgr.put(context,"token",token);
                                    setIntentClass(HomeActivity.class);
                                    finish();

                                }else{
                                    AppUtils.stopTimer();
                                    showBaseDialog(jsonObject.getString("errmsg"),"好");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                        }
                    });



        }
    }

    @OnClick(R.id.getcode_btn)
    public void doGetCode(){

        if (!ButtonUtils.isFastDoubleClick(R.id.finish_btn)) {

            final String phoneNub = getEdit(forget_tel);
            if(phoneNub.length()>5){
                AppUtils.startCountdown(getcode_text,getcode_btn);
                OkHttpUtils
                        .post()
                        .url(WebUri.GETPHONECODE)
                        .addParams("mobile", phoneNub)
                        .build()
                        .execute(new StringCallback(){
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {

                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    String errCode = jsonObject.getString("errcode");
                                    if(errCode.equals("200")){
                                        codeId = jsonObject.getString("codeid");
                                    }else if(errCode.equals("40002")){

                                        AppUtils.stopTimer();

                                        AlertDialog dialog = new AlertDialog.Builder(context).create();//创建对话框
                                        dialog.setCanceledOnTouchOutside(false);
                                        dialog.setTitle("提示");//设置对话框标题
                                        dialog.setMessage(jsonObject.getString("errmsg"));//设置文字显示内容
                                        //分别设置三个button
                                        dialog.setButton(DialogInterface.BUTTON_POSITIVE,"使用此手机号登录", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();//关闭对话框

                                                Intent intent1 = new Intent();
                                                intent1.putExtra("phone", phoneNub);
                                                RegistActivity.this.setResult(REQUESTCODE, intent1);
                                                RegistActivity.this.finish();

                                            }
                                        });
                                        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "更换其它手机号", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                forget_tel.setText("");
                                                dialog.dismiss();//关闭对话框
                                            }
                                        });
                                        dialog.show();//显示对话框

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


    /**
     * @param root 最外层的View
     * @param scrollToView 不想被遮挡的View,会移动到这个Veiw的可见位置
     */
    private int scrollToPosition=0;
    private void autoScrollView(final View root, final View scrollToView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        Rect rect = new Rect();

                        //获取root在窗体的可视区域
                        root.getWindowVisibleDisplayFrame(rect);

                        //获取root在窗体的不可视区域高度(被遮挡的高度)
                        int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;

                        //若不可视区域高度大于150，则键盘显示
                        if (rootInvisibleHeight > 150) {

                            //获取scrollToView在窗体的坐标,location[0]为x坐标，location[1]为y坐标
                            int[] location = new int[2];
                            scrollToView.getLocationInWindow(location);

                            //计算root滚动高度，使scrollToView在可见区域的底部
                            int scrollHeight = (location[1] + scrollToView.getHeight()) - rect.bottom;

                            //注意，scrollHeight是一个相对移动距离，而scrollToPosition是一个绝对移动距离
                            scrollToPosition += scrollHeight;

                        } else {
                            //键盘隐藏
                            scrollToPosition = 0;
                        }
                        root.scrollTo(0, scrollToPosition);

                    }
                });
    }

}
