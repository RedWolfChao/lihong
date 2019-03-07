package com.quanying.app.ui.showroominsidepage;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.quanying.app.R;
import com.quanying.app.adapter.SRLatestAdapter;
import com.quanying.app.app.MyApplication;
import com.quanying.app.bean.ShowRoomBean;
import com.quanying.app.ui.base.BaseFragment;
import com.quanying.app.view.RecycleViewDivider;
import com.quanying.app.weburl.WebUri;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import okhttp3.Call;

/**
 * @author ChayChan
 * @date 2017/6/23  11:22
 */
public class LatestFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    LRecyclerView recyclerview;

    @BindView(R.id.gank_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    public static final String CONTENT = "content";
    private TextView mTextView;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;

    private String lastId="";
    private SRLatestAdapter mAdapter;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.f_latest;
    }

    @Override
    protected void initView() {
        recyclerview.setLayoutManager(new LinearLayoutManager(getMContext(),LinearLayoutManager.VERTICAL,false));
        recyclerview.addItemDecoration(new RecycleViewDivider(getMContext(), LinearLayoutManager.VERTICAL, 6, getResources().getColor(R.color.gray_bkg)));
        recyclerview.setPullRefreshEnabled(false);
//add a FooterView
        recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
//                Toast.makeText(getMContext(), "gahsjiashasij", Toast.LENGTH_SHORT).show();

                loadMoreData("");


            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

            @Override
            public void onRefresh() {
                initData();
//                Toast.makeText(getMContext(), "shuaxin", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadMoreData(String pageId) {

        OkHttpUtils
                .post()
                .url(WebUri.GETLATESTSHOWROOM)
                .addParams("token", MyApplication.getToken())
                .addParams("page",lastId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }
                    @Override
                    public void onResponse(String response, int id) {
//                        LogUtils.json(response);
//                        Toast.makeText(getMContext(), ""+response, Toast.LENGTH_SHORT).show();

                        LogUtils.d("dianzan",response);

                        ShowRoomBean showRoomBean = new Gson().fromJson(response,ShowRoomBean.class);
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

    @Override
    protected void initData() {

        OkHttpUtils
                .post()
                .url(WebUri.GETLATESTSHOWROOM)
                .addParams("token", MyApplication.getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }
                    @Override
                    public void onResponse(String response, int id) {

                        Log.e("lalal11",response);

                        ShowRoomBean showRoomBean = new Gson().fromJson(response,ShowRoomBean.class);
                        if(showRoomBean.getErrcode().equals("200")){
                            mAdapter = new SRLatestAdapter(showRoomBean,getMContext());
//                            recyclerview.setAdapter(mAdapter);
                            int size = showRoomBean.getData().size();
                            if(size>0){
                                lastId = showRoomBean.getData().get(size-1).getId();
                            }
                            swipeRefreshLayout.setRefreshing(false);
                            mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
                            recyclerview.setAdapter(mLRecyclerViewAdapter);

                        }
                        /*} catch (JSONException e) {
                            e.printStackTrace();
                        }*/
                    }
                });
    }


}
