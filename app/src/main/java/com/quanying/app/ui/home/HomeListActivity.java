package com.quanying.app.ui.home;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.quanying.app.R;
import com.quanying.app.adapter.HomeListAdapter;
import com.quanying.app.app.MyApplication;
import com.quanying.app.bean.HomeListBean;
import com.quanying.app.ui.base.BaseActivity;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import okhttp3.Call;

public class HomeListActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    LRecyclerView recyclerview;
    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;
    private HomeListAdapter mAdapter;
    private String lastId;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private Intent intent;
    private TextView tvCenter;
    public final static String TITLE = "title";

    private String HTTPURL ;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_home_list;
    }

    @Override
    protected void initData() {
        OkHttpUtils
                .post()
                .url(HTTPURL)
                .addParams("token", MyApplication.getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("homeresp",e.toString());
                    }
                    @Override
                    public void onResponse(String response, int id) {

                        Log.e("homeresp",response);
                        Log.e("offset","3");

                        HomeListBean showRoomBean = new Gson().fromJson(response,HomeListBean.class);
                        if(showRoomBean.getErrcode().equals("200")){

                            mAdapter = new HomeListAdapter(showRoomBean,context);

                            int size = showRoomBean.getData().size();
                            if(size>0){
                                lastId = showRoomBean.getData().get(size-1).getId();
                            }

                            mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
                            recyclerview.setAdapter(mLRecyclerViewAdapter);

                        }
}

                });
    }

    @Override
    protected void initView() {

        intent = getIntent();
        tvCenter = new TextView(context);
        tvCenter.setText(intent.getStringExtra(TITLE));
        tvCenter.setTextColor(getResources().getColor(R.color.white));
        tvCenter.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        tvCenter.setGravity(Gravity.CENTER);
        tvCenter.setSingleLine(true);
        titlebar.setCenterView(tvCenter);

        titlebar.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    finish();
                }
            }
        });


        HTTPURL = intent.getStringExtra("urls");


        recyclerview.setLayoutManager(new GridLayoutManager(this,2));
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

    private void loadMoreData() {


        OkHttpUtils
                .post()
                .url(HTTPURL)
                .addParams("token", MyApplication.getToken())
                .addParams("offset", "5")
                .addParams("page",lastId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("xiangmubaocuo",response);
//                        Toast.makeText(getMContext(), ""+response, Toast.LENGTH_SHORT).show();


                        HomeListBean showRoomBean = new Gson().fromJson(response,HomeListBean.class);
                        if(showRoomBean.getErrcode().equals("200")){
//                            SRLatestAdapter mAdapter = new SRLatestAdapter(showRoomBean,getMContext());
//                            recyclerview.setAdapter(mAdapter);
//                            mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
//                            recyclerview.setAdapter(mLRecyclerViewAdapter);
                            int size = showRoomBean.getData().size();
                            if(size>0) {
                                lastId = showRoomBean.getData().get(size - 1).getId();
                                mAdapter.addAll(showRoomBean.getData());
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
