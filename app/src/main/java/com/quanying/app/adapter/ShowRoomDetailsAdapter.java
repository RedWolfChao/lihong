package com.quanying.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.quanying.app.R;
import com.quanying.app.app.MyApplication;
import com.quanying.app.bean.ShowRoomDetailsBean;
import com.quanying.app.bean.ShowRoomDetailsCommentsBean;
import com.quanying.app.event.MessageEvent;
import com.quanying.app.ui.user.UserHomePageActivity;
import com.quanying.app.view.RecycleViewDivider;
import com.quanying.app.view.SquareImageView;
import com.quanying.app.weburl.WebUri;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/8/9 0009.
 */

public class ShowRoomDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ShowRoomDetailsBean mHomeList;

    private static final int HEAD = 1;
    private static final int FOOT = 3;
    private static final int CONTEXT = 2;
    private static final int CONMENTS = 4;

    private static final int IMG1 = 1;
    private static final int IMG2 = 2;
    private static final int IMG3 = 3;
    private static final int IMG4 = 4;
    private static final int IMG5 = 5;
    private static final int IMG6 = 6;
    private static final int IMG7 = 7;
    private static final int IMG8 = 8;
    private static final int IMG9 = 9;

    //    构造
    public ShowRoomDetailsAdapter(Context mContext, ShowRoomDetailsBean mHomeList) {
        this.mContext = mContext;
        this.mHomeList = mHomeList;
    }

    public void updataItem(ShowRoomDetailsBean mHomeList){

        this.mHomeList = mHomeList;
        notifyDataSetChanged();

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder = null;
        if (viewType == HEAD) {
            view = LayoutInflater.from(mContext).
                    inflate(R.layout.item_showroomhead, parent, false);
            holder = new DetailsHeadHolder(view);
        }else if(viewType == CONTEXT){
            view = LayoutInflater.from(mContext).
                    inflate(R.layout.item_details, parent, false);
            holder = new ImageViewHolder(view);
        }else if(viewType == FOOT){
            view = LayoutInflater.from(mContext).
                    inflate(R.layout.item_showroomfoot, parent, false);
            holder = new DetailsFootHolder(view);
        }else if(viewType == CONMENTS){
            view = LayoutInflater.from(mContext).
                    inflate(R.layout.item_comments, parent, false);
            holder = new CommentsHolder(view);
        }
        return holder;
    }


    //
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final ShowRoomDetailsBean mEntity = mHomeList;
//        holder.setIsRecyclable(false);
        if (holder instanceof DetailsHeadHolder) {
            DetailsHeadHolder ViewHolder = (DetailsHeadHolder) holder;
            Uri uri = Uri.parse(mHomeList.getData().getFace());
            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            imagePipeline.evictFromMemoryCache(uri);
            imagePipeline.evictFromDiskCache(uri);

// combines above two lines
            imagePipeline.evictFromCache(uri);
            ViewHolder.details_head_img.setImageURI(uri);
            ViewHolder.details_head_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,UserHomePageActivity.class);
                    intent.putExtra("ids",mHomeList.getData().getUserid());
                    mContext.startActivity(intent);

                }
            });

            ViewHolder.jump_userpage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,UserHomePageActivity.class);
                    intent.putExtra("ids",mHomeList.getData().getUserid());
                    mContext.startActivity(intent);

                }
            });

            ViewHolder.details_name.setText(mEntity.getData().getNickname());
            ViewHolder.details_time.setText(mEntity.getData().getTimeline());
            ViewHolder.details_title.setText(mEntity.getData().getTitle());

        }else if(holder instanceof ImageViewHolder){
            ImageViewHolder ViewHolder = (ImageViewHolder) holder;
            initRecyclerView(ViewHolder,mEntity.getData().getImages());

        }else if(holder instanceof CommentsHolder){
            CommentsHolder ViewHolder = (CommentsHolder) holder;
            initCommentsRecyclerView(ViewHolder,mEntity.getData().getId());

        }else if(holder instanceof DetailsFootHolder){
            final DetailsFootHolder ViewHolder = (DetailsFootHolder) holder;
//            initRecyclerView(ViewHolder,mEntity.getData().getImages());
            ViewHolder.details_content.setText(mEntity.getData().getDsp());
            ViewHolder.details_zan_nub.setText(mEntity.getData().getZannum());
            ViewHolder.details_msg_nub.setText(mEntity.getData().getPlnum());
            final String isDianZan = mEntity.getData().getZansta();
            final String isShouCang = mEntity.getData().getFavsta();
            if(!isDianZan.equals("no")){

                ViewHolder.details_zanbtn.setImageResource(R.mipmap.zan_c);
                ViewHolder.details_zanbtn.setClickable(false);

            }
            if(isShouCang.equals("yes")){

                ViewHolder.details_star.setImageResource(R.mipmap.shoucang_c);
                ViewHolder.details_star.setClickable(false);

            }

            /*
            * 点赞
            * */
            ViewHolder.details_zanbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(!isDianZan.equals("no")){
                        return;
                    }

                    int zanNub = Integer.parseInt(mEntity.getData().getZannum())+1;

//                    Toast.makeText(mContext, "点击了", Toast.LENGTH_SHORT).show();
                    ViewHolder.details_zan_nub.setText(zanNub+"");
                    ViewHolder.details_zanbtn.setImageResource(R.mipmap.zan_c);

                    OkHttpUtils
                            .post()
                            .url(WebUri.ZAN)
                            .addParams("token", MyApplication.getToken())
                            .addParams("id", mEntity.getData().getId())
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
                                            ViewHolder.details_zanbtn.setClickable(false);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });

                }
            });

            /*
            * 收藏
            * */
            ViewHolder.details_star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(isShouCang.equals("yes")){
                        return;
                    }

