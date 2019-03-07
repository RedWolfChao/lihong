package com.qyw.newdemo.app.adapter;

import android.content.Context;
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
import com.qyw.newdemo.app.entity.ShowRoomEntity;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class ShowroomListAdapter extends ArrayAdapter<ShowRoomEntity> {

    private int resourceId;
    private List<ShowRoomEntity> mList;

    public ShowroomListAdapter(Context context, int resource, List<ShowRoomEntity> objects) {
        super(context, resource,objects);
        mList = objects;
        resourceId = resource;
    }

    /**
     * 获取adapter里有多少个数据项
     */
    @Override
    public int getCount() {
        return mList.size();
    }
    @Override
    public ShowRoomEntity getItem(int position) {
        return mList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ShowRoomEntity roomEntity = getItem(position);
        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_showroom, null);
            holder = new ViewHolder();
            holder.head_Img = (ImageView) convertView.findViewById(R.id.head_img);
//            holder.production_Img = (ImageView) convertView.findViewById(R.id.production_img);
            holder.name_Text = (TextView)convertView.findViewById(R.id.name_text);
            holder.praise_Text = (TextView)convertView.findViewById(R.id.praise_text);
            holder.message_Text = (TextView)convertView.findViewById(R.id.message_text);
            holder.production_Title = (TextView)convertView.findViewById(R.id.production_text);
            holder.time_Text = (TextView)convertView.findViewById(R.id.time_text);
            holder.message_Img = (ImageView) convertView.findViewById(R.id.message_img);
            holder.praise_Img = (ImageView) convertView.findViewById(R.id.praise_img);
//            holder.jump_Btn = (ImageView) convertView.findViewById(R.id.jump_btn);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        Glide.with(getContext()).load(roomEntity.getHead_Img()).placeholder(R.mipmap.ic_launcher).into(holder.head_Img);
        Glide.with(getContext()).load(roomEntity.getHead_Img())
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(holder.head_Img);
//        Glide.with(getContext()).load(roomEntity.getProduction_Img()).placeholder(R.mipmap.ic_launcher).into(holder.production_Img);
       /* Glide.with(getContext()).load(roomEntity.getProduction_Img())
                .bitmapTransform(new BlurTransformation(getContext(), 2), new RoundedCornersTransformation(getContext(),10,10))
                .into(holder.production_Img);*/
        holder.name_Text.setText(roomEntity.getName_Text());
        holder.praise_Text.setText(roomEntity.getPraise_Text());
        holder.message_Text.setText(roomEntity.getMessage_Text());
        holder.production_Title.setText(roomEntity.getProduction_Title());
        holder.time_Text.setText(roomEntity.getTime_Text());
        holder.message_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.praise_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
/*
        holder.jump_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
*/

//        holder.name.setText(persons.get(position).getName());
//        holder.age.setText(persons.get(position).getAge());
        return convertView;
    }

    class  ViewHolder{

        ImageView head_Img;
        ImageView message_Img;
        ImageView praise_Img;
        ImageView jump_Btn;
        ImageView production_Img;
        TextView name_Text;
        TextView praise_Text;
        TextView message_Text;
        TextView production_Title;
        TextView time_Text;

    }

}
