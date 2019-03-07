package com.qyw.newdemo.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qyw.newdemo.R;
import com.qyw.newdemo.app.entity.RecruitmentEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/8/12 0012.
 */

public class RecruitmentAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<RecruitmentEntity> mHomeList;

    private static final int HOLDER_TYPE_CLASSIFY = 1;
    private static final int HOLDER_TYPE_ITEM = 2;


    //    构造
    public RecruitmentAdapter(Context mContext, List<RecruitmentEntity> mHomeList) {
        this.mContext = mContext;
        this.mHomeList = mHomeList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder = null;
        if (viewType == HOLDER_TYPE_CLASSIFY) {
            view = LayoutInflater.from(mContext).
                    inflate(R.layout.recruitment_item1, parent, false);
            holder = new ClassifyHolder(view);
        } else if (viewType == HOLDER_TYPE_ITEM){
            view = LayoutInflater.from(mContext).
                    inflate(R.layout.recruitment_item2, parent, false);
            holder = new ItemHolder(view);
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            //  表明是Banner
            return HOLDER_TYPE_CLASSIFY;
        }

        return HOLDER_TYPE_ITEM;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        RecruitmentEntity mEntity = mHomeList.get(position);
//        holder.setIsRecyclable(false);
        if (holder instanceof ClassifyHolder) {

            ClassifyHolder classifyHolder = (ClassifyHolder) holder;
            classifyHolder.recuritment_leftview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"左边的",Toast.LENGTH_SHORT).show();
                }
            });
            classifyHolder.recuritment_Centerview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"中间的",Toast.LENGTH_SHORT).show();
                }
            });
            classifyHolder.recuritment_Rightview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"右边的",Toast.LENGTH_SHORT).show();
                }
            });
        }else if (holder instanceof ItemHolder) {

            ItemHolder itemViewHolder = (ItemHolder) holder;
            itemViewHolder.recruitment_Item_Pay.setText(mEntity.getRecruitment_Pay());
            itemViewHolder.recuritment_Item_Name.setText(mEntity.getRecruitment_Name());
            itemViewHolder.recuritment_Item_City.setText(mEntity.getRecruitment_City());
            itemViewHolder.recuritment_Item_Workdate.setText(mEntity.getRecruitment_Workdate());
            itemViewHolder.recruitment_Item_Post.setText(mEntity.getRecruitment_Post());
            itemViewHolder.recuritment_Item_Date.setText(mEntity.getRecruitment_Date());
            Glide.with(mContext).load(mEntity.getRecruitment_Img()).placeholder(R.mipmap.ic_launcher).into(itemViewHolder.recruitment_Item_Img);

        }

    }

    @Override
    public int getItemCount() {
        return mHomeList.size();
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    /*
* 人才网引导栏
* */
    static class ClassifyHolder extends RecyclerView.ViewHolder {

        public LinearLayout recuritment_leftview, recuritment_Centerview,recuritment_Rightview;
        public TextView recuritment_Lefttext,recuritment_Centertext,recuritment_Righttext;

        public ClassifyHolder(View itemView) {
            super(itemView);
            recuritment_leftview = (LinearLayout) itemView.findViewById(R.id.recuritment_leftview);
            recuritment_Centerview = (LinearLayout) itemView.findViewById(R.id.recuritment_centerview);
            recuritment_Rightview = (LinearLayout) itemView.findViewById(R.id.recuritment_rightview);
            recuritment_Lefttext = (TextView) itemView.findViewById(R.id.recuritment_lefttext);
            recuritment_Centertext = (TextView) itemView.findViewById(R.id.recuritment_centertext);
            recuritment_Righttext = (TextView) itemView.findViewById(R.id.recuritment_righttext);
        }
    }

    /*
* 人才网item
* */
    static class ItemHolder extends RecyclerView.ViewHolder {

        public ImageView recruitment_Item_Img;
        public TextView recruitment_Item_Post,recuritment_Item_Name,recuritment_Item_City,recuritment_Item_Workdate,recruitment_Item_Pay,recuritment_Item_Date;
        public ItemHolder(View itemView) {
            super(itemView);
            recruitment_Item_Img = (ImageView) itemView.findViewById    (R.id.recruitment_item_img);
            recruitment_Item_Post = (TextView) itemView.findViewById    (R.id.recruitment_item_post);
            recuritment_Item_Name = (TextView) itemView.findViewById    (R.id.recuritment_item_name);
            recuritment_Item_City = (TextView) itemView.findViewById    (R.id.recuritment_item_city);
            recuritment_Item_Workdate = (TextView)itemView.findViewById (R.id.recuritment_item_workdate);
            recruitment_Item_Pay = (TextView) itemView.findViewById       (R.id.recruitment_item_pay);
            recuritment_Item_Date = (TextView) itemView.findViewById       (R.id.recuritment_item_date);
        }
    }


}
