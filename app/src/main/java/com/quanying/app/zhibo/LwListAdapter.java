package com.quanying.app.zhibo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.quanying.app.R;

import java.util.ArrayList;

public class LwListAdapter extends ArrayAdapter<GridListItem> {

    private Context mContext;
    private int layoutResourceId;
    private ArrayList<GridListItem> mGridData = new ArrayList<GridListItem>();
    private int isBuy = -1;
    private ViewHolder holder;
    private GridView mList;
    public LwListAdapter(Context context, int resource, ArrayList<GridListItem> objects,GridView mList) {
        super(context, resource, objects);
        this.mContext = context;
        this.layoutResourceId = resource;
        this.mGridData = objects;
        this.mList = mList;
    }

    public void setGridData(ArrayList<GridListItem> mGridData) {
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.lwlist_item, parent, false);
            holder = new ViewHolder();
            holder.gift_title_h = (TextView) convertView.findViewById(R.id.gift_title_h);
            holder.gift_price_h = (TextView) convertView.findViewById(R.id.gift_price_h);
            holder.imageView_h = (ImageView) convertView.findViewById(R.id.img_item_h);
            holder.is_buy_view_h = (ImageView) convertView.findViewById(R.id.is_buy_view_h);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GridListItem item = mGridData.get(position);
        holder.gift_title_h.setText(item.getTitle());
        holder.gift_price_h.setText(item.getPrice());
        //Picasso.with(mContext).load(item.getImage()).into(holder.imageView_h);

//        Picasso.with(mContext).load(item.getImage()).error(R.drawable.gift_img).transform(new PicassoRoundTransform()).into(holder.imageView_h);
        Glide.with(mContext)
                .load(item.getImage())
                .into(holder.imageView_h);
        if(item.getIsBuy()>0){
            holder.is_buy_view_h.setVisibility(View.VISIBLE);
        }else{
            holder.is_buy_view_h.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }



    private class ViewHolder {
        TextView gift_title_h;
        TextView gift_price_h;
        ImageView imageView_h;
        ImageView is_buy_view_h;
    }




}