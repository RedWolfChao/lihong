package com.quanying.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quanying.app.R;
import com.quanying.app.bean.SearchBean;
import com.quanying.app.ui.user.UserHomePageActivity;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

//    public void SearchAda
    private SearchBean mList;
    private Context mContext;

    private static final int HOLDER_TYPE_TITLE = 1;
    private static final int HOLDER_TYPE_ITEM = 2;

    public SearchAdapter(Context mContext, SearchBean mList) {
        this.mContext = mContext;
        this.mList = mList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder = null;
  /*
        }*/
        if (viewType == HOLDER_TYPE_TITLE) {
            view = LayoutInflater.from(mContext).
                    inflate(R.layout.item_search_title, parent, false);
            holder = new TitleHolder(view);
//            return new TitleHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_title, parent, false));
        }else if (viewType == HOLDER_TYPE_ITEM) {
            view = LayoutInflater.from(mContext).
                    inflate(R.layout.item_search, parent, false);
            holder = new ItemHolder(view);
//            return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false));

        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

//        SearchBean searchBean = mList.get(i);
        if (viewHolder instanceof TitleHolder) {
            TitleHolder mTitleHolder = (TitleHolder) viewHolder;
        }else if(viewHolder instanceof  ItemHolder){

            Log.e("shuju",mList.getData().get(i-1).toString());
            ItemHolder itemHolder = (ItemHolder) viewHolder;
            itemHolder.qy_name.setText(mList.getData().get(i-1).getNickname());
            String isQy = mList.getData().get(i-1).getHrtype();
            if(!isQy.equals("2")){
                itemHolder.qy_show.setVisibility(View.INVISIBLE);
            }
            itemHolder.check_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,UserHomePageActivity.class);
                    intent.putExtra("ids",mList.getData().get(i-1).getUid());
                    mContext.startActivity(intent);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mList.getData().size()+1;
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

        TextView qy_name;


        public TitleHolder(View itemView) {
            super(itemView);

        }
    }

    class ItemHolder extends  RecyclerView.ViewHolder{

        TextView qy_name;
        ImageView qy_show;
        LinearLayout check_btn;

        public ItemHolder(View itemView) {
            super(itemView);
            qy_name = (TextView) itemView.findViewById(R.id.qiyename);
            qy_show = itemView.findViewById(R.id.qy_show);
            check_btn = itemView.findViewById(R.id.check_btn);
        }
    }



}
