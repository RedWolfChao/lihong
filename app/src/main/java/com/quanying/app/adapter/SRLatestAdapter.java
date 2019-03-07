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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.quanying.app.R;
import com.quanying.app.app.MyApplication;
import com.quanying.app.bean.ShowRoomBean;
import com.quanying.app.ui.showroominsidepage.ShowRoominsideActivity;
import com.quanying.app.ui.user.UserHomePageActivity;
import com.quanying.app.weburl.WebUri;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;

public class SRLatestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ShowRoomBean mBean;
    private List<ShowRoomBean.DataBean> mList;
    private Context context;
    private static final int TYPE_NORMAL = 1;


    public SRLatestAdapter(ShowRoomBean list,Context context) {
        this.mList = list.getData();
        this.context = context;
    }


//
//    public void addAll(ShowRoomBean roomBean) {
//        mList.addAll(roomBean.getData());
//    }

    public void addAll(List<ShowRoomBean.DataBean> list) {
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


            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_showroom,viewGroup,false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        final ViewHolder vh = (ViewHolder) viewHolder;
        vh .setIsRecyclable(false);
        final ShowRoomBean.DataBean mBean = mList.get(i);
        vh.name_text.setText(mBean.getNickname());
        Uri zoomviewurl = Uri.parse(mBean.getFace());
        vh.head_img.setImageURI(zoomviewurl);

        vh.head_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,UserHomePageActivity.class);
                intent.putExtra("ids",mBean.getUserid());
                context.startActivity(intent);
            }
        });

        if(mBean.getTp().equals("1")){

            vh.userTag.setText("个人");
            vh.userTag.setBackgroundResource(R.color.colorBackground);

        }else{
            vh.userTag.setText("企业");
            vh.userTag.setBackgroundResource(R.color.loginbtn);
        }
            vh.context_title.setText(mBean.getTitle());
            vh.context_btext.setText(mBean.getDsp());
            vh.time_text.setText(mBean.getAddtime());
            vh.praise_text.setText(mBean.getPlnum());
            vh.production_text.setText("共"+mBean.getImgnum()+"张");
            vh.message_text.setText(mBean.getZannum());
            if(mBean.getZaned().equals("1")){
                vh.praise_img.setImageResource(R.mipmap.zan_c);
                vh.praise_img.setClickable(false);
            }

            vh.jump_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ShowRoominsideActivity.class);
                   intent .putExtra("ids",mBean.getId());
                   context.startActivity(intent);
                }
            });

            vh.praise_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ShowRoominsideActivity.class);
                   intent .putExtra("ids",mBean.getId());
                   context.startActivity(intent);
                }
            }); vh.message_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ShowRoominsideActivity.class);
                   intent .putExtra("ids",mBean.getId());
                   context.startActivity(intent);
                }
            });




            vh.praise_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int zanNub = Integer.parseInt(mBean.getZannum())+1;

                    vh.message_text.setText(zanNub+"");
                    vh.praise_img.setImageResource(R.mipmap.zan_c);

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
            if(mBean.getImages()!=null) {
                if (mBean.getImages().size() > 0) {
                    int imgSize = mBean.getImages().size();
              /*  if(imgSize==1){
                    vh.img1.setVisibility(View.VISIBLE);
                    Uri img1url = Uri.parse(mBean.getImages().get(0).getThumb());
                    vh.img1.setImageURI(img1url);
//                    Glide.with(context).load(mBean.getImages().get(0)).into(vh.img1);
                }*/
                    switch (imgSize) {
                        case 1:

//                        Glide.with(context).load(mBean.getImages().get(0).getThumb()).into(vh.img1);
                            Uri img1url = Uri.parse(mBean.getImages().get(0).getThumb());
                            vh.img1.setImageURI(img1url);

                            vh.img2.setVisibility(View.INVISIBLE);
                            vh.img3.setVisibility(View.INVISIBLE);
                            break;
                        case 2:

//                        Glide.with(context).load(mBean.getImages().get(1).getThumb()).into(vh.img2);
                            Uri img2url = Uri.parse(mBean.getImages().get(1).getThumb());
                            vh.img2.setImageURI(img2url);
                            Uri img1urls1 = Uri.parse(mBean.getImages().get(0).getThumb());
                            vh.img1.setImageURI(img1urls1);

                            vh.img3.setVisibility(View.INVISIBLE);
                            break;
                        case 3:

//                        Glide.with(context).load(mBean.getImages().get(2).getThumb()).into(vh.img3);
                            Uri img3url = Uri.parse(mBean.getImages().get(2).getThumb());
                            vh.img3.setImageURI(img3url);

                            Uri img2url1 = Uri.parse(mBean.getImages().get(1).getThumb());
                            vh.img2.setImageURI(img2url1);

                            Uri img1url2 = Uri.parse(mBean.getImages().get(0).getThumb());
                            vh.img1.setImageURI(img1url2);

                            break;
                    }


                }
            }

    }

    @Override
    public int getItemCount() {
        if(null == mList){

            return 0;

        }
        if (mList.size() > 0) {
            return mList.size();
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


    class ViewHolder extends RecyclerView.ViewHolder{

        TextView userTag;  //用户标签
        SimpleDraweeView  head_img; //用户头像
        TextView name_text; //用户名字
        TextView context_title; //标题头
        TextView context_btext; //标题尾
         TextView time_text; //上传时间
         TextView praise_text; //评论数
         TextView production_text; //总张数
         ImageView praise_img; //点赞
         ImageView message_img; //评论
        SimpleDraweeView img1; //
        SimpleDraweeView img2; //
        SimpleDraweeView img3; //
         TextView message_text; //点赞数量
         View head_line; //点赞数量
         LinearLayout jump_btn; //点击进入详情

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userTag = itemView.findViewById(R.id.userTag);
            head_line = itemView.findViewById(R.id.head_line);
            head_img = itemView.findViewById(R.id.head_img);
            name_text = itemView.findViewById(R.id.name_text);
            context_title = itemView.findViewById(R.id.context_title);
            context_btext = itemView.findViewById(R.id.context_btext);
            time_text = itemView.findViewById(R.id.time_text);
            praise_text = itemView.findViewById(R.id.praise_text);
            production_text = itemView.findViewById(R.id.production_text);
            praise_img = itemView.findViewById(R.id.praise_img);
            message_img = itemView.findViewById(R.id.message_img);
            jump_btn = itemView.findViewById(R.id.linearLayout);
            img1 = itemView.findViewById(R.id.img1);
            img2 = itemView.findViewById(R.id.img2);
            img3 = itemView.findViewById(R.id.img3);
            message_text = itemView.findViewById(R.id.message_text);

        }
    }

}
