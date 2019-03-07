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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.quanying.app.R;
import com.quanying.app.app.MyApplication;
import com.quanying.app.bean.MyFansBean;
import com.quanying.app.ui.user.UserHomePageActivity;
import com.quanying.app.weburl.WebUri;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;

public class MyFansAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private MyFansBean mBean;
    private List<MyFansBean.DataBean> mList;
    private Context context;
    private static final int TYPE_NORMAL = 1;


    public MyFansAdapter(MyFansBean list, Context context) {
        this.mList = list.getData();
        this.context = context;
    }


//
//    public void addAll(MyFansBean roomBean) {
//        mList.addAll(roomBean.getData());
//    }

    public void addAll(List<MyFansBean.DataBean> list) {
        int lastIndex = this.mList.size();
        if (this.mList.addAll(list)) {
//            notifyItemMoved(lastIndex, list.size());
//            notifyItemInserted(lastIndex);
            notifyDataSetChanged();
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_myfans,null));

            return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        final ViewHolder vh = (ViewHolder) viewHolder;
        vh .setIsRecyclable(false);
        final MyFansBean.DataBean mBean = mList.get(i);
        vh.fans_name .setText(mBean.getNickname()+"");
        vh.fans_sign.setText(mBean.getSign()+"");

        if(mBean.getHf().equals("one")){
            vh.add_img.setImageResource(R.mipmap.tianjiaguanzhu);
            vh.add_img.setClickable(true);
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

        vh.head_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String str = mBean.getUserid()+"";
                Intent intent2 = new Intent(context, UserHomePageActivity.class);
                intent2.putExtra("ids",str);
                Toast.makeText(context, ""+str, Toast.LENGTH_SHORT).show();
                context.startActivity(intent2);
            }
        });vh.fans_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = mBean.getUserid()+"";
                Intent intent2 = new Intent(context, UserHomePageActivity.class);
                intent2.putExtra("ids",str);
                Toast.makeText(context, ""+str, Toast.LENGTH_SHORT).show();
                context.startActivity(intent2);
            }
        });

//        vh.item_btn.setTag(mBean.getDataid());

        vh.add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!mBean.getHf().equals("one")){
                    return;
                }

                vh.add_img.setImageResource(R.mipmap.yitianjia);
                vh.add_img.setClickable(false);

                OkHttpUtils
                        .post()
                        .url(WebUri.ADDFOCUS)
                        .addParams("token", MyApplication.getToken())
                        .addParams("userid",mBean.getUserid()+"")
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
                                        vh.add_img.setClickable(false);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });

            }
        });


    }

    @Override
    public int getItemCount() {
        if (mList.size() > 0) {
            return mList.size();
        }
        return 0;

    }

    public void updataUi(MyFansBean uBean) {

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

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView fans_name;  //
        TextView fans_sign;  //
        SimpleDraweeView  head_img; //用户头像
        ImageView add_img; //


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fans_name = itemView.findViewById(R.id.fans_name);
            fans_sign = itemView.findViewById(R.id.fans_sign);
            head_img = itemView.findViewById(R.id.head_img);
            add_img = itemView.findViewById(R.id.add_img);



        }
    }

}