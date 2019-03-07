package com.quanying.app.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import butterknife.BindView;
import butterknife.ButterKnife;
import com.quanying.app.MyApplication;
import com.quanying.app.R;
import com.quanying.app.alipay.AlipayUtil;
import com.quanying.app.alipay.PayResult;
import com.quanying.app.bean.AnyEventType;
import com.quanying.app.wxapi.WXPayUtil;
import org.greenrobot.eventbus.EventBus;

public class LoginWebActivity extends FragmentActivity {

  @BindView(R.id.webview)
  WebView webView;

  private String CAHCEDIR = null;
  private static String IMAGE_CACHE_DIR_1 = "qyapp/files/imgcache1";
  private String playUrl;
  private JSInterface jsInterface = new JSInterface();

  private void initWebView() {
    webView.getSettings().setJavaScriptEnabled(true);
    webView.getSettings().setUserAgentString(webView.getSettings().getUserAgentString() + "qyApp");
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

    playUrl = "http://m.7192.com/passport/loginzb";

    webView.loadUrl(playUrl);
    Log.e("load?",":"+playUrl);
    String CookieStr = cookieManager.getCookie(playUrl);
    Log.e("kevincookies",":"+CookieStr);
    MyApplication.Cookies = CookieStr;
    CookieSyncManager.createInstance(LoginWebActivity.this);
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
          cookieManager.removeAllCo okie();
          cookieManager.setCookie(MyHttpClient.getHost(context), MyHttpClient.getHttpCookies());//cookies是在HttpClient中获得的cookie
          CookieSyncManager.getInstance().sync();
      }
      */
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
      Log.e("kanknapay",orderJson);
      try {
        AlipayUtil.doPay(LoginWebActivity.this, handler, orderJson);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        Toast.makeText(LoginWebActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
        webView.loadUrl("javascript:pay_sta('fail','ali')");
      }
    }

    @JavascriptInterface
    public void wxPay(String orderJson) {
      Log.e("kanknapay",orderJson);
      WXPayUtil.doPay(LoginWebActivity.this, orderJson);
    }
    @JavascriptInterface
    public void close_wv(String orderJson) {
      finish();
      EventBus.getDefault().post(new AnyEventType());
    }

  }

  public Handler handler = new Handler() {
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case AlipayUtil.SDK_PAY_FLAG: {
          PayResult payResult = new PayResult((String) msg.obj);
          /**
           * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
           * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
           * docType=1) 建议商户依赖异步通知
           */
          String resultInfo = payResult.getResult();// 同步返回需要验证的信息

          String resultStatus = payResult.getResultStatus();
          // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
          if (TextUtils.equals(resultStatus, "9000")) {
            Toast.makeText(LoginWebActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
            // webView.loadUrl(AlipayUtil.RETURN_URL);
            webView.loadUrl("javascript:pay_sta('success','ali')");
          } else {
            // 判断resultStatus 为非"9000"则代表可能支付失败
            // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
            if (TextUtils.equals(resultStatus, "8000")) {
              Toast.makeText(LoginWebActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
              webView.loadUrl("javascript:pay_sta('other','ali')");
            } else {
              // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
              Toast.makeText(LoginWebActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
              webView.loadUrl("javascript:pay_sta('fail','ali')");
            }
          }
          break;
        }
        case WXPayUtil.WX_PAY_STATUS: {
          int code = (Integer) msg.obj;
          if (code == 0) {
            webView.loadUrl("javascript:pay_sta('success','wx')");
          } else if (code == -1) {
            webView.loadUrl("javascript:pay_sta('fail','wx')");
          } else if (code == -2) {
            webView.loadUrl("javascript:pay_sta('cancel','wx')");
          }
          break;
        }
        default:
          break;
      }
    };
  };

}
