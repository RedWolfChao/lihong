package com.quanying.app.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quanying.app.R;
import com.quanying.app.app.MyApplication;
import com.quanying.app.bean.UpBean;
import com.quanying.app.bean.UserPageHeadBean;
import com.quanying.app.weburl.WebUri;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/8/9 0009.
 */

public class UserPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private UserPageHeadBean mHomeList;
    public List<UpBean> zpBean;

    private static final int HOLDER_TYPE_HEAD = 1;
    private static final int HOLDER_TYPE_FOOT = 2;


    //    构造
    public UserPageAdapter(Context mContext, UserPageHeadBean mHomeList,List<UpBean> zpBean) {
        this.mContext = mContext;
        this.mHomeList = mHomeList;
        this.zpBean = zpBean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder = null;
        if (viewType == HOLDER_TYPE_HEAD) {
            view = LayoutInflater.from(mContext).
                    inflate(R.layout.item_userpagegr_head, parent, false);
            holder = new DisplayViewHolder(view);
        }else if(viewType == HOLDER_TYPE_FOOT){
            Log.e("shenmegui","zoule");
            view = LayoutInflater.from(mContext).
                    inflate(R.layout.item_comments, parent, false);
            holder = new CommentsHolder(view);
        }
        return holder;
    }
    //
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

//        UserPageHeadBean mEntity = mHomeList.get(position);
//        holder.setIsRecyclable(false);
        if (holder instanceof DisplayViewHolder) {
            final DisplayViewHolder bannerViewHolder = (DisplayViewHolder) holder;
            bannerViewHolder.up_gr_fs.setText(mHomeList.getFansnum());
            bannerViewHolder.up_gr_gz.setText(mHomeList.getCarenum());
            bannerViewHolder.up_gr_sigin.setText(mHomeList.getInfo().getSign());
            if(!mHomeList.getUid().equals(MyApplication.getUserID())){

                bannerViewHolder.up_gr_sx.setVisibility(View.VISIBLE);
                bannerViewHolder.up_gr_gz_btn.setVisibility(View.VISIBLE);
                if(!mHomeList.getHascare().equals("2")){

                    bannerViewHolder.up_gr_gz_btn.setImageResource(R.mipmap.redxin);
                    bannerViewHolder.up_gr_gz_btn.setClickable(false);

                }


            }
            bannerViewHolder.up_gr_sx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
             /*       Intent intent = new Intent(mContext, SendMessageActivity.class);
                    intent.putExtra(SendMessageActivity.HXID,mHomeList.getHx_name());
                    intent.putExtra(SendMessageActivity.TITLE,mHomeList.getInfo().getNickname());
                    mContext.startActivity(intent);
*/
/*
                    // 跳转到聊天界面，开始聊天
                    Intent intent = new Intent(mContext, ECChatActivity.class);
                    intent.putExtra("ec_chat_id", mHomeList.getHx_name());
                    mContext.startActivity(intent);*/
                }
            });



            bannerViewHolder.up_gr_gz_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    bannerViewHolder.up_gr_gz_btn.setImageResource(R.mipmap.redxin);

                    OkHttpUtils
                            .post()
                            .url(WebUri.ADDFOCUS)
                            .addParams("token", MyApplication.getToken())
                            .addParams("userid",mHomeList.getUid()+"")
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
//                                            vh.add_img.setClickable(false);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });

                }
            });


//            initRecyclerView(bannerViewHolder);
        }else if(holder instanceof CommentsHolder){
            CommentsHolder ViewHolder = (CommentsHolder) holder;
            initCommentsRecyclerView(ViewHolder);
//            Log.e("zouleme","zoule");
        }

    }

    private void initCommentsRecyclerView(final CommentsHolder viewHolder) {


//        viewHolder.mRecyclerView.setLayoutManager(new ExpandLinearLayoutManager(mContext));
//        viewHolder.mRecyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 1,mContext. getResources().getColor(R.color.dividing_light)));
        viewHolder.mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext){
            @Override
            public boolean canScrollVertically() {
                //解决ScrollView里存在多个RecyclerView时滑动卡顿的问题
                //如果你的RecyclerView是水平滑动的话可以重写canScrollHorizontally方法
                return false;
            }

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        });
//解决数据加载不完的问题
        viewHolder.mRecyclerView.setNestedScrollingEnabled(false);
        viewHolder.mRecyclerView.setHasFixedSize(true);
//解决数据加载完成后, 没有停留在顶部的问题
        viewHolder.mRecyclerView.setFocusable(false);


        MyCreationAdapter adapter = new MyCreationAdapter(zpBean,mContext);
        Log.e("kevinthis","lalal");
        viewHolder.mRecyclerView.setAdapter(adapter);




    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            //  表明是Banner
            return HOLDER_TYPE_HEAD;
        }else if (position == 1){
            return HOLDER_TYPE_FOOT;
        }
        return HOLDER_TYPE_FOOT;
    }

    @Override
    public int getItemCount() {
        return 2 ;
    }


    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    public void updata(List<UpBean> zpBean) {
        this.zpBean = zpBean;
        notifyDataSetChanged();
    }


    /*
        *个人主页头部部分
    * */
    static class DisplayViewHolder extends RecyclerView.ViewHolder {

        public TextView up_gr_sigin;
        public TextView up_gr_gz;
        public TextView up_gr_fs;

        public ImageView up_gr_sx;
        public ImageView up_gr_gz_btn;


        public DisplayViewHolder(View itemView) {
            super(itemView);

            up_gr_sigin = itemView.findViewById(R.id.up_gr_sigin);
            up_gr_gz = itemView.findViewById(R.id.up_gr_gz);
            up_gr_fs = itemView.findViewById(R.id.up_gr_fs);

            up_gr_sx = (ImageView) itemView.findViewById(R.id.up_gr_sx);
            up_gr_gz_btn =  itemView.findViewById(R.id.up_gr_gz_btn);



        }
    }
    /*
     * 父容器
     * */
    static class CommentsHolder extends RecyclerView.ViewHolder {

        public RecyclerView mRecyclerView;

        public CommentsHolder(View itemView) {
            super(itemView);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerview);
        }
    }


}
