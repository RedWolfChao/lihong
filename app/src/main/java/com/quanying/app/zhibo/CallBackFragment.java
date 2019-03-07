package com.quanying.app.zhibo;

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
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.quanying.app.R;
import com.quanying.app.app.MyApplication;
import com.quanying.app.weburl.WebUri;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CallBackFragment extends Fragment {

  @BindView(R.id.webview)
  WebView webView;

  @BindView(R.id.err_page)
  LinearLayout err_page;

  private Unbinder unbinder;
  private String CAHCEDIR = null;
  private static String IMAGE_CACHE_DIR_1 = "qyapp/files/imgcache1";

  private JSInterface jsInterface = new JSInterface();
  private String playUrl;

  public static CallBackFragment newInstance(){
    CallBackFragment f = new CallBackFragment();
    Bundle b = new Bundle();
    b.putString("fragment","CallBackFragment");
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
    //webview.loadUrl("http://tv.7192.com/chat/23.html");
  }

  private void initWebView() {
    webView.getSettings().setJavaScriptEnabled(true);
    webView.getSettings().setUserAgentString(webView.getSettings().getUserAgentString() +WebUri.USERAGAENT);
    webView.addJavascriptInterface(jsInterface, "qyapp");
    webView.setWebViewClient(client);
    webView.setWebChromeClient(chromeClient);
    //webView.addJavascriptInterface(jsInterface, "qyapp");

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
    if(MyApplication.PLAYSTATUS.equals("2")) {
      playUrl = "http://tv.7192.com/cb/" + MyApplication.PLAYCALLBACKID + ".html?islive=2";
    }else{
      playUrl = "http://tv.7192.com/cb/" + MyApplication.PLAYID + ".html";

    }
    Log.e("shenmeyisi",":"+playUrl);
    webView.loadUrl(playUrl);
    String CookieStr = cookieManager.getCookie(playUrl);

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

  private boolean loadError = false;
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
      loadError = true;
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
      super.onReceivedHttpAuthRequest(view, handler, host, realm);
    }

  };

  class JSInterface {
    public JSInterface() {

    }

    @JavascriptInterface
    public void playvideo(String str) {
      Log.e("playlive",str);
      try {
        JSONObject jsonObject = new JSONObject(str);
        String playid = jsonObject.getString("id");
        String playcid = jsonObject.getString("cid");

        Intent intent = new
            Intent(getActivity(),DbUIActivity.class);
        //在Intent对象当中添加一个键值对
        MyApplication.PLAYCALLBACKID = playid;
        MyApplication.PLAYCID = playcid;

        if(MyApplication.PLAYSTATUS.equals("2")) {
      /*    AnyEventType ae = new AnyEventType();
          ae.setCallBackStatus("1");*/
          EventBus.getDefault().post(new AnyEventType());
          return;
        }
        startActivity(intent);




      } catch (JSONException e) {
        e.printStackTrace();
      }

    }

  }

}
