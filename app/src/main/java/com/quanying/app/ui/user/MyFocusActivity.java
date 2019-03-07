package com.quanying.app.ui.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.quanying.app.R;
import com.quanying.app.adapter.MyFocusAdapter;
import com.quanying.app.app.MyApplication;
import com.quanying.app.bean.MyFocusBean;
import com.quanying.app.ui.base.BaseActivity;
import com.quanying.app.weburl.WebUri;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import okhttp3.Call;

public class MyFocusActivity extends BaseActivity {

    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;

    @BindView(R.id.recyclerview)
    LRecyclerView recyclerview;
    private MyFocusAdapter mAdapter;
    private String lastId;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_my_focus;
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

   /*     mAdapter.setOnViewItemClickListener(new MyFocusAdapter.OnItemClickListener()     {
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

    private void loadMoreData() {

        OkHttpUtils
                .post()
                .url(WebUri.MYFOCUS)
                .addParams("token", MyApplication.getToken())
                .addParams("page", lastId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MyFocusBean uBean = new Gson().fromJson(response, MyFocusBean.class);
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

    @Override
    protected void initData() {
//MYFOCUS

        OkHttpUtils
                .post()
                .url(WebUri.MYFOCUS)
                .addParams("token", MyApplication.getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MyFocusBean uBean = new Gson().fromJson(response, MyFocusBean.class);
                        if (uBean.getErrcode().equals("200")) {

//                            recyclerview.setAdapter(mAdapter);
                            int size = uBean.getData().size();
                            if (size > 0) {

                                lastId = uBean.getData().get(size - 1).getId();

                                if(mAdapter==null) {
                                    mAdapter = new MyFocusAdapter(uBean, context);
                                    mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
                                    recyclerview.setAdapter(mLRecyclerViewAdapter);
                                }else{

                                    mAdapter.updataUi(uBean);

                                }
                                initOper();

                            }      }

                        Log.e("focus",response);




                    }
                });
    }

    private void initOper() {
        mAdapter.setItemClickListener(new MyFocusAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = recyclerview.getChildAdapterPosition(view);
                String tag = (String) view.getTag();
//                Toast.makeText(context, ""+tag, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, UserHomePageActivity.class);
                intent .putExtra("ids",tag);
                context.startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view) {
                int position = recyclerview.getChildAdapterPosition(view);
                final String tag = (String) view.getTag();
//                Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();

                final String items[] = {"取消关注"};
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
                                            .url(WebUri.DELFOCUS)
                                            .addParams("token", MyApplication.getToken())
                                            .addParams("userid",tag)
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



   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_focus);
    }*/



