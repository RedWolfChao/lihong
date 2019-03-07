package com.qyw.newdemo.app.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.widget.PullZoomView;
import com.lzy.widget.manager.ExpandLinearLayoutManager;
import com.qyw.newdemo.R;
import com.qyw.newdemo.app.base.SlidingActivity;
import com.qyw.newdemo.app.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserHomePageActivity extends SlidingActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView ;
    @BindView(R.id.pzv)
    PullZoomView pzv ;
//    顶部标题
    private View titleBar_Bg;
    private TextView titleBar_title;
    private View status_bar_fix;
    private View titleBar;



    @Override
    protected int getLayoutResId() {
        return R.layout.activity_userhomepage;
    }

    @Override
    protected void initData() {

        ButterKnife.bind(this);

        initView();

    }

    private void initView() {

        titleBar = findViewById(R.id.titleBar);
        titleBar_Bg = titleBar.findViewById(R.id.bg);
        //当状态栏透明后，内容布局会上移，这里使用一个和状态栏高度相同的view来修正内容区域
        status_bar_fix = titleBar.findViewById(R.id.status_bar_fix);
        status_bar_fix.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.getStatusHeight(context)));
        /*
        * 设置透明标题
        * */
        titleBar_title = (TextView) titleBar.findViewById(R.id.title);
        titleBar_Bg.setAlpha(0);
        status_bar_fix.setAlpha(0);
        titleBar_title.setText("标题栏透明度(0%)");

        ExpandLinearLayoutManager ms= new ExpandLinearLayoutManager(this);
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为横向布局
        recyclerView.setLayoutManager(new ExpandLinearLayoutManager(this));
//        recyclerView.setAdapter(new MyAdapter());

        float sensitive =  1.5f;
        int zoomTime = 700;
        boolean isParallax =true;
        boolean isZoomEnable = true;

        pzv.setIsParallax(isParallax);
        pzv.setIsZoomEnable(isZoomEnable);
        pzv.setSensitive(sensitive);
        pzv.setZoomTime(zoomTime);
        pzv.setOnScrollListener(new PullZoomView.OnScrollListener() {
            @Override
            public void onScroll(int l, int t, int oldl, int oldt) {
                System.out.println("onScroll   t:" + t + "  oldt:" + oldt);

//                Log.d("gaodu",""+Math.abs(totalDy)+" X "+dx);


            }

            @Override
            public void onHeaderScroll(int currentY, int maxY) {
                System.out.println("onHeaderScroll   currentY:" + currentY + "  maxY:" + maxY);
                float alpha = 1.0f * currentY / maxY;
                titleBar_Bg.setAlpha(alpha);
                //注意头部局的颜色也需要改变
                status_bar_fix.setAlpha(alpha);
                titleBar_title.setText("标题栏透明度(" + (int) (alpha * 100) + "%)");
            }

            @Override
            public void onContentScroll(int l, int t, int oldl, int oldt) {
                System.out.println("onContentScroll   t:" + t + "  oldt:" + oldt);
            }
        });
        pzv.setOnPullZoomListener(new PullZoomView.OnPullZoomListener() {
            @Override
            public void onPullZoom(int originHeight, int currentHeight) {
                System.out.println("onPullZoom  originHeight:" + originHeight + "  currentHeight:" + currentHeight);
            }

            @Override
            public void onZoomFinish() {
                System.out.println("onZoomFinish");
            }
        });

    }

  /*  private class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private ArrayList<String> strings;

        public MyAdapter() {
            strings = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                strings.add("条目：" + i);
            }
        }

        @Override
        public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SimpleViewHolder(View.inflate(getApplicationContext(), android.R.layout.simple_list_item_1, null));
        }

        @Override
        public void onBindViewHolder(SimpleViewHolder holder, int position) {
            holder.bindData(position);
        }

        @Override
        public int getItemCount() {
            return strings.size();
        }

        protected class SimpleViewHolder extends RecyclerView.ViewHolder {

            TextView textView;

            public SimpleViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView;
            }

            public void bindData(int position) {
                textView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
                textView.setText(strings.get(position));
                textView.setTextColor(Color.WHITE);
//                textView.setBackgroundColor(ColorUtil.generateBeautifulColor());
            }
        }
    }*/
}
