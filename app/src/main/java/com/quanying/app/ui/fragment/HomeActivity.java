package com.quanying.app.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.gyf.barlibrary.ImmersionBar;
import com.quanying.app.R;
import com.quanying.app.app.MyApplication;
import com.quanying.app.event.MessageEvent;
import com.quanying.app.manager.ActivityManage;
import com.quanying.app.ui.user.LoginActivity;
import com.quanying.app.ui.user.WebActivity;
import com.quanying.app.utils.OrderInfoUtil2_0;
import com.quanying.app.utils.PayResult;
import com.quanying.app.view.CustomViewPager;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class HomeActivity extends FragmentActivity  implements EasyPermissions.PermissionCallbacks{

    private CustomViewPager mVpContent;
    private BottomBarLayout mBottomBarLayout;

    private List<Fragment> mFragmentList = new ArrayList<>();
    private RotateAnimation mRotateAnimation;

    private static final int SDK_PAY_FLAG = 1;

    /*
    *
    *  prepayId = jsonObject.getString("prepayid");
            nonceStr = jsonObject.getString("noncestr");
            timeStamp = jsonObject.getString("timestamp");
            sign = jsonObject.getString("sign");
    * */

    private String partnerId;
    private String nonceStr;
    private String timeStamp;
    private String prepayId;
    private String sign;
    private IWXAPI iwxapi; //微信支付api

    private int lastClickNub = 0;

    public static Activity mThis;
    private static final int REQUEST_CODE_PERMISSION_CHOOSE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        mThis = this;
        ImmersionBar.with(this)
                .init();
        initView();
        initData();
        initListener();
    }

    private void initView() {

        EventBus.getDefault().register(this);

        mVpContent = (CustomViewPager) findViewById(R.id.vp_content);
        mVpContent.setScanScroll(false);
        mVpContent.setOffscreenPageLimit(5);
                mBottomBarLayout = (BottomBarLayout) findViewById(R.id.bbl);
        choosePhoto();
    }

    private void initData() {

        HomeFragment videoFragment = new HomeFragment();
        mFragmentList.add(videoFragment);

        TalentFragment microFragment = new TalentFragment();
        mFragmentList.add(microFragment);

        ShowRoomFragment homeFragment = new ShowRoomFragment();
        mFragmentList.add(homeFragment);

        QingjianFragment meFragment = new QingjianFragment();
        mFragmentList.add(meFragment);

        UserFragment userFragment = new UserFragment();
        mFragmentList.add(userFragment);

      /*  OkHttpUtils
                .post()
                .url(WebUri.VERSIONCODEUPDATE)
                .addHeader()
*/
    }

    private  int positon = 0;

    private void initListener() {
        mVpContent.setAdapter(new MyAdapter(getSupportFragmentManager()));
        mBottomBarLayout.setViewPager(mVpContent);
        mBottomBarLayout.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem bottomBarItem, int i, int currentPosition) {


Log.e("weizhi","currentPosition:"+currentPosition+"~~"+mBottomBarLayout.getCurrentItem()+"~~~"+bottomBarItem.getTextView().getText().toString()+"`````"+i);

//                Toast.makeText(HomeActivity.this, "i"+":"+currentPosition, Toast.LENGTH_SHORT).show();

                if(currentPosition!=0){

                    mBottomBarLayout.getBottomItem(0).setStatus(false);
                }
                if(currentPosition!=1){
                    if(currentPosition!=4){
                        lastClickNub = 0;
                        mBottomBarLayout.getBottomItem(1).setStatus(false);
                    }

                }
                if(currentPosition!=2){

                    /*                 *
                        *            *
                            *     *
                                ************
                                  *      *
                                    *   *
                                      *
                                     \*/
                    mBottomBarLayout.getBottomItem(2).setStatus(false);
                }

                if(mBottomBarLayout.getCurrentItem()==1){
                    if(currentPosition == 4){

                    if(MyApplication.getToken().equals("")){
                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        bottomBarItem.setStatus(false);
                        mBottomBarLayout.setCurrentItem(0);
                        return;
//                        mBottomBarLayout.getBottomItem(0).set
                    }


                        bottomBarItem.setStatus(false);
                        lastClickNub = 1;

                        mBottomBarLayout.setCurrentItem(1);
//                        Toast.makeText(HomeActivity.this, "tiaozhuanle", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(HomeActivity.this,WebActivity.class);
                        intent.putExtra("title","title");
                        intent.putExtra("urls","http://m.7192.com/home");
                        startActivity(intent);
                        return;
                    }
                }

                if(currentPosition == 3 ||currentPosition == 4){
                    Log.e("weizhi","token"+MyApplication.getToken());

                    if(currentPosition ==4&&lastClickNub == 1)
                    {
                        if(MyApplication.getToken().equals("")){
                            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                            bottomBarItem.setStatus(false);
                            mBottomBarLayout.setCurrentItem(0);
                            return;
//                        mBottomBarLayout.getBottomItem(0).set
                        }


                        bottomBarItem.setStatus(false);
                        lastClickNub = 1;

                        mBottomBarLayout.setCurrentItem(1);
//                        Toast.makeText(HomeActivity.this, "tiaozhuanle", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(HomeActivity.this,WebActivity.class);
                        intent.putExtra("title","title");
                        intent.putExtra("urls","http://m.7192.com/home");
                        startActivity(intent);
                        return;
                    }

//


                    if(MyApplication.getToken().equals("")){

//                        Toast.makeText(HomeActivity.this, "", Toast.LENGTH_SHORT).show();
                        Log.e("weizhi","this");
                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        mBottomBarLayout.setCurrentItem(positon);
                        mBottomBarLayout.getBottomItem(positon).setStatus(true);
                        bottomBarItem.setStatus(false);
                        return;
//                        mBottomBarLayout.getBottomItem(0).set
                    }
                    if(currentPosition == 3){

                        mBottomBarLayout.setCurrentItem(3);

                    }  if(currentPosition == 4){

                        mBottomBarLayout.setCurrentItem(4);

                    }

                }else{
                    Log.e("weizhi","this");
                    positon=currentPosition;
                }

            }

        });
        mBottomBarLayout.setSmoothScroll(true);
    }


    /**停止首页页签的旋转动画*/
    private void cancelTabLoading(BottomBarItem bottomItem) {
        Animation animation = bottomItem.getImageView().getAnimation();
        if (animation != null){
            animation.cancel();
        }
    }



    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_CHOOSE_PHOTO)
    public void choosePhoto() {

        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA,Manifest.permission.INTERNET};
        if (EasyPermissions.hasPermissions(this, perms)) {


        }else{
            EasyPermissions.requestPermissions(this, "请开起存储空间和相机权限，以正常使用上传图片功能！", REQUEST_CODE_PERMISSION_CHOOSE_PHOTO, perms);
        }

    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }


    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            exitBy2Click();      //调用双击退出函数
        }
        return false;
    }
    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            ActivityManage.getInstance().finishActivity();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if(messageEvent.getMessage().equals("jumprencai")){
            mBottomBarLayout.setCurrentItem(1);

        } if(messageEvent.getMessage().equals("jumpqingjian")){
            mBottomBarLayout.setCurrentItem(3);

        }

        if(messageEvent.getMessage().equals("loginout")){
            mBottomBarLayout.setCurrentItem(0);
        }
    }


    public void aliPayOper(String biz_content){

        boolean rsa2 = (MyApplication.RSA_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(MyApplication.APPID, rsa2,biz_content);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String privateKey = MyApplication.RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(HomeActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();


    }


    public void wxPayOper(String msg){
        try {
            JSONObject jsonObject = new JSONObject(msg);
            partnerId = jsonObject.getString("partnerid");
            prepayId = jsonObject.getString("prepayid");
            nonceStr = jsonObject.getString("noncestr");
            timeStamp = jsonObject.getString("timestamp");
            sign = jsonObject.getString("sign");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        iwxapi = WXAPIFactory.createWXAPI(this, null); //初始化微信api
        iwxapi.registerApp(MyApplication.WX_APPID); //注册appid  appid可以在开发平台获取
        Runnable payRunnable = new Runnable() {  //这里注意要放在子线程
            @Override
            public void run() {
                PayReq request = new PayReq(); //调起微信APP的对象
                //下面是设置必要的参数，也就是前面说的参数,这几个参数从何而来请看上面说明
                request.appId = MyApplication.WX_APPID;
                request.partnerId = partnerId;
                request.prepayId = prepayId;
                request.packageValue = "Sign=WXPay";
                request.nonceStr = nonceStr;
                request.timeStamp = timeStamp;
                request.sign = sign;
                iwxapi.sendReq(request);//发送调起微信的请求
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }



    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(HomeActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(HomeActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }

            }
        };
    };


}
