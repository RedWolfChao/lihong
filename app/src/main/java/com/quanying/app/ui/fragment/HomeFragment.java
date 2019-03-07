package com.quanying.app.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.quanying.app.R;
import com.quanying.app.app.MyApplication;
import com.quanying.app.event.MessageEvent;
import com.quanying.app.ui.base.BaseFragment;
import com.quanying.app.ui.home.HomeListActivity;
import com.quanying.app.ui.home.SearchActivity;
import com.quanying.app.ui.message.MessageActivity;
import com.quanying.app.ui.user.LoginActivity;
import com.quanying.app.ui.user.WebActivity;
import com.quanying.app.weburl.WebUri;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Cookie;

/**
 * @author ChayChan
 * @date 2017/6/23  11:22
 */
public class HomeFragment extends BaseFragment {


    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.jump_message)
    ImageView jumpMessage;
    JSInterface jsInterface = new JSInterface();

    private String WEBURI = "http://wap.7192.com?p=app";

    @BindView(R.id.noweb_ui)
    LinearLayout noweb_ui;

    private boolean isSuccess = false;
    private boolean isError = false;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.f_web;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);

        //支持屏幕缩放
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setBuiltInZoomControls(false);
        //不显示webview缩放按钮
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUserAgentString(webView.getSettings().getUserAgentString() +WebUri.USERAGAENT);
        webView.addJavascriptInterface(jsInterface, "qyapp");
//        webView.setWebViewClient(client);
        // 设置setWebChromeClient对象
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);

        webView.setWebChromeClient(
                new WebChromeClient() {
                    public void onProgressChanged(WebView view, int progress) {// 载入进度改变而触发
                        if (progress == 100) {
                            //handler.sendEmptyMessage(1);// 如果全部载入,隐藏进度对话框
                            isSuccess = true;
                        }

                        super.onProgressChanged(view, progress);
                    }
                    //扩展支持alert事件
                    @Override
                    public void onReceivedIcon(WebView view, Bitmap icon) {
                        super.onReceivedIcon(view, icon);

                    }

                    @Override
                    public void onReceivedTitle(WebView view, String title) {
                        super.onReceivedTitle(view, title);
//                        Log.i(TAG, "onReceivedTitle:title ------>" + title);
                      /*  if (title.contains("404")){
                            noweb_ui.setVisibility(View.VISIBLE);
                        }*/
                    }

                    @Override
                    public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                        callback.invoke(origin, true, false);
                        super.onGeolocationPermissionsShowPrompt(origin, callback);
                    }

                    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("提示").setMessage(message).setPositiveButton("确定", null);
                        builder.setCancelable(false);
                        //  builder.setIcon(R.drawable.ic_launcher);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        result.confirm();
                        return true;
                    }


                    @Override
                    public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                        AlertDialog.Builder b = new AlertDialog.Builder(getMContext());
                        b.setTitle("Confirm");
                        b.setMessage(message);
                        b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        });
                        b.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.cancel();
                            }
                        });
                        b.create().show();
                        return true;
                    }




                }
        );

        webView.setWebViewClient(new WebViewClient() {
            //覆盖shouldOverrideUrlLoading 方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("kevin","url:"+url);

                if (url.toLowerCase().startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(intent);
                } else {
                    view.loadUrl(url);
                }
                return true;
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if (!isError||isSuccess) {

                    //回调成功后的相关操作

                    EventBus.getDefault().post(new MessageEvent("homefinish"));
                    noweb_ui.setVisibility(View.GONE);
                    return;
                }



                Log.e("why???","caonimede");
                noweb_ui.setVisibility(View.VISIBLE);


            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    return;
                }
                // 在这里显示自定义错误页
                Log.e("why???","zhegegouride");
                isSuccess = false;
                isError = true;
            }

            //处理网页加载失败时
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (request.isForMainFrame()){
                    // 在这里显示自定义错误页
                    Log.e("why???","goucaode");
                    isSuccess = false;
                    isError = true;
                }
            }
        });