//                    int zanNub = Integer.parseInt(mEntity.getData().getZannum())+1;

//                    Toast.makeText(mContext, "点击了", Toast.LENGTH_SHORT).show();
                    ViewHolder.details_star.setImageResource(R.mipmap.shoucang_c);
                    ViewHolder.details_star.setClickable(false);

                    OkHttpUtils
                            .post()
                            .url(WebUri.SHOUCANG)
                            .addParams("token", MyApplication.getToken())
                            .addParams("id", mEntity.getData().getId())
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
                                            ViewHolder.details_star.setClickable(false);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });

                }
            });



        }

    }
    LRecyclerViewAdapter mLRecyclerViewAdapter;

    String Page="";
    ShowRoomCommentsAdapter adapter;
    private void initRecyclerView(ImageViewHolder mCompaniesViewHolder, List<ShowRoomDetailsBean.DataBean.ImagesBean> images) {
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        mCompaniesViewHolder.mRecyclerView.setLayoutManager(manager);
//        ArrayList<Integer> list = new ArrayList<>();
//        list.add(R.mipmap.guide1);
//        list.add(R.mipmap.guide2);
//        list.add(R.mipmap.guide3);
//        list.add(R.mipmap.guide_350_01);
//        list.add(R.mipmap.guide_350_02);
//        list.add(R.mipmap.guide_350_03);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(images);
        mCompaniesViewHolder.mRecyclerView.setAdapter(adapter);
        //  监听RecyclerView 滑动事件 当左滑超过1时 并且有数据需要添加时 进行数据添加
    }

    public void loadFoot(){

        OkHttpUtils
                .post()
                .url(WebUri.COMMENTSLIST)
                .addParams("id", loadId+"")
                .addParams("page", Page+"")

                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                                Log.e("chakanshuju",response);
                        ShowRoomDetailsCommentsBean mBean = new  Gson().fromJson(response,ShowRoomDetailsCommentsBean.class);
                        if(mBean.getErrcode().equals("200")){

//                                    ShowRoomCommentsAdapter adapter = new ShowRoomCommentsAdapter(mBean,mContext);

                            if(mBean.getData().size()>0){
                                Page = mBean.getData().get(mBean.getData().size()-1).getId();
                                if(adapter==null){


                                    adapter = new ShowRoomCommentsAdapter(mBean,mContext,loadId);
//                                mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);

                                    loadCompaniesViewHolder.mRecyclerView.setAdapter(adapter);

                                    if(mBean.getData().size()>0){

                                    }else{
                                        EventBus.getDefault().post(new MessageEvent("stop"));
                                    }
                                    return;

                                }
                                adapter.addAll(mBean);
                            }else{
//                                EventBus.getDefault().post(new MessageEvent("stop"));
                            }

//                                    mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
//
//                                    mCompaniesViewHolder.mRecyclerView.setAdapter(mLRecyclerViewAdapter);


                        }
                    }
                });

    }

    public void loadNew(){

        OkHttpUtils
                .post()
                .url(WebUri.COMMENTSLIST)
                .addParams("id", loadId+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                                Log.e("kankanshuju",response);

                        ShowRoomDetailsCommentsBean mBean = new  Gson().fromJson(response,ShowRoomDetailsCommentsBean.class);
                        if (adapter == null) {

                            adapter = new ShowRoomCommentsAdapter(mBean,mContext,loadId);
//                                mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);

                            loadCompaniesViewHolder.mRecyclerView.setAdapter(adapter);

                            if(mBean.getData().size()>0){

                            }else{
                                EventBus.getDefault().post(new MessageEvent("stop"));
                            }
                            return;

                        }else{
                        adapter.refresh(mBean);}
//                                mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);

//                        mCompaniesViewHolder.mRecyclerView.setAdapter(adapter);

                       /* if(mBean.getData().size()>0){

                        }else{
                            EventBus.getDefault().post(new MessageEvent("stop"));
                        }
*/

                        if(mBean.getData().size()>0){
                            Page = mBean.getData().get(mBean.getData().size()-1).getId();

//                            adapter.addAll(mBean);
                        }


                    }

                });

    }

        String loadId ;
    CommentsHolder loadCompaniesViewHolder ;
    private void initCommentsRecyclerView(final CommentsHolder mCompaniesViewHolder, final String ids) {
//        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
//        mCompaniesViewHolder.mRecyclerView.setLayoutManager(manager);

        loadId = ids;
        loadCompaniesViewHolder = mCompaniesViewHolder;

        mCompaniesViewHolder.mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        mCompaniesViewHolder.mRecyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 1,mContext. getResources().getColor(R.color.dividing_light)));

