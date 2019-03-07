package com.quanying.app.zhibo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.HttpAuthHandler;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.quanying.app.R;
import com.quanying.app.app.MyApplication;
import com.quanying.app.weburl.WebUri;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Cookie;


public class PayPlayWebActivity extends FragmentActivity {

  @BindView(R.id.webview)
  WebView webView;

  private String CAHCEDIR = null;
  private static String IMAGE_CACHE_DIR_1 = "qyapp/files/imgcache1";
  private String playUrl;
  private JSInterface jsInterface = new JSInterface();

  private void initWebView() {
    webView.getSettings().setJavaScriptEnabled(true);
    webView.getSettings().setUserAgentString(webView.getSettings().getUserAgentString() +WebUri.USERAGAENT);
    webView.setWebViewClient(client);
    webView.setWebChromeClient(chromeClient);

    webView.addJavascriptInterface(jsInterface, "qyapp");

    webView.getSettings().setUseWideViewPort(true);
    webView.getSettings().setLoadWithOverviewMode(true);
    // webView.getSettings().setSupportMultipleWindows(true);
    //
    // webView.setWebChromeClient(new WebChromeClient() {
    // @Override
    // public boolean onCreateWindow(WebView view, boolean isDialog, boolean
    // isUserGesture, Message resultMsg) {
    // ((WebView.WebViewTransport) resultMsg.obj).setWebView(new
    // WebView(view.getContext()));
    // resultMsg.sendToTarget();
    // return true;
    // }
    // });
     /*   CAHCEDIR = StorageUtils.getOwnCacheDirectory(getActivity(), IMAGE_CACHE_DIR_1).getAbsolutePath();
        //initImageLoader(this);
        //initPickerDialog();
        // loadingDialog = new LoadingDialog(this);
        Log.e("kevincookies",":"+CAHCEDIR);*/
    CookieManager cookieManager = CookieManager.getInstance();

    playUrl = "http://tv.7192.com/home/fee/buymv.html?mvid='+id";

    if(MyApplication.PLAYSTATUS.equals("2")) {
      playUrl = "http://tv.7192.com/home/fee/buymv.html?mvid="+MyApplication.PLAYCALLBACKID;
    }else{
      playUrl = "http://tv.7192.com/home/fee/buymv.html?liveid="+MyApplication.PLAYID;

    }

  Log.e("playUrl",playUrl);
    webView.loadUrl(playUrl);
    synCookies(PayPlayWebActivity.this,playUrl);

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

  @SuppressWarnings("deprecation")
  public void synCookies(Context context, String url) {

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
    CookieSyncManager.getInstance().sync();
  }

  private WebChromeClient chromeClient = new WebChromeClient() {
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
      // loadingDialog.dismiss();

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

    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
      super.onReceivedHttpAuthRequest(view, handler, host, realm);
    }

  };
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_pay_web);
    ButterKnife.bind(this);
    initWebView();
  }


  class JSInterface {
    public JSInterface() {

    }

    @JavascriptInterface
    public void aliPay(String orderJson) {

      Toast.makeText(PayPlayWebActivity.this, "无法支付", Toast.LENGTH_SHORT).show();

    }

    @JavascriptInterface
    public void wxPay(String orderJson) {

    }
    @JavascriptInterface
    public void close_wv(String orderJson) {
      finish();
    }

  }


}
