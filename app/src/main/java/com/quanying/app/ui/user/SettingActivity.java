package com.quanying.app.ui.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quanying.app.R;
import com.quanying.app.app.MyApplication;
import com.quanying.app.event.MessageEvent;
import com.quanying.app.ui.base.BaseActivity;
import com.quanying.app.utils.DataCleanManager;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import ch.ielse.view.SwitchView;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;
    @BindView(R.id.setting_item1)   //账户与安全
            LinearLayout zhanghu;
    @BindView(R.id.setting_item4)   //关于我们
            LinearLayout aboutUs;
    @BindView(R.id.login_out)   //退出登录
            LinearLayout login_out;
    @BindView(R.id.setting_item3)   //清除缓存
            LinearLayout cleanCache;

    @BindView(R.id.cache_text)   //缓存大小
            TextView cache_text;

    private SwitchView vLauncherVoice;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        vLauncherVoice = findViewById(R.id.msg_swi);
        titlebar.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    finish();
                }
            }
        });
//        titlebar.setAlpha(0);

        vLauncherVoice.setOpened(MyApplication.getJpushStatus());
        vLauncherVoice.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                MyApplication.openJpush();
                vLauncherVoice.setOpened(true);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                MyApplication.stopJpush();
                vLauncherVoice.setOpened(false);
            }
        });

    }
    @Override
    protected void initData() {

        try {
            cache_text.setText(DataCleanManager.getTotalCacheSize(context).toString());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
    * 关于我们
    * */
    @OnClick(R.id.setting_item4)
    public void jumpAboutUs(){
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra(WebActivity.PARAM_URL, "http://m.7192.com/aboutus?hideheader=1");
        intent.putExtra(WebActivity.TITLE, "关于我们");
        startActivity(intent);

    }

    /*
    * 关于我们
    * */
    @OnClick(R.id.setting_item3)
    public void cleanCache(){

//        Intent intent = new Intent(this, WebActivity.class);
//        intent.putExtra(WebActivity.PARAM_URL, "http://www.baidu.com");
//        intent.putExtra(WebActivity.TITLE, "关于我们");
//        startActivity(intent);

        try {
            if(DataCleanManager.getIntCache(context)<10){
                showBaseDialog("无需清理！","好");
            return;

            }

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setMessage("确定清除"+DataCleanManager.getTotalCacheSize(context).toString()+"缓存？")//设置对话框的标题
                    //设置对话框的按钮
                    .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("清除", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DataCleanManager.clearAllCache(context);
                            try {
                                cache_text.setText(DataCleanManager.getTotalCacheSize(context).toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    })
                    .create();
            dialog.show();




        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
    * 退出登录
    * */
    @OnClick(R.id.login_out)
    public void jumpLogin(){

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("确定退出该账号")//设置对话框的标题
                //设置对话框的按钮
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        DataCleanManager.clearAllCache(context);
                        EventBus.getDefault().post(new MessageEvent("loginout"));
                        MyApplication.loginBack();
                        Intent intent = new Intent(context, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .create();
        dialog.show();



    }
    /*
    * 账户与安全
    * */
    @OnClick(R.id.setting_item1)
    public void jumpAccountSafe(){
        Intent intent = new Intent(this, AccountSafeActivity.class);
        startActivity(intent);
//        finish();
    }

}
