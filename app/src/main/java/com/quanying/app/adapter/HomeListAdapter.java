package com.quanying.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.quanying.app.R;
import com.quanying.app.bean.HomeListBean;
import com.quanying.app.ui.showroominsidepage.ShowRoominsideActivity;

import java.util.List;

public class HomeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private HomeListBean mBean;
    private List<HomeListBean.DataBean> mList;
    private Context context;
    private static final int TYPE_NORMAL = 1;
    private RequestOptions options;


    public HomeListAdapter(HomeListBean list, Context context) {
        this.mList = list.getData();
        this.context = context;
    }


//
//    public void addAll(HomeListBean roomBean) {
//        mList.addAll(roomBean.getData());
//    }

    public void addAll(List<HomeListBean.DataBean> list) {
        int lastIndex = this.mList.size();
        if (this.mList.addAll(list)) {
//            notifyItemMoved(lastIndex, list.size());
//            notifyItemInserted(lastIndex);
            notifyDataSetChanged();
        }
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_imgcard,null));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        final ViewHolder vh = (ViewHolder) viewHolder;
        vh .setIsRecyclable(true);
        final HomeListBean.DataBean mBean = mList.get(i);


        Uri uri = Uri.parse(mBean.getFace());
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.evictFromMemoryCache(uri);
        imagePipeline.evictFromDiskCache(uri);
//                                AppSharePreferenceMgr.set(getMContext(),"userid","");
// combines above two lines
        imagePipeline.evictFromCache(uri);
        vh.head_img.setImageURI(uri);

        RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
        roundingParams.setRoundAsCircle(true);
        vh.head_img.getHierarchy().setRoundingParams(roundingParams);


        vh.home_username.setText(mBean.getNickname()+"");
        vh.home_context.setText(mBean.getTitle());
        vh.home_sum.setText("共"+mBean.getImgnum()+"张");

        options = new RequestOptions();
        options.placeholder(R.mipmap.nowebimg);
        options.error(R.mipmap.nowebimg);

        Glide.with( context )
                .load( mBean.getThumb())
                .apply(options)//图片加载出来前，显示的图片
                //.error(R.mipmap.nowebimg)//图片加载失败后，显示的图片
                .into(  vh.zp_fm);

        vh.zp_fm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowRoominsideActivity.class);
                intent .putExtra("ids",mBean.getId());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        if (mList.size() > 0) {
            return mList.size();
        }
        return 0;

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


    class ViewHolder extends RecyclerView.ViewHolder{

        TextView home_username;  //用户标签
        TextView home_context;  //用户标签
        TextView home_sum;  //用户标签
        SimpleDraweeView  head_img; //用户头像
        ImageView zp_fm;
  

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            home_username = itemView.findViewById(R.id.home_username);
            home_context = itemView.findViewById(R.id.home_context);
            home_sum = itemView.findViewById(R.id.home_sum);
            head_img = itemView.findViewById(R.id.head_img);
            zp_fm = itemView.findViewById(R.id.zp_fm);
        
        }
    }

}
