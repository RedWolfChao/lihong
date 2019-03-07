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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.quanying.app.R;
import com.quanying.app.app.MyApplication;
import com.quanying.app.event.MessageEvent;
import com.quanying.app.impl.SonicJavaScriptInterface;
import com.quanying.app.impl.SonicRuntimeImpl;
import com.quanying.app.impl.SonicSessionClientImpl;
import com.quanying.app.service.DownloadService;
import com.quanying.app.ui.base.BaseActivity;
import com.quanying.app.ui.fragment.HomeActivity;
import com.quanying.app.utils.AppImageMgr;
import com.quanying.app.utils.OrderInfoUtil2_0;
import com.quanying.app.utils.PayResult;
import com.quanying.app.weburl.WebUri;
import com.quanying.app.zhibo.DbUIActivity;
import com.quanying.app.zhibo.XbPlayUIActivity;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.sonic.sdk.SonicCacheInterceptor;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicConstants;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;
import com.tencent.sonic.sdk.SonicSessionConnection;
import com.tencent.sonic.sdk.SonicSessionConnectionInterceptor;
import com.tsy.sdk.social.PlatformType;
import com.tsy.sdk.social.SocialApi;
import com.tsy.sdk.social.listener.ShareListener;
import com.tsy.sdk.social.share_media.ShareWebMedia;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;
import com.yuyh.library.imgsel.config.ISListConfig;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.greendao.annotation.Index;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import me.curzbin.library.BottomDialog;
import me.curzbin.library.Item;
import me.curzbin.library.OnItemClickListener;
import okhttp3.Call;

public class WebActivity extends BaseActivity {

    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;

    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.p_btn)
    LinearLayout p_btn;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.progressBar_text)
    TextView progressBar_text;

    @BindView(R.id.noweb_ui)
    LinearLayout noweb_ui;
    private boolean isSuccess = false;
    private boolean isError = false;

    @BindView(R.id.shaxin)
    Button shaxin;

    private static final int SDK_PAY_FLAG = 1;

    //  webview setting
    public final static String PARAM_URL = "urls";
    public final static String TITLE = "title";

    private String lastUrl = "";

    private SonicSession sonicSession;
    private SocialApi mSocialApi;

    public final static int FILE_CHOOSER_RESULT_CODE_FOR_ANDROID_5 = 5;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private ValueCallback<Uri> mUploadMessage;// 表单的数据信息
    private Uri imageUri;
    private TextView tvCenter;
    private Intent intent;
    private String urls;
    private String titleText;
    JSInterface jsInterface = new JSInterface();
    private String uid;
    private int REQUEST_LIST_CODE = 1010;
    private String playId;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {
        Log.e("dayinkankantoken",MyApplication.getToken());
        EventBus.getDefault().register(this);
        mSocialApi = SocialApi.get(getApplicationContext());
        p_btn.setVisibility(View.GONE);
        intent = getIntent();
        tvCenter = new TextView(context);
        titleText = intent.getStringExtra(TITLE);
        tvCenter.setText(titleText);
        tvCenter.setTextColor(getResources().getColor(R.color.white));
        tvCenter.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        tvCenter.setGravity(Gravity.CENTER);
        tvCenter.setSingleLine(true);
        titlebar.setCenterView(tvCenter);
        urls = intent.getStringExtra("urls");
        titlebar.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    finish();
                }
                if (action == CommonTitleBar.ACTION_RIGHT_BUTTON) {

                    String titStr = titleText;
                    if (TextUtils.isEmpty(titStr)) {
                        titStr = "全影网";
                    }
                    showShareUi(titStr, urls, titStr + "-全影网","");

                }
            }
        });

