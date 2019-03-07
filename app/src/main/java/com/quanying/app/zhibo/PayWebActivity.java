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
import com.quanying.app.weburl.WebUri;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Cookie;

public class PayWebActivity extends FragmentActivity {

  @BindView(R.id.webview)
  WebView webView;

  private String CAHCEDIR = null;
  private static String IMAGE_CACHE_DIR_1 = "qyapp/files/imgcache1";
  private String playUrl;
  private JSInterface jsInterface = new JSInterface();

  private void initWebView() {
    EventBus.getDefault().register(this);
    webView.getSettings().setJavaScriptEnabled(true);
    webView.getSettings().setUserAgentString(webView.getSettings().getUserAgentString() +WebUri.USERAGAENT);
    webView.setWebViewClient(client);
    webView.setWebChromeClient(chromeClient);

    webView.addJavascriptInterface(jsInterface, "qyapp");

    webView.getSettings().setUseWideViewPort(true);
    webView.getSettings().setLoadWithOverviewMode(true);

    CookieManager cookieManager = CookieManager.getInstance();

    playUrl = " http://m.7192.com/appzbcz.html";

    webView.loadUrl(playUrl);
    synCookies(PayWebActivity.this,playUrl);

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
  class JSInterface {
    public JSInterface() {

    }

    @JavascriptInterface
    public void aliPay(String orderJson) {

      Toast.makeText(PayWebActivity.this, "支付异常", Toast.LENGTH_SHORT).show();

    }

    @JavascriptInterface
    public void wxPay(String orderJson) {
      Toast.makeText(PayWebActivity.this, "支付异常", Toast.LENGTH_SHORT).show();
    }
    @JavascriptInterface
    public void close_wv(String orderJson) {
      finish();
    }

  }



  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onMessageEvent(AnyEventType event) {

    if(!event.getStatus().equals("")){
      if (event.getStatus().equals("success")){
        webView.loadUrl("javascript:pay_sta('success','wx')");
      }else if (event.getStatus().equals("fail")){
        webView.loadUrl("javascript:pay_sta('fail','wx')");
      }
    }


  }

}
