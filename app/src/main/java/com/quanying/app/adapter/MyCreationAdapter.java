package com.quanying.app.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.quanying.app.R;
import com.quanying.app.app.MyApplication;
import com.quanying.app.bean.MyCreationBean;
import com.quanying.app.bean.UpBean;
import com.quanying.app.event.MessageEvent;
import com.quanying.app.ui.showroominsidepage.ShowRoominsideActivity;
import com.quanying.app.weburl.WebUri;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;

public class MyCreationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private MyCreationBean mBean;
    private List<UpBean> mList;
    private Context context;



    public MyCreationAdapter(List<UpBean> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }


//
//    public void addAll(MyCreationBean roomBean) {
//        mList.addAll(roomBean.getData());
//    }

    public void addAll(List<UpBean> list) {
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


                return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_zplist, viewGroup,false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


        if (viewHolder instanceof ViewHolder) {


            if (mList==null||mList.size()==0) {

                final ViewHolder vh = (ViewHolder) viewHolder;
                vh .setIsRecyclable(false);

                vh.userpage_ui.setVisibility(View.INVISIBLE);

                return;

            }

        final ViewHolder vh = (ViewHolder) viewHolder;
        vh .setIsRecyclable(false);


        final UpBean mBean = mList.get(i);

        Uri zoomviewurl = Uri.parse(mBean.getFace());


            vh.context_title.setText(mBean.getTitle());

        vh.context_time.setText(mBean.getAddtime()+"");
            vh.praise_text.setText(mBean.getPlnum());
            vh.production_text.setText("共"+mBean.getImgnum()+"张");
            vh.message_text.setText(mBean.getZannum());


            vh.jump_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ShowRoominsideActivity.class);
                   intent .putExtra("ids",mBean.getId());
                   context.startActivity(intent);
                }
            });


            vh.del_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doDelWorks(mBean);
                }
            });
            if(!mBean.getUserid().equals(MyApplication.getUserID())){

                vh.del_img.setVisibility(View.INVISIBLE);
                vh.del_text.setVisibility(View.INVISIBLE);
                vh.del_text.setClickable(false);
                vh.del_img.setClickable(false);

            }

            vh.del_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    doDelWorks(mBean);

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





        Log.e("zoula","lalalallalal");
            if(mBean.getImages().size()>0){
                int imgSize = mBean.getImages().size();
              /*  if(imgSize==1){
                    vh.img1.setVisibility(View.VISIBLE);
                    Uri img1url = Uri.parse(mBean.getImages().get(0).getThumb());
                    vh.img1.setImageURI(img1url);
//                    Glide.with(context).load(mBean.getImages().get(0)).into(vh.img1);
                }*/
                switch(imgSize){
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

    private void doDelWorks(final UpBean mBean) {

        // 创建构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // 设置参数
        builder.setTitle("提示")
                .setMessage("确定删除该作品")
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {// 积极

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {

                    }
                })
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {// 消极

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        // TODO Auto-generated method stub
                        OkHttpUtils
                                .post()
                                .url(WebUri.USERPAGEDEL)
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

                                                MessageEvent event = new MessageEvent(mBean.getId()+"");
                                                event.setStatus("gx");
                                                EventBus.getDefault().post(event);

                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });
                    }
                });
        builder.create().show();



    }

    @Override
    public int getItemCount() {

        if(mList == null){

            return  1;
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

    class NoViewHolder extends RecyclerView.ViewHolder{


        public NoViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder{


        TextView context_title; //标题头

        TextView context_time; //标题尾

         TextView praise_text; //评论数
         TextView production_text; //总张数
         TextView del_text; //
         ImageView praise_img; //点赞
         ImageView message_img; //评论
         ImageView del_img; //评论
        RelativeLayout userpage_ui; //评论


        SimpleDraweeView img1; //
        SimpleDraweeView img2; //
        SimpleDraweeView img3; //

         TextView message_text; //点赞数量
         View head_line; //
         LinearLayout jump_btn; //点击进入详情

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            head_line = itemView.findViewById(R.id.head_line);

            context_title = itemView.findViewById(R.id.context_title);
            context_time = itemView.findViewById(R.id.context_time);
            del_text = itemView.findViewById(R.id.del_text);


            praise_text = itemView.findViewById(R.id.praise_text);
            userpage_ui = itemView.findViewById(R.id.userpage_ui);
            production_text = itemView.findViewById(R.id.production_text);
            praise_img = itemView.findViewById(R.id.praise_img);
            message_img = itemView.findViewById(R.id.message_img);
            del_img = itemView.findViewById(R.id.del_img);
            jump_btn = itemView.findViewById(R.id.zpimg_ui);
            img1 = itemView.findViewById(R.id.img1);
            img2 = itemView.findViewById(R.id.img2);
            img3 = itemView.findViewById(R.id.img3);
            message_text = itemView.findViewById(R.id.message_text);

        }
    }

}
