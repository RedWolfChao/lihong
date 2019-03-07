package com.quanying.app.ui.user;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.quanying.app.R;
import com.quanying.app.adapter.MyFansAdapter;
import com.quanying.app.app.MyApplication;
import com.quanying.app.bean.MyFansBean;
import com.quanying.app.ui.base.BaseActivity;
import com.quanying.app.weburl.WebUri;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import okhttp3.Call;

public class MyFansActivity extends BaseActivity {

    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;
    private String lastId;
    private MyFansAdapter mAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    @BindView(R.id.recyclerview)
    LRecyclerView recyclerview;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_myfans;
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



    }
    @Override
    protected void initData() {

        OkHttpUtils
                .post()
                .url(WebUri.MYFANS)
                .addParams("token", MyApplication.getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MyFansBean uBean = new Gson().fromJson(response, MyFansBean.class);
                        if (uBean.getErrcode().equals("200")) {

//                            recyclerview.setAdapter(mAdapter);
                            int size = uBean.getData().size();
                            if (size > 0) {

                                lastId = uBean.getData().get(size - 1).getId();

                                if(mAdapter==null) {


                                    Log.e("fans","!!!!!!!!!!!!!!!!!!");

                                    mAdapter = new MyFansAdapter(uBean, context);
                                    mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
                                    recyclerview.setAdapter(mLRecyclerViewAdapter);
                                }else{

                                    mAdapter.updataUi(uBean);

                                }


                            }}

                        Log.e("fans",response);




                    }
                });

    }

    private void loadMoreData() {

        OkHttpUtils
                .post()
                .url(WebUri.MYFANS)
                .addParams("token", MyApplication.getToken())
                .addParams("page", lastId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MyFansBean uBean = new Gson().fromJson(response, MyFansBean.class);
                        if (uBean.getErrcode().equals("200")) {

//                            recyclerview.setAdapter(mAdapter);
                            int size = uBean.getData().size();
                            if (size > 0) {
                                lastId = uBean.getData().get(size - 1).getId();
                            }
//                            mAdapter.addAll(uBean.getData());
//                            mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
//                            recyclerview.setAdapter(mLRecyclerViewAdapter);

//                        Log.e("collection",response);
                            if(size>0) {
                                lastId = uBean.getData().get(size - 1).getId();
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


}
