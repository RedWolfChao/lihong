package com.quanying.app.ui.user;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.quanying.app.R;
import com.quanying.app.app.MyApplication;
import com.quanying.app.impl.SonicJavaScriptInterface;
import com.quanying.app.impl.SonicRuntimeImpl;
import com.quanying.app.impl.SonicSessionClientImpl;
import com.quanying.app.ui.base.BaseActivity;
import com.quanying.app.utils.OrderInfoUtil2_0;
import com.quanying.app.utils.PayResult;
import com.quanying.app.weburl.WebUri;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.sonic.sdk.SonicCacheInterceptor;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicConstants;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;
import com.tencent.sonic.sdk.SonicSessionConnection;
import com.tencent.sonic.sdk.SonicSessionConnectionInterceptor;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;

public class WebPayActivity extends BaseActivity {

    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;

    @BindView(R.id.p_btn)
    LinearLayout p_btn;

    @BindView(R.id.wxpay)
    ImageView wxpay;

    @BindView(R.id.alipay)
    ImageView alipay;

    @BindView(R.id.buy_foot)
    LinearLayout disUi;

    @BindView(R.id.buy_dialogUi)
    RelativeLayout buy_dialogUi;

    @BindView(R.id.webview)
    WebView webView;

//  webview setting
    public final static String PARAM_URL = "param_url";
    public final static String TITLE = "title";

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

    public static Activity mThis;

    private SonicSession sonicSession;


