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
import com.quanying.app.bean.GridListItem;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
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

        Picasso.with(mContext).load(item.getImage()).error(R.drawable.gift_img).transform(new PicassoRoundTransform()).into(holder.imageView_h);

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