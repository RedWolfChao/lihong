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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.quanying.app.R;
import com.quanying.app.bean.SearchEndBean;
import com.quanying.app.ui.user.UserHomePageActivity;

public class SearchEndAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

//    public void SearchAda
    private SearchEndBean mList;
    private Context mContext;

    private static final int HOLDER_TYPE_ITEM = 1;

    public SearchEndAdapter(Context mContext, SearchEndBean mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        ItemHolder viewHolder = new ItemHolder(LayoutInflater.from(mContext).inflate(R.layout.item_search_end,null));

        return viewHolder;

    }

    @Override
    public int getItemCount() {
        if (mList.getData().size() > 0) {
            return mList.getData().size();
        }
        return 0;

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

            ItemHolder itemHolder = (ItemHolder) viewHolder;
            itemHolder.username.setText(mList.getData().get(i).getNickname());
            String isQy = mList.getData().get(i).getHrtype();
            if(!isQy.equals("2")){
                itemHolder.isqy.setVisibility(View.INVISIBLE);
            }
         RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
         roundingParams.setRoundAsCircle(true);
         Uri urls = Uri.parse(mList.getData().get(i).getFace());
         itemHolder.touxiang.getHierarchy().setRoundingParams(roundingParams);
         itemHolder.touxiang.setImageURI(urls);

         itemHolder.check_btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(mContext,UserHomePageActivity.class);
                 intent.putExtra("ids",mList.getData().get(i).getUid());
                 mContext.startActivity(intent);
             }
         });

     }



    class ItemHolder extends  RecyclerView.ViewHolder{

        TextView username;
        SimpleDraweeView touxiang;
        ImageView isqy;
        RelativeLayout check_btn;

        public ItemHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.username);
            touxiang = itemView.findViewById(R.id.touxiang);
            isqy = itemView.findViewById(R.id.isqy);
            check_btn = itemView.findViewById(R.id.check_btn);
        }
    }



}
