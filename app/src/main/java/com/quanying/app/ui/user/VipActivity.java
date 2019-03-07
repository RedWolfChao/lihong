package com.quanying.app.ui.user;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.quanying.app.R;
import com.quanying.app.app.MyApplication;
import com.quanying.app.bean.BuyVipBean;
import com.quanying.app.ui.base.BaseActivity;
import com.quanying.app.weburl.WebUri;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import okhttp3.Call;

public class VipActivity extends BaseActivity {

    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    BuyVipBean mBean;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_vip;
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

    }
    @Override
    protected void initData() {

        OkHttpUtils
                .post()
                .url(WebUri.BUYVIPLIST)
                .addParams("token", MyApplication.getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Log.e("vipres",response);

                        mBean = new Gson().fromJson(response,BuyVipBean.class);
                        if(mBean.getErrcode().equals("200")){

                            MyAdapter myAdapter = new MyAdapter(mBean);
                            recyclerview.setAdapter(myAdapter);

                        }

                    }
                });



    }


    private class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        BuyVipBean aBean;

        public MyAdapter(BuyVipBean Bean) {
            this.aBean = mBean;
        }

        @Override
        public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SimpleViewHolder(View.inflate(getApplicationContext(),R.layout.item_vip, null));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
            SimpleViewHolder vh = (MyAdapter.SimpleViewHolder) viewHolder;
            vh .setIsRecyclable(false);
            vh.title.setText(aBean.getData().get(i).getTitle());
            vh.price.setText(aBean.getData().get(i).getPrice());
            vh.click_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,VipPayWebActivity.class);
                    intent.putExtra("urls",aBean.getData().get(i).getDsp());
                    intent.putExtra("title","详情");
                    intent.putExtra("pc",aBean.getData().get(i).getPc());
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return aBean.getData().size();
        }

        protected class SimpleViewHolder extends RecyclerView.ViewHolder {

            TextView title;
            TextView price;
            RelativeLayout click_btn;

            public SimpleViewHolder(View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.title);
                price = itemView.findViewById(R.id.price);
                click_btn = itemView.findViewById(R.id.click_btn);
            }

        }
    }



}
