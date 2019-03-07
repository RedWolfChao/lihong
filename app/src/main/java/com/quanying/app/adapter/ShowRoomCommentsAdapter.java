package com.quanying.app.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.quanying.app.R;
import com.quanying.app.bean.ShowRoomDetailsCommentsBean;
import com.quanying.app.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;

public class ShowRoomCommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ShowRoomDetailsCommentsBean mBean;
    private Context context;
    private String zpId ;

    public ShowRoomCommentsAdapter(ShowRoomDetailsCommentsBean list, Context context,String zpId) {
        this.mBean = list;
        this.context = context;
        this.zpId = zpId;
    }



    public void addAll(ShowRoomDetailsCommentsBean roomBean) {


        mBean.getData().addAll(roomBean.getData());
        notifyDataSetChanged();
    }

    public void refresh(ShowRoomDetailsCommentsBean roomBean) {
        this.mBean = roomBean;
        notifyDataSetChanged();
    }

 /*   public void addAll(List<ShowRoomBean.DataBean> list) {
        int lastIndex = this.mList.size();
        if (this.mList.addAll(list)) {
//            notifyItemMoved(lastIndex, list.size());
//            notifyItemInserted(lastIndex);
            notifyDataSetChanged();
        }
    }
*/

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


            return new ItemHolder(LayoutInflater.from(context).inflate(R.layout.item_pl,null));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

        final ItemHolder vh = (ItemHolder) viewHolder;
        Uri zoomviewurl = Uri.parse(mBean.getData().get(i).getFace());
        vh.head_Img.setImageURI(zoomviewurl);
        vh.message_Text.setText(mBean.getData().get(i).getContent());
        vh.time_Text.setText(mBean.getData().get(i).getTimeline());
        vh.name_Text.setText(mBean.getData().get(i).getNickname());

        final String ids = mBean.getData().get(i).getId();
        final String name = mBean.getData().get(i).getNickname();

        String repname = mBean.getData().get(i).getRepname();
        if(!TextUtils.isEmpty(repname)){

            vh.rep_name.setVisibility(View.VISIBLE);
            vh.rep_name.setText("回复 "+repname+":");

        }else{
            vh.rep_name.setVisibility(View.GONE);
        }


        vh.head_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MessageEvent event = new MessageEvent(ids+"");
                event.setStatus("send");
                event.setTitle(""+zpId);
                event.setContext(""+name);
                EventBus.getDefault().post(event);

            }
        });

    }

    @Override
    public int getItemCount() {
        if (mBean.getData().size() > 0) {
            return mBean.getData().size();
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


    class ItemHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView head_Img;
        TextView message_Text;
        TextView time_Text;
        TextView name_Text;
        TextView rep_name;




        public ItemHolder(View itemView) {
            super(itemView);
            head_Img = (SimpleDraweeView) itemView.findViewById(R.id.head_img);
            message_Text = (TextView) itemView.findViewById(R.id.message_text);
            time_Text = (TextView) itemView.findViewById(R.id.time_text);
            name_Text = (TextView) itemView.findViewById(R.id.name_text);
            rep_name = (TextView) itemView.findViewById(R.id.rep_name);
        }
    }


}
