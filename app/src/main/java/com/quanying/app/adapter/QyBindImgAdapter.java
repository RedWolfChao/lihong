package com.quanying.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.quanying.app.R;
import com.quanying.app.bean.MyFocusBean;
import com.quanying.app.bean.QyUserPageBean;

public class QyBindImgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private RequestOptions options;
    private MyFocusBean mBean;
    private QyUserPageBean mList;
    private Context context;
    private static final int TYPE_NORMAL = 1;


    public QyBindImgAdapter(QyUserPageBean list, Context context) {
        this.mList = list;
        this.context = context;
        options = new RequestOptions();
        options.placeholder(R.mipmap.nowebimg);
        options.error(R.mipmap.nowebimg);


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_userpageimg,null));
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        final ViewHolder vh = (ViewHolder) viewHolder;
        vh .setIsRecyclable(false);
//        final QyUserPageBean.InfoBean.ImagesBean  mBean = (QyUserPageBean.InfoBean.ImagesBean) mList.getInfo().getImages();


        Glide.with( context )
                .load( mList.getInfo().getImages().get(i))
                .apply(options)//图片加载出来前，显示的图片
                //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                .into( vh.imgs);

    }

    @Override
    public int getItemCount() {
        if (mList.getInfo().getImages().size()> 0) {
            return mList.getInfo().getImages().size();
        }
        return 0;

    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView add_img; //
        ImageView imgs; //


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            add_img = itemView.findViewById(R.id.add_img);
            imgs = itemView.findViewById(R.id.imgs);


        }
    }


}
