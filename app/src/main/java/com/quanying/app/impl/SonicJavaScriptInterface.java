package com.quanying.app.impl;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.quanying.app.ui.user.WebActivity;
import com.tencent.sonic.sdk.SonicDiffDataCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class SonicJavaScriptInterface {

    private final SonicSessionClientImpl sessionClient;

    private final Intent intent;
    private  Activity context;

    public static final String PARAM_CLICK_TIME = "clickTime";

    public static final String PARAM_LOAD_URL_TIME = "loadUrlTime";

    private static final int SDK_PAY_FLAG = 1;

    public SonicJavaScriptInterface(SonicSessionClientImpl sessionClient, Intent intent, Activity context) {
        this.sessionClient = sessionClient;
        this.intent = intent;
        this.context = context;
    }


    @JavascriptInterface
    public void getDiffData() {
        // the callback function of demo page is hardcode as 'getDiffDataCallback'
        getDiffData2("getDiffDataCallback");
    }
    @JavascriptInterface
    public void goback(String orderJson) {
        // the callback function of demo page is hardcode as 'getDiffDataCallback'

        Toast.makeText(context, ""+orderJson, Toast.LENGTH_SHORT).show();
        try {
            JSONObject jsonObject = new JSONObject(orderJson);

            if(jsonObject.getString("url").equals("back")){

                context.finish();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @JavascriptInterface
    public void opennewview(String orderJson){
//            WebView
        try {

            JSONObject jsonObject = new JSONObject(orderJson);
            String webUrl = jsonObject.getString("url");
            Log.e("kevin","ist"+webUrl);
            String title = "title";
            Intent intent = new Intent(context,WebActivity.class);
            intent.putExtra("title",title);
            intent.putExtra("urls",webUrl);
            context.startActivity(intent);
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
    public void getDiffData2(final String jsCallbackFunc) {
        if (null != sessionClient) {
            sessionClient.getDiffData(new SonicDiffDataCallback() {
                @Override
                public void callback(final String resultData) {
                    Runnable callbackRunnable = new Runnable() {
                        @Override
                        public void run() {
                            String jsCode = "javascript:" + jsCallbackFunc + "('"+ toJsString(resultData) + "')";
                            sessionClient.getWebView().loadUrl(jsCode);
                        }
                    };
                    if (Looper.getMainLooper() == Looper.myLooper()) {
                        callbackRunnable.run();
                    } else {
                        new Handler(Looper.getMainLooper()).post(callbackRunnable);
                    }
                }
            });
        }
    }




    @JavascriptInterface
    public String getPerformance() {
        long clickTime = intent.getLongExtra(PARAM_CLICK_TIME, -1);
        long loadUrlTime = intent.getLongExtra(PARAM_LOAD_URL_TIME, -1);
        try {
            JSONObject result = new JSONObject();
            result.put(PARAM_CLICK_TIME, clickTime);
            result.put(PARAM_LOAD_URL_TIME, loadUrlTime);
            return result.toString();
        } catch (Exception e) {

        }

        return "";
    }

    /*
    * * From RFC 4627, "All Unicode characters may be placed within the quotation marks except
    * for the characters that must be escaped: quotation mark,
    * reverse solidus, and the control characters (U+0000 through U+001F)."
    */
    private static String toJsString(String value) {
        if (value == null) {
            return "null";
        }
        StringBuilder out = new StringBuilder(1024);
        for (int i = 0, length = value.length(); i < length; i++) {
            char c = value.charAt(i);


            switch (c) {
                case '"':
                case '\\':
                case '/':
                    out.append('\\').append(c);
                    break;

                case '\t':
                    out.append("\\t");
                    break;

                case '\b':
                    out.append("\\b");
                    break;

                case '\n':
                    out.append("\\n");
                    break;

                case '\r':
                    out.append("\\r");
                    break;

                case '\f':
                    out.append("\\f");
                    break;

                default:
                    if (c <= 0x1F) {
                        out.append(String.format("\\u%04x", (int) c));
                    } else {
                        out.append(c);
                    }
                    break;
            }

        }
        return out.toString();
    }
}