    public final static int FILE_CHOOSER_RESULT_CODE_FOR_ANDROID_5 = 5;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private ValueCallback<Uri> mUploadMessage;// 表单的数据信息
    private Uri imageUri;
    private TextView tvCenter;
    private Intent intent;
    private String urls;
    private String pc;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {
        intent = getIntent();
        tvCenter = new TextView(context);
        tvCenter.setText(intent.getStringExtra(TITLE));
        tvCenter.setTextColor(getResources().getColor(R.color.white));
        tvCenter.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        tvCenter.setGravity(Gravity.CENTER);
        tvCenter.setSingleLine(true);
        titlebar.setCenterView(tvCenter);
        urls = intent.getStringExtra("urls");
        pc = intent.getStringExtra("pc");
        titlebar.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    finish();
                }
            }
        });

        initWebView();

    }

    private void initWebView() {

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        // init sonic engine if necessary, or maybe u can do this when application created
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(new SonicRuntimeImpl(getApplication()), new SonicConfig.Builder().build());
        }
        SonicSessionClientImpl sonicSessionClient = null;

        // if it's sonic mode , startup sonic session at first time
        if (true) { // sonic mode
            SonicSessionConfig.Builder sessionConfigBuilder = new SonicSessionConfig.Builder();
            sessionConfigBuilder.setSupportLocalServer(true);

            // if it's offline pkg mode, we need to intercept the session connection
            if (false) {
                sessionConfigBuilder.setCacheInterceptor(new SonicCacheInterceptor(null) {
                    @Override
                    public String getCacheData(SonicSession session) {
                        return null; // offline pkg does not need cache
                    }
                });

                sessionConfigBuilder.setConnectionInterceptor(new SonicSessionConnectionInterceptor() {
                    @Override
                    public SonicSessionConnection getConnection(SonicSession session, Intent intent) {
                        return new OfflinePkgSessionConnection(WebPayActivity.this, session, intent);
                    }
                });
            }

            // create sonic session and run sonic flow
            sonicSession = SonicEngine.getInstance().createSession(urls, sessionConfigBuilder.build());
            if (null != sonicSession) {
                sonicSession.bindClient(sonicSessionClient = new SonicSessionClientImpl());
            } else {
                // this only happen when a same sonic session is already running,
                // u can comment following codes to feedback as a default mode.
                // throw new UnknownError("create session fail!");
//                Toast.makeText(this, "create sonic session fail!", Toast.LENGTH_LONG).show();
            }
        }
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (sonicSession != null) {
                    sonicSession.getSessionClient().pageFinish(url);
                }
            }

            @TargetApi(21)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return shouldInterceptRequest(view, request.getUrl().toString());
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                if (sonicSession != null) {
                    return (WebResourceResponse) sonicSession.getSessionClient().requestResource(url);
                }
                return null;
            }
        });

        WebSettings webSettings = webView.getSettings();

        // add java script interface
        // note:if api level lower than 17(android 4.2), addJavascriptInterface has security
        // issue, please use x5 or see https://developer.android.com/reference/android/webkit/
        // WebView.html#addJavascriptInterface(java.lang.Object, java.lang.String)
        webSettings.setJavaScriptEnabled(true);
        webView.removeJavascriptInterface("searchBoxJavaBridge_");
        intent.putExtra(SonicJavaScriptInterface.PARAM_LOAD_URL_TIME, System.currentTimeMillis());
        webView.addJavascriptInterface(new SonicJavaScriptInterface(sonicSessionClient, intent,this), "sonic");

        // init webview settings
        webSettings.setAllowContentAccess(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webView.setWebChromeClient(chromeClient);

        // webview is ready now, just tell session client to bind
        if (sonicSessionClient != null) {
            sonicSessionClient.bindWebView(webView);
            sonicSessionClient.clientReady();
        } else { // default mode
            webView.loadUrl(urls);
        }

    }


    @Override
    protected void initData() {

        disUi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buy_dialogUi.setVisibility(View.GONE);
            }
        });

        wxpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpUtils
                        .post()
                        .url(WebUri.DOBUY)
                        .addParams("token", MyApplication.getToken())
                        .addParams("pc",pc)
                        .addParams("tp","wx")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {

                                Log.e("kankan",MyApplication.getToken()+"："+pc+":"+response);
                                wxPayOper(response);
                            }
                        });
            }
        });
        alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpUtils
                        .post()
                        .url(WebUri.DOBUY)
                        .addParams("token", MyApplication.getToken())
                        .addParams("pc",pc)
                        .addParams("tp","ali")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                                Toast.makeText(context, "网络请求失败，请检查网络后重试", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onResponse(String response, int id) {

                                Log.e("kankan",MyApplication.getToken()+"："+pc+":"+response);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    aliPayOper(jsonObject.getString("orderJson"));


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
            }
        });

        p_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buy_dialogUi.setVisibility(View.VISIBLE);



            }
        });

    }

    /*
     * 支持浏览器调用系统相册、拍照等功能
     * */
    private WebChromeClient chromeClient = new WebChromeClient() {
        // Android > 4.1.1 调用这个方法
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
			/*uploadInfo = uploadMsg;
			imagePickDialog.show();*/
            mUploadMessage=uploadMsg;
            take();
        }

        // 3.0 + 调用这个方法
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
/*			uploadInfo = uploadMsg;
			imagePickDialog.show();*/
            mUploadMessage=uploadMsg;
            take();

        }
        /**
         * alert弹框
         *
         * @return
         */
        @Override
        public boolean onJsAlert(WebView view, String url, final String message, JsResult result) {
            Log.d("main", "onJsAlert:" + message);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    new AlertDialog.Builder(context)
                            .setTitle("全影提示")
                            .setMessage(message)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    webView.reload();//重写刷新页面
                                }
                            })
                            .setNegativeButton("取消",null)
                            .show();

                }
            });
            result.confirm();//这里必须调用，否则页面会阻塞造成假死
            return true;
        }


        // Android < 3.0 调用这个方法
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
			/*uploadInfo = uploadMsg;
			imagePickDialog.show();*/
            mUploadMessage=uploadMsg;
            take();
        }

        @Override
        public boolean onShowFileChooser(WebView webView,
                                         ValueCallback<Uri[]> filePathCallback,
                                         FileChooserParams fileChooserParams) {
            mUploadCallbackAboveL=filePathCallback;
            take();
            return true;
        }

        /*
         * 需要缓冲进度条或dialog开启
         * */
    /*    @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
            } else {
                if (View.GONE == progressBar.getVisibility()) {
                    progressBar.setVisibility(View.VISIBLE);
                }
                progressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }*/
    };
    private static class OfflinePkgSessionConnection extends SonicSessionConnection {

        private final WeakReference<Context> context;

        public OfflinePkgSessionConnection(Context context, SonicSession session, Intent intent) {
            super(session, intent);
            this.context = new WeakReference<Context>(context);
        }

        @Override
        protected int internalConnect() {
            Context ctx = context.get();
            if (null != ctx) {
                try {
                    InputStream offlineHtmlInputStream = ctx.getAssets().open("sonic-demo-index.html");
                    responseStream = new BufferedInputStream(offlineHtmlInputStream);
                    return SonicConstants.ERROR_CODE_SUCCESS;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            return SonicConstants.ERROR_CODE_UNKNOWN;
        }

        @Override
        protected BufferedInputStream internalGetResponseStream() {
            return responseStream;
        }

        @Override
        public void disconnect() {
            if (null != responseStream) {
                try {
                    responseStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public int getResponseCode() {
            return 200;
        }

        @Override
        public Map<String, List<String>> getResponseHeaderFields() {
            return new HashMap<>(0);
        }

        @Override
        public String getResponseHeaderField(String key) {
            return "";
        }
    }


    private void take(){
        File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyApp");
        // Create the storage directory if it does not exist
        if (! imageStorageDir.exists()){
            imageStorageDir.mkdirs();
        }
        File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        imageUri = Uri.fromFile(file);

        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for(ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent i = new Intent(captureIntent);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            i.setPackage(packageName);
            i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            cameraIntents.add(i);

        }
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        Intent chooserIntent = Intent.createChooser(i,"Image Chooser");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
        startActivityForResult(chooserIntent,  FILE_CHOOSER_RESULT_CODE_FOR_ANDROID_5);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Uri result = (data == null || resultCode != Activity.RESULT_OK) ? null: data.getData();

        if (resultCode != Activity.RESULT_OK) {
            //uploadInfo.onReceiveValue(null);
            if(mUploadCallbackAboveL !=null){
                mUploadCallbackAboveL.onReceiveValue(null);
                return;
            }
            if(mUploadMessage!=null){
                mUploadMessage.onReceiveValue(null);
                return;
            }
        }

        if(requestCode==FILE_CHOOSER_RESULT_CODE_FOR_ANDROID_5)
        {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            }
            else  if (mUploadMessage != null) {

                if(result==null){
                    //                   mUploadMessage.onReceiveValue(imageUri);
                    mUploadMessage.onReceiveValue(imageUri);
                    mUploadMessage = null;

                    Log.e("imageUri",imageUri+"");
                }else {
                    mUploadMessage.onReceiveValue(result);
                    mUploadMessage = null;
                }
            }
        }
    }
    @SuppressWarnings("null")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILE_CHOOSER_RESULT_CODE_FOR_ANDROID_5
                || mUploadCallbackAboveL == null) {
            return;
        }

        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                results = new Uri[]{imageUri};
            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();

                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }

                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        if(results!=null){
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        }else{
            results = new Uri[]{imageUri};
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        }

        return;
    }




  /*

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if(messageEvent.getMessage().equals("jumprencai")){
            mBottomBarLayout.setCurrentItem(1);
        }
    }*/


    public void aliPayOper(String biz_content){

        Log.e("kankanzhi",biz_content);
        boolean rsa2 = (MyApplication.RSA_PRIVATE.length() > 0);

        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(MyApplication.APPID, rsa2,biz_content);

        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = MyApplication.RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(WebPayActivity.this);
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
                        Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }

            }
        }
    };



}