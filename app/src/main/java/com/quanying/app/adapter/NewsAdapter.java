package com.quanying.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.quanying.app.R;
import com.quanying.app.bean.NewsBean;
import com.quanying.app.ui.user.WebActivity;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<NewsBean.DataBean> mList;
    private Context context;
    private OnItemClickListener mItemClickListener;


    public NewsAdapter(NewsBean list, Context context) {
        this.mList = list.getData();
        this.context = context;
    }


//
//    public void addAll(NewsBean roomBean) {
//        mList.addAll(roomBean.getData());
//    }

    public void addAll(List<NewsBean.DataBean> list) {
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

        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_news,null));
            return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        final ViewHolder vh = (ViewHolder) viewHolder;
        vh .setIsRecyclable(false);
        final NewsBean.DataBean mBean = mList.get(i);
        vh.news_title.setText(mBean.getTitle());
        vh.news_time.setText(mBean.getTimeline());
        vh.news_content.setText(mBean.getSubject());
        Uri uri = Uri.parse(mBean.getThumb());
        vh.viewImg.setImageURI(uri);

        vh.viewImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, WebActivity.class);
                intent .putExtra("urls",mBean.getLink());
                intent .putExtra("title","title");
                context.startActivity(intent);

            }
        });
/*        RoundingParams roundingParams = vh.viewImg.getHierarchy().getRoundingParams();

        roundingParams.setRoundAsCircle(true);
        vh.viewImg.getHierarchy().setRoundingParams(roundingParams);*/

        RoundingParams roundingParams = new RoundingParams();
        roundingParams.setCornersRadii(20, 20, 0, 0);
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(context.getResources());
        GenericDraweeHierarchy hierarchy = builder.build();
        hierarchy.setRoundingParams(roundingParams);
        vh.viewImg.setHierarchy(hierarchy);

    }

    @Override
    public int getItemCount() {
        if (mList.size() > 0) {
            return mList.size();
        }
        return 0;

    }

    public void updataUi(NewsBean uBean) {

        this.mList = uBean.getData();
        notifyDataSetChanged();

    }

    public static interface OnItemClickListener {
        void onItemClick(View view);
        void onItemLongClick(View view);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        SimpleDraweeView  viewImg; //用户头像
        TextView news_title; //
        TextView news_time; //
        TextView news_content; //


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            viewImg = itemView.findViewById(R.id.viewImg);
            news_title = itemView.findViewById(R.id.news_title);
            news_time = itemView.findViewById(R.id.news_time);
            news_content = itemView.findViewById(R.id.news_content);

        }
    }

}
