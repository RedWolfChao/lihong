package com.quanying.app.ui.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.quanying.app.R;
import com.quanying.app.adapter.ZpAdapter;
import com.quanying.app.app.MyApplication;
import com.quanying.app.bean.UserCollectionBean;
import com.quanying.app.ui.base.BaseActivity;
import com.quanying.app.ui.showroominsidepage.ShowRoominsideActivity;
import com.quanying.app.weburl.WebUri;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Cookie;

public class ZpActivity extends BaseActivity {

    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;

    @BindView(R.id.recyclerview)
    LRecyclerView recyclerview;
    private ZpAdapter mAdapter;
    private String lastId;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;

    @BindView(R.id.show_zp)
    TextView show_zp;
 @BindView(R.id.show_tk)
    TextView show_tk;
 @BindView(R.id.webview)
 WebView webView;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_zp;
    }

    @Override
    protected void initView() {

        titlebar.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    finish();
                }
            }
        });

        recyclerview.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
//        recyclerview.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.VERTICAL, 6, getResources().getColor(R.color.gray_bkg)));
        recyclerview.setPullRefreshEnabled(false);
//add a FooterView
        recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
//                Toast.makeText(getMContext(), "gahsjiashasij", Toast.LENGTH_SHORT).show();

                loadMoreData();


            }
        });

        initWebView();

   /*     mAdapter.setOnViewItemClickListener(new ZpAdapter.OnItemClickListener()     {
                                                @Override
                                                public void onItemClick(View view) {
                                                    int position = recyclerview.getChildAdapterPosition(view);
//                                                    Toast.makeText(this, "onItemClick : " + position, Toast.LENGTH_SHORT).show();
                                                }
                                                @Override
                                                public void onItemLongClick(View view) {
                                                    int position = recyclerview.getChildAdapterPosition(view);
//                                                    Toast.makeText(this, "onItemLongClick : " + position, Toast.LENGTH_SHORT).show();
                                                }
                                            }*/



    }

    private void initWebView() {


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
        webView.addJavascriptInterface(new JSInterface(), "qyapp");
//        webView.setWebViewClient(client);
        // 设置setWebChromeClient对象
        webView.setWebChromeClient(
                new WebChromeClient() {
                    public void onProgressChanged(WebView view, int progress) {// 载入进度改变而触发
                        if (progress == 100) {
                            //handler.sendEmptyMessage(1);// 如果全部载入,隐藏进度对话框
                        }

                        super.onProgressChanged(view, progress);
                    }
                    //扩展支持alert事件
                    @Override
                    public void onReceivedIcon(WebView view, Bitmap icon) {
                        super.onReceivedIcon(view, icon);

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
                        AlertDialog.Builder b = new AlertDialog.Builder(context);
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


        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);

        synCookies(context,"http://wap.7192.com/?mod=app&act=collection");
        webView.loadUrl("http://wap.7192.com/?mod=app&act=collection");
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
        });
    }

    @OnClick(R.id.show_zp)
    public void showZp(){

        show_zp.setTextColor(getResources().getColor(R.color.colorBackground));
        show_tk.setTextColor(getResources().getColor(R.color.tab_normal_color));
        recyclerview.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);

    }
    @OnClick(R.id.show_tk)
    public void showTk(){

        show_zp.setTextColor(getResources().getColor(R.color.tab_normal_color));
        show_tk.setTextColor(getResources().getColor(R.color.colorBackground));

        recyclerview.setVisibility(View.GONE);
        webView.setVisibility(View.VISIBLE);

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
    @SuppressLint("JavascriptInterface")
    @SuppressWarnings("unused")
    class JSInterface {

        public JSInterface() {

        }

        @JavascriptInterface
        public void opennewview(String orderJson) {
//            WebView
            try {

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
    }

    private void loadMoreData() {

        OkHttpUtils
                .post()
                .url(WebUri.MYCOLLECTION)
                .addParams("token", MyApplication.getToken())
                .addParams("page", lastId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        UserCollectionBean uBean = new Gson().fromJson(response, UserCollectionBean.class);
                        if (uBean.getErrcode().equals("200")) {

//                            recyclerview.setAdapter(mAdapter);
                            int size = uBean.getData().size();
                            if (size > 0) {
                                lastId = uBean.getData().get(size - 1).getSort();
                            }
//                            mAdapter.addAll(uBean.getData());
//                            mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
//                            recyclerview.setAdapter(mLRecyclerViewAdapter);

//                        Log.e("collection",response);
                            if(size>0) {
                                lastId = uBean.getData().get(size - 1).getSort();
                                mAdapter.addAll(uBean.getData());
//                            mAdapter.notifyItemRangeInserted(0,size);
                                recyclerview.refreshComplete(size);// REQUEST_COUNT为每页加载数量
                            }else{

                                recyclerview.setNoMore(true);

                            }

                        }else{
                            recyclerview.setNoMore(true);
                        }

                    }
                });

    }

    @Override
    protected void initData() {
//MYCOLLECTION

        OkHttpUtils
                .post()
                .url(WebUri.MYCOLLECTION)
                .addParams("token", MyApplication.getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        UserCollectionBean uBean = new Gson().fromJson(response, UserCollectionBean.class);
                        if (uBean.getErrcode().equals("200")) {

//                            recyclerview.setAdapter(mAdapter);
                            Log.e("kanklanzhi",""+response);

                            int size = uBean.getData().size();
                            if (size > 0) {

                                lastId = uBean.getData().get(size - 1).getSort();

                                if(mAdapter==null) {
                                    mAdapter = new ZpAdapter(uBean, context);
                                    mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
                                    recyclerview.setAdapter(mLRecyclerViewAdapter);
                                }else{

                                    mAdapter.updataUi(uBean);

                                }
                                initOper();

                            }


//                        Log.e("collection",response);


                        }

                    }
                });
    }

    private void initOper() {
        mAdapter.setItemClickListener(new ZpAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = recyclerview.getChildAdapterPosition(view);
                String tag = (String) view.getTag();
//                Toast.makeText(context, ""+tag, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ShowRoominsideActivity.class);
                intent .putExtra("ids",tag);
                context.startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view) {
                int position = recyclerview.getChildAdapterPosition(view);
                final String tag = (String) view.getTag();
//                Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();

                final String items[] = {"删除该收藏"};
                AlertDialog dialog = new AlertDialog.Builder(context)

                        .setTitle("提示")//设置对话框的标题
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(context, items[which], Toast.LENGTH_SHORT).show();
                                if(which==0){

//                                    Toast.makeText(ZpActivity.this, "删除", Toast.LENGTH_SHORT).show();

                                    OkHttpUtils
                                            .post()
                                            .url(WebUri.DELCOLLECTION)
                                            .addParams("token",MyApplication.getToken())
                                            .addParams("id",tag)
                                            .build()
                                            .execute(new StringCallback() {
                                                @Override
                                                public void onError(Call call, Exception e, int id) {

                                                }

                                                @Override
                                                public void onResponse(String response, int id) {

                                                    try {
                                                        JSONObject jsonObject = new JSONObject(response);
                                                        if(jsonObject.getString("errcode").equals("200")){

                                                            initData();

                                                        }

                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });

                                }
                            }
                        })
                        .create();
                dialog.show();


            }
        });

    }
}
