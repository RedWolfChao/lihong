package com.qyw.newdemo.app.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qyw.newdemo.R;
import com.qyw.newdemo.app.entity.RecruitmentEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16 0016.
 */

public class SearchResultAdapter   extends ArrayAdapter<RecruitmentEntity> {


    private int resourceId;
    private List<RecruitmentEntity> mList;
    
    public SearchResultAdapter(@NonNull Context context, @LayoutRes int resourceId, @NonNull List<RecruitmentEntity> objects) {
        super(context, resourceId, objects);
        this.resourceId = resourceId;
        this.mList = objects;
    }

    /**
     * 获取adapter里有多少个数据项
     */
    @Override
    public int getCount() {
        return mList.size();
    }
    @Override
    public RecruitmentEntity getItem(int position) {
        return mList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        RecruitmentEntity itemEntity = getItem(position);
        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.recruitment_item2, null);
            holder = new ViewHolder();

            holder. recruitment_Item_Img    = (ImageView) convertView.findViewById(R.id.recruitment_item_img);     
            holder.recruitment_Item_Post     = (TextView) convertView.findViewById(R.id.recruitment_item_post);    
            holder.recuritment_Item_Name     = (TextView) convertView.findViewById(R.id.recuritment_item_name);    
            holder.recuritment_Item_City     = (TextView) convertView.findViewById(R.id.recuritment_item_city);    
            holder.recuritment_Item_Workdate = (TextView) convertView.findViewById(R.id.recuritment_item_workdate);
            holder.recruitment_Item_Pay      = (TextView) convertView.findViewById  (R.id.recruitment_item_pay);   
            holder.recuritment_Item_Date     = (TextView) convertView.findViewById   (R.id.recuritment_item_date); 
            
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.recruitment_Item_Pay.setText(itemEntity.getRecruitment_Pay());          
        holder.recuritment_Item_Name.setText(itemEntity.getRecruitment_Name());        
        holder.recuritment_Item_City.setText(itemEntity.getRecruitment_City());        
        holder.recuritment_Item_Workdate.setText(itemEntity.getRecruitment_Workdate());
        holder.recruitment_Item_Post.setText(itemEntity.getRecruitment_Post());        
        holder.recuritment_Item_Date.setText(itemEntity.getRecruitment_Date());

        Glide.with(getContext()).load(itemEntity.getRecruitment_Img()).placeholder(R.mipmap.ic_launcher).into(holder.recruitment_Item_Img);

//        holder.age.setText(persons.get(position).getAge());


        return convertView;
    }

    class  ViewHolder{

        ImageView recruitment_Item_Img;
        TextView recruitment_Item_Post;
        TextView recuritment_Item_Name;
        TextView recuritment_Item_City;
        TextView recuritment_Item_Workdate;
        TextView recruitment_Item_Pay;
        TextView recuritment_Item_Date;

    }


}
