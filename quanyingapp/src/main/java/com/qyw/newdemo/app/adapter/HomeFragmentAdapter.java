package com.qyw.newdemo.app.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qyw.newdemo.R;
import com.qyw.newdemo.app.entity.HomeFragmentEntity;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/9 0009.
 */

public class HomeFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<HomeFragmentEntity> mHomeList;

    private static final int HOLDER_TYPE_BANNER = 1;
    private static final int HOLDER_TYPE_DISPLAY = 2;
    private static final int HOLDER_TYPE_COMPANIES = 3;
    private static final int HOLDER_TYPE_EVENTS = 4;
    private static final int HOLDER_TYPE_ITEM = 5;

    //    构造
    public HomeFragmentAdapter(Context mContext, List<HomeFragmentEntity> mHomeList) {
        this.mContext = mContext;
        this.mHomeList = mHomeList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder = null;
        if (viewType == HOLDER_TYPE_BANNER) {
            view = LayoutInflater.from(mContext).
                    inflate(R.layout.home_item1, parent, false);
            holder = new BannerViewHolder(view);
        } else if (viewType == HOLDER_TYPE_DISPLAY) {
            view = LayoutInflater.from(mContext).
                    inflate(R.layout.home_item2, parent, false);
            holder = new DisplayViewHolder(view);
        } else if (viewType == HOLDER_TYPE_COMPANIES) {
            view = LayoutInflater.from(mContext).inflate(R.layout.home_item3, parent, false);
            holder = new CompaniesViewHolder(view);
        } else if (viewType == HOLDER_TYPE_EVENTS) {
            view = LayoutInflater.from(mContext).
                    inflate(R.layout.home_item4, parent, false);
            holder = new EventsViewHolder(view);
        } else if (viewType == HOLDER_TYPE_ITEM) {
            view = LayoutInflater.from(mContext).
                    inflate(R.layout.home_item5, parent, false);
            holder = new ItemHolder(view);
        }
        return holder;
    }


    //
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        HomeFragmentEntity mEntity = mHomeList.get(position);
//        holder.setIsRecyclable(false);
        if (holder instanceof BannerViewHolder) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            //设置图片加载器
            bannerViewHolder.view_Banner.setImageLoader(new GlideImageLoader());
            //设置图片集合
            List<String> images = mEntity.getBanner_Images();
