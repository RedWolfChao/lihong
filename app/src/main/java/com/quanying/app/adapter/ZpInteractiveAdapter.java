package com.quanying.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.quanying.app.R;
import com.quanying.app.app.MyApplication;
import com.quanying.app.bean.ZpInteractiveBean;
import com.quanying.app.event.MessageEvent;
import com.quanying.app.ui.showroominsidepage.ShowRoominsideActivity;
import com.quanying.app.ui.user.UserHomePageActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ZpInteractiveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<ZpInteractiveBean.DataBean> mList;
    private Context context;
    private OnItemClickListener mItemClickListener;


    public ZpInteractiveAdapter(ZpInteractiveBean list, Context context) {
        this.mList = list.getData();
        this.context = context;
    }


//
//    public void addAll(ZpInteractiveBean roomBean) {
//        mList.addAll(roomBean.getData());
//    }

    public void addAll(List<ZpInteractiveBean.DataBean> list) {
        int lastIndex = this.mList.size();
        if (this.mList.addAll(list)) {
//            notifyItemMoved(lastIndex, list.size());
//            notifyItemInserted(lastIndex);
            notifyDataSetChanged();
        }
    }


    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_zphd,null));

            return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        final ViewHolder vh = (ViewHolder) viewHolder;
        vh .setIsRecyclable(false);
        final ZpInteractiveBean.DataBean mBean = mList.get(i);
//        TextView name_text; //
//        TextView time_text; //
//        TextView context_; //
//        TextView show_ll_tv; //
//        ImageView show_ll_iv; //

        vh.name_text.setText(mBean.getNickname());
        vh.time_text.setText(mBean.getTimeline());
        vh.context_.setText(mBean.getContent());
        if(!TextUtils.isEmpty(mBean.getRepnote())) {
            vh.show_ll_tv.setText("我的消息：" + mBean.getRepnote());
        }else{

            Glide.with(context)
                    .load(mBean.getThumb())
                    .into(vh.show_ll_iv);
            vh.show_ll_tv.setText(mBean.getTitle());
        }

        if(mBean.getTouserid().equals(MyApplication.getUserID())){

            vh.context_tome.setVisibility(View.VISIBLE);

        }else{

            vh.context_tome.setVisibility(View.GONE);
        }

        vh.context_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MessageEvent msg = new MessageEvent("plzp");
                msg.setContext(mBean.getDataid());
                msg.setTitle(mBean.getUserid());
                msg.setStatus(mBean.getNickname());
                EventBus.getDefault().post(msg);
            }
        });

   vh.name_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MessageEvent msg = new MessageEvent("plzp");
                msg.setContext(mBean.getDataid());
                msg.setTitle(mBean.getUserid());
                msg.setStatus(mBean.getNickname());
                EventBus.getDefault().post(msg);
            }
        });
   vh.time_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MessageEvent msg = new MessageEvent("plzp");
                msg.setContext(mBean.getDataid());
                msg.setTitle(mBean.getUserid());
                msg.setStatus(mBean.getNickname());
                EventBus.getDefault().post(msg);
            }
        });   vh.context_tome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MessageEvent msg = new MessageEvent("plzp");
                msg.setContext(mBean.getDataid());
                msg.setTitle(mBean.getUserid());
                msg.setStatus(mBean.getNickname());
                EventBus.getDefault().post(msg);
            }
        });


        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.nowebimg);
        options.error(R.mipmap.nowebimg);



        vh.show_ll_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowRoominsideActivity.class);
                intent .putExtra("ids",mBean.getDataid());
                context.startActivity(intent);
            }
        });


        Uri uri = Uri.parse(mBean.getFace());
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.evictFromMemoryCache(uri);
        imagePipeline.evictFromDiskCache(uri);

// combines above two lines
        imagePipeline.evictFromCache(uri);
        vh.head_img.setImageURI(uri);

        vh.head_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                userid

                Intent intent = new Intent(context, UserHomePageActivity.class);
                intent.putExtra("ids",mBean.getUserid());
                context.startActivity(intent);
            }
        });


     /*   Glide.with( context )
                .load(mBean.getThumb() )
                .apply(options)//图片加载出来前，显示的图片
                //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                .into( vh.zp_bg);*/


    }

    @Override
    public int getItemCount() {
        if (mList.size() > 0) {
            return mList.size();
        }
        return 0;

    }

    public void updataUi(ZpInteractiveBean uBean) {

        this.mList = uBean.getData();
        notifyDataSetChanged();

    }

    public static interface OnItemClickListener {
        void onItemClick(View view);
        void onItemLongClick(View view);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        SimpleDraweeView  head_img; //用户头像
        TextView name_text; //
        TextView time_text; //
        TextView context_; //
        TextView show_ll_tv; //
        TextView context_tome; //
        ImageView show_ll_iv; //

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name_text = itemView.findViewById(R.id.name_text);
            time_text = itemView.findViewById(R.id.time_text);
            head_img = itemView.findViewById(R.id.head_img);
            context_ = itemView.findViewById(R.id.context_);
            show_ll_tv = itemView.findViewById(R.id.show_ll_tv);
            show_ll_iv = itemView.findViewById(R.id.show_ll_iv);
            context_tome = itemView.findViewById(R.id.context_tome);


        }
    }

}
