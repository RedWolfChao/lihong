package com.quanying.app.ui.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.quanying.app.R;
import com.quanying.app.adapter.SearchAdapter;
import com.quanying.app.adapter.SearchEndAdapter;
import com.quanying.app.app.MyApplication;
import com.quanying.app.bean.SearchBean;
import com.quanying.app.bean.SearchEndBean;
import com.quanying.app.ui.base.BaseActivity;
import com.quanying.app.utils.AppKeyBoardMgr;
import com.quanying.app.weburl.WebUri;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class SearchActivity extends BaseActivity {


    @BindView(R.id.recyclerview)
    RecyclerView mRv;
    @BindView(R.id.search_end_rv)
    RecyclerView search_end_rv;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.back_btn)
    TextView back_btn;

    private List<SearchBean> mList;
    SearchAdapter mAdapter;

    private boolean isCha;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initData() {

        LinearLayoutManager layoutManage = new LinearLayoutManager(context);
        mRv.setLayoutManager(layoutManage);
        search_end_rv.setLayoutManager(new LinearLayoutManager(context));
        OkHttpUtils
                .post()
                .url(WebUri.HOTSEARCH)
                .addParams("token", MyApplication.getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Log.e("resou",response);
                        SearchBean mBean = new Gson().fromJson(response,SearchBean.class);

                        mAdapter = new SearchAdapter(context,mBean);
                        mRv.setAdapter(mAdapter);

                    }
                });
//        getSearchEnd("好");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppKeyBoardMgr.closeKeybord(et_search,context);

    }

    public void getSearchEnd(String str){

        OkHttpUtils
                .post()
                .url(WebUri.SEARCHEND)
                .addParams("key",str)
                .addParams("token",MyApplication.getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        isCha = false;
                        Log.e("searchend",response);
                        SearchEndBean searchEndBean = new Gson().fromJson(response,SearchEndBean.class);
                        if(searchEndBean.getErrcode().equals("200")){

                            search_end_rv.setVisibility(View.VISIBLE);
                            mRv.setVisibility(View.GONE);

                            SearchEndAdapter sAdapter = new SearchEndAdapter(context,searchEndBean);
                            search_end_rv.setAdapter(sAdapter);


                        }else{
                            showBaseDialog("未查询到结果，建议您修改搜索词","好");
                        }


                    }
                });
    }


    @Override
    protected void initView() {

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppKeyBoardMgr.closeKeybord(et_search,context);
                finish();
            }
        });
        et_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_ENTER) {

                    if(!isCha){
                    isCha = true;
//                        Toast.makeText(context, "heheheheh", Toast.LENGTH_SHORT).show();
                    getSearchEnd(et_search.getText().toString().trim());}
                    // do some your things
                }   if (keyCode == event.KEYCODE_SEARCH) {
                    // do some your things
                 /*   Toast.makeText(context, "lalalal", Toast.LENGTH_SHORT).show();
                    getSearchEnd(et_search.getText().toString().trim());*/
                }
                return false;
            }
        });


    }
}