/*        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

            @Override
            public void onRefresh() {
//                initData();
                webView.reload();

                if(!MyApplication.getToken().equals("")){
//                    synCookies(getMContext(),WEBURI);
                    CookieSyncManager.getInstance().sync();
                }
            }
        });*/

        noweb_ui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                webView.reload();
                EventBus.getDefault().post(new MessageEvent("shuaxinrcw"));

                if(!MyApplication.getToken().equals("")){
//                    synCookies(getMContext(),WEBURI);
                    CookieSyncManager.getInstance().sync();
                    EventBus.getDefault().post(new MessageEvent("","5"));
                }


            }
        });

    }

    @Override
    protected void initData() {

        webView.loadUrl(WEBURI);

        if(!MyApplication.getToken().equals("")){
//            synCookies(getMContext(),WEBURI);
            CookieSyncManager.getInstance().sync();
            Log.e("zoulezhe","!");
        }

    }


    @SuppressWarnings("deprecation")
    public void synCookies(Context context, String url) {

        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeAllCookie();

        SharedPrefsCookiePersistor mData = new SharedPrefsCookiePersistor(getMContext());
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
        CookieSyncManager.getInstance().sync();
    }

    @SuppressLint("JavascriptInterface")
    @SuppressWarnings("unused")
    class JSInterface {

        public JSInterface() {

        }
        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void qiyezuopin(String orderJson){
            Intent intent = new Intent(getMContext(), HomeListActivity.class);
            intent.putExtra("title","优秀企业作品");
            intent.putExtra("urls", WebUri.HOMELIST);//HOMEGRLIST
            startActivity(intent);

        }
        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void gerenzuopin(String orderJson){
            Intent intent = new Intent(getMContext(), HomeListActivity.class);
            intent.putExtra("title","优秀个人作品");
            intent.putExtra("urls", WebUri.HOMEGRLIST);//HOMEGRLIST
            startActivity(intent);

        }
        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void sousuo(String s){

//            Toast.makeText(getMContext(), "diidididiid"+s, Toast.LENGTH_SHORT).show();
            setIntentClass(SearchActivity.class);

        }
        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void rencaiwang(String s){
//            Toast.makeText(getMContext(), "-------------------------", Toast.LENGTH_SHORT).show();
            EventBus.getDefault().post(new MessageEvent("jumprencai"));

        }
   @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void rencaiqiuzhi(String s){
//            Toast.makeText(getMContext(), "-------------------------", Toast.LENGTH_SHORT).show();
            EventBus.getDefault().post(new MessageEvent("jumprencai"));
            EventBus.getDefault().post(new MessageEvent("jrrc"));

        } @JavascriptInterface
        public void qingjian(String s){
//            Toast.makeText(getMContext(), "-------------------------", Toast.LENGTH_SHORT).show();
            EventBus.getDefault().post(new MessageEvent("jumpqingjian"));


        }
        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void yinglouzhaopin(String s){
//            Toast.makeText(getMContext(), "-------------------------", Toast.LENGTH_SHORT).show();
            EventBus.getDefault().post(new MessageEvent("jumprencai"));
            EventBus.getDefault().post(new MessageEvent("jryl"));

        }


        @JavascriptInterface
        public void logIn(String str){

//            Log.e("diaoyong?","``````````````````````");
            Log.e("diaoyong?","``````````````````````");
            startActivity(new Intent(getMContext(), LoginActivity.class));
        }
        @JavascriptInterface
        public void logIn(){
            Log.e("diaoyong?","``````````````````````");
            startActivity(new Intent(getMContext(), LoginActivity.class));
        }


        @JavascriptInterface
        public void opennewview(String orderJson){
//            WebView
            try {
//                Toast.makeText(getMContext(), ""+orderJson, Toast.LENGTH_SHORT).show();
                JSONObject jsonObject = new JSONObject(orderJson);
                String webUrl = jsonObject.getString("url");
                Log.e("kevin","ist"+webUrl);
                String title = "title";
                Intent intent = new Intent(getMContext(),WebActivity.class);
                intent.putExtra("title",title);
                intent.putExtra("urls",webUrl);
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
        public void mokuai(String orderJson){
//            WebView
//            Toast.makeText(getMContext(), "orderJson"+orderJson, Toast.LENGTH_SHORT).show();
            try {

                JSONObject jsonObject = new JSONObject(orderJson);
                String webUrl = jsonObject.getString("link");

                String title = jsonObject.getString("title");
                Intent intent = new Intent(getMContext(),WebActivity.class);
                intent.putExtra("title",title);
                intent.putExtra("urls",webUrl);
                startActivity(intent);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {

        if(messageEvent.getMessage().equals("loginout")){
            cleanWebCache();
            webView.reload();
        }

        if(messageEvent.getMessage().equals("rcwfinish")){
            webView.reload();
            if(!MyApplication.getToken().equals("")){
//                synCookies(getMContext(),WEBURI);
                CookieSyncManager.getInstance().sync();
                Log.e("zoulezhe","!");
            }
        }
    }

    public void  cleanWebCache (){

        //清空所有Cookie
        CookieSyncManager.createInstance(getMContext());  //Create a singleton CookieSyncManager within a context
        CookieManager cookieManager = CookieManager.getInstance(); // the singleton CookieManager instance
        cookieManager.removeAllCookie();// Removes all cookies.
        CookieSyncManager.getInstance().sync(); // forces sync manager to sync now

    }


    @OnClick(R.id.jump_message)
    public void doJumpMessageActivity(){

        startActivity(new Intent(getMContext(), MessageActivity.class));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(EventBus.getDefault().isRegistered(          this)) {
            EventBus.getDefault().unregister(this);
        }

    }
}
