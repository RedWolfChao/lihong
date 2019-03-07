package com.quanying.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.quanying.app.R;
import com.quanying.app.bean.GridItem;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter<GridItem> {

    private Context mContext;
    private int layoutResourceId;
    private ArrayList<GridItem> mGridData = new ArrayList<GridItem>();
    private int isBuy = -1;
    private ViewHolder holder;
    private GridView mList;
    public GridViewAdapter(Context context, int resource, ArrayList<GridItem> objects,GridView mList) {
        super(context, resource, objects);
        this.mContext = context;
        this.layoutResourceId = resource;
        this.mGridData = objects;
        this.mList = mList;
    }

    public void setGridData(ArrayList<GridItem> mGridData) {
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.gift_item, parent, false);
            holder = new ViewHolder();
            holder.gift_title = (TextView) convertView.findViewById(R.id.gift_title);
            holder.gift_price = (TextView) convertView.findViewById(R.id.gift_price);
            holder.imageView = (ImageView) convertView.findViewById(R.id.img_item);
            holder.is_buy_view = (ImageView) convertView.findViewById(R.id.is_buy_view);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GridItem item = mGridData.get(position);
        holder.gift_title.setText(item.getTitle());
        holder.gift_price.setText(item.getPrice());
        //Picasso.with(mContext).load(item.getImage()).into(holder.imageView);

        Picasso.with(mContext).load(item.getImage()).error(R.drawable.gift_img).transform(new PicassoRoundTransform()).into(holder.imageView);

        if(item.getIsBuy()>0){
            holder.is_buy_view.setVisibility(View.VISIBLE);
        }else{
            holder.is_buy_view.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }


/*
    public void updateView(int itemIndex){
        int visiblePosition = mList.getFirstVisiblePosition();
        View v = mList.getChildAt(itemIndex - visiblePosition);
        ViewHolder viewHolder =(ViewHolder)v.getTag();
        if(viewHolder!= null){
            //viewHolder.titleTextView.setText("我更新了");
            //holder = (ViewHolder) view.getTag();
            viewHolder.is_buy_view.setVisibility(View.VISIBLE);
        }
    }
    public void cleanDateView(int itemIndex){
        int visiblePosition = mList.getFirstVisiblePosition();
        View v = mList.getChildAt(itemIndex - visiblePosition);
        ViewHolder viewHolder =(ViewHolder)v.getTag();
        if(viewHolder!= null){
            //viewHolder.titleTextView.setText("我更新了");
            //holder = (ViewHolder) view.getTag();
            viewHolder.is_buy_view.setVisibility(View.INVISIBLE);
        }
    }*/


    private class ViewHolder {
        TextView gift_title;
        TextView gift_price;
        ImageView imageView;
        ImageView is_buy_view;
    }

    public class PicassoRoundTransform implements Transformation {

        @Override
        public Bitmap transform(Bitmap source) {
            int widthLight = source.getWidth();
            int heightLight = source.getHeight();

            Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(output);
            Paint paintColor = new Paint();
            paintColor.setFlags(Paint.ANTI_ALIAS_FLAG);

            RectF rectF = new RectF(new Rect(0, 0, widthLight, heightLight));

            canvas.drawRoundRect(rectF, widthLight / 8, heightLight / 8, paintColor);

            Paint paintImage = new Paint();
            paintImage.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
            canvas.drawBitmap(source, 0, 0, paintImage);
            source.recycle();
            return output;
        }

        @Override
        public String key() {
            return "roundcorner";
        }



    }

}