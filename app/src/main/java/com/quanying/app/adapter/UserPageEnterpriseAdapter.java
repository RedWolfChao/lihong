package com.quanying.app.adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.facebook.drawee.view.SimpleDraweeView;
import com.quanying.app.R;
import com.quanying.app.app.MyApplication;
import com.quanying.app.bean.QyUserPageBean;
import com.quanying.app.bean.UpBean;
import com.quanying.app.event.MessageEvent;
import com.quanying.app.weburl.WebUri;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/8/9 0009.
 */

public class UserPageEnterpriseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private QyUserPageBean mHomeList;
    public List<UpBean> zpBean;

    private static final int HOLDER_TYPE_HEAD = 1;
    private static final int HOLDER_TYPE_FOOT = 2;
    private RequestOptions options;
    private boolean imgs_show;
    private List<String> list;
    private DisplayViewHolder bannerViewHolder;


    //    构造
    public UserPageEnterpriseAdapter(Context mContext, QyUserPageBean mHomeList, List<UpBean> zpBean) {
        this.mContext = mContext;
        this.mHomeList = mHomeList;
        this.zpBean = zpBean;
    }

    public void updataHeader(QyUserPageBean qyBean) {

        this.mHomeList = qyBean;
//        notifyDataSetChanged();
//        initHeader(bannerViewHolder);
//        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder = null;
        if (viewType == HOLDER_TYPE_HEAD) {

            LayoutInflater inflater = LayoutInflater.from(mContext);

            view = inflater.
                    inflate(R.layout.item_userpagegr_c_head, parent, false);
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

//        QyUserPageBean mEntity = mHomeList.get(position);
//        holder.setIsRecyclable(false);
        if (holder instanceof DisplayViewHolder) {
            bannerViewHolder = (DisplayViewHolder) holder;
             bannerViewHolder.up_gr_fs.setText(mHomeList.getFansnum());
            bannerViewHolder.up_gr_gz.setText(mHomeList.getCarenum());
            bannerViewHolder.up_gr_sigin.setText(mHomeList.getInfo().getSign());
            bannerViewHolder.up_gr_gz_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    bannerViewHolder.up_gr_gz_btn.setImageResource(R.mipmap.redxin);
//                    mHomeList.setHascare("2");
//                    notifyDataSetChanged();

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
//                                    Toast.makeText(mContext, "点击"+response, Toast.LENGTH_SHORT).show();
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        if(jsonObject.getString("errcode").equals("200")){
//                                            vh.add_img.setClickable(false);
                                            MessageEvent event = new MessageEvent("");
                                            event.setStatus("gx");
                                            EventBus.getDefault().post(event);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });

                }
            });

            if(!mHomeList.getUid().equals(MyApplication.getUserID())){

                if(MyApplication.getUserID().equals("")){

                    return;

                }
                bannerViewHolder.up_gr_sx.setVisibility(View.VISIBLE);
                bannerViewHolder.up_gr_gz_btn.setVisibility(View.VISIBLE);
                if(!mHomeList.getHascare().equals("2")){

                    bannerViewHolder.up_gr_gz_btn.setImageResource(R.mipmap.redxin);
//                    bannerViewHolder.up_gr_gz_btn.setClickable(false);

                }

            }else{

                bannerViewHolder.up_gr_gz_btn.setVisibility(View.INVISIBLE);

            }
            bannerViewHolder.up_gr_sx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
              /*      Intent intent = new Intent(mContext, SendMessageActivity.class);
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



            initHeader(bannerViewHolder);

            initCommentsRecyclerView(bannerViewHolder);
        }else if(holder instanceof CommentsHolder){
            Log.e("zouleme","zoule");
//            CommentsHolder ViewHolder = (CommentsHolder) holder;
//            initCommentsRecyclerView(ViewHolder);

        }

    }

    private void initHeader(DisplayViewHolder bannerViewHolder) {

        int listSize = mHomeList.getInfo().getImages().size();
        options = new RequestOptions();
        options.placeholder(R.mipmap.nowebimg);
        options.error(R.mipmap.nowebimg);
        if(listSize>0){

            if(imgs_show){
                return;
            }
            imgs_show = true;
            Log.e("changduwei",listSize+"");
            bannerViewHolder.imgsList.setVisibility(View.VISIBLE);

            list = new ArrayList<>();

//                int listSize = mHomeList.getInfo().getImages().size();

            for (int i =0;i<listSize;i++){
                list.add(mHomeList.getInfo().getImages().get(i).getSrc());
//                    Log.e()

            }

            bannerViewHolder.qy_img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkImg(0);
                }
            });     bannerViewHolder.qy_img2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkImg(1);
                }
            });     bannerViewHolder.qy_img3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkImg(2);
                }
            });     bannerViewHolder.qy_img4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkImg(3);
                }
            });     bannerViewHolder.qy_img5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkImg(4);
                }
            });

            bannerViewHolder.qy_del_btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MessageEvent event = new MessageEvent(mHomeList.getInfo().getImages().get(0).getId()+"");
                    event.setStatus("delzp");
                    EventBus.getDefault().post(event);
                }
            });
            bannerViewHolder.qy_del_btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MessageEvent event = new MessageEvent(mHomeList.getInfo().getImages().get(1).getId()+"");
                    event.setStatus("delzp");
                    EventBus.getDefault().post(event);
                }
            });
            bannerViewHolder.qy_del_btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MessageEvent event = new MessageEvent(mHomeList.getInfo().getImages().get(2).getId()+"");
                    event.setStatus("delzp");
                    EventBus.getDefault().post(event);
                }
            });
            bannerViewHolder.qy_del_btn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MessageEvent event = new MessageEvent(mHomeList.getInfo().getImages().get(3).getId()+"");
                    event.setStatus("delzp");
                    EventBus.getDefault().post(event);
                }
            });
            bannerViewHolder.qy_del_btn5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MessageEvent event = new MessageEvent(mHomeList.getInfo().getImages().get(4).getId()+"");
                    event.setStatus("delzp");
                    EventBus.getDefault().post(event);
                }
            });



            bannerViewHolder.add_imgs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MessageEvent event = new MessageEvent("");
                    event.setStatus("addzp");
                    EventBus.getDefault().post(event);
                }
            });




            switch(listSize){
                case 1:
                    bannerViewHolder.qy_img1.setVisibility(View.VISIBLE);
                    bannerViewHolder.qy_ui_img1.setVisibility(View.VISIBLE);
                    String urls_1_1 = mHomeList.getInfo().getImages().get(0).getSrc();

                    Uri uri_1_1 = Uri.parse(urls_1_1);
                    bannerViewHolder.qy_img1.setImageURI(uri_1_1);

                    if(mHomeList.getInfo().getUserid().equals(MyApplication.getUserID())){
                        bannerViewHolder.qy_del_btn1.setVisibility(View.VISIBLE);
                        bannerViewHolder.add_imgs.setVisibility(View.VISIBLE);

                    }else{
                        bannerViewHolder.qy_del_btn1.setVisibility(View.GONE);
                        bannerViewHolder.add_imgs.setVisibility(View.GONE);
                    }





                    break;
                case 2:
                    bannerViewHolder.qy_img1.setVisibility(View.VISIBLE);
                    bannerViewHolder.qy_img2.setVisibility(View.VISIBLE);

                    bannerViewHolder.qy_ui_img1.setVisibility(View.VISIBLE);
                    bannerViewHolder.qy_ui_img2.setVisibility(View.VISIBLE);

                    String urls_2_1 = mHomeList.getInfo().getImages().get(0).getSrc();
                    String urls_2_2 = mHomeList.getInfo().getImages().get(1).getSrc();

                    Uri uri_2_1 = Uri.parse(urls_2_1);
                    bannerViewHolder.qy_img1.setImageURI(uri_2_1);

                    Uri uri_2_2 = Uri.parse(urls_2_2);
                    bannerViewHolder.qy_img2.setImageURI(uri_2_2);

                    if(mHomeList.getInfo().getUserid().equals(MyApplication.getUserID())){
                        bannerViewHolder.qy_del_btn1.setVisibility(View.VISIBLE);
                        bannerViewHolder.qy_del_btn2.setVisibility(View.VISIBLE);
                        bannerViewHolder.add_imgs.setVisibility(View.VISIBLE);
                    }else{
                        bannerViewHolder.qy_del_btn1.setVisibility(View.GONE);
                        bannerViewHolder.qy_del_btn2.setVisibility(View.GONE);
                        bannerViewHolder.add_imgs.setVisibility(View.GONE);
                    }
                    break;
                case 3:
                    bannerViewHolder.qy_img1.setVisibility(View.VISIBLE);
                    bannerViewHolder.qy_img2.setVisibility(View.VISIBLE);
                    bannerViewHolder.qy_img3.setVisibility(View.VISIBLE);

                    bannerViewHolder.qy_ui_img1.setVisibility(View.VISIBLE);
                    bannerViewHolder.qy_ui_img2.setVisibility(View.VISIBLE);
                    bannerViewHolder.qy_ui_img3.setVisibility(View.VISIBLE);

                    String urls_3_1 = mHomeList.getInfo().getImages().get(0).getSrc();
                    String urls_3_2 = mHomeList.getInfo().getImages().get(1).getSrc();
                    String urls_3_3 = mHomeList.getInfo().getImages().get(2).getSrc();


                    Uri uri_3_1 = Uri.parse(urls_3_1);
                    bannerViewHolder.qy_img1.setImageURI(uri_3_1);

                    Uri uri_3_2 = Uri.parse(urls_3_2);
                    bannerViewHolder.qy_img2.setImageURI(uri_3_2);

                    Uri uri_3_3 = Uri.parse(urls_3_3);
                    bannerViewHolder.qy_img3.setImageURI(uri_3_3);

                    if(mHomeList.getInfo().getUserid().equals(MyApplication.getUserID())){
                        bannerViewHolder.qy_del_btn1.setVisibility(View.VISIBLE);
                        bannerViewHolder.qy_del_btn2.setVisibility(View.VISIBLE);
                        bannerViewHolder.qy_del_btn3.setVisibility(View.VISIBLE);
                        bannerViewHolder.add_imgs.setVisibility(View.VISIBLE);
                    }else{
                        bannerViewHolder.qy_del_btn1.setVisibility(View.GONE);
                        bannerViewHolder.qy_del_btn2.setVisibility(View.GONE);
                        bannerViewHolder.qy_del_btn3.setVisibility(View.GONE);
                        bannerViewHolder.add_imgs.setVisibility(View.GONE);
                    }
                    break;
                case 4:
                    bannerViewHolder.qy_img1.setVisibility(View.VISIBLE);
                    bannerViewHolder.qy_img2.setVisibility(View.VISIBLE);
                    bannerViewHolder.qy_img3.setVisibility(View.VISIBLE);
                    bannerViewHolder.qy_img4.setVisibility(View.VISIBLE);

                    bannerViewHolder.qy_ui_img1.setVisibility(View.VISIBLE);
                    bannerViewHolder.qy_ui_img2.setVisibility(View.VISIBLE);
                    bannerViewHolder.qy_ui_img3.setVisibility(View.VISIBLE);
                    bannerViewHolder.qy_ui_img4.setVisibility(View.VISIBLE);

                    String urls1 = mHomeList.getInfo().getImages().get(0).getSrc();
                    String urls2 = mHomeList.getInfo().getImages().get(1).getSrc();
                    String urls3 = mHomeList.getInfo().getImages().get(2).getSrc();
                    String urls4 = mHomeList.getInfo().getImages().get(3).getSrc();

                    Uri uri = Uri.parse(urls1);
                    bannerViewHolder.qy_img1.setImageURI(uri);

                    Uri uri2 = Uri.parse(urls2);
                    bannerViewHolder.qy_img2.setImageURI(uri2);

                    Uri uri3 = Uri.parse(urls3);
                    bannerViewHolder.qy_img3.setImageURI(uri3);

                    Uri uri4 = Uri.parse(urls4);
                    bannerViewHolder.qy_img4.setImageURI(uri4);

                  /*       Glide.with( mContext )
                                .load(urls2)

                                //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                                .into(bannerViewHolder.qy_img2);
                               Glide.with( mContext )
                                .load(urls3)

                                //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                                .into(bannerViewHolder.qy_img3);
                               Glide.with( mContext )
                                .load(urls4)

                                //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                                .into(bannerViewHolder.qy_img4);*/


                    if(mHomeList.getInfo().getUserid().equals(MyApplication.getUserID())){
                        bannerViewHolder.qy_del_btn1.setVisibility(View.VISIBLE);
                        bannerViewHolder.qy_del_btn2.setVisibility(View.VISIBLE);
                        bannerViewHolder.qy_del_btn3.setVisibility(View.VISIBLE);
                        bannerViewHolder.qy_del_btn4.setVisibility(View.VISIBLE);
                        bannerViewHolder.add_imgs.setVisibility(View.VISIBLE);
                    }else{
                        bannerViewHolder.qy_del_btn1.setVisibility(View.GONE);
                        bannerViewHolder.qy_del_btn2.setVisibility(View.GONE);
                        bannerViewHolder.qy_del_btn3.setVisibility(View.GONE);
                        bannerViewHolder.qy_del_btn4.setVisibility(View.GONE);
                        bannerViewHolder.add_imgs.setVisibility(View.GONE);
                    }
                    break;
                case 5:
                    bannerViewHolder.qy_img1.setVisibility(View.VISIBLE);
                    bannerViewHolder.qy_img2.setVisibility(View.VISIBLE);
                    bannerViewHolder.qy_img3.setVisibility(View.VISIBLE);
                    bannerViewHolder.qy_img4.setVisibility(View.VISIBLE);
                    bannerViewHolder.qy_img5.setVisibility(View.VISIBLE);

                    bannerViewHolder.qy_ui_img1.setVisibility(View.VISIBLE);
                    bannerViewHolder.qy_ui_img2.setVisibility(View.VISIBLE);
                    bannerViewHolder.qy_ui_img3.setVisibility(View.VISIBLE);
                    bannerViewHolder.qy_ui_img4.setVisibility(View.VISIBLE);
                    bannerViewHolder.qy_ui_img5.setVisibility(View.VISIBLE);

                    String urls_5_1 = mHomeList.getInfo().getImages().get(0).getSrc();
                    String urls_5_2 = mHomeList.getInfo().getImages().get(1).getSrc();
                    String urls_5_3 = mHomeList.getInfo().getImages().get(2).getSrc();
                    String urls_5_4 = mHomeList.getInfo().getImages().get(3).getSrc();
                    String urls_5_5 = mHomeList.getInfo().getImages().get(4).getSrc();

                    Uri uri_5 = Uri.parse(urls_5_1);
                    bannerViewHolder.qy_img1.setImageURI(uri_5);

                    Uri uri_5_2 = Uri.parse(urls_5_2);
                    bannerViewHolder.qy_img2.setImageURI(uri_5_2);

                    Uri uri_5_3 = Uri.parse(urls_5_3);
                    bannerViewHolder.qy_img3.setImageURI(uri_5_3);

                    Uri uri_5_4 = Uri.parse(urls_5_4);
                    bannerViewHolder.qy_img4.setImageURI(uri_5_4);

                    Uri uri_5_5 = Uri.parse(urls_5_5);
                    bannerViewHolder.qy_img4.setImageURI(uri_5_5);


                    if(mHomeList.getInfo().getUserid().equals(MyApplication.getUserID())){
                        bannerViewHolder.qy_del_btn1.setVisibility(View.VISIBLE);
                        bannerViewHolder.qy_del_btn2.setVisibility(View.VISIBLE);
                        bannerViewHolder.qy_del_btn3.setVisibility(View.VISIBLE);
                        bannerViewHolder.qy_del_btn4.setVisibility(View.VISIBLE);
                        bannerViewHolder.qy_del_btn5.setVisibility(View.VISIBLE);
                        bannerViewHolder.add_imgs.setVisibility(View.VISIBLE);
                    }else{
                        bannerViewHolder.qy_del_btn1.setVisibility(View.GONE);
                        bannerViewHolder.qy_del_btn2.setVisibility(View.GONE);
                        bannerViewHolder.qy_del_btn3.setVisibility(View.GONE);
                        bannerViewHolder.qy_del_btn4.setVisibility(View.GONE);
                        bannerViewHolder.qy_del_btn5.setVisibility(View.GONE);
                        bannerViewHolder.add_imgs.setVisibility(View.GONE);
                    }
                    break;

            }


        }else{

            bannerViewHolder.imgsList.setVisibility(View.GONE);

            if(mHomeList.getInfo().getUserid().equals(MyApplication.getUserID())){
                bannerViewHolder.imgsList.setVisibility(View.VISIBLE);
                bannerViewHolder.add_imgs.setVisibility(View.VISIBLE);
                bannerViewHolder.add_imgs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessageEvent event = new MessageEvent("");
                        event.setStatus("addzp");
                        EventBus.getDefault().post(event);
                    }
                });
            }

        }


    }

    public void checkImg(int position){

        int dz = position ;
        //这是你的数据


        File downloadDir = new File(Environment.getExternalStorageDirectory(), "quanying");
        BGAPhotoPreviewActivity.IntentBuilder photoPreviewIntentBuilder = new BGAPhotoPreviewActivity.IntentBuilder(mContext)
                .saveImgDir(downloadDir); // 保存图片的目录，如果传 null，则没有保存图片功能
        if (list.size() == 1) {
            // 预览单张图片
            photoPreviewIntentBuilder.previewPhoto(list.get(0));
        } else if (list.size() > 1) {
            // 预览多张图片
            photoPreviewIntentBuilder.previewPhotos((ArrayList<String>) list)
                    .currentPosition(dz); // 当前预览图片的索引
        }
        mContext.startActivity(photoPreviewIntentBuilder.build());


    }


    private void initCommentsRecyclerView(final DisplayViewHolder viewHolder) {


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
        return 1 ;
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
    public void adddata(MyCreationBean zpBean) {
        this.zpBean.getData().add((MyCreationBean.DataBean) zpBean.getData());
        notifyDataSetChanged();
    }
*/


    /*
        *个人主页头部部分
    * */
    static class DisplayViewHolder extends RecyclerView.ViewHolder {

        public TextView up_gr_sigin;
        public TextView up_gr_gz;
        public TextView up_gr_fs;

        public ImageView up_gr_sx;
        public ImageView up_gr_gz_btn;

        HorizontalScrollView imgsList;

        LinearLayout qy_showimg_item;

        RelativeLayout qy_ui_img1;
        RelativeLayout qy_ui_img2;
        RelativeLayout qy_ui_img3;
        RelativeLayout qy_ui_img4;
        RelativeLayout qy_ui_img5;
        SimpleDraweeView qy_img1;
        SimpleDraweeView qy_img2;
        SimpleDraweeView qy_img3;
        SimpleDraweeView qy_img4;
        SimpleDraweeView qy_img5;
        ImageView qy_del_btn1;
        ImageView qy_del_btn2;
        ImageView qy_del_btn3;
        ImageView qy_del_btn4;
        ImageView qy_del_btn5;
        ImageView add_imgs;

        public RecyclerView mRecyclerView;

        public DisplayViewHolder(View itemView) {
            super(itemView);

            up_gr_sigin = itemView.findViewById(R.id.up_gr_sigin);
            up_gr_gz = itemView.findViewById(R.id.up_gr_gz);
            up_gr_fs = itemView.findViewById(R.id.up_gr_fs);

            qy_ui_img1 = itemView.findViewById(R.id.qy_ui_img1);
            qy_ui_img2 = itemView.findViewById(R.id.qy_ui_img2);
            qy_ui_img3 = itemView.findViewById(R.id.qy_ui_img3);
            qy_ui_img4 = itemView.findViewById(R.id.qy_ui_img4);
            qy_ui_img5 = itemView.findViewById(R.id.qy_ui_img5);

            qy_img1 = itemView.findViewById(R.id.qy_img1);
            qy_img2 = itemView.findViewById(R.id.qy_img2);
            qy_img3 = itemView.findViewById(R.id.qy_img3);
            qy_img4 = itemView.findViewById(R.id.qy_img4);
            qy_img5 = itemView.findViewById(R.id.qy_img5);

            qy_del_btn1 = itemView.findViewById(R.id.qy_del_btn1);
            qy_del_btn2 = itemView.findViewById(R.id.qy_del_btn2);
            qy_del_btn3 = itemView.findViewById(R.id.qy_del_btn3);
            qy_del_btn4 = itemView.findViewById(R.id.qy_del_btn4);
            qy_del_btn5 = itemView.findViewById(R.id.qy_del_btn5);

            add_imgs = itemView.findViewById(R.id.add_imgs);

            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerview);

            up_gr_sx = (ImageView) itemView.findViewById(R.id.up_gr_sx);
            up_gr_gz_btn =  itemView.findViewById(R.id.up_gr_gz_btn);
            qy_showimg_item =  itemView.findViewById(R.id.qy_showimg_item);
            imgsList = (HorizontalScrollView)itemView.findViewById(R.id.qy_showimg);


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
