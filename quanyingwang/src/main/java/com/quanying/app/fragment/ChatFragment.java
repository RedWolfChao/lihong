package com.quanying.app.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.HttpAuthHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.quanying.app.MyApplication;
import com.quanying.app.R;
import com.quanying.app.bean.AnyEventType;
import org.greenrobot.eventbus.EventBus;

import static com.quanying.app.R.id.webview;

/**
 * Created by Administrator on 2017/8/14 0014.
 */

public class ChatFragment extends Fragment {
    @BindView(webview)
    WebView webView;


    @BindView(R.id.err_page)
    LinearLayout err_page;
    private Unbinder unbinder;
    private String CAHCEDIR = null;
    private static String IMAGE_CACHE_DIR_1 = "qyapp/files/imgcache1";
    private String playUrl;

    private boolean loadError = false;

    public static ChatFragment newInstance(){
        ChatFragment f = new ChatFragment();
        Bundle b = new Bundle();
        b.putString("fragment","ChatFragment");
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_particulars,container,false);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initWebView();

    }

    private void initWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUserAgentString(webView.getSettings().getUserAgentString() + "qyApp");
        webView.setWebViewClient(client);
        webView.setWebChromeClient(chromeClient);
        //webView.addJavascriptInterface(jsInterface, "qyapp");

        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        CookieManager cookieManager = CookieManager.getInstance();
        if(MyApplication.PLAYSTATUS.equals("2")){
            playUrl = "http://tv.7192.com/chat/"+MyApplication.PLAYCALLBACKID+".html?islive=2";
        }else{
        playUrl = "http://tv.7192.com/chat/"+MyApplication.PLAYID+".html";}
        Log.e("chaturl",":"+playUrl);
        webView.loadUrl(playUrl);
        String CookieStr = cookieManager.getCookie(playUrl);
        Log.e("kevincookies",":"+CookieStr);
        MyApplication.Cookies = CookieStr;
        CookieSyncManager.createInstance(getActivity());
        cookieManager.setAcceptCookie(true);

        cookieManager.setCookie("http://tv.7192.com",CookieStr);//cookies是在HttpClient中获得的cookie
      //MyApplication my
        CookieSyncManager.getInstance().sync();



        /*List<Cookie> cookies = Cookie.parseAll("http://tv.7192.com/chat/23.html", headers);
        //防止header没有Cookie的情况
        if (cookies != null){
            //存储到Cookie管理器中
            client.cookieJar().saveFromResponse(loginUrl, cookies);//这样就将Cookie存储到缓存中了
        }
        MyApplication.init();*/
    }
/*
    public static void initCookieSyncManager(Context context) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeAllCookie();
        cookieManager.setCookie(MyHttpClient.getHost(context), MyHttpClient.getHttpCookies());//cookies是在HttpClient中获得的cookie
        CookieSyncManager.getInstance().sync();
    }
    */
    private WebChromeClient chromeClient = new WebChromeClient() {

    @Override
    public void onReceivedTitle(WebView view, String title) {
        //判断标题 title 中是否包含有“error”字段，如果包含“error”字段，则设置加载失败，显示加载失败的视图
        if(!TextUtils.isEmpty(title)&&title.toLowerCase().contains("error")){
            loadError = true;
        }
    }
        // Android > 4.1.1 调用这个方法
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {

        }

        // 3.0 + 调用这个方法
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {

        }

        // Android < 3.0 调用这个方法
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {

        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {

            super.onProgressChanged(view, newProgress);
        }
    };
    private WebViewClient client = new WebViewClient() {

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
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            // loadingDialog.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            if (!loadError) {//当网页加载成功的时候判断是否加载成功

                err_page.setVisibility(View.GONE);

            } else { //加载失败的话，初始化页面加载失败的图，然后替换正在加载的视图页面
                err_page.setVisibility(View.VISIBLE);
            }


            //eventBus.post(new AnyEventType event);
            // loadingDialog.dismiss();
            EventBus.getDefault().post(new AnyEventType());
            // if (extraURL != null && !extraURL.equals(URL)) {
            // view.loadUrl(extraURL);
            // }
            // extraURL = null;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            // loadingDialog.dismiss();
            // if (extraURL != null && !extraURL.equals(URL)) {
            // view.loadUrl(extraURL);
            // }
            // extraURL = null;

            loadError = true;


        }

        @Override
        public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
            super.onReceivedHttpAuthRequest(view, handler, host, realm);
        }

    };
}
