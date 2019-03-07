package com.qyw.newdemo.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.qyw.newdemo.R;
import com.qyw.newdemo.app.entity.HomeFragmentEntity;
import com.qyw.newdemo.app.entity.HomePageHeaderBean;

import java.util.List;

public class HomePageHeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    HomePageHeaderBean homePageHeaderBean;

    //    构造
    public HomePageHeaderAdapter(Context mContext, List<HomeFragmentEntity> mHomeList) {
        this.mContext = mContext;
        this.homePageHeaderBean = homePageHeaderBean;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return homePageHeaderBean.getInfo().getImages().size();
    }


    /*
     * 推荐企业
     * */
    static class ShowImageHolder extends RecyclerView.ViewHolder {

        public RecyclerView mRecyclerView;

        public ShowImageHolder(View itemView) {
            super(itemView);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.mItemRecyclerView);
        }
    }

    /*
     * 推荐企业
     * */
    static class ImageItemHolder extends RecyclerView.ViewHolder {

        public RecyclerView mRecyclerView;

        public ImageItemHolder(View itemView) {
            super(itemView);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.mItemRecyclerView);
        }
    }



}