//            images = mEntity.getBanner_Images();
            bannerViewHolder.view_Banner.setImages(images);
            //banner设置方法全部调用完毕时最后调用
            bannerViewHolder.view_Banner.start();
        } else if (holder instanceof DisplayViewHolder) {
            DisplayViewHolder mDisplayViewHolder = (DisplayViewHolder) holder;
            mDisplayViewHolder.display_Btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "优秀作品展", Toast.LENGTH_SHORT).show();
                }
            });
            mDisplayViewHolder.display_Btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "优秀案例作品", Toast.LENGTH_SHORT).show();
                }
            });
           /* Glide.with(mContext).load(entranceIconRes[position - 1]).
                    diskCacheStrategy(DiskCacheStrategy.ALL).into(entranceViewHolder.imageView);*/
            //  TODO 改
        } else if (holder instanceof CompaniesViewHolder) {
            CompaniesViewHolder mCompaniesViewHolder = (CompaniesViewHolder) holder;
            initRecyclerView(mCompaniesViewHolder);
            //  ItemViewHolder
        } else if (holder instanceof EventsViewHolder) {

            EventsViewHolder mEventViewHolder = (EventsViewHolder) holder;
            mEventViewHolder.findall_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "查看详情", Toast.LENGTH_SHORT).show();
                }
            });

        } else if (holder instanceof ItemHolder) {
//            homeitem_Title;
//            public TextView homeitem_Date;
//           public ImageView homeitem_Imgs;

            HomeFragmentEntity itemEntity = mHomeList.get(position-4);

            ItemHolder itemViewHolder = (ItemHolder) holder;
            itemViewHolder.homeitem_Title.setText(itemEntity.getEventsItemEventsTitles());
            itemViewHolder.homeitem_Date.setText(itemEntity.getEventsItemDate());
//            GlideApp.with(this).load("http://goo.gl/gEgYUd").into(imageView);
            Glide.with(mContext).load(itemEntity.getEventsItemImgs()).placeholder(R.mipmap.ic_launcher).into(itemViewHolder.homeitem_Imgs);
        }

    }

    /**
     * 设置横向RecyclerView
     *
     * @param mCompaniesViewHolder
     */
    private void initRecyclerView(CompaniesViewHolder mCompaniesViewHolder) {
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        mCompaniesViewHolder.mRecyclerView.setLayoutManager(manager);
        ArrayList<Integer> list = new ArrayList<>();
        list.add(R.mipmap.guide1);
        list.add(R.mipmap.guide2);
        list.add(R.mipmap.guide3);
        list.add(R.mipmap.guide_350_01);
        list.add(R.mipmap.guide_350_02);
        list.add(R.mipmap.guide_350_03);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(list);
        mCompaniesViewHolder.mRecyclerView.setAdapter(adapter);
        //  监听RecyclerView 滑动事件 当左滑超过1时 并且有数据需要添加时 进行数据添加
        mCompaniesViewHolder.mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dx > 0 && adapter.dataIsNotify) {
                    adapter.addData();
                }
            }
        });

    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            //  表明是Banner
            return HOLDER_TYPE_BANNER;
        }
        if (position == 1) {

            return HOLDER_TYPE_DISPLAY;
        }
        if (position == 2) {

            return HOLDER_TYPE_COMPANIES;
        }
        if (position == 3) {

            return HOLDER_TYPE_EVENTS;
        }

        return HOLDER_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mHomeList.size();
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    /*
    * banner适配
    * */
    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */
            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);

        }
    }

    /*
    * 首页banner
    * */
    static class BannerViewHolder extends RecyclerView.ViewHolder {
        public Banner view_Banner;

        public BannerViewHolder(View itemView) {
            super(itemView);
            view_Banner = (Banner) itemView.findViewById(R.id.banner);
        }
    }

    /*
    * 作品展
    * */
    static class DisplayViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout display_Btn1, display_Btn2;

        public DisplayViewHolder(View itemView) {
            super(itemView);
            display_Btn1 = (RelativeLayout) itemView.findViewById(R.id.display_btn1);
            display_Btn2 = (RelativeLayout) itemView.findViewById(R.id.display_btn2);
        }
    }

    /*
    * 推荐企业
    * */
    static class CompaniesViewHolder extends RecyclerView.ViewHolder {

        public TextView mSeeMoreBtn;
        public RecyclerView mRecyclerView;

        public CompaniesViewHolder(View itemView) {
            super(itemView);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.mItemRecyclerView);
        }
    }

    /*
    * 活动咨询
    * */
    static class EventsViewHolder extends RecyclerView.ViewHolder {

        public TextView findall_Btn;

        public EventsViewHolder(View itemView) {
            super(itemView);
            findall_Btn = (TextView) itemView.findViewById(R.id.findall_btn);
        }
    }

    /*
    * 活动列表
    * */
    static class ItemHolder extends RecyclerView.ViewHolder {

        public TextView homeitem_Title;
        public TextView homeitem_Date;
        public ImageView homeitem_Imgs;

        public ItemHolder(View itemView) {
            super(itemView);
            homeitem_Title = (TextView) itemView.findViewById(R.id.homeitem_title);
            homeitem_Date = (TextView) itemView.findViewById(R.id.homeitem_date);
            homeitem_Imgs = (ImageView) itemView.findViewById(R.id.homeitem_img);
        }
    }


    private class RecyclerViewAdapter extends RecyclerView.Adapter {
        public ArrayList<Integer> mList;
        public boolean isNotExistMore = false;
        public ArrayList<Integer> addList;
        public boolean dataIsNotify;

        public RecyclerViewAdapter(ArrayList<Integer> list) {
            mList = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
          /*  if (i == 0) {
                return new TextViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_textview_holder, viewGroup, false));
            } else if (i == mList.size() - 1) {
                //  末尾
                //  不存在 更多
                if (isNotExistMore) {
                    return new ImageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_imageview_holder, viewGroup, false));
                } else {
                    //  存在更多
                    return new TextViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_textview_holder, viewGroup, false));
                }

            } else {*/
                return new ImageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_imageview_holder, viewGroup, false));
//            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            if (viewHolder instanceof ImageViewHolder) {

                ImageViewHolder holder = (ImageViewHolder) viewHolder;
                Glide.with(mContext).load(mList.get(i)).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv);

            /*    TextViewHolder holder = (TextViewHolder) viewHolder;
                if (i == 0) {
                    holder.tv.setText("推荐企业");
                } else {

                    holder.tv.setText("查看更多");
                    holder.tv.setOnClickListener(v -> {

                        *//*ArrayList<Integer> list2 = new ArrayList<>();
                        list2.add(R.mipmap.guide1);
                        list2.add(R.mipmap.guide2);
                        list2.add(R.mipmap.guide3);
                        addDataReady(list2);*//*
                        Toast.makeText(mContext,"查看更多",Toast.LENGTH_SHORT).show();

                    });

                }*/
            }
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        //  addDataReady
        public void addDataReady(ArrayList<Integer> list) {
            //  首先移出最后一个元素
            mList.remove(mList.size() - 1);
            //  不存在显示更多 此时
            isNotExistMore = true;
            addList = list;
            //  证明现在左滑已经可以添加数据了
            dataIsNotify = true;
            notifyDataSetChanged();
        }

        //  addData
        public void addData() {
            //  直接Add就行了
            mList.addAll(addList);
            mList.add(1);
            dataIsNotify = false;
            isNotExistMore = false;
            notifyDataSetChanged();
        }

/*
        class TextViewHolder extends RecyclerView.ViewHolder {
            TextView tv;

            public TextViewHolder(View itemView) {
                super(itemView);
                Log.e("RedWolf", "TextViewHolder: ");
                tv = (TextView) itemView.findViewById(R.id.mTextViewHoldTv);
            }
        }
*/

        class ImageViewHolder extends RecyclerView.ViewHolder {
            ImageView iv;

            public ImageViewHolder(View itemView) {
                super(itemView);
                iv = (ImageView) itemView.findViewById(R.id.mImageViewHolderIv);
            }
        }
    }
}
