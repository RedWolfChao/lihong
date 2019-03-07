package com.quanying.app.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
import com.quanying.app.R;
import com.quanying.app.bean.UserCollectionBean;

import java.util.List;

public class ZpAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<UserCollectionBean.DataBean> mList;
    private Context context;
    private OnItemClickListener mItemClickListener;


    public ZpAdapter(UserCollectionBean list, Context context) {
        this.mList = list.getData();
        this.context = context;
    }


//
//    public void addAll(UserCollectionBean roomBean) {
//        mList.addAll(roomBean.getData());
//    }

    public void addAll(List<UserCollectionBean.DataBean> list) {
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

        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_zpsc,null));


        viewHolder.item_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(view);
            }
        });

        viewHolder.item_btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mItemClickListener.onItemLongClick(v);
                return true;
            }
        });

            return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        final ViewHolder vh = (ViewHolder) viewHolder;
        vh .setIsRecyclable(false);
        final UserCollectionBean.DataBean mBean = mList.get(i);
        vh.user_name .setText(mBean.getNickname()+"");
        vh.add_time .setText(mBean.getTimeline()+"");
        vh.zp_title .setText(mBean.getTitle()+"");


        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.nowebimg);
        options.error(R.mipmap.nowebimg);

        Uri uri = Uri.parse(mBean.getFace());
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.evictFromMemoryCache(uri);
        imagePipeline.evictFromDiskCache(uri);

// combines above two lines
        imagePipeline.evictFromCache(uri);
        vh.head_img.setImageURI(uri);

        vh.item_btn.setTag(mBean.getDataid());

        Glide.with( context )
                .load(mBean.getThumb() )
                .apply(options)//图片加载出来前，显示的图片
                //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                .into( vh.zp_bg);


    }

    @Override
    public int getItemCount() {
        if (mList.size() > 0) {
            return mList.size();
        }
        return 0;

    }

    public void updataUi(UserCollectionBean uBean) {

        this.mList = uBean.getData();
        notifyDataSetChanged();

    }

    /*

        @Override
        public int getItemViewType(int position) {
            if (position>=mList.getData().size()){
                return TYPE_FOOT;
            }
            return TYPE_NORMAL;
        }
    */
public static interface OnItemClickListener {
    void onItemClick(View view);
    void onItemLongClick(View view);
}

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView user_name;  //
        TextView add_time;  //
        SimpleDraweeView  head_img; //用户头像
        ImageView zp_bg; //
        TextView zp_title; //
        LinearLayout item_btn; //


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            user_name = itemView.findViewById(R.id.user_name);
            add_time = itemView.findViewById(R.id.add_time);
            head_img = itemView.findViewById(R.id.head_img);
            zp_bg = itemView.findViewById(R.id.zp_bg);
            zp_title = itemView.findViewById(R.id.zp_title);
            item_btn = itemView.findViewById(R.id.item_btn);


        }
    }

}
