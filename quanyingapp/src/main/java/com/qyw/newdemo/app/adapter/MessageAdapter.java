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
import com.qyw.newdemo.app.entity.MessageEntity;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Administrator on 2017/8/19 0019.
 */

public class MessageAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<MessageEntity> mList;

    private static final int HOLDER_TYPE_TITLE = 1;
    private static final int HOLDER_TYPE_ITEM = 2;

    public MessageAdapter(Context mContext, List<MessageEntity> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder = null;
        if (viewType == HOLDER_TYPE_TITLE) {
            view = LayoutInflater.from(mContext).
                    inflate(R.layout.item_message_title, parent, false);
            holder = new TitleHolder(view);
        } else if (viewType == HOLDER_TYPE_ITEM) {
            view = LayoutInflater.from(mContext).
                    inflate(R.layout.item_message, parent, false);
            holder = new ItemHolder(view);
        }
        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MessageEntity mEntity = mList.get(position);
//        holder.setIsRecyclable(false);
        if (holder instanceof TitleHolder) {
            TitleHolder mTitleHolder = (TitleHolder) holder;
            mTitleHolder.message_Title1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"官方消息",Toast.LENGTH_SHORT).show();
                }
            });
            mTitleHolder.message_Title2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"作品互动",Toast.LENGTH_SHORT).show();
                }
            });
        } else if (holder instanceof ItemHolder) {
            MessageEntity mItemEntity = mList.get(position-1);
            ItemHolder itemHolder = (ItemHolder) holder;
//            itemHolder.head_Img

            itemHolder.message_Text.setText(mItemEntity.getContent_text());
            itemHolder.name_Text.setText(mItemEntity.getUser_name());
            itemHolder.time_Text.setText(mItemEntity.getTime_text());
            Glide.with(mContext).load(mItemEntity.getUsericon_url()).placeholder(R.mipmap.ic_launcher)
                    .bitmapTransform(new CropCircleTransformation(mContext))
                    .into(itemHolder.head_Img);
//            Glide.with(mContext).load(mItemEntity.getUsericon_url()).placeholder(R.mipmap.ic_launcher).into(itemHolder.head_Img);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            //  表明是标题栏
            return HOLDER_TYPE_TITLE;
        }
        position-=1;
        return HOLDER_TYPE_ITEM;
    }

    class TitleHolder extends  RecyclerView.ViewHolder{

        LinearLayout message_Title1;
        LinearLayout message_Title2;


        public TitleHolder(View itemView) {
            super(itemView);
            message_Title1 = (LinearLayout) itemView.findViewById(R.id.message_title1);
            message_Title2 = (LinearLayout) itemView.findViewById(R.id.message_title2);
        }
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        ImageView head_Img;
        TextView message_Text;
        TextView time_Text;
        TextView name_Text;

        public ItemHolder(View itemView) {
            super(itemView);
            head_Img = (ImageView) itemView.findViewById(R.id.head_img);
            message_Text = (TextView) itemView.findViewById(R.id.message_text);
            time_Text = (TextView) itemView.findViewById(R.id.time_text);
            name_Text = (TextView) itemView.findViewById(R.id.name_text);
        }
    }

}
