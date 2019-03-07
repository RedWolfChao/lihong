package com.quanying.app.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.quanying.app.R;
import com.quanying.app.bean.MyFocusBean;

import java.util.List;

public class MyFocusAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private MyFocusBean mBean;
    private List<MyFocusBean.DataBean> mList;
    private Context context;
    private static final int TYPE_NORMAL = 1;
    private OnItemClickListener mItemClickListener;


    public MyFocusAdapter(MyFocusBean list, Context context) {
        this.mList = list.getData();
        this.context = context;
    }


//
//    public void addAll(MyFocusBean roomBean) {
//        mList.addAll(roomBean.getData());
//    }

    public void addAll(List<MyFocusBean.DataBean> list) {
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

        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_myfans,null));


        viewHolder.item_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(view);
            }
        });

        viewHolder.item_btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mItemClickListener.onItemLongClick(v);
                return true;
            }
        });

            return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        final ViewHolder vh = (ViewHolder) viewHolder;
        vh .setIsRecyclable(false);
        final MyFocusBean.DataBean mBean = mList.get(i);
        vh.fans_name .setText(mBean.getNickname()+"");

        if(mBean.getSign()==null) {
            vh.fans_sign.setText("");
        }else {
            vh.fans_sign.setText(mBean.getSign() + "");
        }

        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.nowebimg);
        options.error(R.mipmap.nowebimg);

        RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
        roundingParams.setRoundAsCircle(true);
        vh.head_img.getHierarchy().setRoundingParams(roundingParams);

        Uri uri = Uri.parse(mBean.getFace());
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.evictFromMemoryCache(uri);
        imagePipeline.evictFromDiskCache(uri);

// combines above two lines
        imagePipeline.evictFromCache(uri);
        vh.head_img.setImageURI(uri);

        vh.item_btn.setTag(mBean.getUserid()+"");


/*
            vh.praise_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

          
                    OkHttpUtils
                            .post()
                            .url(WebUri.ZAN)
                            .addParams("token", MyApplication.getToken())
                            .addParams("id", mBean.getId())
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {

                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);

                                        if(jsonObject.getString("errcode").equals("200")){
                                            vh.praise_img.setClickable(false);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });

                }
            });
*/


    }

    @Override
    public int getItemCount() {
        if (mList.size() > 0) {
            return mList.size();
        }
        return 0;

    }

    public void updataUi(MyFocusBean uBean) {

        this.mList = uBean.getData();
        notifyDataSetChanged();

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
public static interface OnItemClickListener {
    void onItemClick(View view);
    void onItemLongClick(View view);
}


    class ViewHolder extends RecyclerView.ViewHolder{

        TextView fans_name;  //
        TextView fans_sign;  //
        SimpleDraweeView  head_img; //用户头像
        ImageView add_img; //
        RelativeLayout item_btn; //


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fans_name = itemView.findViewById(R.id.fans_name);
            fans_sign = itemView.findViewById(R.id.fans_sign);
            head_img = itemView.findViewById(R.id.head_img);
            add_img = itemView.findViewById(R.id.add_img);
            item_btn = itemView.findViewById(R.id.item_btn);

        }
    }


}