//        mCompaniesViewHolder


        OkHttpUtils
                .post()
                .url(WebUri.COMMENTSLIST)
                .addParams("id", ids)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Log.e("dongdong",response);
                        ShowRoomDetailsCommentsBean mBean = new  Gson().fromJson(response,ShowRoomDetailsCommentsBean.class);
                        if(mBean.getErrcode().equals("200")){
//                            if(mBean.getData().size()>0){

                                Page = mBean.getData().get(mBean.getData().size()-1).getId();

                                adapter = new ShowRoomCommentsAdapter(mBean,mContext,ids);
//                                mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);

                                mCompaniesViewHolder.mRecyclerView.setAdapter(adapter);

                                if(mBean.getData().size()>0){

                                }else{
                                    EventBus.getDefault().post(new MessageEvent("stop"));
                                }


                            /*}else{
                                Page = mBean.getData().get(mBean.getData().size()-1).getId();

                                adapter = new ShowRoomCommentsAdapter(mBean,mContext);
                                mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);

                                mCompaniesViewHolder.mRecyclerView.setAdapter(mLRecyclerViewAdapter);
//                                mLRecyclerViewAdapter.removeFooterView();
                            }*/

                        }
                    }
                });

//        RecyclerViewAdapter adapter = new RecyclerViewAdapter(images);
//        mCompaniesViewHolder.mRecyclerView.setAdapter(adapter);

    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            //  表明是Banner
            return HEAD;
        }
        if (position == 1) {

            return CONTEXT;
        }
        if (position == 2) {

            return FOOT;
        }
        if (position == 3) {

            return CONMENTS;
        }
        return CONMENTS;
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
    }


    /*
     * 详情头
     * */
    static class DetailsHeadHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView details_head_img;
        public TextView details_name;
        public TextView details_time;
        public TextView details_title;
        public LinearLayout jump_userpage;

        public DetailsHeadHolder(View itemView) {
            super(itemView);
            details_head_img = (SimpleDraweeView) itemView.findViewById(R.id.details_head_img);
            details_name = (TextView) itemView.findViewById(R.id.details_name);
            details_time = (TextView) itemView.findViewById(R.id.details_time);
            details_title = (TextView) itemView.findViewById(R.id.details_title);
            jump_userpage = itemView.findViewById(R.id.jump_userpage);
        }
    }
  /*
     * 详情尾
     * */
    static class DetailsFootHolder extends RecyclerView.ViewHolder {

        public ImageView details_zanbtn;
        public ImageView details_msgbtn;
        public ImageView details_star;
        public TextView details_content;
        public TextView details_zan_nub;
        public TextView details_msg_nub;

        public DetailsFootHolder(View itemView) {
            super(itemView);
            details_zanbtn = (ImageView) itemView.findViewById(R.id.details_zanbtn);
            details_msgbtn = (ImageView) itemView.findViewById(R.id.details_msgbtn);
            details_star = (ImageView) itemView.findViewById(R.id.details_star);
            details_content = (TextView) itemView.findViewById(R.id.details_content);
            details_zan_nub = (TextView) itemView.findViewById(R.id.details_zan_nub);
            details_msg_nub = (TextView) itemView.findViewById(R.id.details_msg_nub);
        }
    }

    /*
     * 图片排版父容器
     * */
    static class ImageViewHolder extends RecyclerView.ViewHolder {

        public RecyclerView mRecyclerView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.mItemRecyclerView);
        }
    }
    /*
     * 评论 父容器
     * */
    static class CommentsHolder extends RecyclerView.ViewHolder {

        public RecyclerView mRecyclerView;

        public CommentsHolder(View itemView) {
            super(itemView);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerview);
        }
    }

    RequestOptions options;

    private class RecyclerViewAdapter extends RecyclerView.Adapter {
        public List<ShowRoomDetailsBean.DataBean.ImagesBean>  mList;




        public RecyclerViewAdapter(List<ShowRoomDetailsBean.DataBean.ImagesBean> images) {
            mList = images;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

            options = new RequestOptions();
            options.placeholder(R.mipmap.nowebimg);
            options.error(R.mipmap.nowebimg);


            i = mList.size();
            if(i==IMG1){
                return new Image1(LayoutInflater.from(mContext).inflate(R.layout.item_photo1, viewGroup, false));
            }else if(i==IMG2){
                return new Image2(LayoutInflater.from(mContext).inflate(R.layout.item_photo2, viewGroup, false));
            }else if(i==IMG3){
                return new Image3(LayoutInflater.from(mContext).inflate(R.layout.item_photo3, viewGroup, false));
            }else if(i==IMG4){
                return new Image4(LayoutInflater.from(mContext).inflate(R.layout.item_photo4, viewGroup, false));
            }else if(i==IMG5){
                return new Image5(LayoutInflater.from(mContext).inflate(R.layout.item_photo5, viewGroup, false));
            }else if(i==IMG6){
                return new Image6(LayoutInflater.from(mContext).inflate(R.layout.item_photo6, viewGroup, false));
            }else if(i==IMG7){
                return new Image7(LayoutInflater.from(mContext).inflate(R.layout.item_photo7, viewGroup, false));
            }else if(i==IMG8){
                return new Image8(LayoutInflater.from(mContext).inflate(R.layout.item_photo8, viewGroup, false));
            }else if(i==IMG9){
                return new Image9(LayoutInflater.from(mContext).inflate(R.layout.item_photo9, viewGroup, false));
            }


            return new ImageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_photo9, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            if (viewHolder instanceof Image1) {
                Log.e("phptest",mList.get(0).getSrc()+"");
                Image1 holder = (Image1) viewHolder;
                Glide.with( mContext )
                        .load( mList.get(0).getSrc())
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img);


                holder.img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("1");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);

                    }
                });

            }else if(viewHolder instanceof Image9){

                Image9 holder = (Image9) viewHolder;
                Log.e("imgurls",""+mList.get(1).getImageurl() );
                Glide.with( mContext )
                        .load( mList.get(0).getSrc())
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img1);
                Glide.with( mContext )
                        .load( mList.get(1).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img2);
                Glide.with( mContext )
                        .load( mList.get(2).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img3);
                Glide.with( mContext )
                        .load( mList.get(3).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img4);
                Glide.with( mContext )
                        .load( mList.get(4).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img5);
                Glide.with( mContext )
                        .load( mList.get(5).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img6);
                Glide.with( mContext )
                        .load( mList.get(6).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img7);
                Glide.with( mContext )
                        .load( mList.get(7).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img8);
                Glide.with( mContext )
                        .load( mList.get(8).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img9);

                holder.img9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("9");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("8");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("7");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("6");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("5");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("4");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("3");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("2");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("1");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });



            }else if(viewHolder instanceof Image8){
                Image8 holder = (Image8) viewHolder;




                Glide.with( mContext )
                        .load( mList.get(0).getSrc())
                        .apply(options)
                        .into( holder.img1);
                Glide.with( mContext )
                        .load( mList.get(1).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img2);
                Glide.with( mContext )
                        .load( mList.get(2).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img3);
                Glide.with( mContext )
                        .load( mList.get(3).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img4);
                Glide.with( mContext )
                        .load( mList.get(4).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img5);
                Glide.with( mContext )
                        .load( mList.get(5).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img6);
                Glide.with( mContext )
                        .load( mList.get(6).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img7);
                Glide.with( mContext )
                        .load( mList.get(7).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img8);



                holder.img8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("8");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("7");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("6");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("5");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("4");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("3");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("2");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("1");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);

                    }
                });



            }else if(viewHolder instanceof Image7){
                Image7 holder = (Image7) viewHolder;
                Glide.with( mContext )
                        .load( mList.get(0).getSrc())
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img1);
                Glide.with( mContext )
                        .load( mList.get(1).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img2);
                Glide.with( mContext )
                        .load( mList.get(2).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img3);
                Glide.with( mContext )
                        .load( mList.get(3).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img4);
                Glide.with( mContext )
                        .load( mList.get(4).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img5);
                Glide.with( mContext )
                        .load( mList.get(5).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img6);
                Glide.with( mContext )
                        .load( mList.get(6).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img7);


                holder.img7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("7");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("6");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("5");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("4");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("3");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("2");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("1");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });


            }else if(viewHolder instanceof Image6){
                Image6 holder = (Image6) viewHolder;
                Glide.with( mContext )
                        .load( mList.get(0).getSrc())
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img1);
                Glide.with( mContext )
                        .load( mList.get(1).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img2);
                Glide.with( mContext )
                        .load( mList.get(2).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img3);
                Glide.with( mContext )
                        .load( mList.get(3).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img4);
                Glide.with( mContext )
                        .load( mList.get(4).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img5);
                Glide.with( mContext )
                        .load( mList.get(5).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img6);


                holder.img6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("6");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("5");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("4");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("3");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("2");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("1");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });


            }else if(viewHolder instanceof Image5){
                Image5 holder = (Image5) viewHolder;
                Glide.with( mContext )
                        .load( mList.get(0).getSrc())
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img1);
                Glide.with( mContext )
                        .load( mList.get(1).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img2);
                Glide.with( mContext )
                        .load( mList.get(2).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img3);
                Glide.with( mContext )
                        .load( mList.get(3).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img4);
                Glide.with( mContext )
                        .load( mList.get(4).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img5);


                holder.img5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("5");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("4");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("3");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("2");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("1");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });

            }else if(viewHolder instanceof Image4){
                Image4 holder = (Image4) viewHolder;
                Glide.with( mContext )
                        .load( mList.get(0).getSrc())
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img1);
                Glide.with( mContext )
                        .load( mList.get(1).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img2);
                Glide.with( mContext )
                        .load( mList.get(2).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img3);
                Glide.with( mContext )
                        .load( mList.get(3).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img4);


                holder.img4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("4");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("3");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("2");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("1");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });

            }else if(viewHolder instanceof Image3){
                Image3 holder = (Image3) viewHolder;
                Glide.with( mContext )
                        .load( mList.get(0).getSrc())
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img1);
                Glide.with( mContext )
                        .load( mList.get(1).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img2);
                Glide.with( mContext )
                        .load( mList.get(2).getSrc() )
                        .apply(options)//图片加载出来前，显示的图片
                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .into( holder.img3);


                holder.img3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("3");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("2");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("1");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });

            }else if(viewHolder instanceof Image2){
                Image2 holder = (Image2) viewHolder;
                /*
                *
                * 后期做相应修改
                * */
                Glide.with( mContext )
                        .load( mList.get(0).getSrc())

//                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
//                        .centerCrop()
                        .into( holder.img1);
                Glide.with( mContext )
                        .load( mList.get(1).getSrc() )
//                        //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                        .thumbnail( 0.2f )
                        .into( holder.img2);


                holder.img2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("2");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });
                holder.img1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("1");
                        event.setStatus("checkImg");
                        EventBus.getDefault().post(event);
                    }
                });

            }
        }

        @Override
        public int getItemCount() {
//            Toast.makeText(mContext, ""+mList.size(), Toast.LENGTH_SHORT).show();
            return 1;
        }

        @Override
        public int getItemViewType(int position) {

            Log.e("weizhi",position+"");

            return position;
        }


        class Image1 extends RecyclerView.ViewHolder {
            SquareImageView img;

            public Image1(View itemView) {
                super(itemView);
                Log.e("RedWolf", "TextViewHolder: ");
                img = (SquareImageView) itemView.findViewById(R.id.details_img1);
            }
        }
        class Image2 extends RecyclerView.ViewHolder {
            ImageView img1;
            ImageView img2;

            public Image2(View itemView) {
                super(itemView);
                Log.e("RedWolf", "TextViewHolder: ");
                img1 = (ImageView) itemView.findViewById(R.id.details_img1);
                img2 = (ImageView) itemView.findViewById(R.id.details_img2);
            }
        }
        class Image3 extends RecyclerView.ViewHolder {
            ImageView img1;
            SquareImageView img2;
            SquareImageView img3;

            public Image3(View itemView) {
                super(itemView);
                Log.e("RedWolf", "TextViewHolder: ");
                img1 =  itemView.findViewById(R.id.details_img1);
                img2 = (SquareImageView) itemView.findViewById(R.id.details_img2);
                img3 = (SquareImageView) itemView.findViewById(R.id.details_img3);
            }
        }
        class Image4 extends RecyclerView.ViewHolder {
            SquareImageView img1;
            SquareImageView img2;
            SquareImageView img3;
            SquareImageView img4;

            public Image4(View itemView) {
                super(itemView);
                Log.e("RedWolf", "TextViewHolder: ");
                img1 = (SquareImageView) itemView.findViewById(R.id.details_img1);
                img2 = (SquareImageView) itemView.findViewById(R.id.details_img2);
                img3 = (SquareImageView) itemView.findViewById(R.id.details_img3);
                img4 = (SquareImageView) itemView.findViewById(R.id.details_img4);
            }
        }
        class Image5 extends RecyclerView.ViewHolder {
            SquareImageView img1;
            SquareImageView img2;
            SquareImageView img3;
            SquareImageView img4;
            SquareImageView img5;

            public Image5(View itemView) {
                super(itemView);
                Log.e("RedWolf", "TextViewHolder: ");
                img1 = (SquareImageView) itemView.findViewById(R.id.details_img1);
                img2 = (SquareImageView) itemView.findViewById(R.id.details_img2);
                img3 = (SquareImageView) itemView.findViewById(R.id.details_img3);
                img4 = (SquareImageView) itemView.findViewById(R.id.details_img4);
                img5 = (SquareImageView) itemView.findViewById(R.id.details_img5);
            }
        }
        class Image6 extends RecyclerView.ViewHolder {
            SquareImageView img1;
            SquareImageView img2;
            SquareImageView img3;
            SquareImageView img4;
            SquareImageView img5;
            SquareImageView img6;

            public Image6(View itemView) {
                super(itemView);
                Log.e("RedWolf", "TextViewHolder: ");
                img1 = (SquareImageView) itemView.findViewById(R.id.details_img1);
                img2 = (SquareImageView) itemView.findViewById(R.id.details_img2);
                img3 = (SquareImageView) itemView.findViewById(R.id.details_img3);
                img4 = (SquareImageView) itemView.findViewById(R.id.details_img4);
                img5 = (SquareImageView) itemView.findViewById(R.id.details_img5);
                img6 = (SquareImageView) itemView.findViewById(R.id.details_img6);
            }
        }
        class Image7 extends RecyclerView.ViewHolder {
            SquareImageView img1;
            SquareImageView img2;
            SquareImageView img3;
            SquareImageView img4;
            SquareImageView img5;
            SquareImageView img6;
            SquareImageView img7;

            public Image7(View itemView) {
                super(itemView);
                Log.e("RedWolf", "TextViewHolder: ");
                img1 = (SquareImageView) itemView.findViewById(R.id.details_img1);
                img2 = (SquareImageView) itemView.findViewById(R.id.details_img2);
                img3 = (SquareImageView) itemView.findViewById(R.id.details_img3);
                img4 = (SquareImageView) itemView.findViewById(R.id.details_img4);
                img5 = (SquareImageView) itemView.findViewById(R.id.details_img5);
                img6 = (SquareImageView) itemView.findViewById(R.id.details_img6);
                img7 = (SquareImageView) itemView.findViewById(R.id.details_img7);
            }
        }
        class Image8 extends RecyclerView.ViewHolder {
            SquareImageView img1;
            SquareImageView img2;
            SquareImageView img3;
            SquareImageView img4;
            SquareImageView img5;
            SquareImageView img6;
            SquareImageView img7;
            SquareImageView img8;

            public Image8(View itemView) {
                super(itemView);
                Log.e("RedWolf", "TextViewHolder: ");
                img1 = (SquareImageView) itemView.findViewById(R.id.details_img1);
                img2 = (SquareImageView) itemView.findViewById(R.id.details_img2);
                img3 = (SquareImageView) itemView.findViewById(R.id.details_img3);
                img4 = (SquareImageView) itemView.findViewById(R.id.details_img4);
                img5 = (SquareImageView) itemView.findViewById(R.id.details_img5);
                img6 = (SquareImageView) itemView.findViewById(R.id.details_img6);
                img7 = (SquareImageView) itemView.findViewById(R.id.details_img7);
                img8 = (SquareImageView) itemView.findViewById(R.id.details_img8);
            }
        }
        class Image9 extends RecyclerView.ViewHolder {
            SquareImageView img1;
            SquareImageView img2;
            SquareImageView img3;
            SquareImageView img4;
            SquareImageView img5;
            SquareImageView img6;
            SquareImageView img7;
            SquareImageView img8;
            SquareImageView img9;

            public Image9(View itemView) {
                super(itemView);
                Log.e("RedWolf", "TextViewHolder: ");
                img1 = (SquareImageView) itemView.findViewById(R.id.details_img1);
                img2 = (SquareImageView) itemView.findViewById(R.id.details_img2);
                img3 = (SquareImageView) itemView.findViewById(R.id.details_img3);
                img4 = (SquareImageView) itemView.findViewById(R.id.details_img4);
                img5 = (SquareImageView) itemView.findViewById(R.id.details_img5);
                img6 = (SquareImageView) itemView.findViewById(R.id.details_img6);
                img7 = (SquareImageView) itemView.findViewById(R.id.details_img7);
                img8 = (SquareImageView) itemView.findViewById(R.id.details_img8);
                img9 = (SquareImageView) itemView.findViewById(R.id.details_img9);
            }
        }

    }
}