// 自定义图片加载器
        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
        if (titleText.equals("title")) {
            titlebar.setVisibility(View.GONE);
        }

        if (titleText.equals("goumai")) {
            titlebar.setVisibility(View.GONE);
            playId = intent.getStringExtra("playid");
        }

        initWebView();
        shaxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                synCookies(context, urls);
                webView.reload();
            }
        });
    }

    private void showShareUi(final String title, final String link, final String nr,String thrumb) {
        final Bitmap bit;
        if(!TextUtils.isEmpty(thrumb)) {
             bit = AppImageMgr.getBitMBitmap(thrumb);
        }else{
            bit= readBitMap(getApplicationContext(), R.mipmap.appicon);

        }

        new BottomDialog(context)
                .title("分享到")
                .orientation(BottomDialog.HORIZONTAL)
                .inflateMenu(R.menu.menu_share, new OnItemClickListener() {
                    @Override
                    public void click(Item item) {
//                        Toast.makeText(HomeActivity.this, getString(R.string.share_title) + item.getTitle(), Toast.LENGTH_SHORT).show();

                        if (item.getTitle().equals("QQ")) {

                            ShareWebMedia shareMedia = new ShareWebMedia();
                            shareMedia.setTitle(title);
                            shareMedia.setDescription(nr);
                            shareMedia.setWebPageUrl(link);
                            shareMedia.setThumb(bit);

                            mSocialApi.doShare(WebActivity.this, PlatformType.QQ, shareMedia, new ShareListener() {
                                @Override
                                public void onComplete(PlatformType platform_type) {
                                    Log.i("tsy", "share onComplete");
                                }

                                @Override
                                public void onError(PlatformType platform_type, String err_msg) {
                                    Log.i("tsy", "share onError:" + err_msg);
                                }

                                @Override
                                public void onCancel(PlatformType platform_type) {
                                    Log.i("tsy", "share onCancel");
                                }
                            });
                        }

                        if (item.getTitle().equals("微信")) {
                            ShareWebMedia shareMedia = new ShareWebMedia();
                            shareMedia.setTitle(title);
                            shareMedia.setDescription(nr);
                            shareMedia.setWebPageUrl(link);
                            shareMedia.setThumb(bit);

                            mSocialApi.doShare(WebActivity.this, PlatformType.WEIXIN, shareMedia, new ShareListener() {
                                @Override
                                public void onComplete(PlatformType platform_type) {
                                    Log.i("tsy", "share onComplete");
                                }

                                @Override
                                public void onError(PlatformType platform_type, String err_msg) {
                                    Log.i("tsy", "share onError:" + err_msg);
                                }

                                @Override
                                public void onCancel(PlatformType platform_type) {
                                    Log.i("tsy", "share onCancel");
                                }
                            });

                        }

                        if (item.getTitle().equals("QQ空间")) {
                            ShareWebMedia shareMedia = new ShareWebMedia();
                            shareMedia.setTitle(title);
                            shareMedia.setDescription(nr);
                            shareMedia.setWebPageUrl(link);
                            shareMedia.setThumb(bit);

                            mSocialApi.doShare(WebActivity.this, PlatformType.QZONE, shareMedia, new ShareListener() {
                                @Override
                                public void onComplete(PlatformType platform_type) {
                                    Log.i("tsy", "share onComplete");
                                }

                                @Override
                                public void onError(PlatformType platform_type, String err_msg) {
                                    Log.i("tsy", "share onError:" + err_msg);
                                }

                                @Override
                                public void onCancel(PlatformType platform_type) {
                                    Log.i("tsy", "share onCancel");
                                }
                            });
                        }

                        if (item.getTitle().equals("朋友圈")) {
                            ShareWebMedia shareMedia = new ShareWebMedia();
                            shareMedia.setTitle(title);
                            shareMedia.setDescription(nr);
                            shareMedia.setWebPageUrl(link);
                            shareMedia.setThumb(bit);

                            mSocialApi.doShare(WebActivity.this, PlatformType.WEIXIN_CIRCLE, shareMedia, new ShareListener() {
                                @Override
                                public void onComplete(PlatformType platform_type) {
                                    Log.i("tsy", "share onComplete");
                                }

                                @Override
                                public void onError(PlatformType platform_type, String err_msg) {
                                    Log.i("tsy", "share onError:" + err_msg);
                                }

                                @Override
                                public void onCancel(PlatformType platform_type) {
                                    Log.i("tsy", "share onCancel");
                                }
                            });
                        }
                    }
                })
                .show();

    }


    private Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
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
            if (true) {
                sessionConfigBuilder.setCacheInterceptor(new SonicCacheInterceptor(null) {
                    @Override
                    public String getCacheData(SonicSession session) {
                        return null; // offline pkg does not need cache
                    }
                });

                sessionConfigBuilder.setConnectionInterceptor(new SonicSessionConnectionInterceptor() {
                    @Override
                    public SonicSessionConnection getConnection(SonicSession session, Intent intent) {
                        return new OfflinePkgSessionConnection(WebActivity.this, session, intent);
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
/*
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    //按返回键操作并且能回退网页
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                        //后退
                        webView.goBack();
                        Toast.makeText(context, "拦截", Toast.LENGTH_SHORT).show();
                        return true;
                    }else{

                        Toast.makeText(context, "没有上一页", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });*/
        Log.e("hey", "我是真的干够了，如果你以后负责维护，请不要找我！");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                progressBar.setVisibility(View.GONE);
                progressBar_text.setVisibility(View.GONE);

                if (sonicSession != null) {
                    sonicSession.getSessionClient().pageFinish(url);
                }
                if (!isError || isSuccess) {

                    //回调成功后的相关操作

                    noweb_ui.setVisibility(View.GONE);
                    return;
                }


                noweb_ui.setVisibility(View.VISIBLE);

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

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return;
                }
                // 在这里显示自定义错误页
                Log.e("why???", "zhegegouride");
                isSuccess = false;
                isError = true;
            }

            //处理网页加载失败时
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (request.isForMainFrame()) {
                    // 在这里显示自定义错误页
                    Log.e("why???", "goucaode");
                    isSuccess = false;
                    isError = true;
                }


            }


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

               Log.e("heheheurl", "```````````````````````````"+url);

                if (lastUrl.equals(url)) {
                    return true;
                }

                lastUrl = url;

//                Toast.makeText(context, ""+url, Toast.LENGTH_SHORT).show();
                if (url.startsWith("http") || url.startsWith("https")) { //http和https协议开头的执行正常的流程

                    if (url.equals("http://m.7192.com/") || url.equals("http://m.7192.com")) {
                        Intent intent = new Intent(context, HomeActivity.class);
                        startActivity(intent);
                    } else {
//                        view.loadUrl(url);


                        if (url.equals(urls)) {

                            view.reload();

                        } else {

                            Log.e("urlload",url);
/*
                            Intent intent = new Intent(context, WebActivity.class);
                            intent.putExtra("urls", url);
                            intent.putExtra("title", titleText);
                            startActivity(intent);*/
                            view.loadUrl(url);
                        }
                    }
                } else {
                   /* Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(in);
                    view.loadUrl(url);*/
//                    return true;
                    String tag = "tel";
                    if (url.contains(tag)) {
                        String mobile = url.substring(url.lastIndexOf(":")+1);
                        Log.e("mobile----------->",mobile);

                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + mobile);
                        intent.setData(data);
                        startActivity(intent);


                        //Android6.0以后的动态获取打电话权限
//                       return true;
                    }

                }


                return true;
            }

        });

        WebSettings webSettings = webView.getSettings();

        // add java script interface
        // note:if api level lower than 17(android 4.2), addJavascriptInterface has security
        // issue, please use x5 or see https://developer.android.com/reference/android/webkit/
        // WebView.html#addJavascriptInterface(java.lang.Object, java.lang.String)
        webSettings.setJavaScriptEnabled(true);
