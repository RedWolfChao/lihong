package com.quanying.app.ui.user;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.quanying.app.R;
import com.quanying.app.ui.base.BaseActivity;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import butterknife.BindView;

public class WebInstructionsActivity extends BaseActivity {

    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;

    @BindView(R.id.webview)
    WebView webView ;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_web_instructions;
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

        webView.loadUrl("http://passport.7192.com/sync/agreement");
        webView.setWebViewClient(new WebViewClient() {
            //覆盖shouldOverrideUrlLoading 方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


    }
    @Override
    protected void initData() {



    }
}