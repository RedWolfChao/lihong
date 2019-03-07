package com.quanying.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quanying.app.R;
import com.quanying.app.bean.MessageBean;
import com.quanying.app.ui.message.OfficialMessageActivity;
import com.quanying.app.ui.message.ZpInteractiveActivity;
import com.quanying.app.ui.user.WebActivity;

import java.util.List;

public class MsgItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<MessageBean.DataBean> mList;
    private Context context;
    private OnItemClickListener mItemClickListener;

    private static final int HOLDER_TYPE_TITLE = 0;
    private static final int HOLDER_TYPE_ITEM = 1;


    public MsgItemAdapter(MessageBean list, Context context) {
        this.mList = list.getData();
        this.context = context;
    }


//
//    public void addAll(NewsBean roomBean) {
//        mList.addAll(roomBean.getData());
//    }

    public void addAll(List<MessageBean.DataBean> list) {
        int lastIndex = this.mList.size();
        if (this.mList.addAll(list)) {
//            notifyItemMoved(lastIndex, list.size());
//            notifyItemInserted(lastIndex);
            notifyDataSetChanged();
        }
    }


    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            //  表明是标题栏
            return HOLDER_TYPE_TITLE;
        }
        return HOLDER_TYPE_ITEM;
    }



    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        RecyclerView.ViewHolder holder = null;
        if (i == HOLDER_TYPE_TITLE) {
//            holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_title_message,null));
            view = LayoutInflater.from(context).
                    inflate(R.layout.item_title_message, viewGroup, false);
            holder = new TitleHolder(view);
        }else if( i == HOLDER_TYPE_ITEM){
            holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_msg,null));

        }
      /*  ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_msg,null));*/
            return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder instanceof TitleHolder){
            final  TitleHolder tv = (TitleHolder) viewHolder;
//            titleHolder .setIsRecyclable(false);
//            tv .setIsRecyclable(false);
            tv.xx_tz.setClickable(true);

//            tv.xx_tz_tv.setText("123123123123123123123");
            tv.xx_tz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context,OfficialMessageActivity.class));
                }
            });
            tv.xx_hd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context,ZpInteractiveActivity.class));
                }
            });
            tv.xx_hy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,WebActivity.class);
                    intent.putExtra("title","title");
                    intent.putExtra("urls","http://m.7192.com/home");
                    context.startActivity(intent);
                }
            });
            tv.xx_kf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,WebActivity.class);
                    intent.putExtra("title","title");
                    intent.putExtra("urls","http://m.7192.com/aboutus");
                    context.startActivity(intent);
                }
            });


        }else if (viewHolder instanceof ViewHolder) {
            final ViewHolder vh = (ViewHolder) viewHolder;
            vh .setIsRecyclable(false);
            final MessageBean.DataBean mBean  =mList.get(i);

            if(mBean.getTitle()==null){
                return;
            }
            vh.msg_title.setText(mBean.getTitle());
            vh.msg_time.setText(mBean.getTimeline());
            vh.msg_context.setText(mBean.getIntro());
            vh.jump_web.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,WebActivity.class);
                    intent.putExtra("title","详情");
                    intent.putExtra("urls",mBean.getLink());
                    context.startActivity(intent);
                }
            });
        }


    }

    @Override
    public int getItemCount() {

        return mList.size();

    }

/*    public void updataUi(NewsBean uBean) {

        this.mList = uBean.getData();
        notifyDataSetChanged();

    }*/

    public static interface OnItemClickListener {
        void onItemClick(View view);
        void onItemLongClick(View view);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView msg_title; //
        TextView msg_time; //
        TextView msg_context; //
        LinearLayout jump_web;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            msg_title = itemView.findViewById(R.id.msg_title);
            msg_time = itemView.findViewById(R.id.msg_time);
            msg_context = itemView.findViewById(R.id.msg_context);
            jump_web = itemView.findViewById(R.id.jump_web);

        }
    }
    class TitleHolder extends RecyclerView.ViewHolder{

        ImageView xx_tz;

        LinearLayout xx_hd;
        LinearLayout xx_hy;
        LinearLayout xx_kf;

        public TitleHolder(@NonNull View itemView) {
            super(itemView);

            xx_tz = itemView.findViewById(R.id.xx_tz);
            xx_hd = itemView.findViewById(R.id.xx_hd);
            xx_hy = itemView.findViewById(R.id.xx_hy);
            xx_kf = itemView.findViewById(R.id.xx_kf);




        }
    }

}
