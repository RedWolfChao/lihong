package com.quanying.app.ui.message;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.quanying.app.R;
import com.quanying.app.adapter.MsgItemAdapter;
import com.quanying.app.app.MyApplication;
import com.quanying.app.bean.MessageBean;
import com.quanying.app.ui.base.BaseActivity;
import com.quanying.app.weburl.WebUri;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import okhttp3.Call;

public class MessageActivity extends BaseActivity {


    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;

    @BindView(R.id.recyclerview)
    LRecyclerView mRecyclerView;


    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private MessageBean mBean ;
    private MsgItemAdapter msgItemAdapter ;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initData() {

        OkHttpUtils
                .post()
                .url(WebUri.GETMESSAGE)
                .addParams("token", MyApplication.getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Log.e("dayinkankan",response);
                        Log.e("dayinkankantoken",MyApplication.getToken());
                        mBean = new Gson().fromJson(response,MessageBean.class);

                        if(mBean.getErrcode().equals("200")){

                       /*     int size = mBean.getData().size();
                            if (size > 0) {
                                lastId = mBean.getData().get(size - 1).getMessageid();
                            }
*/
                            mBean.getData().add(0,new MessageBean.DataBean());

                            msgItemAdapter = new MsgItemAdapter(mBean,context);
                            mLRecyclerViewAdapter = new LRecyclerViewAdapter(msgItemAdapter);
                            mRecyclerView.setAdapter(mLRecyclerViewAdapter);

                        }


                    }
                });


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

        mRecyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setPullRefreshEnabled(false);
    }



}