//        webView.removeJavascriptInterface("searchBoxJavaBridge_");
        intent.putExtra(SonicJavaScriptInterface.PARAM_LOAD_URL_TIME, System.currentTimeMillis());
        webView.getSettings().setUserAgentString(webView.getSettings().getUserAgentString() +WebUri.USERAGAENT);
//        webView.getSettings().setUserAgentString(webView.getSettings().getUserAgentString() + "qyw2018_androidqyw2017");

//        webView.loadData(data, "text/html", "UTF -8");//API提供的标准用法，无法解决乱码问题


        // init webview settings


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//软件解码
        }
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);//硬件解码

        webSettings.setAllowContentAccess(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        webSettings.setMediaPlaybackRequiresUserGesture(false);

        webView.setWebChromeClient(chromeClient);
        webView.addJavascriptInterface(jsInterface, "qyapp");

        synCookies(context, urls);

        // webview is ready now, just tell session client to bind
        if (sonicSessionClient != null) {
            sonicSessionClient.bindWebView(webView);
            sonicSessionClient.clientReady();
        } else { // default mode
            webView.loadUrl(urls);




        }


        noweb_ui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                webView.reload();


            }
        });




    }

    @SuppressWarnings("deprecation")
    public void synCookies(Context context, String url) {
/*
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeAllCookie();

        SharedPrefsCookiePersistor mData = new SharedPrefsCookiePersistor(context);
        List<Cookie> cookies = new ArrayList<Cookie>();
        cookies = mData.loadAll();
        StringBuffer sb = new StringBuffer();


        for ( Cookie cookie : cookies)
        {

            String cookieName = cookie.name();
            String cookieValue = cookie.value();
            if (!TextUtils.isEmpty(cookieName)
                    && !TextUtils.isEmpty(cookieValue)) {
                sb.append(cookieName + "=");
                sb.append(cookieValue + ";");
            }
        }

        android.webkit.CookieManager cookieManager1 = android.webkit.CookieManager.getInstance();
//        cookieManager1.setCookie(url,sb.toString());
        String[] cookie = sb.toString().split(";");
        for (int i = 0; i < cookie.length; i++) {
            cookieManager.setCookie(url, cookie[i]);// cookies是在HttpClient中获得的cookie
        }
        CookieSyncManager.getInstance().sync();*/
    }

    @SuppressLint("JavascriptInterface")
    @SuppressWarnings("unused")
    class JSInterface {

        public JSInterface() {

        }

        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void rencaiwang(String s) {
//            Toast.makeText(getMContext(), "-------------------------", Toast.LENGTH_SHORT).show();
            EventBus.getDefault().post(new MessageEvent("jumprencai"));

        }

        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void rencaiqiuzhi(String s) {
//            Toast.makeText(getMContext(), "-------------------------", Toast.LENGTH_SHORT).show();
            EventBus.getDefault().post(new MessageEvent("jumprencai"));
            EventBus.getDefault().post(new MessageEvent("jrrc"));

        }

        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void yinglouzhaopin(String s) {
//            Toast.makeText(getMContext(), "-------------------------", Toast.LENGTH_SHORT).show();
            EventBus.getDefault().post(new MessageEvent("jumprencai"));
            EventBus.getDefault().post(new MessageEvent("jryl"));
        }

        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void backSx(String s) {
//            Toast.makeText(getMContext(), "-------------------------", Toast.LENGTH_SHORT).show();

//                MyApplication.BACKURL = jsonObject.getString("url");
//                Toast.makeText(context, "打上标记", Toast.LENGTH_SHORT).show();
                MyApplication.DOREFRESH = true;


        }

     @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void backSx() {
//            Toast.makeText(getMContext(), "-------------------------", Toast.LENGTH_SHORT).show();

//                MyApplication.BACKURL = jsonObject.getString("url");
                Toast.makeText(context, "打上标记", Toast.LENGTH_SHORT).show();
         MyApplication.DOREFRESH = true;


        }

        @JavascriptInterface
        public void playvideo(String str) {
            Log.e("playlive",str);
            try {
                JSONObject jsonObject = new JSONObject(str);
                String playid = jsonObject.getString("id");
                String playcid = jsonObject.getString("cid");

                Intent intent = new
                        Intent(context,DbUIActivity.class);
                //在Intent对象当中添加一个键值对
                MyApplication.PLAYCALLBACKID = playid;
                MyApplication.PLAYCID = playcid;
/*
                if(MyApplication.PLAYSTATUS.equals("2")) {
      *//*    AnyEventType ae = new AnyEventType();
          ae.setCallBackStatus("1");*//*
                    EventBus.getDefault().post(new AnyEventType());
                    return;
                }*/
                startActivity(intent);




            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void upData(String appUrls){

            Intent intent = new Intent(WebActivity.this,DownloadService.class);
            intent.putExtra("url", appUrls);
            startService(intent);

        }


    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void openshare(String s) {
//            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();

        try {
            JSONObject jsonObject = new JSONObject(s);
            String title = jsonObject.getString("title");
            String dsp = jsonObject.getString("dsp");
            String link = jsonObject.getString("link");
            String thumb = jsonObject.getString("thumb");
            if (TextUtils.isEmpty(title)) {

                title = "全影网";

            }
            if (TextUtils.isEmpty(dsp)) {

                dsp = "7192.com影楼第一门户网站";

            }
            showShareUi(title, link, dsp,thumb);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @JavascriptInterface
    public void uploadImage(String orderJson) {
//            WebView
//            Toast.makeText(HomeActivity.this, ""+orderJson, Toast.LENGTH_SHORT).show();
        Log.e("order", orderJson);
        try {
            JSONObject jsonObject = new JSONObject(orderJson);
            uid = jsonObject.getString("id");
            int num = jsonObject.getInt("num");
            onTakePhoto(num);
        } catch (JSONException e) {
            e.printStackTrace();
        }


         /*   OkHttpUtils
                    .post()
                    .url("https://123ibt.com/upfile.html?tb=1&cp=2&c=save&dosubmit=test_ad_upload&rtn="+orderJson)
                    .addFile("imgs","imgs.png",new File(""))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {

                        }
                    });
*/


    }


    @JavascriptInterface
    public void opennewview(String orderJson) {
//            WebView
        try {
//                Toast.makeText(context, ""+orderJson, Toast.LENGTH_SHORT).show();
            JSONObject jsonObject = new JSONObject(orderJson);
            String webUrl = jsonObject.getString("url");
            Log.e("kevin", "ist" + webUrl);
            String title = "title";
            Intent intent = new Intent(context, WebActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("urls", webUrl);
            startActivity(intent);
            /*
             *
             * */
               /* MyApplication.stackUrl.add(webUrl);
//                MyApplication.webUrl = webUrl;
                startActivity(new Intent(HomeActivity.this,HomeActivity.class));*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public void goback(String orderJson) {
        // the callback function of demo page is hardcode as 'getDiffDataCallback'


        try {
            JSONObject jsonObject = new JSONObject(orderJson);

            if (jsonObject.getString("url").equals("back")) {

                if(titleText.equals("goumai")){

                    Intent zbIntent = new Intent(context, XbPlayUIActivity.class);
                    zbIntent.putExtra("play_id", playId);
                    startActivity(zbIntent);
                    finish();
                    return;

                }

                finish();


            } else if (jsonObject.getString("url").equals("goback")) {
                if(titleText.equals("goumai")){

                    Intent zbIntent = new Intent(context, XbPlayUIActivity.class);
                    zbIntent.putExtra("play_id", playId);
                    startActivity(zbIntent);
                    finish();
                    return;

                }



                WebActivity.this.finish();
            } else {
                if(titleText.equals("goumai")){

                    Intent zbIntent = new Intent(context, XbPlayUIActivity.class);
                    zbIntent.putExtra("play_id", playId);
                    startActivity(zbIntent);
                    finish();
                    return;

                }
                MyApplication.BACKURL = jsonObject.getString("url");
                MyApplication.DOREFRESH = true;
                        finish();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @JavascriptInterface
    public void logIn(String str) {

        WebActivity.this.startActivity(new Intent(context, LoginActivity.class));
//            startActivity(new Intent(context, LoginActivity.class));
    }   @JavascriptInterface
    public void fabu(String str) {


            startActivity(new Intent(context, AddCreationActivity.class));
    }

    @JavascriptInterface
    public void logIn() {
//            startActivity(new Intent(context, LoginActivity.class));
        if (!TextUtils.isEmpty(MyApplication.getToken())) {
            synCookies(context, urls);
            webView.reload();
            Toast.makeText(context, "刷新", Toast.LENGTH_SHORT).show();
            return;
        } else {
            WebActivity.this.startActivity(new Intent(context, LoginActivity.class));
        }
    }

    @JavascriptInterface
    public void vipshop(String str) {
//            Toast.makeText(context, "123", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(context, VipActivity.class));
    }
        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void vipshop(){
//            Toast.makeText(context, "123", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(context, VipActivity.class));
    }


    @JavascriptInterface
    public void playlive(String id) {
//            Toast.makeText(context, "```````````````````````"+id, Toast.LENGTH_SHORT).show();

        //在Intent对象当中添加一个键值对
        MyApplication.PLAYID = id;
        MyApplication.PLAYSTATUS = 1 + "";
/*
            Intent intents = new
                    Intent(context,PlayUIActivity.class);
            intents.putExtra("play_id",id);
            startActivity(intents);*/

        Intent zbIntent = new Intent(context, XbPlayUIActivity.class);
        zbIntent.putExtra("play_id", id);
        startActivity(zbIntent);

    }


    @JavascriptInterface
    public void mokuai(String orderJson) {
//            WebView
//            Toast.makeText(context, "orderJson"+orderJson, Toast.LENGTH_SHORT).show();
        try {

            JSONObject jsonObject = new JSONObject(orderJson);
            String webUrl = jsonObject.getString("link");
            String title = jsonObject.getString("title");
            Intent intent = new Intent(context, WebActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("urls", webUrl);
            startActivity(intent);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

        @JavascriptInterface
        public void aliPay(String orderJson) {

            Log.e("kankanzhi",orderJson);
            boolean rsa2 = (MyApplication.RSA_PRIVATE.length() > 0);

            Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(MyApplication.APPID, rsa2,orderJson);

            String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

            String privateKey = MyApplication.RSA_PRIVATE;
            String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
            final String orderInfo = orderParam + "&" + sign;
            Runnable payRunnable = new Runnable() {

                @Override
                public void run() {
                    PayTask alipay = new PayTask(WebActivity.this);
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

        @JavascriptInterface
        public void wxPay(String orderJson) {
            try {
                JSONObject jsonObject = new JSONObject(orderJson);
                partnerId = jsonObject.getString("partnerid");
                prepayId = jsonObject.getString("prepayid");
                nonceStr = jsonObject.getString("noncestr");
                timeStamp = jsonObject.getString("timestamp");
                sign = jsonObject.getString("sign");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            iwxapi = WXAPIFactory.createWXAPI(context, null); //初始化微信api
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


}
    private String partnerId;
    private String nonceStr;
    private String timeStamp;
    private String prepayId;
    private String sign;
    private IWXAPI iwxapi; //微信支付api

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
                        webView.loadUrl("javascript:pay_sta("+"'success','ali'"+")");
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
                        webView.loadUrl("javascript:pay_sta("+"'fail','ali'"+")");
                    }
                    break;
                }

            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {

        if(messageEvent.getMessage().equals("homefinish")){

            if(MyApplication.BACKURL.equals("reloadurl")){
//                Toast.makeText(context, "调用刷新", Toast.LENGTH_SHORT).show();
                webView.reload();

//                showBaseDialog("登录成功！","确认");

         /*       MyApplication.BACKURL = "";
                synCookies(context,urls);*/

            }else{
                MyApplication.BACKURL = "";

            }
        }
        if(messageEvent.getMessage().equals("pay_sta")){

            if(messageEvent.getStatus().equals("success")){
                webView.loadUrl("javascript:pay_sta("+"'success','wx'"+")");
            }else{
                webView.loadUrl("javascript:pay_sta("+"'fail','wx'"+")");
            }

        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        lastUrl = "";
        if(  MyApplication.DOREFRESH ){
            MyApplication.DOREFRESH =false;
            webView.reload();
        }



    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void onTakePhoto(int num){
/*
        PictureSelector.create(HomeActivity.this)
                .openGallery(PictureMimeType.ofImage())
                .selectionMode(PictureConfig.SINGLE)
                .previewImage(true)
                .forResult(PictureConfig.CHOOSE_REQUEST);*/
        imgIndex =0;
// 自由配置选项
        ISListConfig config = new ISListConfig.Builder()
                // 是否多选, 默认true
                .multiSelect(true)
                // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                .rememberSelected(false)
                // “确定”按钮背景色
                .btnBgColor(Color.GRAY)
                // “确定”按钮文字颜色
                .btnTextColor(Color.BLUE)
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#3F51B5"))
                // 返回图标ResId
                .backResId(R.mipmap.redback)
                // 标题
                .title("图片")
                // 标题文字颜色
                .titleColor(Color.WHITE)
                // TitleBar背景色
                .titleBgColor(Color.parseColor("#3F51B5"))
                // 裁剪大小。needCrop为true的时候配置
                .cropSize(1, 1, 200, 200)
                .needCrop(false)
                // 第一个是否显示相机，默认true
                .needCamera(false)
                // 最大选择图片数量，默认9
                .maxNum(num)
                .build();

// 跳转到图片选择器
        ISNav.getInstance().toListActivity(this, config, REQUEST_LIST_CODE);

    }

    @Override
    protected void initData() {

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
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
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
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.destroy();
        }
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

    }
    public static void convertToJpg(String pngFilePath, String jpgFilePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(pngFilePath);
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(jpgFilePath))) {

            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos)) {
                bos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFileName(String pathandname){
        int start=pathandname.lastIndexOf("/");
        int end=pathandname.lastIndexOf(".");
        if(start!=-1 && end!=-1){
            return pathandname.substring(start+1,end);
        }else{
            return null;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Uri result = (data == null || resultCode != Activity.RESULT_OK) ? null: data.getData();

        // 图片选择结果回调
        if (requestCode == 1010 && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
                Log.e("pathListl", pathList.size()+"");

                for(int i =0 ;i<pathList.size();i++){

                    String end = pathList.get(i).substring(pathList.get(i).lastIndexOf(".") + 1, pathList.get(i).length()).toLowerCase();
                    if(!end.equals("jpg")||!end.equals("jpeg")){

                        File file = new File(Environment.getExternalStorageDirectory()+"/jpgfiles");
//判断文件夹是否存在,如果不存在则创建文件夹
                        if (!file.exists()) {
                            file.mkdir();
                        }
                        String fileName = getFileName(pathList.get(i));
//                    Toast.makeText(this, selectList.get(0).getCompressPath()+"", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(this, fileName+"", Toast.LENGTH_SHORT).show();
                        convertToJpg(pathList.get(i),file.getPath()+"/"+fileName+".jpg");
                        pathList.set(i,file.getPath()+"/"+fileName+".jpg");
                    }
                }
            doUpImg(pathList);



            }


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

    private int imgIndex = 0;

    public void doUpImg(final List<String> pathList){


//        Toast.makeText(context, "id"+uid+"sid"+MyApplication.getUserID()+"index"+imgIndex, Toast.LENGTH_SHORT).show();

        /** 倒计时60秒，一次1秒 */
        CountDownTimer timer = new CountDownTimer(3*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onFinish() {
                OkHttpUtils
                        .post()
                        .url("http://qingjian.7192.com/qjapp.html?a=ajax&c=upload")
                        .addFile("file", "imgs.jpg", new File(pathList.get(imgIndex)))
                        .addParams("id",uid)
                        .addParams("sid",MyApplication.getUserID())
                        .addParams("index",imgIndex+"")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Log.e("dayinkan", e.toString());
//                        Toast.makeText(context,  e.toString(), Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onResponse(String response, int id) {

                                Log.e("dayinkan", response);
//                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                Log.e("dayinkanuid", MyApplication.getUserID()+"");

//                                        Log.e("dayinkan", pathList.get(finalI));
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getString("errcode").equals("200")) {


                                        String imgpath = jsonObject.getString("imgpath");
                                        String imgid = jsonObject.getString( "imgid");
                                        webView.loadUrl("javascript:setImg('" + imgid+"','"+imgpath+"','0 ')");
                                        Log.e("dayinkan", "javascript:setImg('" + imgid+"','"+imgpath+"','0 ')");
                                        imgIndex = imgIndex+1;
                                        if(imgIndex<pathList.size()){

                                            doUpImg(pathList);
                                        }
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
            }
        }.start();



    }

/*



* */
@Override
public void onBackPressed() {
    super.onBackPressed();
    if(titleText.equals("goumai")){

        Intent zbIntent = new Intent(context, XbPlayUIActivity.class);
        zbIntent.putExtra("play_id", playId);
        startActivity(zbIntent);
        finish();
        return;

    }
}


}